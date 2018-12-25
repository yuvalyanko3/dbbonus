package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.util.Log;

import control.Logic;
import model.Participate;
import model.Raffle;
import model.Resident;
import utils.Consts;
import utils.InvalidDataException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditParticipate extends JDialog {

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
					EditParticipate frame = new EditParticipate();
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
	public EditParticipate() {
		initComp();
	}
	
	public EditParticipate(JFrame owner, Participate participate) {
		super(owner, true);
		this.participate = participate;
		initComp();
		refreshComp();
	}
	


	private void refreshComp() {
		setParticipate();
		buildRaffleTable(Logic.getInstance().getParticipateRaffels(participate));
	}

	private void initComp() {
		setBounds(100, 100, 311, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 295, 462);
		pMain.setBackground(Color.decode("#EAE6EF"));
		contentPane.add(pMain);
		pMain.setLayout(null);
		
		JLabel lblHeader = new JLabel("Edit participate:");
		lblHeader.setFont(new Font("Times New Roman", Font.BOLD, 38));
		lblHeader.setBounds(10, 11, 302, 44);
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
		scrollPane.setBounds(20, 152, 254, 216);
		pMain.add(scrollPane);
		
		pTable = new JTable() {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		pTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(pTable);
		
		JPanel pRemove = new JPanel();
		pRemove.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pRemove.setBounds(20, 371, 122, 67);
		pRemove.setBackground(Color.decode("#EAE6EF"));
		pMain.add(pRemove);
		
		JLabel lblRemoveFromRaffle = new JLabel("Remove from raffle:");
		lblRemoveFromRaffle.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		pRemove.add(lblRemoveFromRaffle);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(pTable.getSelectedRow() == -1)
						throw new InvalidDataException("Please select a raffle.");
					int rafNum = Integer.parseInt(pTable.getValueAt(pTable.getSelectedRow(), 0).toString());
					if(Logic.getInstance().deleteParticipateFromRaffle(participate, rafNum))
					{
						JOptionPane.showMessageDialog(null, "Raffle was deleted from participate.");
						refreshComp();
					}
					else
						throw new InvalidDataException("Raffle was not deleted from participate.");
				}catch(InvalidDataException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pRemove.add(btnRemove);
		
		JPanel pUpdate = new JPanel();
		pUpdate.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pUpdate.setBackground(new Color(234, 230, 239));
		pUpdate.setBounds(152, 371, 122, 67);
		pMain.add(pUpdate);
		
		JLabel lblWinngPlace = new JLabel("Update winning place:");
		lblWinngPlace.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		pUpdate.add(lblWinngPlace);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(pTable.getSelectedRow() == -1)
						throw new InvalidDataException("Please select a raffle.");
					updateWinningPlace(Integer.parseInt(pTable.getValueAt(pTable.getSelectedRow(), 0).toString()));
				}catch(InvalidDataException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		pUpdate.add(btnUpdate);
	}
	
	private void buildRaffleTable(ArrayList<Raffle> raffles) {
		Object[] columns = {"Raffle number", "Raffle date"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		pTable.setModel(model);
		((DefaultTableModel)pTable.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		if(!raffles.isEmpty())
		{
			for(Raffle r : raffles)
			{
				int i = 0;
				row[i++] = r.getRaffleNO();
				row[i++] = r.getRaffleDate();
				model.addRow(row);	
			}
		}

	}
	
	private void setParticipate() {
		parId.setText(participate.getResidentId());
	}
	
	private void updateWinningPlace(int raffNum) {
		boolean flag = true;
		String winningPlace  = "";
		int wNum = -1;
		while(flag)
		{
			winningPlace = JOptionPane.showInputDialog("Please enter the participate winning place:");
			if(winningPlace == null)
				flag = false;
			else if(!isNumber(winningPlace))
				JOptionPane.showMessageDialog(null, "Please enter a number.", "Input error", JOptionPane.ERROR_MESSAGE);
			else
			{
				int temp = Integer.parseInt(winningPlace);
				if(temp < 1 || temp > 255)
					JOptionPane.showMessageDialog(null, "Winning place must be between 1-255.", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					wNum = temp;
					flag = false;
				}
	
			}
			
		}
		if(wNum != -1)
		{
			if(Logic.getInstance().uptateParticipateWinningPlace(participate, raffNum, wNum))
				JOptionPane.showMessageDialog(null, "Winning place was updated successfully.");
			else
				JOptionPane.showMessageDialog(null, "Winning place was not updated.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private boolean isNumber(String s) {
		boolean toReturn = false;
		if(s.matches("[0-9]+"))
			toReturn = true;
		return toReturn;
	}
	
	///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	
	private JPanel contentPane;
	private Participate participate;
	private JTextField parId;
	private JTable pTable;
	private DefaultTableModel model;
}
