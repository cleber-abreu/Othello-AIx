package com.tcc.othello.global;

import com.tcc.othello.model.PlayerObservable;
import com.tcc.othello.model.Locale;
import com.tcc.othello.model.Player;

public class Game implements PlayerObservable {
	
	private PlayerObservable gameObservable;
	
	public Game(PlayerObservable gameObservable) {
		this.gameObservable = gameObservable;
	}

	public void start() {
	}
	
	public static void changeTurn() {
	}

	@Override
	public void move(Player player, Locale locale) {
		gameObservable.move(player, locale);
	}
}
