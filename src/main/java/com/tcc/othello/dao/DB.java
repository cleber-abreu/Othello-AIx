package com.tcc.othello.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.tcc.othello.model.Test;

public abstract class DB {
	public static final String JDBC = "org.sqlite.JDBC";
	public static final String JDBC_CONNECTION = "jdbc:sqlite";
	public static final String DB_FILE_NAME = "res"+File.separator+"database.db";
	
	public static void initDB() throws ClassNotFoundException, SQLException {
		File file = new File(DB.DB_FILE_NAME);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			Class.forName(DB.JDBC);
			Connection connection = openConnection();

			Statement stmt = connection.createStatement();
			
			stmt.executeUpdate(TestDAO.createTable());
			stmt.close();

			TestDAO.insert(new Test("Coluna1", "ab", 123, 13.4));
			TestDAO.insert(new Test("new1", "bc", 321, 0));
			TestDAO.insert(new Test("Trinith", "d", 123456789, 12.12345));
			TestDAO.insert(new Test("Hello", "ef", -1, 4));
		}
	}
	
	public static Connection openConnection() throws SQLException{
		return DriverManager.getConnection(DB.JDBC_CONNECTION + ":" + DB.DB_FILE_NAME);
	}
	
	abstract class TableTest{
		public static final String TABLE_NAME = "TEST";
		public static final String COLUMN_ID = "ID";
		public static final String COLUMN_TEST_1 = "COLUMN_1";
		public static final String COLUMN_TEST_2 = "COLUMN_2";
		public static final String COLUMN_TEST_3 = "COLUMN_3";
		public static final String COLUMN_TEST_4 = "COLUMN_4";
	}
	
}
