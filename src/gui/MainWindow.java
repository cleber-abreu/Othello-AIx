package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Game;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	

	public static void main(String[] args) {

		Game game = new Game();
		Board gameBoard = new Board();
		gameBoard.drawDiscs(game.getFieldsBlack());
		gameBoard.drawDiscs(game.getFieldsWhite());
		gameBoard.drawDiscs(game.getPossibleMoves());
		
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
