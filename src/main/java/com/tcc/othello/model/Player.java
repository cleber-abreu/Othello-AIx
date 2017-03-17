package com.tcc.othello.model;

import java.util.ArrayList;

public abstract class Player {	
	protected PlayerObservable playerObservable;
	private FieldStatus color;
	private Player opponent;
	
	public static Player initWith(PlayerType playerType){
		switch (playerType) {
		case RANDOM:
			return new PlayerRandom();
		case MINIMAX:
			return new PlayerMinmax();
		default :
			return new PlayerHuman();
		}
	}

	public abstract void takeTurn(ArrayList<Locale> moveOptions);

	public void setPlayerObservable(PlayerObservable playerObservable) {
		this.playerObservable = playerObservable;
	}
	
	public FieldStatus getColor() {
		return color;
	}

	public void setColor(FieldStatus color) {
		this.color = color;
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
}
