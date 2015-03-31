package com.berko.states;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.berko.ohhai.Ohhai;
import com.berko.ui.Board;
import com.berko.ui.Tile;

public class PlayState extends State{

	private final int MAX_FINGERS = 2;
	private Board board;
	private int[][] seed;
	private int amountOfTiles;
	private int boardHeight;
	private float boardOffset;
	private float touchTimer, timeSinceTouched;
	private int boardSize;
	private boolean toSolve;
	
	
	public PlayState(Manager manager) {
		super(manager);
		boardSize = 4;
		touchTimer = 0.5f;
		timeSinceTouched = 0;
		amountOfTiles = Ohhai.WIDTH / boardSize;
		boardHeight = (amountOfTiles * boardSize);
		boardOffset = (Ohhai.HEIGHT - (boardHeight)) / 2;
		int[][] seed = {{0,1,0,2},
					{2,1,0,0},
					{0,0,1,0},
					{0,2,0,1}};
		
		board = new Board(seed, boardSize);
		toSolve = false;
		

	}
	
	
	
	public void input() {
		for (int i = 0; i < MAX_FINGERS; i++) {
			if (Gdx.input.isTouched(i)) {
				mouse.x = Gdx.input.getX(i);
				mouse.y = Gdx.input.getY(i);
				cam.unproject(mouse);
				int row = (int) ((mouse.y - boardOffset) / amountOfTiles);
				int col = (int) (mouse.x / amountOfTiles);
				if (row >= 0 && row < boardSize && col >= 0 && col < boardSize && timeSinceTouched > touchTimer) {
					timeSinceTouched = 0;
					
					board.board[row][col].setState();
				}
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.S)) {
				board.solve();
			}
		}
	}
	
	public void update(float dt) {
		input();
		
		if (board.board_is_valid()) {
			System.out.println("WINNER");
		}
		
		if (timeSinceTouched <= touchTimer) {
			timeSinceTouched += dt;
		}
		
		board.update(dt);
		
	
	}
	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		board.render(sb);
		sb.end();
		
	}
	
	
}
