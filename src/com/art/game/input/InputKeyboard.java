package com.art.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.art.game.Game;

public class InputKeyboard implements KeyListener {
	
	public Key cima = Key.CIMA;
	public Key baixo = Key.BAIXO;
	public Key esquerda = Key.ESQUERDA;
	public Key direita = Key.DIREITA;
	
	public InputKeyboard(Game game) {
		game.addKeyListener(this);
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		alternar(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		alternar(e.getKeyCode(), false);
		
	}
	
	public void alternar(int codigo, boolean pressionado) {
		
		if (codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) cima.alternar(pressionado);
		if (codigo == KeyEvent.VK_S || codigo == KeyEvent.VK_DOWN) baixo.alternar(pressionado);
		if (codigo == KeyEvent.VK_A || codigo == KeyEvent.VK_LEFT) esquerda.alternar(pressionado);
		if (codigo == KeyEvent.VK_D || codigo == KeyEvent.VK_RIGHT) direita.alternar(pressionado);
		
	}
}
