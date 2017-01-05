package com.tcc.othello.model;

import java.util.ArrayList;

import com.tcc.othello.gui.Gameboard;

public class PlayerHuman extends Player {

	private Gameboard gameboard;
	
	public PlayerHuman(FieldStatus colorDisc, Gameboard gameboard) {
		super(colorDisc);
		this.gameboard = gameboard;
	}
	
	private void enableOnClickAtDiscs(ArrayList<Field> fields) {
		
		if (fields != null) {
			for (Field field : fields) {
				gameboard.getField(field.getRow(), field.getCol())
						.getDisc().enableOnClick(this);
				gameboard.getField(field.getRow(), field.getCol())
						.getDisc().repaint();
			}
		}
	}
	
	@Override
	public void play(ArrayList<Field> moveOptions) {
		enableOnClickAtDiscs(moveOptions);
	}
	

	@Override
	public boolean is(Players type) {
		if (type == Players.HUMAN)
			return true;
		else
			return false;
	}

	@Override
	public Players getType() {
		return Players.HUMAN;
	}
	
}
