package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Texto extends JLabel {

	private Font font = new Font(Font.DIALOG, Font.BOLD, 12);

	public Texto(String text) {
		setText(text);
	}

	@Override
	public Color getForeground() {
		return Color.WHITE;
	}

	@Override
	public Font getFont() {
		return font;
	}

}
