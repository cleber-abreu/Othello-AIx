package com.tcc.othello.model;

import java.util.ArrayList;

public interface BoardObservable {
	void onBoardClick(Locale locale);
	void paintMovement(FieldStatus playerColor, ArrayList<Locale> locales);
	void paintMoveOptions(FieldStatus playerColor, ArrayList<Locale> locales);
}
