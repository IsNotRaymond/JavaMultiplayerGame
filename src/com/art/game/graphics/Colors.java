package com.art.game.graphics;

public class Colors {
	
	public static int get(int cor1, int cor2, int cor3, int cor4) {
		
		return (get(cor4) << 24) + (get(cor3) << 16) + (get(cor2) << 8) + get(cor1); 
	}

	private static int get(int color) {
		if (color < 0) return 255;
		int red = color / 100 % 10;
		int green = color / 10 % 10;
		int blue = color % 10;
		
		return red * 36 + green * 6 + blue;
	}
}
