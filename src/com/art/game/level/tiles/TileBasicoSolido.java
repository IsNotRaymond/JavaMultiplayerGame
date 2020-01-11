package com.art.game.level.tiles;

public class TileBasicoSolido extends TileBasico {

	public TileBasicoSolido(int id, int x, int y, int cor, int corLevel) {
		super(id, x, y, cor, corLevel);
		this.solido = true;
	}

}
