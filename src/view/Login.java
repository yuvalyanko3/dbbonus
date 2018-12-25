package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import control.Logic;
import model.UserCredentials;
import utils.InvalidDataException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {



	/**
	 * Launch the application.
	 */
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
					Login frame = new Login();
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
	public Login() {
		initComponents();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 317, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(0, 0, 301, 261);
		pMain.setBackground(Color.decode("#EAE6EF"));
		contentPane.add(pMain);
		pMain.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Times New Roman", Font.BOLD, 38));
		lblLogin.setBounds(10, 11, 167, 57);
		pMain.add(lblLogin);
		
		userName = new JTextField();
		userName.setBounds(43, 115, 134, 28);
		pMain.add(userName);
		userName.setColumns(10);
		
		pass = new JPasswordField();
		pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
		});
		pass.setBounds(43, 179, 134, 28);
		pMain.add(pass);
		
		JLabel lblUserName = new JLabel("Username:");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUserName.setBounds(43, 79, 89, 14);
		pMain.add(lblUserName);
		
		JLabel lblNewLabel = new JLabel("Username or contractor number.");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setBounds(43, 98, 172, 14);
		pMain.add(lblNewLabel);
		
		JLabel lblPass = new JLabel("Password:");
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPass.setBounds(43, 154, 118, 14);
		pMain.add(lblPass);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String name = userName.getText();
					String password = new String(pass.getPassword());
					if(name.trim().isEmpty())
						throw new InvalidDataException("Username cannot be empty.");
					if(password.trim().isEmpty())
						throw new InvalidDataException("Password cannot be empty.");
				UserCredentials user = checkUserCredentials(name, password);
				if(user == null)
					throw new InvalidDataException("Username or password is incorrect.");
				else
				{
					Logic.getInstance().setCurrentUserInfo(user.getUserName(), user.getUserId());
					Home window = new Home();
					window.setVisible(true);
					setVisible(false);
					dispose();
				}
				
				}catch(InvalidDataException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid login", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.setBounds(202, 227, 89, 23);
		pMain.add(btnLogin);
		
		btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openSignUpWindow();
			}
		});
		btnSignUp.setBounds(10, 227, 89, 23);
		pMain.add(btnSignUp);
	}
	

	private UserCredentials checkUserCredentials(String name, String password) {
		return Logic.getInstance().getUserCredentials(name, password);
	}
	
	private void openSignUpWindow() {
		AddResident window = new AddResident(this);
		window.changeHeader("Sign up:");
		window.setVisible(true);
	}
	
	///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	private JPanel contentPane;
	private JTextField userName;
	private JPasswordField pass;
	private JButton btnLogin;
	private JButton btnSignUp;

}
