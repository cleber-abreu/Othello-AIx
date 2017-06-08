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
		updateNumberDiscs(Rules.countDiscs(FieldStatus.BLACK, fields), Rules.countDiscs(FieldStatus.WHITE, fields));
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
			// game over
			else {
				int countactivePlayer = Rules.countDiscs(activePlayer.getColor(), fields);
				int countOpponent = Rules.countDiscs(activePlayer.getOpponent().getColor(), fields);

				if (countactivePlayer > countOpponent) {
//					System.out.println("Preto ganhou com " + countBlackField + " x " + countWhiteField);
					gameOver(activePlayer.getColor());
					activePlayer.gameOver(true);
					activePlayer.getOpponent().gameOver(false);
				}
				else if (countactivePlayer < countOpponent) {
//					System.out.println("Branco ganhou com " + countWhiteField + " x " + countBlackField);
					gameOver(activePlayer.getOpponent().getColor());
					activePlayer.gameOver(false);
					activePlayer.getOpponent().gameOver(true);
				}
				else {
					gameOver(FieldStatus.VOID);
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
	
	@Override
	public void paintMovement(FieldStatus playerColor, ArrayList<Locale> locales) {
		gameObservable.paintMovement(playerColor, locales);
		updateNumberDiscs(Rules.countDiscs(FieldStatus.BLACK, fields), Rules.countDiscs(FieldStatus.WHITE, fields));
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
	public void gameOver(FieldStatus winnerColor) {
		gameObservable.gameOver(winnerColor);
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
