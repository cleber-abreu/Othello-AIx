package model;

public class Field {
	private int row;
	private int col;
	private FieldStatus status;

	public Field() {
	}

	public Field(int row, int col) {
		this.row = row;
		this.col = col;
		this.status = FieldStatus.VOID;
	}

	public Field(int row, int col, FieldStatus status) {
		this.row = row;
		this.col = col;
		this.status = status;
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

	public FieldStatus getStatus() {
		return status;
	}

	public void setStatus(FieldStatus status) {
		this.status = status;
	}

}
