package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.jfree.util.Log;

import com.toedter.calendar.JDateChooser;

import control.Logic;
import model.City;
import model.Resident;
import utils.TextFieldFocus;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditResident extends JDialog {


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditResident frame = new EditResident();
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
	public EditResident() {
		initComp();
	}
	public EditResident(JFrame owner, Resident resident) {
		super(owner, true);
		this.resident = resident;
		initComp();
		initCities();
		updResidentCity();
	}
	
	private void initComp() {
		setBounds(100, 100, 410, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 394, 318);
		pMain.setBackground(Color.decode("#EAE6EF"));
		contentPane.add(pMain);
		pMain.setLayout(null);

		JLabel lblEditResident = new JLabel("Edit Resident:");
		lblEditResident.setFont(new Font("Times New Roman", Font.BOLD, 29));
		lblEditResident.setBounds(10, 11, 183, 39);
		pMain.add(lblEditResident);

		textField = new JTextField();
		textField.setBounds(0, 0, 0, 1);
		pMain.add(textField);
		textField.setColumns(10);

		pLeft = new JPanel();
		pLeft.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pLeft.setBounds(10, 68, 183, 205);
		pMain.add(pLeft);
		pLeft.setLayout(null);

		resId = new JTextField();
		resId.setEnabled(false);
		resId.setEditable(false);
		resId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(resId.getText().length()>=9)
					arg0.consume();
			}
		});
		resId.setBounds(20, 29, 142, 26);
		pLeft.add(resId);
		resId.addFocusListener(new TextFieldFocus(resId, "Resident ID", ""));
		resId.setColumns(11);

		firstName = new JTextField();
		firstName.setBounds(20, 66, 142, 26);
		pLeft.add(firstName);
		firstName.setColumns(11);

		lastName = new JTextField();
		lastName.setBounds(20, 103, 142, 26);
		pLeft.add(lastName);
		lastName.setColumns(11);
		currentCity = new JComboBox();
		currentCity.setBounds(20, 140, 142, 26);
		pLeft.setBackground(Color.decode("#EAE6EF"));
		pLeft.add(currentCity);

		pRight = new JPanel();
		pRight.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pRight.setBounds(203, 67, 183, 206);
		pRight.setBackground(Color.decode("#EAE6EF"));
		pMain.add(pRight);
		pRight.setLayout(null);

		birthD = new JDateChooser();
		birthD.setBounds(10, 31, 120, 20);
		pRight.add(birthD);

		lblFamilyStatus = new JLabel("Family status:");
		lblFamilyStatus.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblFamilyStatus.setBounds(10, 62, 100, 20);
		pRight.add(lblFamilyStatus);

		lblBirthDate = new JLabel("Birth date:");
		lblBirthDate.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblBirthDate.setBounds(10, 11, 100, 20);
		pRight.add(lblBirthDate);

		rdbtnMarried = new JRadioButton("Married");
		buttonGroup.add(rdbtnMarried);
		rdbtnMarried.setBounds(10, 115, 109, 23);
		rdbtnMarried.setBackground(Color.decode("#EAE6EF"));
		rdbtnMarried.setActionCommand("M");
		pRight.add(rdbtnMarried);

		rdbtnSingle = new JRadioButton("Single");
		buttonGroup.add(rdbtnSingle);
		rdbtnSingle.setToolTipText("");
		rdbtnSingle.setBounds(10, 89, 109, 23);
		rdbtnSingle.setBackground(Color.decode("#EAE6EF"));
		rdbtnSingle.setSelected(true);
		rdbtnSingle.setActionCommand("S");
		pRight.add(rdbtnSingle);

		rbbtnDivorced = new JRadioButton("Divorced");
		buttonGroup.add(rbbtnDivorced);
		rbbtnDivorced.setBounds(10, 141, 109, 23);
		rbbtnDivorced.setBackground(Color.decode("#EAE6EF"));
		rbbtnDivorced.setActionCommand("D");
		pRight.add(rbbtnDivorced);

		rdbtnWidower = new JRadioButton("Widower");
		buttonGroup.add(rdbtnWidower);
		rdbtnWidower.setBounds(10, 167, 109, 23);
		rdbtnWidower.setBackground(Color.decode("#EAE6EF"));
		rdbtnWidower.setActionCommand("W");
		pRight.add(rdbtnWidower);
		
		JButton btnUpadate = new JButton("Upadate");
		btnUpadate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddResident ar = new AddResident();
				Resident r = ar.checkInput(resId.getText(), firstName.getText(), lastName.getText(),
						currentCity.getSelectedItem().toString(), birthD, buttonGroup.getSelection().getActionCommand(), false);
				if(r != null)
				{
					Logic logic = Logic.getInstance();
					logic.updateResident(r);
					logic.deleteEntiteld(r);
					int entitledNum = logic.addEntiteld(r, logic.getCatagoriesForResident(r));
					JOptionPane.showMessageDialog(null, "Resident " + r.getId() + 
							" was updated successfully.\nThe resident is signed up for " + entitledNum + " entitlement categories");
				}
				else
					JOptionPane.showMessageDialog(null, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		btnUpadate.setBounds(297, 284, 89, 23);
		pMain.add(btnUpadate);
		
		JLabel lblNote = new JLabel("<html><p>*Please change <strong>only</strong> the fields you wish to update.</p></html>");
		lblNote.setBounds(10, 48, 310, 14);
		pMain.add(lblNote);
		setResident();

	}
	
	public void setResident(){
		resId.setText(resident.getId());
		firstName.setText(resident.getFirstName());
		lastName.setText(resident.getLastName());
		currentCity.setSelectedItem(resident.getCurrentCity());
		birthD.setDate(resident.getBirthDate());
		switch(resident.getFamilyStatus()) {
			case "S":
				rdbtnSingle.setSelected(true);
				break;
			case "M":
				rdbtnMarried.setSelected(true);
				break;
			case "D":
				rbbtnDivorced.setSelected(true);
				break;
			case "W":
				rdbtnWidower.setSelected(true);
		}
		
	}
	
	private void initCities() {
		ArrayList<City> cities = Logic.getInstance().getCities();
		for(City c : cities)
			currentCity.addItem(c.toString());
	}
	
	private void updResidentCity() {
		ArrayList<City> cities = Logic.getInstance().getCities();
		int ind = 1;
		for(int i = 0 ; i < currentCity.getItemCount() ; i++)
		{
			if(cities.get(i).getName().equals(resident.getCurrentCity()))
				ind = i;
		}		
		currentCity.setSelectedIndex(ind);
	}
	
	///////////////////////////////////CLASS VARIABLES//////////////////////////////////////

	private JPanel contentPane;
	private JTextField resId;
	private JTextField textField;
	private JTextField firstName;
	private JTextField lastName;
	private JPanel pLeft;
	private JPanel pRight;
	private JLabel lblFamilyStatus;
	private JLabel lblBirthDate;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox currentCity;
	private static final int resIdLen = 9;
	private JDateChooser birthD;
	private Resident resident;
	private JRadioButton rdbtnSingle;
	private JRadioButton rdbtnMarried;
	private JRadioButton rbbtnDivorced;
	private JRadioButton rdbtnWidower;
}
