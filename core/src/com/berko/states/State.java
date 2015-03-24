package com.berko.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.berko.ohhai.Ohhai;


// BASS STATE
public abstract class State {

	protected Manager manager;
	protected OrthographicCamera cam;
	protected Vector3 mouse;
	
	protected State(Manager manager) {
		this.manager = manager;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Ohhai.WIDTH, Ohhai.HEIGHT);
		mouse = new Vector3();
	}
	
	public abstract void update(float dt);
	
	public abstract void render(SpriteBatch sb);
	
	public abstract void input();
}
