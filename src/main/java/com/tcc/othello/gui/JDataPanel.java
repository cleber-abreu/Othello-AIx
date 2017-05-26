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

import com.tcc.othello.model.DataPanelObervable;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.PlayerType;

@SuppressWarnings("serial")
public class JDataPanel extends JPanel{
	
	private final static Font fontTittle = new Font(Font.DIALOG, Font.BOLD, 12);
	private final static Font fontText = new Font(Font.DIALOG_INPUT, Font.PLAIN, 14);
	private final static Font fontNumber = new Font(Font.DIALOG, Font.BOLD, 42);
	
	private static JLabel lblNumDiscsPlayer1;
	private static JLabel lblNumDiscsPlayer2;
	private JLabel lblTittleMatch;
	private JLabel lblTittleScoreboard;
	private JLabel lblPlayer1;
	private JLabel lblPlayer2;
	private JComboBox<PlayerType> listNamePlayer1;
	private JComboBox<PlayerType> listNamePlayer2;
	private JDisc colorPlayer1Disc;
	private JDisc colorPlayer2Disc;
	private JLabel lblPointsPlayer1;
	private JLabel lblPointsPlayer2;
	private JButton btnNewGame;
	
	private DataPanelObervable dataPanelObervable;
	private int gameCounter;
	
	public JDataPanel(DataPanelObervable dataPanelObervable){
		this.dataPanelObervable = dataPanelObervable;
		setPreferredSize(new Dimension(360, 420));
		setBackground(Color.DARK_GRAY);
		setLayout(new GridBagLayout());
		gameCounter = 0;
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		lblTittleMatch = new JLabel("DETALHES DA PARTIDA");
		lblTittleScoreboard = new JLabel("PLACAR GERAL");
		lblPlayer1 = new JLabel("Jogador 1");
		lblPlayer2 = new JLabel("Jogador 2");
		listNamePlayer1 = new JComboBox<>(PlayerType.values());
		listNamePlayer2 = new JComboBox<>(PlayerType.values());
		colorPlayer1Disc = new JDisc(FieldStatus.BLACK);
		colorPlayer2Disc = new JDisc(FieldStatus.WHITE);
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
		colorPlayer1Disc.setBackground(Color.DARK_GRAY);
		colorPlayer2Disc.setBackground(Color.DARK_GRAY);
		
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newGame();
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
		add(colorPlayer1Disc, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(colorPlayer2Disc, gbc);
		
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
	
	public PlayerType getPlayer1() {
		return (PlayerType) listNamePlayer1.getSelectedItem();
	}
	
	public PlayerType getPlayer2() {
		return (PlayerType) listNamePlayer2.getSelectedItem();
	}

	public String getStringPlayer1() {
		return listNamePlayer1.getSelectedItem().toString();
	}
	
	public String getStringPlayer2() {
		return listNamePlayer2.getSelectedItem().toString();
	}

	public void setColorsPlayersDiscs(FieldStatus colorPlayer1, FieldStatus colorPlayer2) {
		colorPlayer1Disc.setStatus(colorPlayer1);
		colorPlayer2Disc.setStatus(colorPlayer2);
	}
	
	public void updateNumberDiscs(int numberDiscsPlayerBlack, int numberDiscsPlayerWhite) {
		if (colorPlayer1Disc.getStatus() == FieldStatus.BLACK) {
			lblNumDiscsPlayer1.setText(String.valueOf(numberDiscsPlayerBlack));
			lblNumDiscsPlayer2.setText(String.valueOf(numberDiscsPlayerWhite));
		}
		else {
			lblNumDiscsPlayer1.setText(String.valueOf(numberDiscsPlayerWhite));
			lblNumDiscsPlayer2.setText(String.valueOf(numberDiscsPlayerBlack));
		}
	}
	
	public void newGame() {
		if (gameCounter > 0) {
			changeFirstPlayer();
		}
		if (gameCounter % 2 == 0) {
			JDataPanel.this.dataPanelObervable.onNewGame(getPlayer1(), getPlayer2());
		} else {
			JDataPanel.this.dataPanelObervable.onNewGame(getPlayer2(), getPlayer1());
		}
		gameCounter++;
	}
	
	public void updateScoreboard(FieldStatus winnerColor) {
		int newScoreboard = 1;
		
		if (winnerColor == colorPlayer1Disc.getStatus()) {
			newScoreboard += Integer.parseInt(lblPointsPlayer1.getText());
			lblPointsPlayer1.setText(String.valueOf(newScoreboard));
		}
		else if (winnerColor == colorPlayer2Disc.getStatus()) {
			newScoreboard += Integer.parseInt(lblPointsPlayer2.getText());
			lblPointsPlayer2.setText(String.valueOf(newScoreboard));
		}
	}
	
	public void enableSelectionPlayer(boolean b) {
		listNamePlayer1.setEnabled(b);
		listNamePlayer2.setEnabled(b);
	}
	
	public void changeFirstPlayer() {
		FieldStatus color = colorPlayer1Disc.getStatus();
		colorPlayer1Disc.setStatus(colorPlayer2Disc.getStatus());
		colorPlayer2Disc.setStatus(color);
	}
	
}
