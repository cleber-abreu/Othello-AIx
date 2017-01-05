package com.tcc.othello.model;

import java.util.ArrayList;

import com.tcc.othello.global.Rules;
import com.tcc.othello.gui.MainWindow;

public abstract class Player {	
	
	private FieldStatus colorDisc;
	private ArrayList<Field> discs;
	
	public abstract void play(ArrayList<Field> moveOptions);
	public abstract boolean is(Players type);
	public abstract Players getType();
	
	public Player(FieldStatus colorDisc) {
		this.colorDisc = colorDisc;
		discs = new ArrayList<Field>();
	}
	

	public FieldStatus getColorDisc() {
		return colorDisc;
	}

	public void setColorDisc(FieldStatus colorDisc) {
		this.colorDisc = colorDisc;
	}

	public ArrayList<Field> getDiscs() {
		return discs;
	}

	public void addDisc(Field field) {
		this.discs.add(field);
		this.discs.addAll(Rules.changeDiscs(field, this.getDiscs(), MainWindow.getGame().getOpponentPlayer().getDiscs()));
	}
	
	public void addDisc(int row, int col) {
		Field field = new Field(row, col, colorDisc);
		this.discs.add(field);
		this.discs.addAll(Rules.changeDiscs(field, this.getDiscs(), MainWindow.getGame().getOpponentPlayer().getDiscs()));
	}
}
