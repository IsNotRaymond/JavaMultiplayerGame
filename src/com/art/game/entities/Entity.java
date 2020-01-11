package com.art.game.entities;

import com.art.game.graphics.Screen;
import com.art.game.level.Level;

public abstract class Entity {
	public int x, y;
	protected Level level;
	
	public Entity(Level level) {
		init(level);
	}
	
	public final void init(Level level) {
		this.level = level;
	}
	
	public abstract void update();
	
	public abstract void renderizar(Screen tela);
}
