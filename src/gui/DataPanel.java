package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class DataPanel  extends JPanel {
	
	private JPanel pnlMatch;
	private JPanel pnlScoreboard;
	private JPanel pnlScorePlay1;
	private JPanel pnlScorePlay2;
	private JLabel lblNumberDiscsPlay1;
	private JLabel lblNumberDiscsPlay2;
	
	public DataPanel() {
		setPreferredSize(new Dimension(220, 420));
		setBackground(Color.DARK_GRAY);
		
		pnlMatch = new JPanel();
		pnlScoreboard = new JPanel();
		pnlScorePlay1 = new JPanel();
		pnlScorePlay2 = new JPanel();
		lblNumberDiscsPlay1 = new JLabel("2");
		lblNumberDiscsPlay2 = new JLabel("2");
		
		pnlMatch.setLayout(new BorderLayout());
		pnlScoreboard.setLayout(new BorderLayout());
		
//		pnlMatch.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
//		pnlScoreboard.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		
		pnlMatch.setBackground(Color.DARK_GRAY);
		pnlScoreboard.setBackground(Color.DARK_GRAY);
		pnlScorePlay1.setBackground(Color.DARK_GRAY);
		pnlScorePlay2.setBackground(Color.DARK_GRAY);
		
		pnlMatch.add(new JLabel("DETALHES DA PARTIDA") {
			@Override
			public Color getForeground() {
				return Color.WHITE;
			}
			
			@Override
			public Font getFont() {
				return new Font("Tahoma",Font.BOLD, 14);
			}
		}, BorderLayout.NORTH);
		
		pnlScorePlay1.add(new Disc(DiscStatus.BRANCO_PLACAR), BorderLayout.CENTER);
		pnlScorePlay1.add(new JLabel("Jogador 1:") {
			@Override
			public Color getForeground() {
				return Color.WHITE;
			}
			
			@Override
			public Font getFont() {
				return new Font("Tahoma",Font.BOLD, 12);
			}
		}, BorderLayout.CENTER);
		
		pnlScorePlay2.add(new Disc(DiscStatus.PRETO_PLACAR), BorderLayout.CENTER);
		pnlScorePlay2.add(new JLabel("Jogador 2:") {
			@Override
			public Color getForeground() {
				return Color.WHITE;
			}
			
			@Override
			public Font getFont() {
				return new Font("Tahoma",Font.PLAIN, 14);
			}
		}, BorderLayout.CENTER);
		
		pnlMatch.add(pnlScorePlay1, BorderLayout.CENTER);
		pnlMatch.add(pnlScorePlay2, BorderLayout.SOUTH);
		
		pnlScoreboard.add(new JLabel("PLACAR GERAL") {
			@Override
			public Color getForeground() {
				return Color.WHITE;
			}
			
			@Override
			public Font getFont() {
				return new Font("Tahoma",Font.BOLD, 14);
			}
		}, BorderLayout.NORTH);
		pnlScoreboard.add(new JLabel("Jogador 1 0x0 Jogador 2") {
			@Override
			public Color getForeground() {
				return Color.WHITE;
			}
			
			@Override
			public Font getFont() {
				return new Font("Tahoma",Font.PLAIN, 14);
			}
		}, BorderLayout.CENTER);
		
		add(pnlMatch, BorderLayout.CENTER);
		add(pnlScoreboard, BorderLayout.CENTER);
	}
	
}
