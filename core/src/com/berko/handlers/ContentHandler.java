package com.berko.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ContentHandler {

	private HashMap<String, TextureAtlas> atlases;
	
	public ContentHandler() {
		atlases = new HashMap<String, TextureAtlas>();
	}
	
	public void loadAtlas(String path, String key) {
		atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
	}
	
	public TextureAtlas getAtlas(String key) {
		return atlases.get(key);

	}
}
