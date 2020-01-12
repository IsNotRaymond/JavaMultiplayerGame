package com.art.game.level.tiles;

import com.art.game.graphics.Screen;
import com.art.game.level.Level;

public class TileBasico extends Tile {
	
	protected int tileID;
	protected int tileCor;

	public TileBasico(int id, int x, int y, int corTile, int corLevel) {
		super(id, false, false, corLevel);
		tileID = x + y * 32;
		tileCor = corTile;
	}

	@Override
	public void update() {
		
	}
	
	public void render(Screen tela, Level level, int x, int y) {
		
	}
	
	@Override
	public void renderizar(Screen tela, Level level, int x, int y) {
		tela.renderizar(x, y, tileID, tileCor, 0x00, 1);
	}

}
