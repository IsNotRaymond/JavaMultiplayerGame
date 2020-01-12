package com.art.game.net.packet;

import com.art.game.net.GameClient;
import com.art.game.net.GameServer;

public class Packet01Desconectar extends Packet {
	private String nomeUsuario;
	
	public Packet01Desconectar(byte[] data) {
		super(01);
		nomeUsuario = lerDados(data);
	}
	
	public Packet01Desconectar(String nomeUsuario) {
		super(01);
		this.nomeUsuario = nomeUsuario;
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
		return ("01" + nomeUsuario).getBytes();
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

}
