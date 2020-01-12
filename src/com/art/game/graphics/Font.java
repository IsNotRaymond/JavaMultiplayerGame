package com.art.game.graphics;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " +
								  "0123456789.,:;'\"!?$%()-=+/      ";
	
	public static void renderizar(String texto, Screen tela, int x, int y, int cor, int escala) {
		texto = texto.toUpperCase();
		
		for (int i = 0; i < texto.length(); i ++) {
			int indiceChar = chars.indexOf(texto.charAt(i));
			
			if (indiceChar != -1) tela.renderizar(x + (i * 8), y, indiceChar + 30 * 32, cor, 0x00, escala);
		}
	}
}
