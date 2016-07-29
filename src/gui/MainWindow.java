package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Game;
import controller.Rules;
import model.FieldStatus;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private static Gameboard gameBoard;
	
	public static void newGame() {
		Game.newGame();
		gameBoard.drawDiscs(Game.fieldsBlack);
		gameBoard.drawDiscs(Game.fieldsWhite);
		gameBoard.drawDiscs(Game.moveOptions);
	}

	public static void changeTurn(FieldStatus status) {
		gameBoard.clearMoveOptions(Game.moveOptions);
		
		if (status == FieldStatus.BLACK) {
			Game.moveOptions = Rules.moveOptions(Game.fieldsBlack, Game.fieldsWhite);
			gameBoard.drawMoveOptions(Game.moveOptions, FieldStatus.OPTION_BLACK);
		}
		else if (status == FieldStatus.WHITE) {
			Game.moveOptions = Rules.moveOptions(Game.fieldsWhite, Game.fieldsBlack);
			gameBoard.drawMoveOptions(Game.moveOptions, FieldStatus.OPTION_WHITE);
		}
		gameBoard.drawDiscs(Game.fieldsBlack);
		gameBoard.drawDiscs(Game.fieldsWhite);
					
	}
	
	public static void main(String[] args) {

		gameBoard = new Gameboard();
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
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
				frmMain.add(gameBoard, BorderLayout.CENTER);
				frmMain.add(new DataPanel(), BorderLayout.EAST);
				frmMain.pack();
				frmMain.setLocationRelativeTo(null);
				frmMain.setVisible(true);
			}
		});
		
	}

}
