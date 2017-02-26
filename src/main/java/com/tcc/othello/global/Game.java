package com.tcc.othello.global;

import com.tcc.othello.model.PlayerObservable;
import com.tcc.othello.model.PlayerType;
import com.tcc.othello.model.BoardObservable;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Locale;
import com.tcc.othello.model.Player;
import com.tcc.othello.model.PlayerHuman;

public class Game implements PlayerObservable, BoardObservable{
	private PlayerObservable gameObservable;
	
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
		gameObservable.move(player, locale);
		changeTurn();
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
}
