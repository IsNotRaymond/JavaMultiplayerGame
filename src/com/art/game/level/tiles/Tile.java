package com.art.game.level.tiles;

import com.art.game.exception.GameException;
import com.art.game.graphics.Colors;
import com.art.game.graphics.Screen;
import com.art.game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VAZIO = new TileBasico(0, 0, 0, Colors.get(000, -1, -1, -1));
	public static final Tile PEDRA = new TileBasico(1, 1, 0, Colors.get(-1, 333, -1, -1));
	public static final Tile GRAMA = new TileBasico(2, 2, 0, Colors.get(-1, 131, 141, -1));

	protected byte id;
	protected boolean solido;
	protected boolean emissor;
	
	public Tile(int id, boolean solido, boolean emissor) {
		this.id = (byte) id;
		if (tiles[id] != null) throw new GameException("ID " + id + " Duplicado");
		
		tiles[id] = this;
		this.solido = solido;
		this.emissor = emissor;
	}
	
	public byte getID() {
		return id;
	}
	
	public boolean solido() {
		return solido;
	}
	
	public boolean emissor() {
		return emissor;
	}
	
	public abstract void renderizar(Screen tela, Level level, int x, int y);
	
}
