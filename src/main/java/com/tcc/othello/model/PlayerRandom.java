package com.tcc.othello.model;

import java.util.ArrayList;
import java.util.Random;

import com.tcc.othello.gui.MainWindow;

public class PlayerRandom extends Player {
	public PlayerRandom(FieldStatus colorDisc) {
		super(colorDisc);
	}

	@Override
	public void play(ArrayList<Field> moveOptions) {
		Random id = new Random();
		Field field = moveOptions.get(id.nextInt(moveOptions.size()));
		addDisc(field.getRow(), field.getCol());
		MainWindow.getGame().changeTurn();
	}

	@Override
	public boolean is(Players type) {
		if (type == Players.RANDOM)
			return true;
		else
			return false;
	}

	@Override
	public Players getType() {
		return Players.RANDOM;
	}
	
}
