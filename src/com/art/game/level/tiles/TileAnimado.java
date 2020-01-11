package com.art.game.level.tiles;

public class TileAnimado extends TileBasico {
	
	private int[][] coordenadasTileAnimado;
	private int indexAnimacaoAtual;
	private long tempoUltimaIteracao;
	private int delayTrocaAnimacao;

	public TileAnimado(int id, int[][] coordenadasAnimacao, int corTile, int corLevel, int delay) {
		super(id, coordenadasAnimacao[0][0], coordenadasAnimacao[0][1], corTile, corLevel);
		
		coordenadasTileAnimado = coordenadasAnimacao;
		indexAnimacaoAtual = 0;
		tempoUltimaIteracao = System.currentTimeMillis();
		delayTrocaAnimacao = delay;
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - tempoUltimaIteracao >= delayTrocaAnimacao) {
			tempoUltimaIteracao += delayTrocaAnimacao;
			
			indexAnimacaoAtual = (indexAnimacaoAtual + 1) % coordenadasTileAnimado.length;
			
			tileID = (coordenadasTileAnimado[indexAnimacaoAtual][0] + (coordenadasTileAnimado[indexAnimacaoAtual][1] * 32));
		}
	}	
}
