package com.art.game.entities;

import com.art.game.graphics.Screen;
import com.art.game.level.Level;

public abstract class Mob extends Entity {
	
	public enum Direcao {
		UP, DOWN, LEFT, RIGHT
	}
	
	protected String nome;
	protected int velocidade;
	protected int numeroPassos = 0;
	
	public boolean caminhando;
	protected Direcao direcao = Direcao.UP;
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
			if (yA < 0) direcao = Direcao.UP;
			if (yA > 0) direcao = Direcao.DOWN;
			if (xA < 0) direcao = Direcao.LEFT;
			if (xA > 0) direcao = Direcao.RIGHT;
			
			x += xA * velocidade;
			y += yA * velocidade;
		}
	}
	
	public abstract boolean colidiu(int xA, int yA);
	
	public String getName() {
		return nome;
	}
	
	@Override
	public abstract void update();

	@Override
	public abstract void renderizar(Screen tela);

}
