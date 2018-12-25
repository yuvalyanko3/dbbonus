package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import control.Logic;
import model.Resident;
import utils.PanelEvents;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

public class RootLayout extends JFrame {

	private JPanel contentPane;

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
					frame = new RootLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public RootLayout() {
		initComponents();
		setApproved();
	}
	
	private void setApproved() {
		String type = Logic.getInstance().getCurrentUserType();
		if(type.equals("resident") || type.equals("participate"))
		{
			lblModify.setText("<html><p>Edit<br>Raffels</p></html>");
			lblCatagory.setText("<html><p>Past<br>Raffels</p></html>");
			lblInfo.setVisible(false);
		}
		if(type.equals("admin"))
		{
			lblModify.setText("<html><p>Modify<br>residents</p></html>");
			
		}
		else if(type.equals("contractor"))
		{
			lblCatagory.setVisible(false);
			lblModify.setVisible(false);
			lblInfo.setVisible(false);
		}
		
	}

	/**
	 * Create the frame.
	 */
	
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 959, 687);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel sidePanel = new JPanel();
		sidePanel.setBounds(0, 0, 143, 66);
		sidePanel.setBackground(Color.decode("#4D435D"));
		contentPane.add(sidePanel);
		sidePanel.setLayout(null);
		
		JPanel upperPanel = new JPanel();
		upperPanel.setBounds(143, 0, 976, 51);
		upperPanel.setBackground(Color.decode("#66597B"));
		contentPane.add(upperPanel);
		upperPanel.setLayout(null);
		
		lblWelcome = new JLabel("New label");
		lblWelcome.setForeground(Color.WHITE);
		lblWelcome.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblWelcome.setBounds(10, 11, 341, 29);
		lblWelcome.setVisible(false);
		upperPanel.add(lblWelcome);
		
		lblHome = new JLabel("Home");
		lblHome.setBounds(10, 87, 76, 29);
		contentPane.add(lblHome);
		lblHome.addMouseListener(new PanelEvents(lblHome));
		lblHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Home t = new Home();
				t.setVisible(true);
				closeWindow();
			}
		});
		lblHome.setForeground(Color.WHITE);
		lblHome.setFont(new Font("Arial", Font.PLAIN, 24));
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(77, 67, 93));
		panel_1.setBounds(0, 137, 143, 66);
		contentPane.add(panel_1);
		
		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(77, 67, 93));
		panel_3.setBounds(0, 273, 143, 66);
		contentPane.add(panel_3);
		
		panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(77, 67, 93));
		panel_5.setBounds(0, 408, 143, 66);
		contentPane.add(panel_5);
		
		panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBackground(new Color(77, 67, 93));
		panel_7.setBounds(0, 540, 143, 108);
		contentPane.add(panel_7);
		
		lblCatagory = new JLabel("Categories");
		lblCatagory.addMouseListener(new PanelEvents(lblCatagory));
		lblCatagory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Logic logic = Logic.getInstance();
				String type = logic.getCurrentUserType();
				if(type.equals("admin"))
				{
					AddCategory window = new AddCategory();
					window.setVisible(true);
					closeWindow();
				}
				else if(type.equals("participate") || type.equals("resident"))
				{
					PastRaffles window = new PastRaffles(frame);
					window.setVisible(true);
				}
				else
					lblCatagory.setVisible(false);
			}
		});
		lblCatagory.setForeground(Color.WHITE);
		lblCatagory.setFont(new Font("Arial", Font.PLAIN, 24));
		lblCatagory.setBounds(10, 346, 123, 51);
		contentPane.add(lblCatagory);
		
		lblModify = new JLabel("Modify");
		lblModify.addMouseListener(new PanelEvents(lblModify));
		lblModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Logic logic = Logic.getInstance();
				String type = logic.getCurrentUserType();
				if(type.equals("resident") || type.equals("participate"))
				{
					JoinRaffle window = new JoinRaffle(frame, logic.getCurrentUserId());
					window.setVisible(true);
					setVisible(false);
					new Home().setVisible(true);
				}
				else if (type.equals("admin"))
				{
					ModifyData window = new ModifyData();
					window.setVisible(true);
					setVisible(false);
					dispose();
				}
			}
		});
		lblModify.setForeground(Color.WHITE);
		lblModify.setFont(new Font("Arial", Font.PLAIN, 24));
		lblModify.setBounds(10, 202, 123, 66);

		contentPane.add(lblModify);
		
		lblInfo = new JLabel("<html><p>More<br>Information</p></html>");
		lblInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MoreInformation window = new MoreInformation(frame);
				window.setVisible(true);
			}
		});
		lblInfo.setForeground(Color.WHITE);
		lblInfo.addMouseListener(new PanelEvents(lblInfo));
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 24));
		lblInfo.setBounds(10, 478, 123, 51);
		contentPane.add(lblInfo);
	}
	
	private void closeWindow() {
		this.setVisible(false);
		dispose();
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	protected void setWelcome(String s) {
		lblWelcome.setText(s);
	}
	
	protected JLabel getWelcome() {
		return lblWelcome;
	}
	
	protected void setSecondLableName(String s) {
		lblModify.setText(s);
	}

///////////////////////////////////CLASS VARIABLES//////////////////////////////////////

	private static RootLayout frame;
	private JLabel lblHome;
	private JPanel panel_1;
	private JPanel panel_3;
	private JPanel panel_5;
	private JPanel panel_7;
	private String userName;
	private JLabel lblWelcome;
	private JLabel lblCatagory;
	private JLabel lblModify;
	private JLabel lblInfo;
	private JLabel label;
}



