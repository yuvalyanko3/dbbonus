package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import control.Logic;
import model.EntitlementCategory;
import model.Resident;
import utils.Consts;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import java.awt.List;
import java.awt.Font;

public class MoreInformation extends JDialog {
	
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				MoreInformation frame = new MoreInformation();
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
	public MoreInformation() {
		
		initComponents();
		createDesicionTable();
	}
	
	public MoreInformation(JFrame owner) {
		
		super(owner, true);
		initComponents();
		createDesicionTable();
	}
	
	
	
	private void initComponents() {
		
		JPanel pMain = new JPanel();
		setBounds(100, 100, 534, 452);
		pMain.setBounds(144, 51, 799, 597);
		pMain.setBackground(Color.decode("#EAE6EF"));
		getContentPane().add(pMain);
		pMain.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 163, 451, 239);
		pMain.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblQuery = new JLabel("Select an option for more information:");
		lblQuery.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblQuery.setBounds(20, 14, 279, 19);
		pMain.add(lblQuery);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(20, 39, 451, 101);
		pMain.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 451, 101);
		panel_1.add(scrollPane_1);
		
		JList<String> list = new JList<>(setList());
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ind = list.getSelectedIndex();
				if(ind == 0)
					createTableQ2();
				if(ind == 1)
					createTableQ4();
				if(ind == 2)
					createTableQ5();
				if(ind == 3)
					createTableQ6();
				if(ind == 4)
					createTableQ8();
			}
		});
		list.setCellRenderer(getRenderer());
		scrollPane_1.setViewportView(list);
	}
	
	
	private void createTableQ2() {
		
		
		Object[] columns = {"Raffle number", "Project number", "Project name"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		((DefaultTableModel)table.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		ArrayList<Object[]> two = Logic.getInstance().queryTwo();
		for(Object[] rs : two)
			model.addRow(rs);	
		
	}
	
	private void createTableQ4() {
		
		
		Object[] columns = {"Resident ID", "Full name"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		((DefaultTableModel)table.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		ArrayList<Object[]> two = Logic.getInstance().querySix();
		for(Object[] rs : two)
			model.addRow(rs);	
		
	}
	
	private void createTableQ5() {
		
		
		Object[] columns = {"Contractor ID", "Name", "Serial number", "Accessory description", "Price", "Category"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		((DefaultTableModel)table.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		ArrayList<Object[]> two = Logic.getInstance().queryFive();
		for(Object[] rs : two)
			model.addRow(rs);	
		
	}
	
	private void createTableQ6() {
		
		
		Object[] columns = {"Contractor ID", "Name"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		((DefaultTableModel)table.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		ArrayList<Object[]> two = Logic.getInstance().queryFour();
		for(Object[] rs : two)
			model.addRow(rs);	
		
	}
	
	private void createTableQ8() {
		
		
		Object[] columns = {"Resident ID", "Full name"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		((DefaultTableModel)table.getModel()).setRowCount(0);
		Object[] row = new Object[columns.length];
		ArrayList<Resident> eight = Logic.getInstance().queryEight();
		for(Resident rs : eight)
		{
			int i = 0;
			row[i++] = rs.getId();
			row[i++] = rs.getFirstName() + " " + rs.getLastName();
			model.addRow(row);	
		}
		
	}
	
	private void createDesicionTable() {
		
		
			Object[] columns = {"Choose an option"};
			model = new DefaultTableModel();
			model.setColumnIdentifiers(columns);
			Object[] row = new Object[5];
			row[0] = "<html><p>Raffles with for than 5 buildings,<br>that got less than 10 floors each</p></html>";
			row[1] = "<html><p>Projects in haifa who<br>are up to standards</p></html>";
			model.addRow(row);

			
	}
	
	
	private DefaultListModel<String> setList(){
		DefaultListModel<String> listM = new DefaultListModel<>();
		listM.addElement("- Raffles with for than 5 buildings,that got less than 10 floors each");
		listM.addElement("- Residents who won a raffle (first 10 places) and changed half of the accessories");
		listM.addElement("- Popular items per contractor");
		listM.addElement("- Projects in haifa who<br>are up to standards");
		listM.addElement("<html><p>"
						+"- Residents who:<br>"
						+ "* Won first place in all of their entitlement categories<br>"
						+ "* Entitled to at least three entitlement categories<br>"
						+ "* Participated in all raffles in their city<br>"
						+ "</p></html>");
		return listM;
		
	}

    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.BLACK));
                return listCellRendererComponent;
            }
        };
    }

	 
///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	 private DefaultTableModel model;
	 private JTable table;
}
