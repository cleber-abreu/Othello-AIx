package com.tcc.othello.model;

import java.util.ArrayList;

public interface PlayerObservable {
	void move(Player player, Locale locale);
	void move(FieldStatus color, ArrayList<Locale> changeDiscs);
}
