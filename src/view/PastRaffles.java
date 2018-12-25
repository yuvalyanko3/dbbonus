package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Logic;
import model.Participate;
import model.Raffle;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JScrollPane;

public class PastRaffles extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PastRaffles frame = new PastRaffles();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PastRaffles() {
		initComp();
	}
	
	public PastRaffles(JFrame owner) {
		super(owner, true);
		Logic logic = Logic.getInstance();
		initComp();
		buildMyRaffleTable(logic.getPastRaffles(new Participate(logic.getCurrentUserId())));
		
	}
	
	
	private void initComp() {
		setBounds(100, 100, 425, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 409, 405);
		contentPane.add(pMain);
		pMain.setBackground(Color.decode("#EAE6EF"));
		pMain.setLayout(null);
		
		JLabel lblPastRaffles = new JLabel("Past Raffles:");
		lblPastRaffles.setFont(new Font("Times New Roman", Font.BOLD, 29));
		lblPastRaffles.setBounds(10, 11, 298, 31);
		pMain.add(lblPastRaffles);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 78, 317, 288);
		pMain.add(scrollPane);
		
		myPastRaf = new JTable() {
			public boolean isCellEditable(int row,int column) {
				return false;
		}};
		scrollPane.setViewportView(myPastRaf);
	}
	
	private void buildMyRaffleTable(ArrayList<Raffle> raffles) {
		Object[] columns = {"Raffle number", "Raffle date", "Winning place"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		myPastRaf.setModel(model);
		((DefaultTableModel)myPastRaf.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		if(!raffles.isEmpty())
		{
			for(Raffle r : raffles)
			{
				int i = 0;
				row[i++] = r.getRaffleNO();
				row[i++] = r.getRaffleDate();
				//Project number is actually the participate winning place in the instance
				row[i++] = r.getProjectNO();
				model.addRow(row);	
			}
		}

	}
	
///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	private JPanel contentPane;
	private JTable myPastRaf;
	private DefaultTableModel model;
}
