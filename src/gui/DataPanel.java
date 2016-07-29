package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import model.FieldStatus;

@SuppressWarnings("serial")
public class DataPanel  extends JPanel {
	
	private final static Font fontTittle = new Font(Font.DIALOG, Font.BOLD, 12);
	private final static Font fontText = new Font(Font.DIALOG_INPUT, Font.PLAIN, 14);
	private final static Font fontNumber = new Font(Font.DIALOG, Font.BOLD, 42);
	
	private static JLabel lblNumberDiscsPlay1;
	private static JLabel lblNumberDiscsPlay2;
	private JLabel lblTittleMatch;
	private JLabel lblTittleScoreboard;
	private JLabel lblNamePlay1;
	private JLabel lblNamePlay2;
	private JLabel lblPointsPlay1;
	private JLabel lblPointsPlay2;
	private JButton btnNewGame;
	
	public DataPanel() {
		setPreferredSize(new Dimension(220, 420));
		setBackground(Color.DARK_GRAY);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		lblTittleMatch = new JLabel("DETALHES DA PARTIDA");
		lblTittleScoreboard = new JLabel("PLACAR GERAL");
		lblNamePlay1 = new JLabel("Jogador1");
		lblNamePlay2 = new JLabel("Jogador2");
		lblNumberDiscsPlay1 = new JLabel("0");
		lblNumberDiscsPlay2 = new JLabel("0");
		lblPointsPlay1 = new JLabel("0");
		lblPointsPlay2 = new JLabel("0");
		btnNewGame = new JButton("Nova Partida");
		
		lblTittleMatch.setFont(fontTittle);
		lblTittleScoreboard.setFont(fontTittle);
		lblNamePlay1.setFont(fontText);
		lblNamePlay2.setFont(fontText);
		lblNumberDiscsPlay1.setFont(fontNumber);
		lblNumberDiscsPlay2.setFont(fontNumber);
		lblPointsPlay1.setFont(fontTittle);
		lblPointsPlay2.setFont(fontTittle);
		
		btnNewGame.setContentAreaFilled(false);
		btnNewGame.setBorderPainted(true);
		btnNewGame.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		
		lblTittleMatch.setForeground(Color.WHITE);
		lblTittleScoreboard.setForeground(Color.WHITE);
		lblNamePlay1.setForeground(Color.WHITE);
		lblNamePlay2.setForeground(Color.WHITE);
		lblNumberDiscsPlay1.setForeground(Color.WHITE);
		lblNumberDiscsPlay2.setForeground(Color.WHITE);
		lblPointsPlay1.setForeground(Color.WHITE);
		lblPointsPlay2.setForeground(Color.WHITE);
		btnNewGame.setForeground(Color.WHITE);
		
		
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainWindow.newGame();
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
		add(lblNamePlay1, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 1;
		add(lblNamePlay2, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new Disc(FieldStatus.SCORE_BLACK), gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(new Disc(FieldStatus.SCORE_WHITE), gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(lblNumberDiscsPlay1, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(lblNumberDiscsPlay2, gbc);
		
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(lblTittleScoreboard, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(lblPointsPlay1, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 5;
		add(lblPointsPlay2, gbc);
		
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 6;
		add(btnNewGame, gbc);
		
	}

	public static void setNumeberDiscs(int numberDiscsPlay1, int numberDiscsPlay2) {
		lblNumberDiscsPlay1.setText(String.valueOf(numberDiscsPlay1));
		lblNumberDiscsPlay2.setText(String.valueOf(numberDiscsPlay2));
	}
	
}
