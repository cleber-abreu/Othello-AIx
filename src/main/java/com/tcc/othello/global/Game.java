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
		
		activePlayer.takeTurn();
	}
	
	public void changeTurn() {
		activePlayer = activePlayer.getOpponent();
		activePlayer.takeTurn();
	}

	/*
	 * implementacao do PlayerObservable, observar o aviso de nova jogada a ser executada
	 */
	@Override
	public void move(Player player, Locale locale) {
		if (!containsDisc(locale)) {
			fields[locale.getRow()][locale.getCol()].setStatus(player.getColor());
			gameObservable.move(player.getColor(), changeDiscs(locale));
			changeTurn();
		}
		else {
			activePlayer.takeTurn();
		}
	}

	/*
	 * implementacao do BoardObervable, observar o click no tabuleiro
	 */
	@Override
	public void onBoardClick(Locale locale) {
		if(activePlayer instanceof PlayerHuman){
			move(activePlayer, locale);
		}
	}
	
	@Override
	public void move(FieldStatus color, ArrayList<Locale> changeDiscs) {}
	
	private boolean containsDisc(Locale locale) {
		if (FieldStatus.VOID == fields[locale.getRow()][locale.getCol()].getStatus()) {
			return false;
		}
		return true;
	}
	
	private ArrayList<Locale> changeDiscs(Locale locale) {
		ArrayList<Locale> changeDiscs = new ArrayList<>();
		ArrayList<Locale> sequenceDiscs = new ArrayList<>();
		changeDiscs.add(locale);
		
		/*
		 * HORIZONTAL RIGHT
		 */
		for (int col = locale.getCol(); col < 7; col++) {
			if (fields[locale.getRow()][col+1].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[locale.getRow()][col].getStatus()) {
				sequenceDiscs.add(new Locale(locale.getRow(), col));
			}
			if (fields[locale.getRow()][col+1].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		sequenceDiscs = new ArrayList<>();
		
		/*
		 * HORIZONTAL LEFT
		 */
		for (int col = locale.getCol(); col > 0; col--) {
			if (fields[locale.getRow()][col-1].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[locale.getRow()][col].getStatus()) {
				sequenceDiscs.add(new Locale(locale.getRow(), col));
			}
			if (fields[locale.getRow()][col-1].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		sequenceDiscs = new ArrayList<>();
		
		/*
		 * VERTICAL DOWN
		 */
		for (int row = locale.getRow(); row < 7; row++) {
			if (fields[row+1][locale.getCol()].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[row][locale.getCol()].getStatus()) {
				sequenceDiscs.add(new Locale(row, locale.getCol()));
			}
			if (fields[row+1][locale.getCol()].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		sequenceDiscs = new ArrayList<>();
		
		/*
		 * VERTICAL UP
		 */
		for (int row = locale.getRow(); row > 0; row--) {
			if (fields[row-1][locale.getCol()].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[row][locale.getCol()].getStatus()) {
				sequenceDiscs.add(new Locale(row, locale.getCol()));
			}
			if (fields[row-1][locale.getCol()].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		sequenceDiscs = new ArrayList<>();
		
		/*
		 * DIAGONAL DOWN-RIGHT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row < 7 && col < 7; row++, col++) {
			if (fields[row+1][col+1].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[row][col].getStatus()) {
				sequenceDiscs.add(new Locale(row, col));
			}
			if (fields[row+1][col+1].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		sequenceDiscs = new ArrayList<>();
		
		/*
		 * DIAGONAL DOWN-LEFT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row < 7 && col > 0; row++, col--) {
			if (fields[row+1][col-1].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[row][col].getStatus()) {
				sequenceDiscs.add(new Locale(row, col));
			}
			if (fields[row+1][col-1].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		sequenceDiscs = new ArrayList<>();
		
		/*
		 * DIAGONAL UP-LEFT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row > 0 && col > 0; row--, col--) {
			if (fields[row-1][col-1].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[row][col].getStatus()) {
				sequenceDiscs.add(new Locale(row, col));
			}
			if (fields[row-1][col-1].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		sequenceDiscs = new ArrayList<>();
		
		/*
		 * DIAGONAL UP-RIGHT
		 */
		for (int row = locale.getRow(), col = locale.getCol(); row > 0 && col < 7; row--, col++) {
			if (fields[row-1][col+1].getStatus() == FieldStatus.VOID) {
				break;
			}
			if (activePlayer.getOpponent().getColor() == fields[row][col].getStatus()) {
				sequenceDiscs.add(new Locale(row, col));
			}
			if (fields[row-1][col+1].getStatus() == activePlayer.getColor()) {
				changeDiscs.addAll(sequenceDiscs);
				break;
			}
		}
		
		for (Locale localeChange : changeDiscs) {
			fields[localeChange.getRow()][localeChange.getCol()].setStatus(activePlayer.getColor());
		}
		
		return changeDiscs;
		
	}
	
}
