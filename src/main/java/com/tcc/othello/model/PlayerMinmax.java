package com.tcc.othello.model;

import java.util.ArrayList;

import com.tcc.othello.global.Rules;

public class PlayerMinmax extends Player {
	
	private static final int LEVEL = 8;
	
	private static final int SCORE_CO = 250;	// CORNER
	private static final int SCORE_WL = 15;		// WALL
	private static final int SCORE_SC = 10;		// ON THE SIDE OF THE CORNER
	private static final int SCORE_DC = -80;	// ON THE DIAGONAL OF THE CORNER
	private static final int SCORE_SW = -5;		// ON THE SIDE OF THE WALL
	private static final int SCORE_CE = 2;		// IN CENTER
	
	
	private static final int[][] RATING = {
		{SCORE_CO, SCORE_SC, SCORE_WL, SCORE_WL, SCORE_WL, SCORE_WL, SCORE_SC, SCORE_CO},
		{SCORE_SC, SCORE_DC, SCORE_SW, SCORE_SW, SCORE_SW, SCORE_SW, SCORE_DC, SCORE_SC},
		{SCORE_WL, SCORE_SW, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_SW, SCORE_WL},
		{SCORE_WL, SCORE_SW, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_SW, SCORE_WL},
		{SCORE_WL, SCORE_SW, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_SW, SCORE_WL},
		{SCORE_WL, SCORE_SW, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_CE, SCORE_SW, SCORE_WL},
		{SCORE_SC, SCORE_DC, SCORE_SW, SCORE_SW, SCORE_SW, SCORE_SW, SCORE_DC, SCORE_SC},
		{SCORE_CO, SCORE_SC, SCORE_WL, SCORE_WL, SCORE_WL, SCORE_WL, SCORE_SC, SCORE_CO}
	};
	

	@Override
	public void takeTurn(final ArrayList<Locale> moveOptions, final Field[][] fields) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Locale locale = minimax(moveOptions, fields);
				playerObservable.move(PlayerMinmax.this, locale);
			}
		}).start();
	}
	
	private Locale minimax(ArrayList<Locale> moveOptions, Field[][] fields) {
		Move bestMove = new Move(Integer.MIN_VALUE);
		
		for (Locale locale : moveOptions) {
			printaIsso(new Move(locale, 0));
			Move m = minimax(moveOptions, fields, this, new Move(locale, 0), 1);
			bestMove = bestMove.value > m.value ? bestMove : m;
		}
		
		printaIsso(bestMove);
		return bestMove.locale;
	}
			
	private Move minimax(ArrayList<Locale> moveOptions, Field[][] fields, Player player, Move move, int level) {
		int modifier = level % 2 == 0 ? 1 : -1;
		
		if(level == LEVEL) {
			return new Move(move.locale, move.value + (rateFrom(move.locale) * modifier));
		}
		
		Field[][] newFields = Rules.simulate(player, move.locale, fields);
		ArrayList<Locale> newMoveOptions = Rules.updateMoveOptions(player, newFields);
		
		Move bestMove = new Move(Integer.MIN_VALUE);
		for (Locale locale : newMoveOptions) {
			Move m = minimax(moveOptions, fields, this, new Move(locale, 0), level + 1);
			bestMove = bestMove.value > m.value ? bestMove : m;
		}
		
		bestMove.value =  move.value + (bestMove.value * modifier);
		return bestMove;
	}
	
	private int rateFrom(Locale locale) {
		return RATING[locale.getRow()][locale.getCol()];
	}
	
	private void printaIsso(Field[][] f) {
		for (Field[] fields : f) {
			for (Field field : fields) {
				if(field.getStatus().equals(FieldStatus.VOID)) {
					System.out.print(".");
				}else if(field.getStatus().equals(FieldStatus.WHITE)){
					System.out.print("W");
				}else if(field.getStatus().equals(FieldStatus.BLACK)){
					System.out.print("B");
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
	private void printaIsso(Move m) {
		System.out.println("col:" + m.locale.getCol() + "\nrow:" + m.locale.getRow() + "\nval" + m.value + "\n\n\n");
	}
	
	class Move{
		int value;
		Locale locale;
		
		public Move(Locale locale, int value) {
			this.locale = locale;
			this.value = value;
		}
		
		public Move(int value) {
			this.value = value;
		}
	}
}
