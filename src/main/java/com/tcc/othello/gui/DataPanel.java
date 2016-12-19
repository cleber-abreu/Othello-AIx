package com.tcc.othello.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Players;

@SuppressWarnings("serial")
public class DataPanel  extends JPanel {
	
	private final static Font fontTittle = new Font(Font.DIALOG, Font.BOLD, 12);
	private final static Font fontText = new Font(Font.DIALOG_INPUT, Font.PLAIN, 14);
	private final static Font fontNumber = new Font(Font.DIALOG, Font.BOLD, 42);
	
	private static JLabel lblNumDiscsPlayer1;
	private static JLabel lblNumDiscsPlayer2;
	private JLabel lblTittleMatch;
	private JLabel lblTittleScoreboard;
	private JLabel lblPlayer1;
	private JLabel lblPlayer2;
	private JComboBox<Players> listNamePlayer1;
	private JComboBox<Players> listNamePlayer2;
	private JLabel lblPointsPlayer1;
	private JLabel lblPointsPlayer2;
	private JButton btnNewGame;
	
	public DataPanel() {
		setPreferredSize(new Dimension(360, 420));
		setBackground(Color.DARK_GRAY);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		lblTittleMatch = new JLabel("DETALHES DA PARTIDA");
		lblTittleScoreboard = new JLabel("PLACAR GERAL");
		lblPlayer1 = new JLabel("Jogador 1");
		lblPlayer2 = new JLabel("Jogador 2");
		listNamePlayer1 = new JComboBox<>(Players.values());
		listNamePlayer2 = new JComboBox<>(Players.values());
		lblNumDiscsPlayer1 = new JLabel("0");
		lblNumDiscsPlayer2 = new JLabel("0");
		lblPointsPlayer1 = new JLabel("0");
		lblPointsPlayer2 = new JLabel("0");
		btnNewGame = new JButton("Nova Partida");
		
		lblTittleMatch.setFont(fontTittle);
		lblTittleScoreboard.setFont(fontTittle);
		lblPlayer1.setFont(fontText);
		lblPlayer2.setFont(fontText);
		lblNumDiscsPlayer1.setFont(fontNumber);
		lblNumDiscsPlayer2.setFont(fontNumber);
		lblPointsPlayer1.setFont(fontTittle);
		lblPointsPlayer2.setFont(fontTittle);
		
		listNamePlayer1.setEditable(true);
		listNamePlayer2.setEditable(true);
		
		btnNewGame.setContentAreaFilled(false);
		btnNewGame.setBorderPainted(true);
		btnNewGame.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		
		lblTittleMatch.setForeground(Color.WHITE);
		lblTittleScoreboard.setForeground(Color.WHITE);
		lblPlayer1.setForeground(Color.WHITE);
		lblPlayer2.setForeground(Color.WHITE);
		lblNumDiscsPlayer1.setForeground(Color.WHITE);
		lblNumDiscsPlayer2.setForeground(Color.WHITE);
		lblPointsPlayer1.setForeground(Color.WHITE);
		lblPointsPlayer2.setForeground(Color.WHITE);
		btnNewGame.setForeground(Color.WHITE);
		
		
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainWindow.newGame();
				listNamePlayer1.setEnabled(false);
				listNamePlayer2.setEnabled(false);
				super.mouseClicked(e);
			}
		});
		
		
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lblTittleMatch, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(lblPlayer1, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(new JLabel(" x "), gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 1;
		add(lblPlayer2, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(listNamePlayer1, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(listNamePlayer2, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new Disc(FieldStatus.SCORE_BLACK), gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(new Disc(FieldStatus.SCORE_WHITE), gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(lblNumDiscsPlayer1, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 4;
		add(lblNumDiscsPlayer2, gbc);
		
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(lblTittleScoreboard, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 6;
		add(lblPointsPlayer1, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 6;
		add(lblPointsPlayer2, gbc);
		
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 7;
		add(btnNewGame, gbc);
		
	}

	public static void setNumDiscs(int numDiscsPlayer1, int numDiscsPlayer2) {
		lblNumDiscsPlayer1.setText(String.valueOf(numDiscsPlayer1));
		lblNumDiscsPlayer2.setText(String.valueOf(numDiscsPlayer2));
	}
	
}
