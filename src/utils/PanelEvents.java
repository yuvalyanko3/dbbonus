package utils;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelEvents implements MouseListener {
	
	private JLabel lbl;

	public PanelEvents(JLabel lbl) {
		super();
		this.lbl = lbl;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		lbl.setForeground(Color.decode("#BECDE1"));
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		lbl.setForeground(Color.decode("#FFFFFF"));
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
