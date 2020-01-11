package com.art.game.entities;

import com.art.game.graphics.Colors;
import com.art.game.graphics.Screen;
import com.art.game.input.InputKeyboard;
import com.art.game.level.Level;

public class Player extends Mob {

	private InputKeyboard input;
	private int cor = Colors.get(-1, 111, 145, 543);
	private int escala = 1;
	
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
		int velocidade = 4;
		int girarTopo = (numeroPassos >> velocidade) & 1;
		int girarBaixo = (numeroPassos >> velocidade) & 1;
		
		if (direcao == 1) xTile += 2;
		
		else if (direcao > 1) {
			xTile += 4 + ((numeroPassos >> velocidade) & 1) * 2;
			girarTopo = (direcao - 1) % 2;
		}
		
		int modificador = 8 * escala;
		int xOffSet = x - modificador / 2;
		int yOffSet = y - modificador / 2 - 4;
		
		tela.renderizar(xOffSet + (modificador * girarTopo), yOffSet , xTile + yTile * 32, cor, girarTopo, escala);
		tela.renderizar(xOffSet + modificador - (modificador * girarTopo), yOffSet , (xTile + 1) + yTile * 32, cor, girarTopo, escala);
		tela.renderizar(xOffSet + (modificador * girarBaixo), yOffSet + modificador, xTile + (yTile + 1)* 32, cor, girarBaixo, escala);
		tela.renderizar(xOffSet + modificador - (modificador * girarBaixo), yOffSet + modificador , (xTile + 1) + (yTile + 1)* 32, cor, girarBaixo, escala);
		
	}

	@Override
	public boolean colidiu(int xA, int yA) {
		int xMinimo = 0, xMaximo = 7;
		int yMinimo = 3, yMaximo = 7;
		
		for (int x = xMinimo; x < xMaximo; x ++) {
			if (tileSolido(xA, yA, x, yMinimo)) {
				return true;
			}
		}
		
		for (int x = xMinimo; x < xMaximo; x ++) {
			if (tileSolido(xA, yA, x, yMaximo)) {
				return true;
			}
		}
		
		for (int y = yMinimo; y < yMaximo; y ++) {
			if (tileSolido(xA, yA, xMinimo, y)) {
				return true;
			}
		}
		
		for (int y = yMinimo; y < yMaximo; y ++) {
			if (tileSolido(xA, yA, xMaximo, y)) {
				return true;
			}
		}
		
		return false;
	}
}
