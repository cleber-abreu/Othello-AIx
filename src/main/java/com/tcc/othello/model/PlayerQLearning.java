package com.tcc.othello.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import com.tcc.othello.dao.DB;

public class PlayerQLearning extends Player {
	private final static int MAX_MOVE_OPTIONS = 28;
	private Connection con;
	private ArrayList<Move> moves;
	
	public PlayerQLearning() {
		try {
			con = DB.openConnection();
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		moves = new ArrayList<Move>();
	}

	@Override
	public void takeTurn(ArrayList<Locale> moveOptions, Field[][] fields) {
		Locale locale = bestMove(moveOptions, fields);
		playerObservable.move(PlayerQLearning.this, locale);
	}
	
	@Override
	public void gameOver(boolean won) {
		for (Move move : moves) {
			if (won) {
				move.rateUp();
			} else {
				move.rateDown();
			}
		}
		updateMoveRate();
	}

	private void updateMoveRate() {
		try {
			Statement stmt = con.createStatement();
			for (Move move : moves) {
				String sql = "UPDATE "+ move.tableName 
						+ " set RATE" + move.index + "=" + move.value 
						+ "WHERE ID='" + move.id + "';";
				stmt.executeUpdate(sql);
			}
			stmt.close();
			con.commit();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private String generatorId(Field[][] fields) {
		StringBuilder id = new StringBuilder();
		for (int row = 0; row < fields.length; row++) {
			for (int col = 0; col < fields.length; col++) {
				id.append(fields[row][col].getStatus().toString());
			}
		}
		return id.toString();
	}
	
	private Locale bestMove(ArrayList<Locale> moveOptions, Field[][] fields) {
		Locale locale = null;
		String tableId = generatorId(fields);
		String tableName = getTableName(fields);
		
		try {
			Statement stmt = con.createStatement();
		
			ResultSet rs = stmt.executeQuery( "SELECT * FROM "+tableName+";" );
			if (rs.wasNull()) {
				insert(moveOptions, tableName, tableId);
				Random random = new Random();
				int move = random.nextInt(moveOptions.size());
				locale = moveOptions.get(move);
				moves.add(new Move(tableId, tableName, move, 100));
			} else {
				while ( rs.next() ) {
					int rate[] = new int[MAX_MOVE_OPTIONS];
					for (int i = 0; i < rate.length; i++) {
						rate[i] = rs.getInt("RATE"+i);
					}
					int best = getBest(rate);
					locale = new Locale(rs.getInt("ROW"+best), rs.getInt("COL"+best));
					moves.add(new Move(tableId, tableName, best, rate[best]));
				}
			}
			
			stmt.close();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locale;
	}
	
	private int getBest(int[] rate) {
		int best = 0;
		int index = 0;
		for (int i = 0; i < rate.length; i++) {
			if (rate[i] > best) {
				best = rate[i];
				index = i;
			}
		}
		return index;
	}

	private void insert(ArrayList<Locale> moveOptions, String tableName, String tableId) {
		try {
			Statement stmt = con.createStatement();
			String sql;
			
			for (Locale locale : moveOptions) {
				sql = "INSER INTO " + tableName + "(ID";
				for (int i = 0; i < MAX_MOVE_OPTIONS; i++) {
					sql += ",ROW"+ i +",COL" + i + ",RATE" + i;
				}
				
				sql += ") VALUES ('" + tableId + "',";
				for (int i = 0; i < MAX_MOVE_OPTIONS; i++) {
					if (moveOptions.size() > i) {
						sql += "," + locale.getRow() + "," + locale.getCol() + ",100);";
					}
					else {
						sql += ",null,null,0);";
					}
				}
				System.out.println(sql);
				stmt.executeUpdate(sql);	
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getTableName(Field[][] fields) {
		return "BOARD"+getTableSize(fields);
	}
	
	private int getTableSize(Field[][] fields) {
		// 8x8
		for (int i = 0; i < fields.length; i++) {
			if (fields[0][i].getStatus() != FieldStatus.VOID ||
					fields[7][i].getStatus() != FieldStatus.VOID ||
					fields[i][0].getStatus() != FieldStatus.VOID ||
					fields[i][7].getStatus() != FieldStatus.VOID) {
				return 8;
			}
		}
		
		// 4x4
		for (int i = 1; i < 7; i++) {
			if (fields[1][i].getStatus() != FieldStatus.VOID ||
					fields[6][i].getStatus() != FieldStatus.VOID ||
					fields[i][1].getStatus() != FieldStatus.VOID ||
					fields[i][6].getStatus() != FieldStatus.VOID) {
				return 6;
			}
		}
		
		return 4;
	}
	
	class Move{
		String tableName;
		String id;
		int index;
		int value;
		
		public Move(String id, String tableName, int index, int value) {
			this.id = id;
			this.tableName = tableName;
			this.index = index;
			this.value = value;
		}

		public void rateDown() {
			value--;
		}

		public void rateUp() {
			value++;
		}
		
	}
}
