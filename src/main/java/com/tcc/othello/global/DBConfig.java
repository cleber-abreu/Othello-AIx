package com.tcc.othello.global;

import java.io.File;

public abstract class DBConfig {
	public static final String JDBC = "org.sqlite.JDBC";
	public static final String JDBC_CONNECTION = "jdbc:sqlite";
	public static final String DB_FILE_NAME = "res"+File.separator+"database.db";
	
}
