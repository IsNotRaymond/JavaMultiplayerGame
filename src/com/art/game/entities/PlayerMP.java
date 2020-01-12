package com.art.game.entities;

import java.net.InetAddress;

import com.art.game.input.InputKeyboard;
import com.art.game.level.Level;

public class PlayerMP extends Player {
	
	public InetAddress enderecoIp;
	public int porta;

	public PlayerMP(Level level, int x, int y, InputKeyboard input, String nomeUsuario, InetAddress enderecoIp, int porta) {
		super(level, x, y, input, nomeUsuario);
		this.enderecoIp = enderecoIp;
		this.porta = porta;
	}
	
	public PlayerMP(Level level, int x, int y, String nomeUsuario, InetAddress enderecoIp, int porta) {
		super(level, x, y, null, nomeUsuario);
		this.enderecoIp = enderecoIp;
		this.porta = porta;
	}
	
	@Override
	public void update() {
		super.update();
	}

}
