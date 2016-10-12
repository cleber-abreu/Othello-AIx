package global;

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
	
	private static Field getField (ArrayList<Field> fields, int row, int col) {
		for (Field field : fields) {
			if (field.getRow() == row && field.getCol() == col) {
				return field;
			}
		}	
		return null;
	}
	
	public static ArrayList<Field> changeDiscs(Field field, ArrayList<Field> fields, ArrayList<Field> fieldsAdv) {
		ArrayList<Field> discs = new ArrayList<>();
		ArrayList<Field> tmpAdd = new ArrayList<>();
		ArrayList<Field> tmpRemove = new ArrayList<>();
			
		// VERTICAL
		
		for (int row = field.getRow() + 1; 
				(row < 8 && containsDisc(fieldsAdv, row, field.getCol())); 
				row++) {
			
			tmpAdd.add(new Field(row, field.getCol(), field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, row, field.getCol()));
			
			if (containsDisc(fields, row+1, field.getCol())) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		tmpAdd = new ArrayList<>();
		tmpRemove = new ArrayList<>();
		
		for (int row = field.getRow() - 1; 
				(row > 1 && containsDisc(fieldsAdv, row, field.getCol())); 
				row--) {
			
			tmpAdd.add(new Field(row, field.getCol(), field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, row, field.getCol()));
			
			if (containsDisc(fields, row-1, field.getCol())) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		tmpAdd = new ArrayList<>();
		tmpRemove = new ArrayList<>();
		
		// HORIZONTAL
		
		for (int col = field.getCol() + 1; 
				(col < 8 && containsDisc(fieldsAdv, field.getRow(), col)); 
				col++) {

			tmpAdd.add(new Field(field.getRow(), col, field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, field.getRow(), col));
			
			if (containsDisc(fields, field.getRow(), col+1)) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		tmpAdd = new ArrayList<>();
		tmpRemove = new ArrayList<>();
		
		for (int col = field.getCol() - 1; 
				(col > 1 && containsDisc(fieldsAdv, field.getRow(), col)); 
				col--) {
			
			tmpAdd.add(new Field(field.getRow(), col, field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, field.getRow(), col));
			
			if (containsDisc(fields, field.getRow(), col-1)) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		tmpAdd = new ArrayList<>();
		tmpRemove = new ArrayList<>();
		
		// DIAGONAL
		
		for (int row = field.getRow() + 1, col = field.getCol() + 1; 
				(row < 8 && col < 8 && containsDisc(fieldsAdv, row, col)); 
				row++, col++) {
			
			tmpAdd.add(new Field(row, col, field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, row, col));
			
			if (containsDisc(fields, row+1, col+1)) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		tmpAdd = new ArrayList<>();
		tmpRemove = new ArrayList<>();
		
		for (int row = field.getRow() - 1, col = field.getCol() - 1; 
				(row > 1 && col > 1 && containsDisc(fieldsAdv, row, col)); 
				row--, col--) {
			
			tmpAdd.add(new Field(row, col, field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, row, col));
			
			if (containsDisc(fields, row-1, col-1)) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		tmpAdd = new ArrayList<>();
		tmpRemove = new ArrayList<>();
		
		for (int row = field.getRow() + 1, col = field.getCol() - 1; 
				(row > 1 && col > 1 && containsDisc(fieldsAdv, row, col)); 
				row++, col--) {
			
			tmpAdd.add(new Field(row, col, field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, row, col));
			
			if (containsDisc(fields, row+1, col-1)) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		tmpAdd = new ArrayList<>();
		tmpRemove = new ArrayList<>();
		
		for (int row = field.getRow() - 1, col = field.getCol() + 1; 
				(row > 1 && col > 1 && containsDisc(fieldsAdv, row, col)); 
				row--, col++) {
			
			tmpAdd.add(new Field(row, col, field.getStatus()));
			tmpRemove.add(getField(fieldsAdv, row, col));
			
			if (containsDisc(fields, row-1, col+1)) {
				discs.addAll(tmpAdd);
				fieldsAdv.removeAll(tmpRemove);
				break;
			}
		}
		
		return discs;
	}

	public static ArrayList<Field> moveOptions(ArrayList<Field> fields, ArrayList<Field> fieldsAdv) {
		ArrayList<Field> options = new ArrayList<>();
		FieldStatus optionStatus;
		
		if (fields != null && fieldsAdv != null && fields.size() > 0 && fieldsAdv.size() > 0) {
		
			optionStatus = (fields.get(0).getStatus() == FieldStatus.WHITE)
				? FieldStatus.OPTION_WHITE
				: FieldStatus.OPTION_BLACK;
		} else {
			return options;
		}

		for (Field field : fields) {

			// VERTICAL

			for (int row = field.getRow() + 1; 
					(row < 8 && containsDisc(fieldsAdv, row, field.getCol())); 
					row++) {
				
				if (!containsDisc(fields, fieldsAdv, row+1, field.getCol())) {
					options.add(new Field(row+1, field.getCol(), optionStatus));
				}
			}
			
			for (int row = field.getRow() - 1; 
					(row > 1 && containsDisc(fieldsAdv, row, field.getCol())); 
					row--) {
				
				if (!containsDisc(fields, fieldsAdv, row-1, field.getCol())) {
					options.add(new Field(row-1, field.getCol(), optionStatus));
				}
			}
			
			// HORIZONTAL

			for (int col = field.getCol() + 1; 
					(col < 8 && containsDisc(fieldsAdv, field.getRow(), col)); 
					col++) {
				
				if (!containsDisc(fields, fieldsAdv, field.getRow(), col+1)) {
					options.add(new Field(field.getRow(), col+1, optionStatus));
				}
			}
			
			for (int col = field.getCol() - 1; 
					(col > 1 && containsDisc(fieldsAdv, field.getRow(), col)); 
					col--) {
				
				if (!containsDisc(fields, fieldsAdv, field.getRow(), col-1)) {
					options.add(new Field(field.getRow(), col-1, optionStatus));
				}
			}

			// DIAGONAL

			for (int row = field.getRow() + 1, col = field.getCol() + 1; 
					(row < 8 && col < 8 && containsDisc(fieldsAdv, row, col)); 
					row++, col++) {
				
				if (!containsDisc(fields, fieldsAdv, row+1, col+1)) {
					options.add(new Field(row+1, col+1, optionStatus));
				}
			}
			
			for (int row = field.getRow() - 1, col = field.getCol() - 1; 
					(row > 1 && col > 1 && containsDisc(fieldsAdv, row, col)); 
					row--, col--) {
				
				if (!containsDisc(fields, fieldsAdv, row-1, col-1)) {
					options.add(new Field(row-1, col-1, optionStatus));
				}
			}
			
			for (int row = field.getRow() + 1, col = field.getCol() - 1; 
					(row < 8 && col > 1 && containsDisc(fieldsAdv, row, col)); 
					row++, col--) {
				
				if (!containsDisc(fields, fieldsAdv, row+1, col-1)) {
					options.add(new Field(row+1, col-1, optionStatus));
				}
			}
			
			for (int row = field.getRow() - 1, col = field.getCol() + 1; 
					(row > 1 && col < 8 && containsDisc(fieldsAdv, row, col)); 
					row--, col++) {
				
				if (!containsDisc(fields, fieldsAdv, row-1, col+1)) {
					options.add(new Field(row-1, col+1, optionStatus));
				}
			}
		}
			
		return options;
	}
}
