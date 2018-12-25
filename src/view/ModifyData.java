package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import org.jfree.util.Log;

import com.graphbuilder.math.TermNode;

import control.Logic;
import model.EntitlementCategory;
import model.Participate;
import model.Resident;
import model.TenantPriceApartment;
import model.UserCredentials;
import utils.Consts;
import utils.InvalidDataException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModifyData extends RootLayout {
	
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
					frame = new ModifyData();
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
	public ModifyData() {

		initComponents();
		setParticipate();
	}
	
	private void refreshComp() {

		if(type.getSelectedItem().toString().equalsIgnoreCase("participate"))
			setParticipate();
		else
			setResident();
	}
	
	private void initComponents() {
		
		JPanel hPanel = new JPanel();
		hPanel.setBounds(0, 66, 143, 139);
		hPanel.setBackground(Color.decode("#4D435D"));
		getContentPane().add(hPanel);
		
		JPanel cPanel = new JPanel();
		cPanel.setBounds(0, 474, 143, 85);
		cPanel.setBackground(Color.decode("#4D435D"));
		getContentPane().add(cPanel);
		
		JPanel mPanel = new JPanel();
		mPanel.setBounds(0, 205, 143, 70);
		mPanel.setBackground(Color.decode("#40384D"));
		getContentPane().add(mPanel);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(143, 51, 800, 556);
		pMain.setBackground(Color.decode("#EAE6EF"));
		getContentPane().add(pMain);
		pMain.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 124, 523, 293);
		pMain.add(scrollPane);
		
		JPanel options = new JPanel();
		options.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		options.setBounds(586, 124, 141, 293);
		options.setBackground(Color.decode("#EAE6EF"));
		pMain.add(options);
		options.setLayout(null);
		
		lblAdd = new JLabel("New label");
		lblAdd.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblAdd.setBounds(10, 11, 143, 23);
		options.add(lblAdd);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(type.getSelectedItem().toString().equalsIgnoreCase("participate"))
					openAddParticipate();
				else
					openAddResident();
			}
		});
		btnAdd.setBounds(20, 45, 89, 23);
		options.add(btnAdd);
		
		lblEdit = new JLabel("New label");
		lblEdit.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblEdit.setBounds(10, 120, 116, 23);
		options.add(lblEdit);
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String str = type.getSelectedItem().toString();
					if(table.getSelectedRow() == -1)
						throw new InvalidDataException("Please select a " + str.toLowerCase() + "." );
					String id = table.getValueAt(table.getSelectedRow(), 0).toString();
					if(str.equalsIgnoreCase("participate"))
						openEditParticipate(id);
					else
						openEditResident(id);
				}catch(InvalidDataException s) {
					JOptionPane.showMessageDialog(null, s.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnEdit.setBounds(20, 154, 89, 23);
		options.add(btnEdit);
		
		lblDelete = new JLabel("New label");
		lblDelete.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblDelete.setBounds(10, 225, 122, 23);
		options.add(lblDelete);
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String userType = type.getSelectedItem().toString().toLowerCase();
					if(table.getSelectedRow() == -1)
						throw new InvalidDataException("please select a " + userType + ".");
					String id = table.getValueAt(table.getSelectedRow(), 0).toString();
					if(userType.equals("participate"))	
						removeParticipate(id);
					else
						removeResident(id);
						
				} catch (InvalidDataException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		btnDelete.setBounds(20, 259, 89, 23);
		options.add(btnDelete);
		
		table = new JTable() {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && type.getSelectedItem().toString().equalsIgnoreCase("participate"))
					viewParticipateDetails();
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		type = new JComboBox();
		type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshComp();
			}
		});
		type.setModel(new DefaultComboBoxModel(new String[] {"Participate", "Resident"}));
		type.setFont(new Font("Times New Roman", Font.BOLD, 20));
		type.setBounds(603, 0, 197, 34);
		pMain.add(type);
		
		lblName = new JLabel("New label");
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 38));
		lblName.setBounds(10, 79, 231, 34);
		pMain.add(lblName);
		
		lblDetails = new JLabel("*Double click a participate for more details.");
		lblDetails.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblDetails.setBounds(35, 421, 249, 14);
		pMain.add(lblDetails);
		
		panel = new JPanel();
		panel.setBackground(new Color(77, 67, 93));
		panel.setBounds(0, 279, 143, 139);
		getContentPane().add(panel);
		
	}
	
	private void setParticipate() {
		lblName.setText("Participates:");
		lblAdd.setText("Add participate:");
		lblEdit.setText("Edit participate:");
		lblDelete.setText("Delete participate:");
		btnAdd.setToolTipText("Add residents to raffles");
		btnEdit.setToolTipText("<html><p>-Update participate winning place<br>-Remove participate from a specific raffle</p></html>");
		btnDelete.setToolTipText("<html><p>Removes participate from the database (removes all<br>relevant raffles for a specific resident)\r\n" + 
				"</p></html>");
		lblDetails.setVisible(true);
		createParticipateTable();
	}
	
	private void setResident() {
		lblName.setText("Residents:");
		lblAdd.setText("Add resident:");
		lblEdit.setText("Edit resident:");
		lblDelete.setText("Delete resident:");
		btnAdd.setToolTipText("Add a resident to the database");
		btnEdit.setToolTipText("<html><p>Update resident personal information</p></html>");
		btnDelete.setToolTipText("<html><p>Remove a resident from the database</p></html>");
		lblDetails.setVisible(false);
		createResidentTable(this.table);
	}
	
	private void createParticipateTable() {
		
		Object[] columns = Consts.participateValues;
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		((DefaultTableModel)table.getModel()).setRowCount(0);
		Object[] row = new Object[Consts.participateValues.length];
		ArrayList<Resident> participates = Logic.getInstance().getParticipatesNames();
		for(Resident p : participates)
		{
			int i = 0;
			row[i++] = p.getId();
			row[i++] = p.getFirstName();
			row[i++] = p.getLastName();
			
			model.addRow(row);	
		}
	}
	
	protected void createResidentTable(JTable table) {
		
		Object[] columns = Consts.residentValues;
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		((DefaultTableModel)table.getModel()).setRowCount(0);
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
			
			model.addRow(row);	
		}	
	}
	
	private void openAddParticipate() {
		AddParticipate window = new AddParticipate(this);
		window.setVisible(true);
		refreshComp();
	}
	
	private void openEditParticipate(String id) {
		Participate p = Logic.getInstance().getParticipate(id);
		EditParticipate window = new EditParticipate(this, p);
		window.setVisible(true);
		refreshComp();
		
	}
	
	private void openAddResident() {
		AddResident window = new AddResident(this);
		window.setVisible(true);
    	refreshComp();
	}
	
	private void openEditResident(String id) {
		Resident r = Logic.getInstance().getResident(id);
		EditResident window = new EditResident(this, r);
		window.setVisible(true);
    	refreshComp();
	}
	
	private void viewParticipateDetails() {
		Participate p = Logic.getInstance().getParticipate(table.getValueAt(table.getSelectedRow(), 0).toString());
		ParticipateDetails window = new ParticipateDetails(this, p);
		window.setVisible(true);
	}
	

	private void removeParticipate(String id) {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete participate " + id + "?", "Remove participate", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION)
		{
			if(Logic.getInstance().deleteResidentFromAllRaffles(new Participate(id)))
			{
				JOptionPane.showMessageDialog(null, "Participate " + id + " was removed successfully.");
				Logic.getInstance().updateUserCredentialsType(id, "Resident");
				refreshComp();
			}
			else
				JOptionPane.showMessageDialog(null, "Participate " + id + " was not removed.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void removeResident(String id) {
		int res = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete resident " + id + "?", "Remove participate", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION)
		{
			Logic logic = Logic.getInstance();
			Resident r = logic.getResident(id);
			ArrayList<Participate> raffles = logic.getParticipateRaffles(new Participate(r.getId()));
			ArrayList<EntitlementCategory> categories = logic.getCatagoriesForResident(r);
			ArrayList<TenantPriceApartment> apartments = logic.getResidentApa(r.getId());
			boolean delRaf, delCat, delApa, delRes;
			delRaf = delCat = delApa = delRes = false;
			if(!raffles.isEmpty())
				delRaf = logic.deleteResidentFromAllRaffles(new Participate(r.getId()));
			else
				delRaf = true;
			if(!categories.isEmpty())
				delCat = logic.deleteEntiteld(r);
			else
				delCat = true;
			if(!apartments.isEmpty())
				delApa = logic.deleteResidentFromApa(r.getId());
			else
				delApa = true;
			if(delRaf && delCat && delApa)
				delRes = logic.deleteResident(r.getId());
			else //ROLLBACK
			{
				if(delCat)
					logic.addEntiteld(r, categories);
				if(delRaf)
				{
					for(Participate par : raffles)
						logic.addParticipate(par);
				}
				if(delApa)
				{
					for(TenantPriceApartment tpa : apartments)
						logic.updateResidentInApa(tpa.getProjectNO(), tpa.getBuildingNO(),
									tpa.getBuildingNO(), tpa.getResidentId());
				}
			}
			if(delRes)
			{
				JOptionPane.showMessageDialog(null, "Resident " + id + " was removed successfully.");
				String type = raffles.isEmpty() ? "Resident" : "Participate";
				logic.deleteUserCredentials(r.getId(), type);
				refreshComp();
			}
			else
				JOptionPane.showMessageDialog(null, "Resident " + id + " was not removed.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
		
///////////////////////////////////CLASS VARIABLES//////////////////////////////////////

	private static ModifyData frame;
	private JTable table;
	private  DefaultTableModel model;
	private JLabel lblName;
	private JLabel lblAdd;
	private JLabel lblEdit;
	private JLabel lblDelete;
	private JComboBox type;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JLabel lblDetails;
	private JPanel panel;
}