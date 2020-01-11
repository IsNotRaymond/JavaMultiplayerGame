package com.art.game.level.tiles;

import com.art.game.graphics.Screen;
import com.art.game.level.Level;

public class TileBasico extends Tile {
	
	protected int tileID;
	protected int tileCor;

	public TileBasico(int id, int x, int y, int cor) {
		super(id, false, false);
		tileID = x + y;
		tileCor = cor;
	}

	@Override
	public void renderizar(Screen tela, Level level, int x, int y) {
		tela.renderizar(x, y, tileID, tileCor);
	}

}