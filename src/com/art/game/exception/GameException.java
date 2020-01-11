package com.art.game.exception;

public class GameException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public GameException(String mensagem) {
		super(mensagem);
	}
} 
