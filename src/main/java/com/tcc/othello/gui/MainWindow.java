package com.tcc.othello.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.tcc.othello.global.Game;
import com.tcc.othello.model.PlayerObservable;
import com.tcc.othello.model.Locale;
import com.tcc.othello.model.Player;

public class MainWindow implements PlayerObservable{
	
	private Gameboard gameboard;
	private DataPanel dataPanel;
	private Game game;
	
	public Gameboard getGameboard() {
		return gameboard;
	}

	public DataPanel getDataPanel() {
		return dataPanel;
	}
	
	public Game getGame() {
		return game;
	}

	public void newGame() {
		game = new Game(this);
		game.start();
	}
	
	public static void main(String[] args) {

		MainWindow main = new MainWindow();
		main.gameboard = new Gameboard();
		main.dataPanel = new DataPanel();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			JFrame frmMain = new JFrame("Othello AIx");
			frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmMain.setLayout(new BorderLayout());
			frmMain.setBackground(Color.DARK_GRAY);
			frmMain.add(main.gameboard, BorderLayout.CENTER);
			frmMain.add(main.dataPanel, BorderLayout.EAST);
			frmMain.pack();
			frmMain.setLocationRelativeTo(null);
			frmMain.setVisible(true);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	@Override
	public void move(Player player, Locale locale) {
		
	}
}
