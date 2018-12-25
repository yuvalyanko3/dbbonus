package view;

import java.awt.EventQueue;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import control.Logic;
import model.EntitlementCategory;
import model.Resident;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class Test extends RootLayout {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Test();
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the panel.
	 */
	public Test() {
		super();
		initComponents();
	}
	private void initComponents() {
		
		JPanel chart = new JPanel();
		chart.setBounds(242, 90, 560, 451);
		getContentPane().add(chart);
		
		
//		ChartPanel cp = new ChartPanel(chart);
		
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Resident> r = Logic.getInstance().getResidents();
				for(Resident s : r)
				{
					System.out.println(s.toString());
					ArrayList<EntitlementCategory> ec = Logic.getInstance().getCatagoriesForResident(s);
					for(EntitlementCategory q : ec)
					{
						System.out.println(q.toString());
					}
					System.out.println("///////////////////////////////////////////////////////////////////////////////");
				}
			}
		});
		btnNewButton.setBounds(158, 323, 89, 23);
		getContentPane().add(btnNewButton);
	}
	
///////////////////////////////////CLASS VARIABLES//////////////////////////////////////

private static Test frame;
}
