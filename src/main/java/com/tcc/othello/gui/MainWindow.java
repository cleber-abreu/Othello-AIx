package com.tcc.othello.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.tcc.othello.global.Game;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private static Gameboard gameboard;
	private static DataPanel dataPanel;
	private static Game game;
	
	public static Gameboard getGameboard() {
		return gameboard;
	}

	public static DataPanel getDataPanel() {
		return dataPanel;
	}
	
	public static Game getGame() {
		return game;
	}

	public static void newGame() {
		game = new Game();
		game.start();
	}
	
	public static void main(String[] args) {

		gameboard = new Gameboard();
		dataPanel = new DataPanel();
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | 
                		InstantiationException | 
                		IllegalAccessException | 
                		UnsupportedLookAndFeelException e) {
                }
				
				JFrame frmMain = new JFrame("Othello AIx");
				frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frmMain.setLayout(new BorderLayout());
				frmMain.setBackground(Color.DARK_GRAY);
				frmMain.add(gameboard, BorderLayout.CENTER);
				frmMain.add(dataPanel, BorderLayout.EAST);
				frmMain.pack();
				frmMain.setLocationRelativeTo(null);
				frmMain.setVisible(true);
			}
		});
	}
	
}
