package com.art.game.graphics;

public class Screen {
	public static final int LARGURA_MAPA = 64;
	public static final int MASK_LARGURA_MAPA = LARGURA_MAPA - 1;
	
	public static final byte BIT_ESPELHAR_X = 0x01;
	public static final byte BIT_ESPELHAR_Y = 0x02;
	
	public int pixels[];
	
	public int largura;
	public int altura;
	
	public SpriteSheet spriteSheet;
	
	public int xOffSet = 0;
	public int yOffSet = 0;

	public Screen(int largura, int altura, SpriteSheet spriteSheet) {
		this.largura = largura;
		this.altura = altura;
		this.spriteSheet = spriteSheet;
		
		pixels = new int[largura * altura];
		
	}
	
	public void renderizar(int xPosicao, int yPosicao, int tile, int cor, int direcaoEspelhar, int escala) {
		xPosicao -= xOffSet; yPosicao -= yOffSet;
		
		int xTile = tile % 32, yTile = tile / 32;
		
		boolean girarX = (direcaoEspelhar & BIT_ESPELHAR_X) > 0;
		boolean girarY = (direcaoEspelhar & BIT_ESPELHAR_Y) > 0;
		
		int tileOffSet = (xTile << 3) + (yTile << 3) * spriteSheet.largura;
		int escalaMapa = escala - 1;
		
		for (int y = 0; y < 8; y ++) {
			int ySheet = y;
			
			if (girarY) ySheet = 7 - y;
			
			int yPixel = y + yPosicao + (y * escalaMapa) - ((escalaMapa << 3) / 2);
			
			for (int x = 0; x < 8; x ++) {
				int xSheet = x;
				
				if (girarX) xSheet = 7 - x;
				
				int xPixel = x + xPosicao + (x * escalaMapa) - ((escalaMapa << 3) / 2);
				
				int c = (cor >> (spriteSheet.pixels[xSheet + ySheet * spriteSheet.largura + tileOffSet] * 8) & 255);
				
				if (c < 255) {
					for (int yEscala  = 0; yEscala < escala; yEscala ++) {
						if (yPixel + yEscala < 0 || yPixel + yEscala >= altura) continue; 
						
						for (int xEscala  = 0; xEscala < escala; xEscala ++) {
							if (xPixel + xEscala < 0 || xPixel + xEscala >= largura) continue;
						
							else pixels[(xPixel + xEscala) + (yPixel + yEscala) * largura] = c;
						}
					}
					
				}
			}
		}
	}

	public void setOffSet(int xOffSet, int yOffSet) {
		this.xOffSet = xOffSet;
		this.yOffSet = yOffSet;
		
	}
}
