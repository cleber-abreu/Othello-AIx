package com.tcc.othello.model;

public abstract class Player {	
	private PlayerObservable gameObservable;
	
	protected abstract void takeTurn();
	
	protected void executeMovement(Locale locale){
		gameObservable.move(this, locale);
	}

	public void setGameObservable(PlayerObservable gameObservable) {
		this.gameObservable = gameObservable;
	}
}
