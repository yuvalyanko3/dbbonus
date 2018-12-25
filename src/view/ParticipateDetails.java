package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import control.Logic;
import model.Participate;
import model.Raffle;
import utils.InvalidDataException;

public class ParticipateDetails extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("ComboBox.background", new ColorUIResource(Color.decode("#EAE6EF")));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParticipateDetails frame = new ParticipateDetails();
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
	public ParticipateDetails() {
		initComp();
	}
	
	public ParticipateDetails(JFrame owner, Participate participate) {
		super(owner, true);
		this.participate = participate;
		initComp();
		refreshComp();
	}
	


	private void refreshComp() {
		setParticipate();
		buildRaffleTable(Logic.getInstance().GetParticipateRaffleDetails(participate));
	}

	private void initComp() {
		setBounds(100, 100, 354, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 338, 383);
		pMain.setBackground(Color.decode("#EAE6EF"));
		contentPane.add(pMain);
		pMain.setLayout(null);
		
		JLabel lblHeader = new JLabel("Participate details:");
		lblHeader.setFont(new Font("Times New Roman", Font.BOLD, 38));
		lblHeader.setBounds(10, 11, 361, 44);
		pMain.add(lblHeader);
		
		JLabel lblNewLabel = new JLabel("Participate ID:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblNewLabel.setBounds(20, 66, 102, 14);
		pMain.add(lblNewLabel);
		
		parId = new JTextField();
		parId.setEnabled(false);
		parId.setEditable(false);
		parId.setBounds(20, 90, 115, 26);
		pMain.add(parId);
		parId.setColumns(10);
		
		JLabel lblRaffles = new JLabel("Raffle(s):");
		lblRaffles.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblRaffles.setBounds(20, 127, 72, 14);
		pMain.add(lblRaffles);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 152, 308, 216);
		pMain.add(scrollPane);
		
		pTable = new JTable() {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		scrollPane.setViewportView(pTable);
	}
	
	private void buildRaffleTable(ArrayList<Participate> raffles) {
		Object[] columns = {"Raffle number", "Raffle date", "Winning place"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		pTable.setModel(model);
		((DefaultTableModel)pTable.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		if(!raffles.isEmpty())
		{
			for(Participate r : raffles)
			{
				int i = 0;
				row[i++] = r.getRaffleNO();
				row[i++] = r.getRegistrationDate();
				row[i++] = r.getWinningPlace();
				model.addRow(row);	
			}
		}

	}
	
	private void setParticipate() {
		parId.setText(participate.getResidentId());
	}
	
	///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	
	private JPanel contentPane;
	private Participate participate;
	private JTextField parId;
	private JTable pTable;
	private DefaultTableModel model;
}