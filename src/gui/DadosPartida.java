package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DadosPartida  extends JPanel {
	
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private Texto p1Discos = new Texto("2");
	private Texto p2Discos = new Texto("2");
	private GridBagConstraints gbc = new GridBagConstraints();
	
	public DadosPartida() {
		setPreferredSize(new Dimension(220, 420));
		setBackground(Color.DARK_GRAY);
		setForeground(Color.WHITE);
		
		add(new Texto("DETALHES DA PARTIDA"), gbc);
		
		p1.setBackground(Color.DARK_GRAY);
		p1.add(new Disco(1));
		p1.add(new Texto("Jogador 1:"));
		p1.add(p1Discos);
		
		p2.setBackground(Color.DARK_GRAY);
		p2.add(new Disco(2));
		p2.add(new Texto("Jogador 2:"));
		p2.add(p2Discos);
		
		add(p1, gbc);
		add(p2, gbc);
		
		add(new Texto("PLACAR GERAL"), gbc);
		gbc.gridx++;
		add(new Texto("Jogador 1: 0"), gbc);
		add(new Texto("Jogador 2: 0"), gbc);
	}
	
}
