package com.berko.ui;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.berko.ohhai.Ohhai;

public class Board {

	public Tile[][] board;
	private boolean isSolved;
	private boolean shouldSolve;
	private boolean isValid;
	private int size; // for each array
	private int amountOfTiles;
	private int boardOffset;

	private Random r;
	
	public Board(int[][] seed, int size) {
		this.isSolved = false;
		this.isValid = false;
		this.shouldSolve = false;
		this.size = size;
		this.amountOfTiles = Ohhai.WIDTH / size;
		this.boardOffset = (Ohhai.HEIGHT - (amountOfTiles * size)) / 2;
		this.board = new Tile[size][size];
		this.r = new Random();

		createBoard(seed, size);
		
	}
	
	private void createBoard(int[][] seed, int size) {
				int lastColor = 0;
			
				for (int row = 0; row < size; row++) {	
					for (int col = 0; col < size; col++) {
						//int ra = r.nextInt(size + 2);
						
						if (seed[row][col] == 0) {
							board[row][col] = new Tile(col * amountOfTiles + amountOfTiles / 2,
									row * amountOfTiles + boardOffset + amountOfTiles / 2, amountOfTiles - 6,
										amountOfTiles - 6, 0, false);
							
						} else {	
					
							board[row][col] = new Tile(col * amountOfTiles + amountOfTiles / 2,
													row * amountOfTiles + boardOffset + amountOfTiles / 2, amountOfTiles - 6,
														amountOfTiles - 6, seed[row][col], true);
						

						} 
						
					
						}
				}
				
				for (int i = 0; i < size; i++) {
					if (row_is_blank(i)) {
						int col = r.nextInt(size);
						if (col > 0) {
						board[i][col].setState(col);
						board[i][col].setStarter();
						} else {
							board[i][col].setState(0);
						}
							
						}
				}
				
			

			
	}
	
	private boolean row_is_blank(int row) {
		int count = 0; 
		for (int col = 0; col < size; col++) {
			if (board[row][col].getState() == 0) {
				count++;
		    }
		}
		
		if (count == size) {
			return true;
		}
		
		return false;
	}
	
	private int count_unknown_squares() {
	    int result = 0;
	    for (int row = 0; row < size; row++) {
	        for (int col = 0; col < size; col++) {
	            if (board[row][col].getState() == 0) {
	                result++;
	            }
	        }
	    }
	    return result;
	}
	
	private boolean row_is_balanced(int row) {
		int maize_count = 0;
		int blue_count = 0;
		
		for (int col = 0; col < size; col++) {
			if (board[row][col].getState() == 1) {
				blue_count++;
			} else if (board[row][col].getState() == 2) {
				maize_count++;
			}
		}
		
		if (maize_count > size / 2 || blue_count > size / 2) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean col_is_balanced(int col) {
		int maize_count = 0;
		int blue_count = 0;
		
		for (int row = 0; row < size; row++) {
			if (board[row][col].getState() == 1) {
				blue_count++;
			} else if (board[row][col].getState() == 2) {
				maize_count++;
			}
		}
		
		if (maize_count > size / 2 || blue_count > size / 2) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean board_is_balanced() {
		for (int i = 0; i < size; i++) {
			if (!row_is_balanced(i) ||
					!col_is_balanced(i) || count_unknown_squares() > 0) {
				return false;
			}
		}
		
		return true;
	}

	
	
	private boolean row_has_no_threes_of_color(	int row, int color) {
		for (int i = 0; i < size - 2; i++) {
			if (board[row][i].getState() == color && 
				board[row][i].getState() == board[row][i + 1].getState() &&
				board[row][i].getState() == board[row][i + 2].getState()){
				return false;
			}
		}
		
		return true;
	}
	
	
	private boolean col_has_no_threes_of_color(int col, int color) {
			for (int i = 0;  i < size - 2; i++) {
				if (board[i][col].getState() == color &&
						board[i][col].getState() == board[i + 1][col].getState() &&
						board[i][col].getState() == board[i + 2][col].getState()) {
					return false;
				}
			}

			return true;
	}

	
	
	private boolean board_has_no_threes() {
		for (int i = 0; i < size; i++) {
			if (col_has_no_threes_of_color(i, 1) == false) {
				return false;
			} else if (col_has_no_threes_of_color(i, 2) == false) {
				return false;
			} else if (row_has_no_threes_of_color(i, 1) == false) {
				return false;
			} else if (row_has_no_threes_of_color(i, 2) == false) {
				return false;
			}
		}
		
		return true;
	}
	
	
	private boolean rows_are_diff(int row1, int row2) {

				int[] tempBoard1 = new int[size];
				int[] tempBoard2 = new int[size];

				for (int i = 0; i < size; i++) {
					tempBoard1[i] = board[row1][i].getState();
					tempBoard2[i] = board[row2][i].getState();
				}


				for (int i = 0; i < size; i++) {
					if (tempBoard1[i] != tempBoard2[i]) return true;
					else if (tempBoard1[i] == 0 || tempBoard2[i] == 0) return true;

				}

				return false;
	}
	
	private boolean cols_are_diff(int col1, int col2) {

			int[] tempBoard1 = new int[size];
			int[] tempBoard2 = new int[size];

			for (int i = 0; i < size; i++) {
				tempBoard1[i] = board[i][col1].getState();
				tempBoard2[i] = board[i][col2].getState();
			}

			for (int i = 0; i < size; i++) {
				if (tempBoard1[i] != tempBoard2[i]) return true;
				else if (tempBoard1[i] == 0 || tempBoard2[i] == 0) return true;
			}

			return false;
	
	}
	

	private boolean board_has_no_duplicates() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!rows_are_diff(i, j) && i != j) return false;
				if (!cols_are_diff(i, j) && i != j) return false;
			}
		}
		
		return true;
	}
	
	
	public boolean board_is_valid() {
		return (board_is_balanced() 
				&& board_has_no_threes() 
				&& board_has_no_duplicates());
	}
	
	

	private int setOppositeColor(int c) {
		if (c == 1) return 2;
		if (c == 2) return 1;
		return 0;
	}
	

	private void solve_three_in_a_row(int row) {
		int count = 0;
		
		for (int i = 1; i < size - 2; i++) {
			int tile = board[row][i].getState();
			
			if (tile > 0) {
				if (tile == board[row][i + 1].getState()) {
					count++;
				}
				if (board[row][i + 1].getState() == 0 && tile == board[row][i + 2].getState()) {
					count++;
				}
				
				if (count == 1) {
					if (board[row][i + 1].getState() == 0) {
						board[row][i + 1].setState(setOppositeColor(board[row][i].getState()));
					} else if (board[row][i - 1].getState() == 0 && i > 0) {
						board[row][i - 1].setState(setOppositeColor(board[row][i].getState()));
						
						if (board[row][i + 2].getState() == 0) {
							board[row][i + 2].setState(setOppositeColor(board[row][i].getState()));
						}
					} else if (board[row][i + 2].getState() == 0) {
						board[row][i + 2].setState(setOppositeColor(board[row][i].getState()));
					}
					
					count = 0;
				}
			}
		}
		
		if (size > 2 && board[row][size - 1].getState() > 0) {
			if (board[row][size - 3].getState() == 0 &&
					board[row][size - 2].getState() == board[row][size - 1].getState()) {
				board[row][size - 3].setState(setOppositeColor(board[row][size - 2].getState()));
			}
		}
		
		
	}
	
	private void solve_three_in_a_column(int col) {
		int count = 0;
		
		for (int i = 1; i < size - 2; i++) {
			int tile = board[i][col].getState();
			
			if (tile > 0) {
				if (tile == board[i + 1][col].getState()) {
					count++;
				} else if (board[i + 1][col].getState() == 0 && tile == board[i + 2][col].getState()) {
					count++;
				}
				
				if (count == 1) {
					if (board[i + 1][col].getState() == 0) {
						board[i + 1][col].setState(setOppositeColor(tile));
					} else if (board [i - 1][col].getState() == 0 && i > 0) {
						board[i - 1][col].setState(setOppositeColor(tile));
						
						if (board[i + 2][col].getState() == 0) {
							board[i + 2][col].setState(setOppositeColor(tile));
						}
					} else if (board[i + 2][col].getState() == 0) {
						board[i + 2][col].setState(setOppositeColor(tile));
					}
					
					
				}
				
				count = 0;
				
			}
		}
		
		if (size > 2 && board[size - 1][col].getState() > 0) {
			if (board[size - 3][col].getState() == 0 &&
					board[size - 2][col].getState() == board[size - 1][col].getState()) {
				board[size - 3][col].setState(setOppositeColor(board[size - 2][col].getState()));
			}
		}
		

	}
	
	
	private void solve_balance_row(int row) {
		int countBlue = 0;
		int countMaise = 0;
		
		for (int i = 0; i < size; i++) {
			if (board[row][i].getState() == 1) countBlue++;
			if (board[row][i].getState() == 2) countMaise++;
		}
		
		if (countBlue == (size / 2)) {
			for (int i = 0; i < size; i++) {
				if (board[row][i].getState() == 0) {
					board[row][i].setState(2);
				}
			}
		}
		
		if (countMaise == (size / 2)) {
			for (int i = 0; i < size; i++) {
				if (board[row][i].getState() == 0) {
					board[row][i].setState(1);
				}
			}
		}
		
	}
	
	private void solve_balance_column(int col) {
		int countBlue = 0;
		int countMaise = 0;
		
		for (int i = 0; i < size; i++) {
			if (board[i][col].getState() == 1) countBlue++;
			if (board[i][col].getState() == 2) countMaise++;
		}
		
		if (countBlue == (size / 2)) {
			for (int i = 0; i < size; i++) {
				if (board[i][col].getState() == 0) {
					board[i][col].setState(2);
				}
			}
		}
		
		if (countMaise == (size / 2)) {
			for (int i = 0; i < size; i++) {
				if (board[i][col].getState() == 0) {
					board[i][col].setState(1);
				}
			}
		}
	}
	

	
	private void solve_duplicates_row(int full_row) {
		int countBlue = 0;
		int countMaise = 0;
		
		for (int col = 0; col < size; col++) {
			if (board[full_row][col].getState() == 1) {
				countBlue++;
			} else if (board[full_row][col].getState() == 2) {
				countMaise++;
			}
			
		}
		
		int color = 0;
		if (countBlue == size / 2) {
			color = 2;
		} else if (countMaise == size / 2) {
			color = 1;
		}
		
		int matching_tiles = 0;
		if (color != 0) {
			for (int other_row = 0; other_row < size; other_row++) {
				if (other_row == full_row) {
					continue;
				}
				matching_tiles = 0;
				
				for (int col = 0; col < size; col++) {
					if (board[other_row][col] == board[full_row][col]) {
						matching_tiles += 2;
					} else {
						matching_tiles--;
					}
				}
				
				if (matching_tiles == 2 * size - 6) {
					for (int col = 0; col < size; col++) {
						if (board[other_row][col].getState() == 0) {
							board[other_row][col].setState(setOppositeColor(board[full_row][col].getState()));
						}
					}
				}
			}
		}
		
	}
	
	private void solve_duplicates_column(int full_col) {
		int countBlue = 0;
		int countMaise = 0;
		
		for (int row = 0; row < size; row++) {
			if (board[row][full_col].getState() == 1) {
				countBlue++;
			} else if (board[row][full_col].getState() == 2) {
				countMaise++;
			}
			
		}
		
		int color = 0;
		if (countBlue == size / 2) {
			color = 2;
		} else if (countMaise == size / 2) {
			color = 1;
		}
		
		int matching_tiles = 0;
		if (color != 0) {
			for (int other_col = 0; other_col < size; other_col++) {
				if (other_col == full_col) {
					continue;
				}
				matching_tiles = 0;
				
				for (int row = 0; row < size; row++) {
					if (board[row][other_col] == board[row][other_col]) {
						matching_tiles += 2;
					} else {
						matching_tiles--;
					}
				}
				
				if (matching_tiles == 2 * size - 6) {
					for (int row = 0; row < size; row++) {
						if (board[row][other_col].getState() == 0) {
							board[row][other_col].setState(setOppositeColor(board[row][full_col].getState()));
						}
					}
				}
			}
		}
	}
	
	
	public void solveBoard() {
		

		if (board_is_valid()) return;
		int unknown_squares_now = count_unknown_squares();
		int unknown_squares_old = unknown_squares_now + 1;
		while (unknown_squares_now != 0 && unknown_squares_old > unknown_squares_now) {
			unknown_squares_now = count_unknown_squares();
			unknown_squares_old = unknown_squares_now + 1;
			
			while (unknown_squares_old > unknown_squares_now) {
				unknown_squares_old = unknown_squares_now;
				
				for (int row = 0; row < size; row++) {
					solve_three_in_a_row(row);
				}
				for (int col = 0; col < size; col++) {
					solve_three_in_a_column(col);
				}
				
				unknown_squares_now = count_unknown_squares();
			}
			
			if (unknown_squares_now != 0) {
				for (int row = 0; row < size; row++) {
					solve_balance_row(row);
				}
				for (int col = 0; col < size; col++) {
					solve_balance_column(col);
				}
				
				unknown_squares_now = count_unknown_squares();
				
			}
			
			if (unknown_squares_now != 0 && unknown_squares_now == unknown_squares_old) {
				for (int row = 0; row < size; row++) {
					solve_duplicates_row(row);
				}
				for (int col = 0; col < size; col++) {
					solve_duplicates_column(col);
				}
				
				unknown_squares_now = count_unknown_squares();
			}
		}
		
		
		if (!board_is_valid()) {
			System.out.println("CANNOT SOLVE. TRY GUESSING");
		}
		
		System.out.println("SHOULD BE SOLVED");
		isSolved = true;
	}
	
	public void solve() {
		shouldSolve = true;
	
	}
	
	public boolean shouldSolve() {
		return shouldSolve;
	}
	
	public boolean getSolved() {
		return isSolved;
	}

	public void update(float dt) {
		
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col].update(dt);
			}
		}
				
		//isSolved = board_is_valid();
		//if (isSolved) {
		//	System.out.println("SOLVED");
		//}
		

		
	}
	
	public void render(SpriteBatch sb) {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col].render(sb);
			}
		}
		
	}
	
	
	
}
	
	
