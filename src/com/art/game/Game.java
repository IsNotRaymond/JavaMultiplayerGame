package com.art.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.art.game.entities.Player;
import com.art.game.entities.PlayerMP;
import com.art.game.graphics.Screen;
import com.art.game.graphics.SpriteSheet;
import com.art.game.input.InputKeyboard;
import com.art.game.input.InputWindow;
import com.art.game.level.Level;
import com.art.game.net.GameClient;
import com.art.game.net.GameServer;
import com.art.game.net.packet.Packet00Login;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static Game game;
	
	public static final int LARGURA = 160;
	public static final int ALTURA = LARGURA / 12 * 9;
	public static final int ESCALA = 3;
	public static final String NOME = "GAME";
	
	public JFrame frame;
	private Thread thread;
	private Screen tela;
	public InputKeyboard input;
	public InputWindow window;
	public Level level;
	public Player jogador;
	
	public GameClient cliente;
	public GameServer servidor;
	
	private BufferedImage imagem = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) imagem.getRaster().getDataBuffer()).getData();
	private int[] cores = new int[6 * 6 * 6];
	
	public boolean executando = false;
	public int contaUpdates = 0;
	
	public boolean debug = true;
	public boolean applet = false;
	
	public Game() {
		setupFrame();
	}
	
	private void setupFrame() {
		setMinimumSize(new Dimension(LARGURA * ESCALA, ALTURA * ESCALA));
		setMaximumSize(new Dimension(LARGURA * ESCALA, ALTURA * ESCALA));
		setPreferredSize(new Dimension(LARGURA * ESCALA, ALTURA * ESCALA));
		
		frame = new JFrame(NOME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		requestFocus();
	}
	
	private synchronized void start() {
		executando = true;
		thread = new Thread(this, "Game-Main");
		thread.start();
		
		if (JOptionPane.showConfirmDialog(this, "Voce deseja iniciar o Servidor") == 0) {
			servidor = new GameServer(this);
			servidor.start();
		}
		
		cliente = new GameClient(this, "localhost");
		cliente.start();
		
	}
	
	public void stop() {
		executando = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long ultimo = System.nanoTime(), agora;
		double NanoSegundosPorUpdate = 1000000000D/60D;
		boolean renderiza;
		int frames = 0;
		int updates = 0;
		
		long ultimoMiliSegundos = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while (executando) {
			agora = System.nanoTime();
			delta += (agora - ultimo) / NanoSegundosPorUpdate;
			ultimo = agora;
			renderiza = false;
			
			while (delta >= 1) {
				updates ++;
				update();
				delta -= 1;
				renderiza = true;
			}
			
			if (renderiza) {
				frames ++;
				render();
			}
			
			
			if (System.currentTimeMillis() - ultimoMiliSegundos > 1000) {
				ultimoMiliSegundos += 1000;
				
				frame.setTitle(NOME + " | FPS: " + String.valueOf(frames) + 
						" | UPDATES: " + String.valueOf(updates));
				
				updates = 0;
				frames = 0;
				
			}
		}
	}
	
	private void init() {
		adicionarCores();
		game = this;
		tela = new Screen(LARGURA, ALTURA, new SpriteSheet("res/sheet/spritesheet.png"));
		input = new InputKeyboard(this);
		window = new InputWindow(this);
		level = new Level("res/levels/watertest.png");

		jogador = new PlayerMP(level, 100, 100, input, JOptionPane.showInputDialog(this, "Por favor, ponha seu nome abaixo"), null, -1);
		level.addEntity(jogador);
		
		// cliente.enviar("ping".getBytes());
		
		if (!applet) {
			Packet00Login login = new Packet00Login(jogador.getNomeUsuario(), jogador.x, jogador.y);
			
			if (servidor != null) {
				servidor.adicionarConexao((PlayerMP) jogador, login);
			}
			
			login.escrever(cliente);
		}
	}

	private void adicionarCores() {
		int index = 0;
		
		for (int r = 0; r < 6; r ++) {
			for (int g = 0; g < 6; g ++) {
				for (int b = 0; b < 6; b ++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					
					cores[index ++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
	}
	
	public void update() {
		contaUpdates ++;
		level.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		level.renderizarTile(tela, jogador.x - (tela.largura / 2), jogador.y - (tela.altura / 2));
		level.renderizarEntities(tela);
		
		
		for (int y = 0; y < tela.altura; y ++) {
			for (int x = 0; x < tela.largura; x ++) {
				int codigoCor = tela.pixels[x + y * tela.largura];
				
				if (codigoCor < 255) {
					pixels[x + y * LARGURA] = cores[codigoCor];
				}
			}
		}
		level.renderizarEntities(tela);
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawRect(0, 0, getWidth(), getHeight());
		g.drawImage(imagem, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

	public static long fatorial(int n) {
		if (n <= 1) return 1;
		
		else return n * fatorial(n - 1);
	}

}
