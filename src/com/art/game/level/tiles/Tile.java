package com.art.game.level.tiles;

import com.art.game.exception.GameException;
import com.art.game.graphics.Colors;
import com.art.game.graphics.Screen;
import com.art.game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VAZIO = new TileBasicoSolido(0, 0, 0, Colors.get(000, -1, -1, -1), 0xFF000000);
	public static final Tile PEDRA = new TileBasicoSolido(1, 1, 0, Colors.get(-1, 333, -1, -1), 0xFF555555);
	public static final Tile GRAMA = new TileBasico(2, 2, 0, Colors.get(-1, 131, 141, -1), 0xFF00FF00);
	public static final Tile AGUA = new TileAnimado(3, new int[][] {{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colors.get(-1, 004, 115, -1), 0xFF0000FF, 1000);

	protected byte id;
	protected boolean solido;
	protected boolean emissor;
	private int corLevel;
	
	public Tile(int id, boolean solido, boolean emissor, int corLevel) {
		this.id = (byte) id;
		if (tiles[id] != null) throw new GameException("ID " + id + " Duplicado");
		
		tiles[id] = this;
		this.solido = solido;
		this.corLevel = corLevel;
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

	public int getCorLevel() {
		return corLevel;
	}
	
	public abstract void update();
	
	public abstract void renderizar(Screen tela, Level level, int x, int y);
	
}
