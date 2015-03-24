package com.berko.states;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.berko.ohhai.Ohhai;
import com.berko.ui.Tile;

public class PlayState extends State{

	private final int MAX_FINGERS = 2;
	private Tile[][] tiles;
	private int amountOfTiles;
	private float boardOffset;
	private float touchTimer, timeSinceTouched;
	private int boardSize;
	
	public PlayState(Manager manager) {
		super(manager);
		boardSize = 4;
		touchTimer = 0.5f;
		timeSinceTouched = 0;
		amountOfTiles = Ohhai.WIDTH / boardSize;
		boardOffset = (Ohhai.HEIGHT - (amountOfTiles * boardSize)) / 2;
		tiles = generateBoard(boardSize);
		

	}
	

	private Tile[][] generateBoard(int size) {
		// Generates a new solved board and then returns it with only starters highlighted.
		Tile board[][];
		board = new Tile[size][size];
		Random r = new Random();
		
		// Make a random board
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				int ra = r.nextInt(3);
				board[row][col] = new Tile(col * amountOfTiles + amountOfTiles / 2, row * amountOfTiles + boardOffset + amountOfTiles / 2, amountOfTiles - 6, amountOfTiles - 6, ra);
			}
		}
		
		// TODO(DANIEL): Solve it
		// TODO(DANIEL): Leave only starter tiles

		return board;
	}
	
	private int count_unknown_squares(int board[][], int size) {
	    int result = 0;
	    for (int row = 0; row < size; row++) {
	        for (int col = 0; col < size; col++) {
	            if (board[row][col] == 0) {
	                result++;
	            }
	        }
	    }
	    return result;
	}
	
	
	
	
	private boolean row_is_balanced(int board[][], int size, int row) {
		int maize_count = 0;
		int blue_count = 0;
		
		for (int col = 0; col < size; col++) {
			if (board[row][col] == 1) {
				blue_count++;
			} else if (board[row][col] == 2) {
				maize_count++;
			}
		}
		
		if (maize_count > size / 2 || blue_count > size / 2) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean col_is_balanced(int board[][], int size, int col) {
		int maize_count = 0;
		int blue_count = 0;
		
		for (int row = 0; row < size; row++) {
			if (board[row][col] == 1) {
				blue_count++;
			} else if (board[row][col] == 2) {
				maize_count++;
			}
		}
		
		if (maize_count > size / 2 || blue_count > size / 2) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean board_is_balanced(int board[][], int size) {
		for (int i = 0; i < size; i++) {
			if (!row_is_balanced(board, size, i) ||
					!col_is_balanced(board, size, i) || count_unknown_squares(board, size) > 0) {
				return false;
			}
		}
		
		return true;
	}
	
	
	private boolean row_has_no_threes_of_color(int board[][],
												int size,
												int row,
												int color) {
		for (int i = 0; i < size - 2; i++) {
			if (board[row][i] == color && 
				board[row][i] == board[row][i + 1] &&
				board[row][i] == board[row][i + 2]){
				return false;
			}
		}
		
		return true;
	}
	
	private boolean col_has_no_threes_of_color(int board[][],
												int size,
												int col,
												int color) {
		for (int i = 0;  i < size - 2; i++) {
			if (board[i][col] == color &&
				board[i][col] == board[i + 1][col] &&
				board[i][col] == board[i + 2][col]) {
				return false;
			}
		}
		
		return true;
	}

	
	private boolean board_has_no_threes(int board[][], int size) {
		for (int i = 0; i < size; i++) {
			if (col_has_no_threes_of_color(board, size, i, 1) == false) {
				return false;
			} else if (col_has_no_threes_of_color(board, size, i, 2) == false) {
				return false;
			} else if (row_has_no_threes_of_color(board, size, i, 1) == false) {
				return false;
			} else if (row_has_no_threes_of_color(board, size, i, 2) == false) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean rows_are_diff(int board[][],
									int size,
									int row1,
									int row2) {

		
		int[] tempBoard1 = new int[size];
		int[] tempBoard2 = new int[size];
		
		for (int i = 0; i < size; i++) {
			tempBoard1[i] = board[row1][i];
			tempBoard2[i] = board[row2][i];
		}
		
		System.out.print(tempBoard1[1]);
		System.out.print(" ");
		System.out.println(tempBoard2[1]);

		
		
		for (int i = 0; i < size; i++) {
			if (tempBoard1[i] != tempBoard2[i]) return true;
			else if (tempBoard1[i] == 0 || tempBoard2[i] == 0) return true;
			
		}
		
		return false;
	}
	private boolean cols_are_diff(int board[][],
			int size,
			int col1,
			int col2) {

		int[] tempBoard1 = new int[size];
		int[] tempBoard2 = new int[size];
		
		for (int i = 0; i < size; i++) {
			tempBoard1[i] = board[i][col1];
			tempBoard2[i] = board[i][col2];
		}
		
		for (int i = 0; i < size; i++) {
			if (tempBoard1[i] != tempBoard2[i]) return true;
			else if (tempBoard1[i] == 0 || tempBoard2[i] == 0) return true;
		}
		
		return false;
}
	
	
	private boolean board_has_no_duplicates(int board[][], int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!rows_are_diff(board, size, i, j) && i != j) return false;
				if (!cols_are_diff(board, size, i, j) && i != j) return false;
			}
		}
		
		return true;
	}
	
	private boolean board_is_valid(int board[][], int size) {
		return (board_is_balanced(board, size) 
				&& board_has_no_threes(board, size) 
				&& board_has_no_duplicates(board, size));
	}
	
	
	private int[][] getNumBoard(Tile[][] board) {
		int[][] numberBoard = new int[boardSize][boardSize];
		
		
		// store board as array of numbers
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				numberBoard[row][col] = board[row][col].getState();
				//System.out.print(numberBoard[row][col]);
				//System.out.print(" ");
			}
			//System.out.println();
		}
		
		return numberBoard;
	}
	
	
	
	public void input() {
		for (int i = 0; i < MAX_FINGERS; i++) {
			if (Gdx.input.isTouched(i)) {
				mouse.x = Gdx.input.getX(i);
				mouse.y = Gdx.input.getY(i);
				cam.unproject(mouse);
				int row = (int) ((mouse.y - boardOffset) / amountOfTiles);
				int col = (int) (mouse.x / amountOfTiles);
				if (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length && timeSinceTouched > touchTimer) {
					timeSinceTouched = 0;
					tiles[row][col].setState();
				}
			}
		}
	}
	
	public void update(float dt) {
		input();
		int[][] numboard = new int[boardSize][boardSize];
		numboard = getNumBoard(tiles);
		
		
		if (timeSinceTouched <= touchTimer) {
			timeSinceTouched += dt;
		}
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				tiles[row][col].update(dt);
			}
		}
		
		
		if (board_is_valid(numboard, boardSize)) {
			System.out.println("FUCK YEA");
		}
		
		
	}
	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[0].length; col++) {
				tiles[row][col].render(sb);
			}
		}
		sb.end();
		
	}
	
	
}
