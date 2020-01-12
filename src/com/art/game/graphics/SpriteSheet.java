package com.art.game.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public String caminho;
	public int largura;
	public int altura;
	
	public int pixels[];
	
	public SpriteSheet(String caminho) {
		carregarImagem(caminho);
	}
	
	private void carregarImagem(String caminho) {
		BufferedImage imagem = null;
		
		try {
			imagem = ImageIO.read(new File(caminho));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (imagem == null) return;
		
		this.caminho = caminho;
		largura = imagem.getWidth();
		altura = imagem.getHeight();
		
		pixels = imagem.getRGB(0, 0, largura, altura, null, 0, largura);
		
		for (int i = 0; i < pixels.length; i ++) {
			pixels[i] = (pixels[i] & 0xFF) / 64;
		}
	}
}
