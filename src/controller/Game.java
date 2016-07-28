package controller;

import java.util.ArrayList;

import model.Field;
import model.FieldStatus;

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
		if (!moveOptions.isEmpty()) {
			
		}
	}
	
	public static Field getField(int row, int col) {
		Field field = null;
		for (Field f : moveOptions) {
			if (f.getRow() == row && f.getCol() == col) {
				return f;
			}
		}
		return field;
	}

}
