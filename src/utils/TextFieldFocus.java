package utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TextFieldFocus implements FocusListener {
	
	private String placeholder;
	private String focusGaind;
	private JTextField txt;
	

	public TextFieldFocus(JTextField txt, String placeholder, String focusGaind) {
		this.txt = txt;
		this.placeholder = placeholder;
		this.focusGaind = focusGaind;
		this.txt.setText(placeholder);
		this.txt.setForeground(Color.LIGHT_GRAY);
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if(txt.getText().equals(placeholder))
			txt.setText(focusGaind);
		txt.setForeground(Color.BLACK);
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if(txt.getText().trim().equals("")) 
		{
			txt.setText(placeholder);
			txt.setForeground(Color.LIGHT_GRAY);
		}
		
	}

}
