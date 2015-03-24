package com.berko.ohhai;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.berko.handlers.ContentHandler;
import com.berko.states.Manager;
import com.berko.states.PlayState;

public class Ohhai extends ApplicationAdapter {

    public static final String TITLE = "Ohhai!";
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
    
    public static ContentHandler res;
    
    private Manager manager;
    private SpriteBatch sb;


	@Override
	public void create () {
        Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
        
        
        res = new ContentHandler();
        res.loadAtlas("pack.atlas", "pack");
        
        sb = new SpriteBatch();
        manager = new Manager();
        manager.push(new PlayState(manager));
    }

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.update(Gdx.graphics.getDeltaTime());
		manager.render(sb);
	
	}
}
