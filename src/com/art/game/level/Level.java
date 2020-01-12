package com.art.game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.art.game.entities.Entity;
import com.art.game.entities.PlayerMP;
import com.art.game.graphics.Screen;
import com.art.game.level.tiles.Tile;

public class Level {

	private byte[] tiles;
	public int largura;
	public int altura;
	public List<Entity> entities = new ArrayList<Entity>();
	private BufferedImage imagem;

	public Level(String caminhoImagem) {
		if (caminhoImagem != null) {
			gerarLevel(caminhoImagem);
		} else {
			largura = 64;
			altura = 64;
			tiles = new byte[largura * altura];
	
			gerarLevel();
		}
	}

	@Deprecated
	public Level(int largura, int altura) {
		tiles = new byte[largura * altura];
		this.largura = largura;
		this.altura = altura;

		gerarLevel();
	}

	public void gerarLevel() {
		for (int y = 0; y < altura; y++) {
			for (int x = 0; x < largura; x++) {
				if (x * y % 10 < 7)
					tiles[x + y * largura] = Tile.GRAMA.getID();
				else
					tiles[x + y * largura] = Tile.PEDRA.getID();
			}
		}
	}
	
	private void gerarLevel(String caminho) {
		try {
			imagem = ImageIO.read(new File(caminho));
			
			largura = imagem.getWidth();
			altura = imagem.getHeight();
			tiles = new byte[largura * altura];
			
			carregarTiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void carregarTiles() {
		int[] corTile = this.imagem.getRGB(0, 0, largura, altura, null, 0, largura);
		
		for (int y = 0; y < altura; y ++) {
			for (int x = 0; x < largura; x ++) {
				
				checarTile : for (Tile t : Tile.tiles) {
					if (t != null && t.getCorLevel() == corTile[x + y * largura]) {
						this.tiles[x + y * largura] = t.getID();
						break checarTile;
					}
					
				}
			}
		}
	}
	
	public synchronized List<Entity> getEntities() {
		return entities;
	}
	
	public void update() {
		for (Entity e : getEntities()) {
			e.update();
		}
		
		for (Tile t : Tile.tiles) {
			if (t == null) break;
			
			t.update();
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

		for (int y = (yOffSet >> 3); y < (yOffSet + tela.altura >> 3) + 1; y++) {
			for (int x = (xOffSet >> 3); x < (xOffSet + tela.largura >> 3) + 1; x++) {
				getTile(x, y).renderizar(tela, this, x << 3, y << 3);
			}
		}
	}

	public void renderizarEntities(Screen tela) {
		for (Entity e : getEntities()) {
			e.renderizar(tela);
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= largura || y < 0 || y >= altura)
			return Tile.VAZIO;

		return Tile.tiles[tiles[x + y * largura]];
	}

	public synchronized void addEntity(Entity e) {
		this.getEntities().add(e);
	}

	public synchronized void removePlayerMP(String nomeUsuario) {
		int indice = 0;
		
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getNomeUsuario().equals(nomeUsuario)) {
				break;
			}
			indice ++;
		}
		
		this.getEntities().remove(indice);
	}
	
	private int getIndicePlayerMP(String nomeUsuario) {
		int indice = 0;
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getNomeUsuario().equals(nomeUsuario)) {
				break;
			}
			indice ++;
		}
		return indice;
	}
	
	public synchronized void moverJogador(String nomeUsuario, int x, int y, int numeroPassos, boolean caminhando, int direcao) {
		int indice = getIndicePlayerMP(nomeUsuario);
		
		PlayerMP jogador = (PlayerMP) this.getEntities().get(indice);
		
		jogador.x = x;
		jogador.y = y;
		jogador.setCaminhando(caminhando);
		jogador.setNumeroPassos(numeroPassos);
        jogador.setDirecao(direcao);
		
		this.getEntities().get(indice).x = x;
		this.getEntities().get(indice).y = y;
	}
}
