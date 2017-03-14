package com.tcc.othello.model;

public class Locale {
	private int row, col;
	
	public Locale(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (((Locale) obj).getRow() == getRow() && ((Locale) obj).getCol() == getCol()) {
			return true;
		}
		return false;
	}
}
