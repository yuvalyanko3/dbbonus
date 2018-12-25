package view;

import java.awt.BorderLayout;
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

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import javax.swing.JButton;

public class JoinRaffle extends JDialog {


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
					JoinRaffle frame = new JoinRaffle();
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
	
	public JoinRaffle() {
		initComponents();
	}
	
	public JoinRaffle(JFrame owner, String id) {
		super(owner, true);
		initComponents();
		resident = Logic.getInstance().getResident(id);
		refreshRaffle(resident.getId());
	}
	
	
	private void refreshRaffle(String id){
		Logic logic = Logic.getInstance();
		ArrayList<Raffle> raffles = logic.getRaffelsForResident(logic.getResident(id));
		buildRaffleTable(distinctRaffels(raffles));
		buildMyRaffleTable(Logic.getInstance().getParticipateRaffels(new Participate(id)));
	}
	
	private void initComponents() {
		setBounds(100, 100, 480, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 464, 499);
		contentPane.add(pMain);
		pMain.setBackground(Color.decode("#EAE6EF"));
		pMain.setLayout(null);
		
		JLabel lblJoinleaveRaffles = new JLabel("Join/Leave Raffle(s):");
		lblJoinleaveRaffles.setFont(new Font("Times New Roman", Font.BOLD, 29));
		lblJoinleaveRaffles.setBounds(10, 11, 298, 31);
		pMain.add(lblJoinleaveRaffles);
		
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(52, 322, 350, 166);
		pMain.add(scrollPane);
		
		rafTable = new JTable() {
		public boolean isCellEditable(int row,int column) {
			return false;
		}};
		rafTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
				{
					try {
						String rafNum = rafTable.getValueAt(rafTable.getSelectedRow(), 0).toString();
						if(rafNum.equalsIgnoreCase("no valid raffles"))
							throw new InvalidDataException("You have no valid raffles.");
						joinRaffle(rafNum);
					}catch(InvalidDataException s) {
						JOptionPane.showMessageDialog(null, s.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}

				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				myRaf.clearSelection();
			}
		});
	
		scrollPane.setViewportView(rafTable);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(52, 107, 350, 171);
		pMain.add(scrollPane_1);
		myRaf = new JTable() {
			public boolean isCellEditable(int row,int column) {
				return false;
			}};
		myRaf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2)
				{
					String rafNum = myRaf.getValueAt(myRaf.getSelectedRow(), 0).toString();
					leaveRaffle(rafNum);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				rafTable.clearSelection();

			}
		});
		scrollPane_1.setViewportView(myRaf);
		
		myRaf.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rafTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblLeave = new JLabel("*Double click a raffle to leave it");
		lblLeave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLeave.setBounds(52, 91, 197, 14);
		pMain.add(lblLeave);
		
		JLabel lbldoubleClickA = new JLabel("*Double click a raffle to join it");
		lbldoubleClickA.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbldoubleClickA.setBounds(52, 307, 197, 14);
		pMain.add(lbldoubleClickA);
		
		JLabel lblAvailableRaffles = new JLabel("Available Raffles:");
		lblAvailableRaffles.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblAvailableRaffles.setBounds(52, 289, 186, 14);
		pMain.add(lblAvailableRaffles);
		
		JLabel lblYourRaffles = new JLabel("Your Future Raffles:");
		lblYourRaffles.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblYourRaffles.setBounds(52, 71, 186, 14);
		pMain.add(lblYourRaffles);
		
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
			row[i++] = "NO VALID RAFFLES";
			row[i++] = "";
			row[i++] = "";
			rafModel.addRow(row);	
		}
	}
	
	private void buildMyRaffleTable(ArrayList<Raffle> raffles) {
		Object[] columns = {"Raffle number", "Raffle date"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		myRaf.setModel(model);
		((DefaultTableModel)myRaf.getModel()).setRowCount(0);
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
	
	private void leaveRaffle(String rafNum) {
		int ans = JOptionPane.showConfirmDialog(null, "Are you sure you wish to LEAVE raffle number " + rafNum +"?", "Leave Raffle", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.YES_OPTION)
		{
			try {
				int raf = Integer.parseInt(myRaf.getValueAt(myRaf.getSelectedRow(), 0).toString());
				if(Logic.getInstance().deleteParticipateFromRaffle(new Participate(resident.getId()), raf))
				{
					JOptionPane.showMessageDialog(null, "Raffle was deleted from participate.");
					refreshRaffle(resident.getId());
					if(myRaf.getRowCount() == 0)
						Logic.getInstance().updateUserCredentialsType(resident.getId(), "Resident");
				}
				else
					throw new InvalidDataException("Raffle was not deleted from participate.");
			}catch(InvalidDataException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void joinRaffle(String rafNum) {
		int ans = JOptionPane.showConfirmDialog(null, "Are you sure you wish to JOIN raffle number " + rafNum +"?", "Join Raffle", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.YES_OPTION)
		{
			Date now = new java.sql.Date(new java.util.Date().getTime());
			int raf = Integer.parseInt(rafTable.getValueAt(rafTable.getSelectedRow(), 0).toString());
			Participate p = new Participate(raf, resident.getId(), now);
			if(Logic.getInstance().addParticipate(p))
			{
				JOptionPane.showMessageDialog(null, "Participate " + p.getResidentId() + " was added to raffle " + rafNum +" successfully.");
				Logic.getInstance().updateUserCredentialsType(resident.getId(), "Participate");
				refreshRaffle(resident.getId());
			}
			else
				JOptionPane.showMessageDialog(null, "Participate " + p.getResidentId() + " was not added.");
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
	
///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	private JPanel contentPane;
	private JTable rafTable;
	private DefaultTableModel rafModel;
	private DefaultTableModel model;
	private JTable myRaf;
	private Resident resident;
}
