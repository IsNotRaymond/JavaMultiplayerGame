package com.art.game.entities;

import com.art.game.graphics.Colors;
import com.art.game.graphics.Font;
import com.art.game.graphics.Screen;
import com.art.game.input.InputKeyboard;
import com.art.game.level.Level;

public class Player extends Mob {

	private InputKeyboard input;
	private int cor = Colors.get(-1, 111, 145, 543);
	private int escala = 1;
	protected boolean nadando = false;
	private int contaUpdates = 0;
	
	private String nomeUsuario;
	
	public Player(Level level, int x, int y, InputKeyboard input, String nomeUsuario) {
		super(level, "Player", x, y, 1);
		this.input = input;
		this.nomeUsuario = nomeUsuario;
	}
	
	@Override
	public void update() {
		contaUpdates ++;
		
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
		
		if (level.getTile(this.x >> 3, this.y >> 3).getID() == 3) {
			nadando = true;
		} 
		
		if (nadando && level.getTile(this.x >> 3, this.y >> 3).getID() != 3) {
			nadando = false;
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
		
		if (nadando) {
			int corAgua;
			yOffSet += 4;
			
			if (contaUpdates % 60 < 15) {
				corAgua = Colors.get(-1, -1, 225, -1);
			} else if (15 <= contaUpdates % 60 && contaUpdates % 60 < 30) {
				yOffSet -= 1;
				corAgua = Colors.get(-1, 225, 115, -1);
			} else if (30 <= contaUpdates % 60 && contaUpdates % 60 < 45) {
				yOffSet -= 1;
				corAgua = Colors.get(-1, 115, -1, 225);
			} else {
				corAgua = Colors.get(-1, 225, 115, -1);
			}
			
			tela.renderizar(xOffSet, yOffSet + 3, 0 + 27 * 32, corAgua, 0x00, 1);
			tela.renderizar(xOffSet + 8, yOffSet + 3, 0 + 27 * 32, corAgua, 0x01, 1);
		}
		
		tela.renderizar(xOffSet + (modificador * girarTopo), yOffSet , xTile + yTile * 32, cor, girarTopo, escala);
		tela.renderizar(xOffSet + modificador - (modificador * girarTopo), yOffSet , (xTile + 1) + yTile * 32, cor, girarTopo, escala);
		
		if (!nadando) {
			tela.renderizar(xOffSet + (modificador * girarBaixo), yOffSet + modificador, xTile + (yTile + 1)* 32, cor, girarBaixo, escala);
			tela.renderizar(xOffSet + modificador - (modificador * girarBaixo), yOffSet + modificador , (xTile + 1) + (yTile + 1)* 32, cor, girarBaixo, escala);
		}
		
		if (nomeUsuario != null) {
			Font.renderizar(nomeUsuario, tela, xOffSet - ((nomeUsuario.length() - 1) / 2 * 8), yOffSet - 10, Colors.get(-1, -1, -1, 555), 1);
		}

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
