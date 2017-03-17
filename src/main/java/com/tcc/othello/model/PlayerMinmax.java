package com.tcc.othello.model;

import java.util.ArrayList;

public class PlayerMinmax extends Player {
	
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
	public void takeTurn(ArrayList<Locale> moveOptions) {
		playerObservable.move(this, max(moveOptions));
	}
	
	private Locale min(ArrayList<Locale> moveOptions) {
		Locale minMove = moveOptions.get(0);
		for (Locale locale : moveOptions) {
			if (RATING[locale.getRow()][locale.getCol()] < RATING[minMove.getRow()][minMove.getCol()]) {
				minMove = locale;
			}
		}
		return minMove;
	}
	
	private Locale max(ArrayList<Locale> moveOptions) {
		Locale maxMove = moveOptions.get(0);
		for (Locale locale : moveOptions) {
			if (RATING[locale.getRow()][locale.getCol()] > RATING[maxMove.getRow()][maxMove.getCol()]) {
				maxMove = locale;
			}
		}
		return maxMove;
	}

}
