package com.art.game.input;

public class Key {
	
	boolean pressionado = false;
	
	public static Key CIMA = new Key();
	public static Key BAIXO = new Key();
	public static Key ESQUERDA = new Key();
	public static Key DIREITA = new Key();
	
	public void alternar(boolean pressionado) {
		this.pressionado = pressionado;
	}
	
	public boolean pressionado() {
		return pressionado;
	}
}
