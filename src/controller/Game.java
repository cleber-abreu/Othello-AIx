package controller;

import java.util.ArrayList;

import model.Field;
import model.FieldStatus;

public class Game {
	private ArrayList<Field> fieldsBlack;
	private ArrayList<Field> fieldsWhite;
	private ArrayList<Field> possibleMoves;
	
	public Game() {
		fieldsBlack = new ArrayList<>();
		fieldsWhite = new ArrayList<>();
		
		fieldsBlack.add(new Field(4, 5, FieldStatus.BLACK));
		fieldsBlack.add(new Field(5, 4, FieldStatus.BLACK));
		fieldsWhite.add(new Field(4, 4, FieldStatus.WHITE));
		fieldsWhite.add(new Field(5, 5, FieldStatus.WHITE));
		
		possibleMoves = Rules.possibleMoves(fieldsBlack, fieldsWhite); 
		if (!possibleMoves.isEmpty()) {
			
		}
	}

	public ArrayList<Field> getFieldsBlack() {
		return fieldsBlack;
	}

	public void setFieldsBlack(ArrayList<Field> fieldsBlack) {
		this.fieldsBlack = fieldsBlack;
	}

	public ArrayList<Field> getFieldsWhite() {
		return fieldsWhite;
	}

	public void setFieldsWhite(ArrayList<Field> fieldsWhite) {
		this.fieldsWhite = fieldsWhite;
	}

	public ArrayList<Field> getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(ArrayList<Field> possibleMoves) {
		this.possibleMoves = possibleMoves;
	}
	
}
