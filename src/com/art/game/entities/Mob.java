package com.art.game.entities;

import com.art.game.graphics.Screen;
import com.art.game.level.Level;
import com.art.game.level.tiles.Tile;

public abstract class Mob extends Entity {
	
	protected String nome;
	protected int velocidade;
	protected int numeroPassos = 0;
	
	public boolean caminhando;
	protected int direcao = 1;
	protected int escala = 1;
	
	public Mob(Level level, String nome, int x, int y, int velocidade) {
		super(level);
		this.nome = nome;
		this.x = x;
		this.y = y;
		this.velocidade = velocidade;
	}
	
	public void mover(int xA, int yA) {
		if (xA != 0 && yA != 0) {
			mover(xA, 0);
			mover(0, yA);
			numeroPassos --;
			return;
		}
		
		numeroPassos ++;
		
		if (!colidiu(xA, yA)) {
			if (yA < 0) direcao = 0;
			if (yA > 0) direcao = 1;
			if (xA < 0) direcao = 2;
			if (xA > 0) direcao = 3;
			
			x += xA * velocidade;
			y += yA * velocidade;
		}
	}
	
	public abstract boolean colidiu(int xA, int yA);
	
	public boolean tileSolido(int xA, int yA, int x, int y) {
		if (level == null) return false;
		
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xA) >> 3, (this.y + y + yA) >> 3);
		
		if (!lastTile.equals(newTile) && newTile.solido())
			return true;
		
		return false;
	}
	
	public String getName() {
		return nome;
	}
	
	public int getNumeroPassos() {
        return numeroPassos;
    }

    public boolean caminhando() {
        return caminhando;
    }

    public int getDirecao() {
        return direcao;
    }

    public void setNumeroPassos(int numeroPassos) {
        this.numeroPassos = numeroPassos;
    }

    public void setCaminhando(boolean caminhando) {
        this.caminhando = caminhando;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }
	
	@Override
	public abstract void update();

	@Override
	public abstract void renderizar(Screen tela);

}
