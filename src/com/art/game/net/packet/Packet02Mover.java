package com.art.game.net.packet;

import com.art.game.net.GameClient;
import com.art.game.net.GameServer;

public class Packet02Mover extends Packet {
	private String nomeUsuario;
	private int x, y;
	
	private int numeroPassos = 0;
    private boolean caminhando;
    private int direcao = 1;
	
	public Packet02Mover(byte[] data) {
		super(02);
		String[] array = lerDados(data).split(",");
		nomeUsuario = array[0];
		this.x = Integer.parseInt(array[1]);
		this.y = Integer.parseInt(array[2]);
		this.numeroPassos = Integer.parseInt(array[3]);
		this.caminhando = Integer.parseInt(array[4]) == 1;
		this.direcao = Integer.parseInt(array[5]);
	}
	
	public Packet02Mover(String nomeUsuario, int x, int y, int numeroPassos, boolean caminhando, int direcao) {
		super(02);
		this.nomeUsuario = nomeUsuario;
		this.x = x;
		this.y = y;
		this.numeroPassos = numeroPassos;
		this.caminhando = caminhando;
		this.direcao = direcao;
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
		return ("02" + this.nomeUsuario + "," + this.x + "," + this.y + "," + this.numeroPassos + "," + (caminhando ? 1 : 0)
	                + "," + this.direcao).getBytes();

	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getNumeroPassos() {
        return numeroPassos;
    }

    public boolean caminhando() {
        return caminhando;
    }

    public int getDirecao() {
        return direcao;
    }
	
}
