package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import utils.InvalidDataException;
import utils.TextFieldFocus;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.toedter.calendar.JDateChooser;
import com.toedter.components.JLocaleChooser;

import control.Logic;
import model.City;
import model.EntitlementCategory;
import model.Resident;
import model.UserCredentials;

import java.awt.Panel;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;

import org.jfree.util.Log;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

public class AddResident extends JDialog {

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
					AddResident frame = new AddResident();
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
	
	public AddResident() {
		initComponents();
		initCities();
	}
	
	public AddResident(JFrame owner) {
		super(owner, true);
		initComponents();
		initCities();
	}

	private void initComponents() {
		setBounds(100, 100, 492, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 476, 417);
		pMain.setBackground(Color.decode("#EAE6EF"));
		contentPane.add(pMain);
		pMain.setLayout(null);

		lblAddResident = new JLabel("Add Resident:");
		lblAddResident.setFont(new Font("Times New Roman", Font.BOLD, 29));
		lblAddResident.setBounds(10, 11, 183, 46);
		pMain.add(lblAddResident);

		textField = new JTextField();
		textField.setBounds(0, 0, 0, 1);
		pMain.add(textField);
		textField.setColumns(10);

		pLeft = new JPanel();
		pLeft.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pLeft.setBounds(10, 54, 456, 326);
		pMain.add(pLeft);
		pLeft.setLayout(null);

		resId = new JTextField();
		resId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(resId.getText().length()>=9)
					arg0.consume();
			}
		});
		resId.setBounds(10, 176, 153, 26);
		pLeft.add(resId);
		resId.setColumns(11);

		firstName = new JTextField();
		firstName.setBounds(10, 231, 153, 26);
		pLeft.add(firstName);
		firstName.setColumns(11);

		lastName = new JTextField();
		lastName.setBounds(10, 288, 153, 26);
		pLeft.add(lastName);
		lastName.setColumns(11);
		pLeft.setBackground(Color.decode("#EAE6EF"));
		
		JLabel lblUserName = new JLabel("Username:");
		lblUserName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblUserName.setBounds(10, 11, 95, 18);
		pLeft.add(lblUserName);
		
		txtUser = new JTextField();
		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String str = Logic.getInstance().getUserName(txtUser.getText());
				if(str == null)
				{
					lblUserError.setVisible(false);
					btnSubmit.setEnabled(true);
				}
				else 
				{
					lblUserError.setVisible(true);
					btnSubmit.setEnabled(false);
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if(txtUser.getText().length()>=20)
					e.consume();
			}
		});
		txtUser.setBounds(10, 31, 153, 26);
		pLeft.add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblPassword.setBounds(10, 68, 95, 14);
		pLeft.add(lblPassword);
		
		JLabel lblpasswordMust = new JLabel("<html><p>*Password must contain up to 12 characters</p></html>");
		lblpasswordMust.setForeground(Color.GRAY);
		lblpasswordMust.setBounds(10, 87, 225, 14);
		pLeft.add(lblpasswordMust);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				String pass = new String(passwordField.getPassword());
					if(pass.length()>=12)
						arg0.consume();
			}
		});
		passwordField.setBounds(10, 104, 153, 26);
		pLeft.add(passwordField);
		
		lblResID = new JLabel("Resident ID:");
		lblResID.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblResID.setBounds(10, 141, 142, 14);
		pLeft.add(lblResID);
		
		lblUserID = new JLabel("*ID must contain 9 numbers");
		lblUserID.setForeground(Color.GRAY);
		lblUserID.setBounds(10, 157, 162, 14);
		pLeft.add(lblUserID);
		
		lblFirstName = new JLabel("First name:");
		lblFirstName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblFirstName.setBounds(10, 213, 131, 18);
		pLeft.add(lblFirstName);
		
		lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLastName.setBounds(10, 268, 131, 18);
		pLeft.add(lblLastName);
		
		currentCity = new JComboBox();
		currentCity.setBounds(287, 241, 153, 26);
		pLeft.add(currentCity);
				
		lblFamilyStatus = new JLabel("Family status:");
		lblFamilyStatus.setBounds(287, 65, 100, 20);
		pLeft.add(lblFamilyStatus);
		lblFamilyStatus.setFont(new Font("Times New Roman", Font.BOLD, 15));
						
		JRadioButton rdbtnSingle = new JRadioButton("Single");
		rdbtnSingle.setBounds(287, 87, 109, 23);
		pLeft.add(rdbtnSingle);
		buttonGroup.add(rdbtnSingle);
		rdbtnSingle.setToolTipText("");
		rdbtnSingle.setBackground(Color.decode("#EAE6EF"));
		rdbtnSingle.setSelected(true);
		rdbtnSingle.setActionCommand("S");
								
		JRadioButton rdbtnMarried = new JRadioButton("Married");
		rdbtnMarried.setBounds(287, 113, 109, 23);
		pLeft.add(rdbtnMarried);
		buttonGroup.add(rdbtnMarried);
		rdbtnMarried.setBackground(Color.decode("#EAE6EF"));
		rdbtnMarried.setActionCommand("M");
										
		JRadioButton rbbtnDivorced = new JRadioButton("Divorced");
		rbbtnDivorced.setBounds(287, 138, 109, 23);
		pLeft.add(rbbtnDivorced);
		buttonGroup.add(rbbtnDivorced);
		rbbtnDivorced.setBackground(Color.decode("#EAE6EF"));
		rbbtnDivorced.setActionCommand("D");
												
		JRadioButton rdbtnWidower = new JRadioButton("Widower");
		rdbtnWidower.setBounds(287, 165, 109, 23);
		pLeft.add(rdbtnWidower);
		buttonGroup.add(rdbtnWidower);
		rdbtnWidower.setBackground(Color.decode("#EAE6EF"));
		rdbtnWidower.setActionCommand("W");
														
		lblBirthDate = new JLabel("Birth date:");
		lblBirthDate.setBounds(287, 10, 100, 20);
		pLeft.add(lblBirthDate);
		lblBirthDate.setFont(new Font("Times New Roman", Font.BOLD, 15));
																
		birthD = new JDateChooser();
		birthD.setBounds(287, 37, 120, 20);
		pLeft.add(birthD);
																		
		lblCityOfResidence = new JLabel("City of residence:");
		lblCityOfResidence.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblCityOfResidence.setBounds(287, 216, 142, 14);
		pLeft.add(lblCityOfResidence);
																		
		lblUserError = new JLabel("<html><p>Username already exists.</p></html>");
		lblUserError.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblUserError.setForeground(Color.RED);
		lblUserError.setBounds(165, 25, 86, 38);
		lblUserError.setVisible(false);
		pLeft.add(lblUserError);

		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Resident resident = checkInput(resId.getText(), firstName.getText(), lastName.getText(), 
					currentCity.getSelectedItem().toString(), birthD, buttonGroup.getSelection().getActionCommand(),true);
			if(resident != null)
			{	
				if(Logic.getInstance().addResident(resident))
				{
					ArrayList<EntitlementCategory> categories = Logic.getInstance().getCatagoriesForResident(resident);
					int entitledNum = Logic.getInstance().addEntiteld(resident, categories);
					UserCredentials uc = new UserCredentials(txtUser.getText(), new String(passwordField.getPassword()), resident.getId(), "Resident");
					Logic.getInstance().insertUserCredentials(uc);
					JOptionPane.showMessageDialog(null, "Resident " + resident.getId() + 
							" was added successfully.\nThe resident is signed up for " + entitledNum + " entitlement categories.");
				}
				else
					JOptionPane.showMessageDialog(null, "Resident " + resident.getId() + " was not added.", "Error", JOptionPane.ERROR_MESSAGE);
			}
					
		}
	});
	btnSubmit.setBounds(387, 383, 79, 23);
	pMain.add(btnSubmit);

	}

	private void initCities() {
		ArrayList<City> cities = Logic.getInstance().getCities();
		for(City c : cities)
			currentCity.addItem(c.toString());
	}

	protected Resident checkInput(String id, String fName, String lName, String city, JDateChooser bDay, String status, boolean isNew) {
		Resident resident = null;
		String password = new String(passwordField.getPassword());
//		if(fName.equalsIgnoreCase("first name"))
//			fName = "";
//		if(lName.equalsIgnoreCase("last name"))
//			lName = "";
		
		try {
			if(isNew)
			{
				if(txtUser.getText().trim().isEmpty())
					throw new InvalidDataException("User name cannot be empty");
				if(password.trim().isEmpty())
					throw new InvalidDataException("Password field cannot be empty.");
				if(!isNumber(id))
					throw new InvalidDataException("Resident ID must contain only numbers.");
				if(id.length() != resIdLen)
					throw new InvalidDataException("Resident ID must contain " + resIdLen + " numbers.");
				if(isResidentExists(id))
					throw new InvalidDataException("Resident with the same ID already exists.");
			}
			if(fName.trim().isEmpty())
				throw new InvalidDataException("First name cannot be empty.");
			if(lName.trim().isEmpty())
				throw new InvalidDataException("Last name cannot be empty.");
			if(bDay.getDate() == null)
				throw new InvalidDataException("Please choose resident birth date.");
			if(!isAgeValid(bDay.getDate()))
				throw new InvalidDataException("Resident must be at least 18 years old.");
			resident = new Resident(id, fName, lName, sqlDate(bDay.getDate()), city, status);
		}catch(InvalidDataException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return resident;
	}

	private boolean isResidentExists(String id) {
		boolean toReturn = false;
		if(Logic.getInstance().getResident(id) != null)
			toReturn = true;
		return toReturn;
	}

	private boolean isNumber(String s) {
		boolean toReturn = false;
		if(s.matches("[0-9]+"))
			toReturn = true;
		return toReturn;
	}

	private boolean isAgeValid(Date date) {
		boolean toReturn = false;
		LocalDate now = LocalDate.now();
		LocalDate than = new LocalDate(date);
		Years years = Years.yearsBetween(than, now);
		if(years.getYears() > 18)
			toReturn = true;
		return toReturn;
	}

	private java.sql.Date sqlDate(Date date){
		return new java.sql.Date(date.getTime());
	}
	
	protected void changeHeader(String header) {
		lblAddResident.setText(header);
	}

	///////////////////////////////////CLASS VARIABLES//////////////////////////////////////

	private JPanel contentPane;
	private JTextField resId;
	private JTextField textField;
	private JTextField firstName;
	private JTextField lastName;
	private JPanel pLeft;
	private JLabel lblFamilyStatus;
	private JLabel lblBirthDate;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox currentCity;
	private static final int resIdLen = 9;
	private JDateChooser birthD;
	private JLabel lblAddResident;
	private JTextField txtUser;
	private JPasswordField passwordField;
	private JLabel lblResID;
	private JLabel lblUserID;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblCityOfResidence;
	private JLabel lblUserError;
	private JButton btnSubmit;
}
