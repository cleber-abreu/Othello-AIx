package com.tcc.othello.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcc.othello.model.Test;

public class TestDAO extends BaseDAO<Test>{
	
	public static final String TABLE_NAME = "TEST";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_TEST_1 = "COLUMN_1";
	public static final String COLUMN_TEST_2 = "COLUMN_2";
	public static final String COLUMN_TEST_3 = "COLUMN_3";
	public static final String COLUMN_TEST_4 = "COLUMN_4";

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}
	
	@Override
	protected String getInsertQuery(Test test) {
		return new StringBuilder().append("INSERT INTO ").append(getTableName()).append("(")
		.append(COLUMN_TEST_1).append(", ")
		.append(COLUMN_TEST_2).append(", ")
		.append(COLUMN_TEST_3).append(", ")
		.append(COLUMN_TEST_4).append(") ")
		.append("VALUES (")
		.append("'").append(test.getColumn1()).append("', ")
		.append("'").append(test.getColumn2()).append("', ")
		.append(test.getColumn3()).append(", ")
		.append(test.getColumn4()).append(");").toString();
	}
	
	@Override
	protected Test resutSetToEntity(ResultSet rs) throws SQLException {
		Test test = new Test();
		test.setId(rs.getLong(COLUMN_ID));
		test.setColumn1(rs.getString(COLUMN_TEST_1));
		test.setColumn2(rs.getString(COLUMN_TEST_2));
		test.setColumn3(rs.getInt(COLUMN_TEST_3));
		test.setColumn4(rs.getDouble(COLUMN_TEST_4));
		
		return test;
	}
	
	public static String createTestTable() {
		return new StringBuilder()
				.append("CREATE TABLE ").append(TABLE_NAME).append("(")
				.append(COLUMN_ID).append(" INTEGER PRIMARY KEY NOT NULL, ")
				.append(COLUMN_TEST_1).append(" TEXT, ")
				.append(COLUMN_TEST_2).append(" CHAR(2), ")
				.append(COLUMN_TEST_3).append(" NUMBER, ")
				.append(COLUMN_TEST_4).append(" REAL) ").toString();
	}
}
