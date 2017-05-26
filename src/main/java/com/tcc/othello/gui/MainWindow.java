package com.tcc.othello.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.tcc.othello.global.Game;
import com.tcc.othello.model.BoardObservable;
import com.tcc.othello.model.DataPanelObervable;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Locale;
import com.tcc.othello.model.Player;
import com.tcc.othello.model.PlayerObservable;
import com.tcc.othello.model.PlayerType;

public class MainWindow implements PlayerObservable, BoardObservable, DataPanelObervable{
	
	private JGameboard gameboard;
	private JDataPanel dataPanel;
	private Game game;
	
	public static void main(String[] args) {

		MainWindow main = new MainWindow();
		main.gameboard = new JGameboard(main);
		main.dataPanel = new JDataPanel(main);
		
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
	
	/*
	 * implementacao do DataPanelObervable, observar o aviso de nova partida
	 */
	@Override
	public void onNewGame(PlayerType blackPlayer, PlayerType whitePlayer) {
		gameboard.clearAll();
		game = new Game(this);
		game.start(blackPlayer, whitePlayer);
	}

	/*
	 * implementacao do PlayerObservable, observar o aviso de nova jogada a ser executada
	 */
	@Override
	public void move(Player player, Locale locale) {
		gameboard.paintMovement(player.getColor(), locale);
	}
	
	public void move(FieldStatus playerColor, ArrayList<Locale> locales) {
		gameboard.paintMovement(playerColor, locales);
	}

	/*
	 * implementacao do BoardObervable, observar o click no tabuleiro
	 */
	@Override
	public void onBoardClick(Locale locale) {
		game.onBoardClick(locale);
	}
	
	@Override
	public void paintMovement(FieldStatus playerColor, ArrayList<Locale> locales) {
		gameboard.paintMovement(playerColor, locales);
	}
	
	@Override
	public void paintMoveOptions(FieldStatus playerColor, ArrayList<Locale> locales) {
		gameboard.paintMoveOptions(playerColor, locales);
	}

	@Override
	public void updateNumberDiscs(int numberDiscsPlayer1, int numberDiscsPlayer2) {
		dataPanel.updateNumberDiscs(numberDiscsPlayer1, numberDiscsPlayer2);
	}
	
	@Override
	public void gameOver(FieldStatus winnerColor) {
		dataPanel.updateScoreboard(winnerColor);
		if (dataPanel.getPlayer1() != PlayerType.HUMAN && dataPanel.getPlayer2() != PlayerType.HUMAN) {
			dataPanel.newGame();
		}
	}
}
