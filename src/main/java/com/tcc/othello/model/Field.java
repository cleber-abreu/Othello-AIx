package com.tcc.othello.model;

public class Field {
	private FieldStatus status;

	public Field() {
		this.status = FieldStatus.VOID;
	}

	public Field(FieldStatus status) {
		this.status = status;
	}

	public FieldStatus getStatus() {
		return status;
	}

	public void setStatus(FieldStatus status) {
		this.status = status;
	}

}
