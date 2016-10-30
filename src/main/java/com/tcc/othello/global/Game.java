package com.tcc.othello.global;

import java.util.ArrayList;

import com.tcc.othello.model.Field;
import com.tcc.othello.model.FieldStatus;

public class Game {
	public static ArrayList<Field> fieldsBlack;
	public static ArrayList<Field> fieldsWhite;
	public static ArrayList<Field> moveOptions;
	
	public static void newGame() {
		fieldsBlack = new ArrayList<>();
		fieldsWhite = new ArrayList<>();
		
		fieldsBlack.add(new Field(4, 5, FieldStatus.BLACK));
		fieldsBlack.add(new Field(5, 4, FieldStatus.BLACK));
		fieldsWhite.add(new Field(4, 4, FieldStatus.WHITE));
		fieldsWhite.add(new Field(5, 5, FieldStatus.WHITE));
		
		moveOptions = Rules.moveOptions(fieldsBlack, fieldsWhite); 
	}
	
	public static void changeDiscs(int row, int col, FieldStatus status) {

		if (status == FieldStatus.BLACK) {
			fieldsBlack.addAll(Rules.changeDiscs(getFieldBlack(row, col), fieldsBlack, fieldsWhite));
		}
		else {
			fieldsWhite.addAll(Rules.changeDiscs(getFieldWhite(row, col), fieldsWhite, fieldsBlack));
		}
		
	}
	
	public static Field getFieldVoid(int row, int col) {
		Field field = null;
		for (Field f : moveOptions) {
			if (f.getRow() == row && f.getCol() == col) {
				return f;
			}
		}
		return field;
	}

	public static Field getFieldBlack(int row, int col) {
		Field field = null;
		for (Field f : fieldsBlack) {
			if (f.getRow() == row && f.getCol() == col) {
				return f;
			}
		}
		return field;
	}

	public static Field getFieldWhite(int row, int col) {
		Field field = null;
		for (Field f : fieldsWhite) {
			if (f.getRow() == row && f.getCol() == col) {
				return f;
			}
		}
		return field;
	}

}
