package com.art.game.net.packet;

import com.art.game.net.GameClient;
import com.art.game.net.GameServer;

public class Packet00Login extends Packet {
	
	private String nomeUsuario;
	private int x, y;

	public Packet00Login(byte[] data) {
		super(00);
		String[] array = lerDados(data).split(",");
		this.nomeUsuario = array[0];
		this.x = Integer.parseInt(array[1]);
		this.y = Integer.parseInt(array[2]);
	}
	
	public Packet00Login(String username, int x, int y) {
		super(00);
		this.nomeUsuario = username;
		this.x = x;
		this.y = y;
	}

	@Override
	public void escrever(GameClient client) {
		client.enviar(getData());
	}

	
	@Override
	public void escrever(GameServer server) {
		server.enviarParaTodosClientes(getData());
	}
	
	@Override
	public byte[] getData() {
		return ("00" + this.nomeUsuario + "," + getX() + "," + getY()).getBytes();
	}

	public String getNomeUsuario() {
		return this.nomeUsuario;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
