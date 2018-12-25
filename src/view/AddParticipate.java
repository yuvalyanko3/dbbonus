package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ListSelectionModel;


import control.Logic;
import model.Participate;
import model.Raffle;
import model.Resident;
import utils.Consts;
import utils.InvalidDataException;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddParticipate extends JDialog {

	private static final long serialVersionUID = 1L;
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
					AddParticipate frame = new AddParticipate();
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
	public AddParticipate() {
		initComp();
		buildResidentTable();
	}
	
	public AddParticipate(JFrame owner) {
		super(owner, true);
		initComp();
		refreshResidents();
	}
	
	private void refreshResidents(){
		buildResidentTable();
	}
	private void refreshRaffle(String id){
		Logic logic = Logic.getInstance();
		ArrayList<Raffle> raffles = logic.getRaffelsForResident(logic.getResident(id));
		buildRaffleTable(distinctRaffels(raffles));
	}
	
	public void initComp() {
		setBounds(100, 100, 585, 599);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 569, 560);
		pMain.setBackground(Color.decode("#EAE6EF"));
		contentPane.add(pMain);
		pMain.setLayout(null);
		
		JLabel lblAddParticipate = new JLabel("Add Participate:");
		lblAddParticipate.setFont(new Font("Times New Roman", Font.BOLD, 29));
		lblAddParticipate.setBounds(10, 11, 210, 45);
		pMain.add(lblAddParticipate);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkData();
//				refreshResidents();
			}

		});
		button.setBounds(459, 526, 79, 23);
		pMain.add(button);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 375, 508, 140);
		pMain.add(scrollPane);
		
		rafTable = new JTable() {
		public boolean isCellEditable(int row,int column) {
			return false;
		}};
		rafTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(rafTable);
		
		JLabel lblPleaseSelectA = new JLabel("Please select a raffle:");
		lblPleaseSelectA.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPleaseSelectA.setBounds(30, 350, 160, 14);
		pMain.add(lblPleaseSelectA);
		
		JLabel lblPleaseSelectA_1 = new JLabel("Please select a resident:");
		lblPleaseSelectA_1.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPleaseSelectA_1.setBounds(30, 67, 160, 14);
		pMain.add(lblPleaseSelectA_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(30, 92, 508, 247);
		pMain.add(scrollPane_1);
		
		resTable = new JTable() {
			public boolean isCellEditable(int row,int column) {
			return false;
		}};
		resTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					String id = resTable.getValueAt(resTable.getSelectedRow(), 0).toString();
					refreshRaffle(id);
				}catch(Exception s) {
					s.printStackTrace();
				}

			}
		});
		resTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(resTable);
	}
	
	private void buildRaffleTable(ArrayList<Raffle> raffles) {
		Object[] columns = Consts.raffleValues;
		rafModel = new DefaultTableModel();
		rafModel.setColumnIdentifiers(columns);
		rafTable.setModel(rafModel);
		((DefaultTableModel)rafTable.getModel()).setRowCount(0);
		Object[] row = new Object[Consts.raffleValues.length];
		if(!raffles.isEmpty())
		{
			for(Raffle r : raffles)
			{
				int i = 0;
				row[i++] = r.getRaffleNO();
				row[i++] = r.getRaffleDate();
				row[i++] = r.getProjectNO();
				rafModel.addRow(row);	
			}
		}
		else
		{
			int i = 0;
			row[i++] = "NO VALID RAFFELS";
			row[i++] = "";
			row[i++] = "";
			rafModel.addRow(row);	
		}

	}
	
	private void buildResidentTable() {
		Object[] columns = Consts.residentValues;
		resModel = new DefaultTableModel();
		resModel.setColumnIdentifiers(columns);
		resTable.setModel(resModel);
		((DefaultTableModel)resTable.getModel()).setRowCount(0);
		Object[] row = new Object[Consts.residentValues.length];
		ArrayList<Resident> residents = Logic.getInstance().getResidents();
		for(Resident r : residents)
		{
			int i = 0;
			row[i++] = r.getId();
			row[i++] = r.getFirstName();
			row[i++] = r.getLastName();
			row[i++] = r.getBirthDate();
			row[i++] = r.getCurrentCity();
			row[i++] = r.getFamilyStatus();
			
			resModel.addRow(row);	
		}	
	}
	
	private ArrayList<Raffle> distinctRaffels(ArrayList<Raffle> raffles){
		ArrayList<Raffle> r = new ArrayList<>();
		for(Raffle raffle : raffles)
		{
			if(!r.contains(raffle))
				r.add(raffle);
		}
		return r;
	}
	
	private void checkData() {
		try {
			if(resTable.getSelectedRow() == -1)
				throw new InvalidDataException("Please select a resident.");
			if(rafTable.getSelectedRow() == -1)
				throw new InvalidDataException("Please select a raffle.");
			if(rafTable.getValueAt(rafTable.getSelectedRow(), 0).toString().equalsIgnoreCase("no valid raffels"))
				throw new InvalidDataException("There are no valid raffels for this resident.");
			Date now = new java.sql.Date(new java.util.Date().getTime());
			String resId = resTable.getValueAt(resTable.getSelectedRow(), 0).toString();
			int rafNum = Integer.parseInt(rafTable.getValueAt(rafTable.getSelectedRow(), 0).toString());
			Participate p = new Participate(rafNum, resId, now);
			if(Logic.getInstance().addParticipate(p))
			{
				JOptionPane.showMessageDialog(null, "Participate " + p.getResidentId() + " was added to raffle " + rafNum +" successfully.");
				Logic.getInstance().updateUserCredentialsType(resId, "Participate");
				refreshRaffle(resId);
			}
			else
				JOptionPane.showMessageDialog(null, "Participate " + p.getResidentId() + " was not added.");
		}catch(InvalidDataException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	private JPanel contentPane;
	private JTable rafTable;
	private JScrollPane scrollPane;
	private DefaultTableModel resModel;
	private DefaultTableModel rafModel;
	private JTable resTable;
}
