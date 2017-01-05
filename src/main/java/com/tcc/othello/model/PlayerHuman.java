package com.tcc.othello.model;

import java.util.ArrayList;

import com.tcc.othello.gui.Gameboard;

public class PlayerHuman extends Player {

	public PlayerHuman() {
		super();
	}
	
	public PlayerHuman(FieldStatus colorDisc) {
		super(colorDisc);
	}
	
	private void enableOnClickAtDiscs(ArrayList<Field> fields, Gameboard gameboard) {
		
		if (fields != null) {
			for (Field field : fields) {
				gameboard.getField(field.getRow(), field.getCol())
						.getDisc().enableOnClick(this);
				gameboard.getField(field.getRow(), field.getCol())
						.getDisc().repaint();
			}
		}
//		synchronized (onClick) {
//			try {
//				onClick.wait();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public void play(ArrayList<Field> moveOptions, Gameboard gameboard) {
		enableOnClickAtDiscs(moveOptions, gameboard);
	}
	
	@Override
	public void play(ArrayList<Field> moveOptions) {}

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
