package com.art.game.entities;

import com.art.game.graphics.Colors;
import com.art.game.graphics.Screen;
import com.art.game.input.InputKeyboard;
import com.art.game.level.Level;

public class Player extends Mob {

	private InputKeyboard input;
	private int cor = Colors.get(-1, 111, 145, 543);
	
	public Player(Level level, int x, int y, InputKeyboard input) {
		super(level, "Player", x, y, 1);
		this.input = input;
		
	}
	
	@Override
	public void update() {
		int xA = 0, yA = 0;
		
		if (input.cima.pressionado()) yA --;
		if (input.baixo.pressionado()) yA ++;
		if (input.direita.pressionado()) xA ++;
		if (input.esquerda.pressionado()) xA --;
		
		if (xA != 0 || yA != 0) {
			mover(xA, yA);
			caminhando = true;
		} else {
			caminhando = false;
		}
	}

	@Override
	public void renderizar(Screen tela) {
		int xTile = 0;
		int yTile = 28;
		
		int modificador = 8 * escala;
		int xOffSet = x - modificador / 2;
		int yOffSet = y - modificador / 2 - 4;
		
		tela.renderizar(xOffSet, yOffSet , xTile + yTile * 32, cor);
		tela.renderizar(xOffSet + modificador, yOffSet , (xTile + 1) + yTile * 32, cor);
		tela.renderizar(xOffSet, yOffSet + modificador, xTile + (yTile + 1)* 32, cor);
		tela.renderizar(xOffSet + modificador, yOffSet + modificador , (xTile + 1) + (yTile + 1)* 32, cor);
		
	}

	@Override
	public boolean colidiu(int xA, int yA) {
		// TODO Auto-generated method stub
		return false;
	}
}
