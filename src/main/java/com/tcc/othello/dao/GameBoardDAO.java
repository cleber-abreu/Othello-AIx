package com.tcc.othello.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcc.othello.model.GameBoardBean;

public class GameBoardDAO extends BaseDAO<GameBoardBean>{
	
	public static final String TABLE_NAME = "GAME_BOARD";
	public static final String COLUMN_HERO_FIELD = "HERO_FIELD";
	public static final String COLUMN_OPPONENT_FIELD = "OPPONENT_FIELD";
	public static final String COLUMN_RATING = "RATING";

	@Override
	protected String getInsertQuery(GameBoardBean entity) {
		return new StringBuilder().append("INSERT INTO ").append(getTableName()).append("(")
				.append(COLUMN_HERO_FIELD).append(", ")
				.append(COLUMN_OPPONENT_FIELD).append(", ")
				.append(COLUMN_RATING).append(") ")
				.append("VALUES (")
				.append(entity.getHeroFields()).append(", ")
				.append(entity.getOponnentFields()).append(", ")
				.append(entity.getRating()).append(");").toString();
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}
	
	public static String createGameBoardTable() {
		return new StringBuilder()
				.append("CREATE TABLE ").append(TABLE_NAME).append("(")
				.append(COLUMN_HERO_FIELD).append(" NUMBER, ")
				.append(COLUMN_OPPONENT_FIELD).append(" NUMBER, ")
				.append(COLUMN_RATING).append(" NUMBER) ").toString();
	}

	@Override
	protected GameBoardBean resutSetToEntity(ResultSet rs) throws SQLException {
		GameBoardBean entity = new GameBoardBean();
		entity.setHeroFields(rs.getLong(COLUMN_HERO_FIELD));
		entity.setOponnentFields(rs.getLong(COLUMN_OPPONENT_FIELD));
		entity.setRating(rs.getInt(COLUMN_RATING));
		
		return entity;
	}
}
