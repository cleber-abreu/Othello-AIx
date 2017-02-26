package com.tcc.othello.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tcc.othello.model.BoardObservable;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Locale;
import com.tcc.othello.model.Player;

@SuppressWarnings("serial")
public class AlternativeBoard extends JPanel{

	private JButton[][] btns;
	private BoardObservable boardObservable;
	
	public AlternativeBoard(BoardObservable boardObservable) {
		this.boardObservable = boardObservable;
		setLayout(new GridLayout(8, 8));
		btns = new JButton[8][8];
		
		for (int y = 0; y < btns.length; y++) {
			for (int x = 0; x < btns[y].length; x++) {
				final int finalX = x;
				final int finalY = y;
				final JButton btn = new JButton("");

			    btn.setPreferredSize(new Dimension(50, 50));
				
				btns[x][y] = btn;
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(btn.getText().equals("")){
							AlternativeBoard.this.boardObservable.onBoardClick(new Locale(finalX, finalY));
						}
					}
				});
				
				add(btn);
			}
		}
		repaint();
	}
	
	public void paintMovement(Player player, Locale locale){
		btns[locale.getX()][locale.getY()].setText(FieldStatus.WHITE.equals(player.getColor()) ? "X" : "O");
		repaint();
	}
	
	public void clean(){
		for (int y = 0; y < btns.length; y++) {
			for (int x = 0; x < btns[y].length; x++) {
				btns[x][y].setText("");
			}
		}
		
		repaint();
	}
}
