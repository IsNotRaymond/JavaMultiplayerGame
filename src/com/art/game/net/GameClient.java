package com.art.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.art.game.Game;
import com.art.game.entities.PlayerMP;
import com.art.game.net.packet.Packet;
import com.art.game.net.packet.Packet.PacketTypes;
import com.art.game.net.packet.Packet00Login;
import com.art.game.net.packet.Packet01Desconectar;
import com.art.game.net.packet.Packet02Mover;

public class GameClient extends Thread {
	private final int MAX_DATAGRAM_PACKET = 1024;
	
	private InetAddress enderecoIP;
	private DatagramSocket socket;
	private Game game;
	
	public GameClient(Game game, String host) {
		this.game = game;
		
		try {
			this.socket = new DatagramSocket();
			this.enderecoIP = InetAddress.getByName(host);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			byte[] data = new byte[MAX_DATAGRAM_PACKET];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
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
				controlarLogin((Packet00Login) packet, enderecoIp, porta);
				
				break;
			case DESCONECTAR:
				packet = new Packet01Desconectar(data);
	            System.out.println("[" + enderecoIp.getHostAddress() + ":" + porta + "] "
	                    + ((Packet01Desconectar) packet).getNomeUsuario() + " deixou o jogo ...");
	            game.level.removePlayerMP(((Packet01Desconectar) packet).getNomeUsuario());
				break;
			case MOVER:
				packet = new Packet02Mover(data);
				controlarMovimento((Packet02Mover) packet);
		}
	}
	
	public void controlarMovimento(Packet02Mover packet) {
		this.game.level.moverJogador(packet.getNomeUsuario(), packet.getX(), packet.getY(),
				packet.getNumeroPassos(), packet.caminhando(), packet.getDirecao());
	}
	
	private void controlarLogin(Packet00Login packet, InetAddress enderecoIp, int porta) {
		System.out.println("[" + enderecoIp.getHostAddress() + ":" + porta + "] " + packet.getNomeUsuario()
        + " entrou no jogo ...");
		
		PlayerMP player = new PlayerMP(game.level, packet.getX(), packet.getY(), packet.getNomeUsuario(), enderecoIp, porta);
		
		game.level.addEntity(player);
	}

	public void enviar(byte[] data) {
		if (!game.applet) {
			DatagramPacket packet = new DatagramPacket(data, data.length, enderecoIP, 1331);
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
