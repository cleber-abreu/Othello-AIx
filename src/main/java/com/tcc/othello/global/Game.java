package com.tcc.othello.global;

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
	private Field[] fields;
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
		
		fields = new Field[64];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new Field();
		}
		gameObservable.move(activePlayer, new Locale(27));
		gameObservable.move(activePlayer, new Locale(36));
		gameObservable.move(activePlayer.getOpponent(), new Locale(28));
		gameObservable.move(activePlayer.getOpponent(), new Locale(35));
		fields[27].setStatus(activePlayer.getColor());
		fields[36].setStatus(activePlayer.getColor());
		fields[28].setStatus(activePlayer.getOpponent().getColor());
		fields[35].setStatus(activePlayer.getOpponent().getColor());
		
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
			gameObservable.move(player, locale);
			fields[locale.getId()].setStatus(player.getColor());
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
	
	private boolean containsDisc(Locale locale) {
		if (FieldStatus.VOID == fields[locale.getId()].getStatus()) {
			return false;
		}
		return true;
	}
}
