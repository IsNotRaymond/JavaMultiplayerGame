package com.art.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.art.game.Game;
import com.art.game.entities.PlayerMP;
import com.art.game.net.packet.Packet;
import com.art.game.net.packet.Packet.PacketTypes;
import com.art.game.net.packet.Packet00Login;
import com.art.game.net.packet.Packet01Desconectar;
import com.art.game.net.packet.Packet02Mover;

public class GameServer extends Thread {
	private final int MAX_DATAGRAM_PACKET = 1024;
	
	private DatagramSocket socket;
	private Game game;
	private List<PlayerMP> jogadoresConectados = new ArrayList<PlayerMP>();
	
	public GameServer(Game game) {
		this.game = game;
		
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			byte[] dados = new byte[MAX_DATAGRAM_PACKET];
			DatagramPacket packet = new DatagramPacket(dados, dados.length);
			
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.processarPacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void processarPacket(byte[] data, InetAddress enderecoIp, int porta) {
		String mensagem = new String(data).trim();
		PacketTypes tipo = Packet.verificarPacket(Integer.parseInt(mensagem.substring(0, 2)));
		Packet packet = null;
		
		switch (tipo) {
			default:
			case INVALIDO:
				break;
			case LOGIN:
				packet = new Packet00Login(data);
				System.out.println("[" + enderecoIp.getHostAddress() + ":" + porta + "] "
	                    + ((Packet00Login) packet).getNomeUsuario() + " conectou ...");			
				
				PlayerMP jogador = new PlayerMP(game.level, 100, 100, ((Packet00Login) packet).getNomeUsuario(), enderecoIp, porta);
				this.adicionarConexao(jogador, ((Packet00Login) packet));
				
				break;
			case DESCONECTAR:
				packet = new Packet01Desconectar(data);				
				System.out.println("[" + enderecoIp.getHostAddress() + ":" + porta + "] "
	                    + ((Packet01Desconectar) packet).getNomeUsuario() + " saiu ...");
				
				this.removerConexao(((Packet01Desconectar) packet));
				
				break;
			case MOVER:
				packet = new Packet02Mover(data);
				controlarMovimento((Packet02Mover) packet);
		}
	}

	private void controlarMovimento(Packet02Mover packet) {
		if (getPlayerMP(packet.getNomeUsuario()) != null) {
			int indice = getIndicePlayerMP(packet.getNomeUsuario());
			
			this.jogadoresConectados.get(indice).x = packet.getX();
			this.jogadoresConectados.get(indice).y = packet.getY();
			packet.escrever(this);
		}
	}

	public void removerConexao(Packet01Desconectar packet) {
		this.jogadoresConectados.remove(getIndicePlayerMP(packet.getNomeUsuario()));
		
		packet.escrever(this);
	}
	
	public PlayerMP getPlayerMP(String nomeUsuario) {
		for (PlayerMP p : this.jogadoresConectados) {
			if (p.getNomeUsuario().equals(nomeUsuario)) {
				return p;
			}
		}
		return null;
	}
	
	public int getIndicePlayerMP(String nomeUsuario) {
		int indice = 0;
		
		for (PlayerMP player : this.jogadoresConectados) {
			if (player.getNomeUsuario().equals(nomeUsuario)) {
				break;
			}
			
			indice ++;
		}
		
		return indice;
	}

	public void adicionarConexao(PlayerMP jogador, Packet00Login packet) {
		boolean foiConectado = false;
		
		for (PlayerMP p : this.jogadoresConectados) {
			if (jogador.getNomeUsuario().equalsIgnoreCase(p.getNomeUsuario())) {
				if (p.enderecoIp == null) {
					p.enderecoIp = jogador.enderecoIp;
				}
				
				if (p.porta == -1) {
					p.porta = jogador.porta;
				}
				
				foiConectado = true;
			} else {
				// mostra para o player conectado que apareceu um novo player
				enviar(packet.getData(), p.enderecoIp, p.porta);
				
				// mostra para o novo player que o player conectado existe
				packet = new Packet00Login(p.getNomeUsuario(), p.x, p.y);
				enviar(packet.getData(), jogador.enderecoIp, jogador.porta);
			}
		}
		
		if (!foiConectado) {
			this.jogadoresConectados.add(jogador);
		}
	}

	public void enviar(byte[] data, InetAddress enderecoIp, int porta) {
		DatagramPacket packet = new DatagramPacket(data, data.length, enderecoIp, porta);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enviarParaTodosClientes(byte[] data) {
		for (PlayerMP p : jogadoresConectados) {
			enviar(data, p.enderecoIp, p.porta);
		}
		
	}
}
