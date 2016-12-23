package com.tcc.othello.dao;

import java.io.File;

public abstract class DB {
	public static final String JDBC = "org.sqlite.JDBC";
	public static final String JDBC_CONNECTION = "jdbc:sqlite";
	public static final String DB_FILE_NAME = "res"+File.separator+"database.db";
	
}
