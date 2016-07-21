package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	public static void main(String[] args) {

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
				
				JFrame frmPrincipal = new JFrame("Othello AIx");
				frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frmPrincipal.setLayout(new BorderLayout());
				frmPrincipal.setBackground(Color.DARK_GRAY);
				frmPrincipal.add(new Tabuleiro(), BorderLayout.CENTER);
				frmPrincipal.pack();
				frmPrincipal.setLocationRelativeTo(null);
				frmPrincipal.setVisible(true);
			}
		});
		
	}

}
