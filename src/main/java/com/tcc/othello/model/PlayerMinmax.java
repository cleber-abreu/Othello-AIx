package com.tcc.othello.model;

import java.util.ArrayList;

import com.tcc.othello.global.Rules;

public class PlayerMinmax extends Player {
	
	private static final int LEVEL = 3;
	
	private static final int[][] RATING = {
		{100, -20, 10, 5, 5, 10, -20, 100},
		{-20, -50, -2, -2, -2, -2, -50, -20},
		{10, -2, -1, -1, -1, -1, -2, 10},
		{5, -2, -1, -1, -1, -1, -2, 5},
		{5, -2, -1, -1, -1, -1, -2, 5},
		{10, -2, -1, -1, -1, -1, -2, 10},
		{-20, -50, -2, -2, -2, -2, -50, -20},
		{100, -20, 10, 5, 5, 10, -20, 100}
	};
	

	@Override
	public void takeTurn(final ArrayList<Locale> moveOptions, final Field[][] fields) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Move move = minimax(moveOptions, fields, PlayerMinmax.this, new Move(0), 0);
				playerObservable.move(PlayerMinmax.this, move.locale);
			}
		}).start();
	}
	
	private Move minimax(ArrayList<Locale> moveOptions, Field[][] fields, Player player, Move move, int level) {
		int modifier = level % 2 == 0 ? 1 : -1;
		Move bestMove = new Move(Integer.MIN_VALUE);
		Field[][] newFields;
		
		for (Locale locale : moveOptions) {
			if(level == LEVEL) {
				return move;
			}
			Move aux = new Move(locale, RATING[locale.getRow()][locale.getCol()]);
			newFields = Rules.simulate(player, locale, fields);
			ArrayList<Locale> newMoveOptions = Rules.updateMoveOptions(player, newFields);
			Move magic = minimax(newMoveOptions, newFields, player.getOpponent(), aux, level + 1);
			bestMove = bestMove.value > magic.value ? bestMove : magic;
		}
		
		bestMove.value = bestMove.value * modifier + move.value;
		return bestMove;
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
