package com.tcc.othello.global;

import java.util.ArrayList;

import com.tcc.othello.model.Field;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Locale;
import com.tcc.othello.model.Player;

public class Rules {
	
	
	public static boolean containsDisc(int row, int col, FieldStatus playerColor, Field[][] fields) {
		if (playerColor == fields[row][col].getStatus()) {
			return true;
		}
		return false;
	}
	
	public static boolean emptyField(int row, int col, Field[][] fields) {
		return containsDisc(row, col, FieldStatus.VOID, fields);
	}
	
	public static Field[][] simulate(Player player, Locale locale, Field[][] fields) {
		Field[][] copy = new Field[fields.length][fields[0].length];
		
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[0].length; j++) {
				copy[i][j] = new Field(fields[i][j].getStatus());
			}
		}
		
		changeDiscs(player, locale, copy);
		
		return copy;
	}

	
	public static ArrayList<Locale> changeDiscs(Player player, Locale locale, Field[][] fields) {
		ArrayList<Locale> changeDiscs = new ArrayList<>();
		changeDiscs.add(locale);
		
		/*
		 * HORIZONTAL RIGHT
		 */
		for (int col = locale.getCol(); col < 7; col++) {
			if (emptyField(locale.getRow(), col+1, fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[locale.getRow()][col].getStatus()) {
				changeDiscs.add(new Locale(locale.getRow(), col));
			}
			if (fields[locale.getRow()][col+1].getStatus() == player.getColor()) {
				break;
			}
		}
		
		/*
		 * HORIZONTAL LEFT
		 */
		for (int col = locale.getCol(); col > 0; col--) {
			if (emptyField(locale.getRow(), col-1, fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[locale.getRow()][col].getStatus()) {
				changeDiscs.add(new Locale(locale.getRow(), col));
			}
			if (fields[locale.getRow()][col-1].getStatus() == player.getColor()) {
				break;
			}
		}
		
		/*
		 * VERTICAL DOWN
		 */
		for (int row = locale.getRow(); row < 7; row++) {
			if (emptyField(row+1, locale.getCol(), fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[row][locale.getCol()].getStatus()) {
				changeDiscs.add(new Locale(row, locale.getCol()));
			}
			if (fields[row+1][locale.getCol()].getStatus() == player.getColor()) {
				break;
			}
		}
		
		/*
		 * VERTICAL UP
		 */
		for (int row = locale.getRow(); row > 0; row--) {
			if (emptyField(row-1, locale.getCol(), fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[row][locale.getCol()].getStatus()) {
				changeDiscs.add(new Locale(row, locale.getCol()));
			}
			if (fields[row-1][locale.getCol()].getStatus() == player.getColor()) {
				break;
			}
		}
		
		/*
		 * DIAGONAL DOWN-RIGHT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row < 7 && col < 7; row++, col++) {
			if (emptyField(row+1, col+1, fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[row][col].getStatus()) {
				changeDiscs.add(new Locale(row, col));
			}
			if (fields[row+1][col+1].getStatus() == player.getColor()) {
				break;
			}
		}
		
		/*
		 * DIAGONAL DOWN-LEFT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row < 7 && col > 0; row++, col--) {
			if (emptyField(row+1, col-1, fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[row][col].getStatus()) {
				changeDiscs.add(new Locale(row, col));
			}
			if (fields[row+1][col-1].getStatus() == player.getColor()) {
				break;
			}
		}
		
		/*
		 * DIAGONAL UP-LEFT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row > 0 && col > 0; row--, col--) {
			if (emptyField(row-1, col-1, fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[row][col].getStatus()) {
				changeDiscs.add(new Locale(row, col));
			}
			if (fields[row-1][col-1].getStatus() == player.getColor()) {
				break;
			}
		}
		
		/*
		 * DIAGONAL UP-RIGHT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row > 0 && col < 7; row--, col++) {
			if (emptyField(row-1, col+1, fields)) {
				break;
			}
			if (player.getOpponent().getColor() == fields[row][col].getStatus()) {
				changeDiscs.add(new Locale(row, col));
			}
			if (fields[row-1][col+1].getStatus() == player.getColor()) {
				break;
			}
		}
		
		for (Locale localeChange : changeDiscs) {
			fields[localeChange.getRow()][localeChange.getCol()].setStatus(player.getColor());
		}
		
		return changeDiscs;
	}
	
	public static ArrayList<Locale> updateMoveOptions(Player activePlayer, Field[][] fields) {
		ArrayList<Locale> moveOptions = new ArrayList<Locale>();
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (containsDisc(row, col, activePlayer.getColor(), fields)) {
					/*
					* HORIZONTAL RIGHT
					*/
					for (int sequenceCol = col+1; sequenceCol < 7; sequenceCol++) {
						if (!containsDisc(row, sequenceCol, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(row, sequenceCol+1, fields)) {
							addIfNotContains(moveOptions, new Locale(row, sequenceCol+1));
							break;
						}
					}
					
					/*
					* HORIZONTAL LEFT
					*/
					for (int sequenceCol = col-1; sequenceCol > 0; sequenceCol--) {
						if (!containsDisc(row, sequenceCol, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(row, sequenceCol-1, fields)) {
							addIfNotContains(moveOptions, new Locale(row, sequenceCol-1));
							break;
						}
					}
					
					/*
					* VERTICAL DOWN
					*/
					for (int sequenceRow = row+1; sequenceRow < 7; sequenceRow++) {
						if (!containsDisc(sequenceRow, col, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(sequenceRow+1, col, fields)) {
							addIfNotContains(moveOptions, new Locale(sequenceRow+1, col));
							break;
						}
					}
					
					/*
					* VERTICAL UP
					*/
					for (int sequenceRow = row-1; sequenceRow > 0; sequenceRow--) {
						if (!containsDisc(sequenceRow, col, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(sequenceRow-1, col, fields)) {
							addIfNotContains(moveOptions, new Locale(sequenceRow-1, col));
							break;
						}
					}
					
					/*
					* DIAGONAL DOWN-RIGHT
					*/
					for (int sequenceRow = row+1, sequenceCol = col+1; sequenceRow < 7 && sequenceCol < 7; sequenceRow++, sequenceCol++) {
						if (!containsDisc(sequenceRow, sequenceCol, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(sequenceRow+1, sequenceCol+1, fields)) {
							addIfNotContains(moveOptions, new Locale(sequenceRow+1, sequenceCol+1));
							break;
						}
					}
					
					/*
					* DIAGONAL DOWN-LEFT
					*/
					for (int sequenceRow = row+1, sequenceCol = col-1; sequenceRow < 7 && sequenceCol > 0; sequenceRow++, sequenceCol--) {
						if (!containsDisc(sequenceRow, sequenceCol, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(sequenceRow+1, sequenceCol-1, fields)) {
							addIfNotContains(moveOptions, new Locale(sequenceRow+1, sequenceCol-1));
							break;
						}
					}

					/*
					* DIAGONAL UP-RIGHT
					*/
					for (int sequenceRow = row-1, sequenceCol = col+1; sequenceRow > 0 && sequenceCol < 7; sequenceRow--, sequenceCol++) {
						if (!containsDisc(sequenceRow, sequenceCol, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(sequenceRow-1, sequenceCol+1, fields)) {
							addIfNotContains(moveOptions, new Locale(sequenceRow-1, sequenceCol+1));
							break;
						}
					}
					
					/*
					* DIAGONAL UP-LEFT
					*/
					for (int sequenceRow = row-1, sequenceCol = col-1; sequenceRow > 0 && sequenceCol > 0; sequenceRow--, sequenceCol--) {
						if (!containsDisc(sequenceRow, sequenceCol, activePlayer.getOpponent().getColor(), fields)) {
							break;
						}
						else if (emptyField(sequenceRow-1, sequenceCol-1, fields)) {
							addIfNotContains(moveOptions, new Locale(sequenceRow-1, sequenceCol-1));
							break;
						}
					}
				}
			}
		}
		
		return moveOptions;
	}
	
	private static void addIfNotContains(ArrayList<Locale> locales, Locale locale) {
		if(!locales.contains(locale)) {
			locales.add(locale);
		}
		
	}
	
}
