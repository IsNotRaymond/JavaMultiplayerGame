package com.art.game.level;

import java.util.ArrayList;
import java.util.List;

import com.art.game.entities.Entity;
import com.art.game.graphics.Screen;
import com.art.game.level.tiles.Tile;

public class Level {

	private byte[] tiles;
	public int largura;
	public int altura;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level(int largura, int altura) {
		tiles = new byte[largura * altura];
		this.largura = largura;
		this.altura = altura;

		gerarLevel();
	}

	public void gerarLevel() {
		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < largura; x++) {
				
				if (x * y % 10 < 5 ) tiles[x + y * largura] = Tile.GRAMA.getID();
				
				else tiles[x + y * largura] = Tile.PEDRA.getID();
			}
		}
	}
	
	public void update() {
		for (Entity e : entities) {
			e.update();
		}
	}

	public void renderizarTile(Screen tela, int xOffSet, int yOffSet) {
		if (xOffSet < 0)
			xOffSet = 0;
		if (xOffSet > ((largura << 3) - tela.largura))
			xOffSet = ((largura << 3) - tela.largura);
		if (yOffSet < 0)
			yOffSet = 0;
		if (yOffSet > ((altura << 3) - tela.altura))
			yOffSet = ((altura << 3) - tela.altura);

		tela.setOffSet(xOffSet, yOffSet);

		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < largura; x++) {
				getTile(x, y).renderizar(tela, this, x << 3, y << 3);
			}
		}
	}
	
	public void renderizarEntities(Screen tela) {
		for (Entity e : entities) {
			e.renderizar(tela);
		}
	}

	private Tile getTile(int x, int y) {
		if (x < 0 || x > largura || y < 0 || y > altura) return Tile.VAZIO;
		
		return Tile.tiles[tiles[x + y * largura]];
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}
}
