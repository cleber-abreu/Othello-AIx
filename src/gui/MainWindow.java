package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Game;
import controller.Rules;
import model.FieldStatus;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private static Gameboard gameBoard;
	
	public static void newGame() {
		gameBoard.clearDiscs(Game.fieldsBlack, Game.fieldsWhite);
		gameBoard.clearMoveOptions(Game.moveOptions);
		Game.newGame();
		gameBoard.drawDiscs(Game.fieldsBlack);
		gameBoard.drawDiscs(Game.fieldsWhite);
		gameBoard.drawDiscs(Game.moveOptions);
		DataPanel.setNumeberDiscs(2, 2);
	}

	public static void changeTurn(FieldStatus status) {
		gameBoard.clearMoveOptions(Game.moveOptions);
		gameBoard.drawDiscs(Game.fieldsBlack);
		gameBoard.drawDiscs(Game.fieldsWhite);
		DataPanel.setNumeberDiscs(Game.fieldsBlack.size(), Game.fieldsWhite.size());
		
		if (status == FieldStatus.BLACK) {
			Game.moveOptions = Rules.moveOptions(Game.fieldsBlack, Game.fieldsWhite);
			
			if (Game.moveOptions.size() > 0) {
				gameBoard.drawMoveOptions(Game.moveOptions, FieldStatus.OPTION_BLACK);
			} else {
				Game.moveOptions = Rules.moveOptions(Game.fieldsWhite, Game.fieldsBlack);
				if (Game.moveOptions.size() > 0) {
					gameBoard.drawMoveOptions(Game.moveOptions, FieldStatus.OPTION_WHITE);
				} else {
					JOptionPane.showMessageDialog(null, "Fim");
				}
			}
		}
		else if (status == FieldStatus.WHITE) {
			Game.moveOptions = Rules.moveOptions(Game.fieldsWhite, Game.fieldsBlack);

			if (Game.moveOptions.size() > 0) {
				gameBoard.drawMoveOptions(Game.moveOptions, FieldStatus.OPTION_WHITE);
			} else {
				Game.moveOptions = Rules.moveOptions(Game.fieldsBlack, Game.fieldsWhite);
				if (Game.moveOptions.size() > 0) {
					gameBoard.drawMoveOptions(Game.moveOptions, FieldStatus.OPTION_BLACK);
				} else {
					JOptionPane.showMessageDialog(null, "Fim");
				}
			}
		}
					
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
