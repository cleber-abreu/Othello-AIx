package com.tcc.othello.model;

public class GameBoardBean {
	private long heroFields;
	private long oponnentFields;
	private int rating;
	
	public long getHeroFields() {
		return heroFields;
	}
	public void setHeroFields(long heroFields) {
		this.heroFields = heroFields;
	}
	public long getOponnentFields() {
		return oponnentFields;
	}
	public void setOponnentFields(long oponnentFields) {
		this.oponnentFields = oponnentFields;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}

	
}
