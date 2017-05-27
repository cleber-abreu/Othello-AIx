package com.tcc.othello.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.tcc.othello.global.Rules;

public class PlayerMinmax extends Player {
	
	private static final int LEVEL = 5;

	private static final int SCORE_CO = 100;	// CORNER
	private static final int SCORE_WL = 15;		// WALL
	private static final int SCORE_SC = 15;		// ON THE SIDE OF THE CORNER
	private static final int SCORE_DC = -20;	// ON THE DIAGONAL OF THE CORNER
	private static final int SCORE_SW = -5;		// ON THE SIDE OF THE WALL
	private static final int SCORE_CE = 2;		// IN CENTER
	
//	HashMap<Integer, ArrayList<Move>> olharIsso = new HashMap<>();
	
	Comparator<Locale> comparator = new Comparator<Locale>() {
		@Override
		public int compare(Locale o1, Locale o2) {
			return RATING[o1.getRow()][o1.getCol()] + RATING[o2.getRow()][o2.getCol()];
		}
	};
	
	
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
					Thread.sleep(6);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				long time = System.currentTimeMillis();
				Locale locale = minimax(moveOptions, fields);
				playerObservable.move(PlayerMinmax.this, locale);
//				time = System.currentTimeMillis() - time;
//				System.out.println(time);
			}
		}).start();
	}
	
	private Locale minimax(ArrayList<Locale> moveOptions, Field[][] fields) {
		Locale locale = minimaxMax(moveOptions, fields, this, 0, 0).locale;
		return locale == null ? Collections.max(moveOptions, comparator) : locale;
	}
	
	private Move minimaxMax(ArrayList<Locale> moveOptions, Field[][] fields, Player player, int value, int level) {
		int bestMove = -10_000_000;
		if(moveOptions.size() == 0) {
			return new Move(null, -5_000_000);
		}else if(level >= LEVEL) {
			Locale locale = Collections.max(moveOptions, comparator);
			return new Move(locale, value + rateFrom(locale, fields, player.getColor()));
		}else {
			Locale aux = null;
			
			for (Locale locale : moveOptions) {
				Field[][] newFields = Rules.simulate(player, locale, fields);
				ArrayList<Locale> newMoveOptions = Rules.updateMoveOptions(player.getOpponent(), newFields);
				
				int m = minimaxMin(newMoveOptions, newFields, player.getOpponent(), value + rateFrom(locale, fields, player.getColor()), level +1);
				if(m > bestMove) {
					bestMove = m;
					aux = locale;
				}
			}
			
			return new Move(aux, aux == null ? -5_000_000 : value + rateFrom(aux, fields, player.getColor()));
		}
	}
	
	private int minimaxMin(ArrayList<Locale> moveOptions, Field[][] fields, Player player, int value, int level) {
		if(moveOptions.size() == 0) {
			return 5_000_000;
		}else {
			Locale locale = Collections.max(moveOptions, comparator);
			
			Field[][] newFields = Rules.simulate(player, locale, fields);
			ArrayList<Locale> newMoveOptions = Rules.updateMoveOptions(player.getOpponent(), newFields);
			
			return minimaxMax(newMoveOptions, newFields, player.getOpponent(), value - rateFrom(locale, fields, player.getColor()), level + 1).value;
		}
	}
	
	private int minimaxMin(ArrayList<Locale> moveOptions, Field[][] fields, Player player, Move move, int level) {
		
		Field[][] newFields = Rules.simulate(player, move.locale, fields);
		ArrayList<Locale> newMoveOptions = Rules.updateMoveOptions(player, newFields);
		
		if(newMoveOptions.size() == 0) {
			return -1_000_000_000;
		}else {
			Locale aux = Collections.max(newMoveOptions, comparator);
			
			return minimaxMax(newMoveOptions, newFields, player.getOpponent(), new Move(aux, move.value - RATING[aux.getRow()][aux.getCol()]), level+1);
		}
	}
		
	private int minimaxMax(ArrayList<Locale> moveOptions, Field[][] fields, Player player, Move move, int level) {
		boolean lastLevel = level >= LEVEL;
		
		Field[][] newFields = Rules.simulate(player, move.locale, fields);
		ArrayList<Locale> newMoveOptions = Rules.updateMoveOptions(player, newFields);
		
		if(newMoveOptions.size() == 0) {
			return 1_000_000_000;
		}else if(lastLevel){
			Locale aux = Collections.max(newMoveOptions, comparator);
			
			return move.value + RATING[aux.getRow()][aux.getCol()];
		}else {
			int bestMove = -1_000_000_000;
			for (Locale locale : newMoveOptions) {
				int m = minimaxMin(moveOptions, fields, player.getOpponent(), new Move(locale, move.value - (RATING[locale.getRow()][locale.getCol()]*2)), level + 1);
				bestMove = bestMove > m ? bestMove : m;
			}

			return bestMove;
		}
	}
	
	private int rateFrom(Locale locale, Field[][] fields, FieldStatus playerColor) {
//		return RATING[locale.getRow()][locale.getCol()] + Rules.difference(fields, playerColor);
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
	
	private void addMoveNoHashMap(Integer position, Move move) {
//		if(olharIsso.get(position) == null) {
//			olharIsso.put(position, new ArrayList<Move>());
//		}
//		
//		olharIsso.get(position).add(move);
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
		
		@Override
		public String toString() {
			return "[row: " + locale.getRow() + " col:" + locale.getCol() + "]: " + value ;
		}
	}
}
