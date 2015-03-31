package com.berko.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.berko.ohhai.Ohhai;

public class Tile extends Box {
	private TextureRegion light;
	private TextureRegion dark;
	private TextureRegion neutral;
	private int state; // NEUTRAL == 0, BLUE == 1, MAIZE == 2
	private boolean starter;
	private float totalWidth;
	private float totalHeight;
	private float timer;
	private float maxTime = 0.5f;
	
	public Tile(float x, float y, float width, float height, int state, boolean starter) {
		light = Ohhai.res.getAtlas("pack").findRegion("light");
		dark = Ohhai.res.getAtlas("pack").findRegion("dark");
		neutral = Ohhai.res.getAtlas("pack").findRegion("neutral");
		this.starter = starter;
		this.state = state;
		this.x = x;
		this.y = y;
		this.totalWidth = width - 15;
		this.totalHeight = height - 15;
		width = height = 0;
	}
	
	public boolean isStarter() {
		return starter;
	}
	

	
	public void setState() {
		if (starter) {
			// TODO(DANIEL): Do a cool booty shaking animation
			return;
		}
		state++;
	}
	
	public void setState(int newState) {
		state = newState;
		System.out.println("SETSTATE");
	}
	
	public int getState() { 
		return state;
	}
	
	public void setStarter() {
		starter = true;
	}
	
	public void setTimer(float t) {
		timer = t;
	}
 	
	public void update(float dt) {
		if (state > 2) {
			state = 0;
		}
		
		if (width < totalWidth && height < totalHeight) {
			timer += dt;
			width = (timer / maxTime) * totalWidth;
			height = (timer / maxTime) * totalHeight;
			
			if (width > totalWidth) width = totalWidth;
			if (height > totalHeight) height = totalHeight;
			

		} 
		
		
		
	}
	
	public void render(SpriteBatch sb) {
		switch (state) {
			case 0:
				sb.draw(neutral, x - width / 2, y - height / 2, width, height);
				break;
			case 1:
				sb.draw(dark, x - width / 2, y - height / 2, width, height);
				break;
			case 2:
				sb.draw(light, x - width / 2, y - height / 2, width, height);
				break;
		}
		
		
	}
	
}
