package com.tcc.othello.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {

	protected abstract T resutSetToEntity(ResultSet rs) throws SQLException;
	protected abstract String getInsertQuery(T entity);
	protected abstract String getTableName();

	public void insert(T entity) {
		try {
			Connection c = DB.openConnection();
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();
			stmt.executeUpdate(getInsertQuery(entity));

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<T> getList() {
		ArrayList<T> entitys = new ArrayList<>();
		
		try {
			Connection c = DB.openConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + getTableName());
			
			while (rs.next()) {
				entitys.add(resutSetToEntity(rs));
			}
			
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entitys;
	}
	
	public static long count(String table){
		
		long a = 0;
		try {
			Connection c = DB.openConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Count(*) as c FROM " + table);
			
			if(rs.next()){
				a = rs.getLong("c");
			}
			
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return a;
	}
}
