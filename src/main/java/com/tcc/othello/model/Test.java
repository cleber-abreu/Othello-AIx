package com.tcc.othello.model;

public class Test {
	private long id;
	private String column1;
	private String column2;
	private int column3;
	private double column4;
	
	public Test() {
		super();
	}

	public Test(String column1, String column2, int column3, double column4) {
		super();
		this.column1 = column1;
		this.column2 = column2;
		this.column3 = column3;
		this.column4 = column4;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public int getColumn3() {
		return column3;
	}

	public void setColumn3(int column3) {
		this.column3 = column3;
	}

	public double getColumn4() {
		return column4;
	}

	public void setColumn4(double column4) {
		this.column4 = column4;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", column1=" + column1 + ", column2=" + column2 + ", column3=" + column3
				+ ", column4=" + column4 + "]";
	}
	
	

}
