package com.tcc.othello.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tcc.othello.model.Test;

public class TestDAO extends BaseDAO{

	public static String createTable() {
		return new StringBuilder()
				.append("CREATE TABLE ").append(DB.TableTest.TABLE_NAME).append("(")
				.append(DB.TableTest.COLUMN_ID).append(" INTEGER PRIMARY KEY NOT NULL, ")
				.append(DB.TableTest.COLUMN_TEST_1).append(" TEXT, ")
				.append(DB.TableTest.COLUMN_TEST_2).append(" CHAR(2), ")
				.append(DB.TableTest.COLUMN_TEST_3).append(" NUMBER, ")
				.append(DB.TableTest.COLUMN_TEST_4).append(" REAL) ").toString();
	}
	
	public static void insert(Test test) {
		try {
			Connection c = DB.openConnection();
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();
			StringBuilder stb = new StringBuilder();
			stb.append("INSERT INTO ").append(DB.TableTest.TABLE_NAME).append("(")
				.append(DB.TableTest.COLUMN_TEST_1).append(", ")
				.append(DB.TableTest.COLUMN_TEST_2).append(", ")
				.append(DB.TableTest.COLUMN_TEST_3).append(", ")
				.append(DB.TableTest.COLUMN_TEST_4).append(") ")
				.append("VALUES (")
				.append("'").append(test.getColumn1()).append("', ")
				.append("'").append(test.getColumn2()).append("', ")
				.append(test.getColumn3()).append(", ")
				.append(test.getColumn4()).append(");");
			stmt.executeUpdate(stb.toString());

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Test> getList() {
		
		List<Test> tests = new ArrayList<>();
		
		try {
			Connection c = DB.openConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + DB.TableTest.TABLE_NAME);
			
			Test test;
			while (rs.next()) {
				test = new Test();
				test.setId(rs.getLong(DB.TableTest.COLUMN_ID));
				test.setColumn1(rs.getString(DB.TableTest.COLUMN_TEST_1));
				test.setColumn2(rs.getString(DB.TableTest.COLUMN_TEST_2));
				test.setColumn3(rs.getInt(DB.TableTest.COLUMN_TEST_3));
				test.setColumn4(rs.getDouble(DB.TableTest.COLUMN_TEST_4));
				tests.add(test);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tests;
	}
}
