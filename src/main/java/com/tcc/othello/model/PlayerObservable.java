package com.tcc.othello.model;

import java.util.ArrayList;

public interface PlayerObservable {
	void move(Player player, Locale locale);
	void paintMovement(FieldStatus playerColor, ArrayList<Locale> locales);
	void paintMoveOptions(FieldStatus playerColor, ArrayList<Locale> locales);
	void updateNumberDiscs(int numberDiscsPlayerBlack, int numberDiscsPlayerWhite);
	void gameOver(int winner);
}
