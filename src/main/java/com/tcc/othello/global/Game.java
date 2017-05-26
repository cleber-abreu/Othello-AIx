package com.tcc.othello.global;

import java.util.ArrayList;

import com.tcc.othello.model.BoardObservable;
import com.tcc.othello.model.Field;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Locale;
import com.tcc.othello.model.Player;
import com.tcc.othello.model.PlayerHuman;
import com.tcc.othello.model.PlayerObservable;
import com.tcc.othello.model.PlayerType;

public class Game implements PlayerObservable, BoardObservable{
	private PlayerObservable gameObservable;
	private Field[][] fields;
	private Player activePlayer;
	private ArrayList<Locale> moveOptions;
	
	public Game(PlayerObservable gameObservable) {
		this.gameObservable = gameObservable;
	}

	public void start(PlayerType blackPlayer, PlayerType whitePlayer) {
		activePlayer = Player.initWith(blackPlayer);
		Player opponent = Player.initWith(whitePlayer);
		
		activePlayer.setColor(FieldStatus.BLACK);
		opponent.setColor(FieldStatus.WHITE);
		
		activePlayer.setPlayerObservable(this);
		opponent.setPlayerObservable(this);
		
		activePlayer.setOpponent(opponent);
		opponent.setOpponent(activePlayer);
		
		fields = new Field[8][8];
		for (int row = 0; row < fields.length; row++) {
			for (int col = 0; col < fields.length; col++) {
				fields[row][col] = new Field();
			}
		}
		gameObservable.move(activePlayer, new Locale(3, 4));
		gameObservable.move(activePlayer, new Locale(4, 3));
		gameObservable.move(activePlayer.getOpponent(), new Locale(3, 3));
		gameObservable.move(activePlayer.getOpponent(), new Locale(4, 4));
		fields[3][4].setStatus(activePlayer.getColor());
		fields[4][3].setStatus(activePlayer.getColor());
		fields[3][3].setStatus(activePlayer.getOpponent().getColor());
		fields[4][4].setStatus(activePlayer.getOpponent().getColor());
		updateNumberDiscs(countDiscs(FieldStatus.BLACK, fields), countDiscs(FieldStatus.WHITE, fields));
		moveOptions = Rules.updateMoveOptions(activePlayer, fields);
		paintMoveOptions(activePlayer.getColor(), moveOptions);
		activePlayer.takeTurn(moveOptions, fields);
	}
	
	public void changeTurn() {
		activePlayer = activePlayer.getOpponent();
		moveOptions = Rules.updateMoveOptions(activePlayer, fields);
		if (moveOptions.size() > 0) {
			paintMoveOptions(activePlayer.getColor(), moveOptions);
			activePlayer.takeTurn(moveOptions, fields);
		}
		else {
			// passa a vez
			activePlayer = activePlayer.getOpponent();
			moveOptions = Rules.updateMoveOptions(activePlayer, fields);
			if (moveOptions.size() > 0) {
				paintMoveOptions(activePlayer.getColor(), moveOptions);
				activePlayer.takeTurn(moveOptions, fields);
			}
			else {
				int countBlackField = countDiscs(FieldStatus.BLACK, fields);
				int countWhiteField = countDiscs(FieldStatus.WHITE, fields);
				
				if (countBlackField > countWhiteField) {
					System.out.println("Preto ganhou com " + countBlackField + " x " + countWhiteField);
					gameOver(1);
				}
				else if (countBlackField < countWhiteField) {
					System.out.println("Branco ganhou com " + countWhiteField + " x " + countBlackField);
					gameOver(2);
				}
				else {
					gameOver(0);
				}
			}
		}
	}

	/*
	 * implementacao do PlayerObservable, observar o aviso de nova jogada a ser executada
	 */
	@Override
	public void move(Player player, Locale locale) {
		if (emptyField(locale, fields)) {
			fields[locale.getRow()][locale.getCol()].setStatus(player.getColor());
			paintMovement(player.getColor(), Rules.changeDiscs(player, locale, fields));
			changeTurn();
		}
		else {
			activePlayer.takeTurn(moveOptions, fields);
		}
	}
	
	
	/*
	 * implementacao do BoardObervable, observar o click no tabuleiro
	 */
	@Override
	public void onBoardClick(Locale locale) {
		if(activePlayer instanceof PlayerHuman){
			if (availablePlay(locale)) {
				move(activePlayer, locale);
			}
		}
	}
	
	private int countDiscs(FieldStatus playerColor, Field[][] fields) {
		int count = 0;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (fields[row][col].getStatus() == playerColor) {
					count++;
				}
			}
		}
		return count;
	}
	
	@Override
	public void paintMovement(FieldStatus playerColor, ArrayList<Locale> locales) {
		gameObservable.paintMovement(playerColor, locales);
		updateNumberDiscs(countDiscs(FieldStatus.BLACK, fields), countDiscs(FieldStatus.WHITE, fields));
	}
	
	@Override
	public void paintMoveOptions(FieldStatus playerColor, ArrayList<Locale> locales) {
		gameObservable.paintMoveOptions(playerColor, locales);
	}
	
	@Override
	public void updateNumberDiscs(int numberDiscsPlayer1, int numberDiscsPlayer2) {
		gameObservable.updateNumberDiscs(numberDiscsPlayer1, numberDiscsPlayer2);
	}
	
	@Override
	public void gameOver(int winner) {
		gameObservable.gameOver(winner);
	}
	
	private boolean emptyField(Locale locale, Field[][] fields) {
		return Rules.containsDisc(locale.getRow(), locale.getCol(), FieldStatus.VOID, fields);
	}
	
	private boolean availablePlay(Locale locale) {
		for (Locale locales: moveOptions) {
			if (locales.equals(locale)) {
				return true;
			}
		}
		return false;
	}
}
