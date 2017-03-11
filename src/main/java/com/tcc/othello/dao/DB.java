package com.tcc.othello.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DB {
	public static final String JDBC = "org.sqlite.JDBC";
	public static final String JDBC_CONNECTION = "jdbc:sqlite";
	public static final String DB_FILE_NAME = "res"+File.separator+"database.db";
	
	public static void initDB(){
		try{
			File file = new File(DB.DB_FILE_NAME);

			if (!file.exists()) {
				file.getParentFile().mkdirs();
				Class.forName(DB.JDBC);
				Connection connection = openConnection();

				Statement stmt = connection.createStatement();
				
				stmt.executeUpdate(GameBoardDAO.createGameBoardTable());
				stmt.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection openConnection() throws SQLException{
		return DriverManager.getConnection(DB.JDBC_CONNECTION + ":" + DB.DB_FILE_NAME);
	}
}
