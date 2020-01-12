package com.art.game.input;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.art.game.Game;
import com.art.game.net.packet.Packet01Desconectar;

public class InputWindow implements WindowListener {

	private final Game game;
	
	public InputWindow(Game game) {
		this.game = game;
		this.game.frame.addWindowListener(this);
	}
	
	@Override
	public void windowActivated(WindowEvent event) { }

	@Override
	public void windowClosed(WindowEvent event) { }

	@Override
	public void windowClosing(WindowEvent event) { 
		Packet01Desconectar packet = new Packet01Desconectar(this.game.jogador.getNomeUsuario());
		packet.escrever(this.game.cliente);
	}

	@Override
	public void windowDeactivated(WindowEvent event) { }

	@Override
	public void windowDeiconified(WindowEvent event) { }

	@Override
	public void windowIconified(WindowEvent event) { }

	@Override
	public void windowOpened(WindowEvent event) { }

}
