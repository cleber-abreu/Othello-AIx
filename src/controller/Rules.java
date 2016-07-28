package controller;

import java.util.ArrayList;

import model.Field;
import model.FieldStatus;

public class Rules {

	private static boolean containsDisc(ArrayList<Field> fields, int row, int col) {
		for (Field field : fields) {
			if (field.getRow() == row && field.getCol() == col) {
				return true;
			}
		}
		return false;
	}

	private static boolean containsDisc(ArrayList<Field> fields, ArrayList<Field> fieldsAdv, int row, int col) {
		for (Field field : fields) {
			if (field.getRow() == row && field.getCol() == col) {
				return true;
			}
		}

		for (Field field : fieldsAdv) {
			if (field.getRow() == row && field.getCol() == col) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Field> moveOptions(ArrayList<Field> fields, ArrayList<Field> fieldsAdv) {
		ArrayList<Field> options = new ArrayList<>();
		FieldStatus optionStatus = FieldStatus.OPTION_BLACK;

		for (Field field : fields) {

			// VERTICAL

			for (int row = field.getRow() + 1; 
					(row < 8 && containsDisc(fieldsAdv, row, field.getCol())); 
					row++) {
				
				if (!containsDisc(fields, fieldsAdv, row+1, field.getCol())) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(row+1, field.getCol(), optionStatus));
				}
			}
			
			for (int row = field.getRow() - 1; 
					(row > 1 && containsDisc(fieldsAdv, row, field.getCol())); 
					row--) {
				
				if (!containsDisc(fields, fieldsAdv, row-1, field.getCol())) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(row-1, field.getCol(), optionStatus));
				}
			}
			
			// HORIZONTAL

			for (int col = field.getCol() + 1; 
					(col < 8 && containsDisc(fieldsAdv, field.getRow(), col)); 
					col++) {
				
				if (!containsDisc(fields, fieldsAdv, field.getRow(), col+1)) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(field.getRow(), col+1, optionStatus));
				}
			}
			
			for (int col = field.getCol() - 1; 
					(col > 1 && containsDisc(fieldsAdv, field.getRow(), col)); 
					col--) {
				
				if (!containsDisc(fields, fieldsAdv, field.getRow(), col-1)) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(field.getRow(), col-1, optionStatus));
				}
			}

			// DIAGONAL

			for (int row = field.getRow() + 1, col = field.getCol() + 1; 
					(row < 8 && col < 8 && containsDisc(fieldsAdv, row, col)); 
					row++, col++) {
				
				if (!containsDisc(fields, fieldsAdv, row+1, col+1)) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(row+1, col+1, optionStatus));
				}
			}
			
			for (int row = field.getRow() - 1, col = field.getCol() - 1; 
					(row > 1 && col > 1 && containsDisc(fieldsAdv, row, col)); 
					row--, col--) {
				
				if (!containsDisc(fields, fieldsAdv, row-1, col-1)) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(row-1, col-1, optionStatus));
				}
			}
			
			for (int row = field.getRow() + 1, col = field.getCol() - 1; 
					(row > 1 && col > 1 && containsDisc(fieldsAdv, row, col)); 
					row++, col--) {
				
				if (!containsDisc(fields, fieldsAdv, row+1, col-1)) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(row+1, col-1, optionStatus));
				}
			}
			
			for (int row = field.getRow() - 1, col = field.getCol() + 1; 
					(row > 1 && col > 1 && containsDisc(fieldsAdv, row, col)); 
					row--, col++) {
				
				if (!containsDisc(fields, fieldsAdv, row-1, col+1)) {
					if (field.getStatus() == FieldStatus.WHITE) {
						optionStatus = FieldStatus.OPTION_WHITE;
					}
					options.add(new Field(row-1, col+1, optionStatus));
				}
			}
		}
			
		return options;
	}
}
