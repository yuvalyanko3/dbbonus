package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.xmlbeans.UserType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.omg.CORBA.PRIVATE_MEMBER;

import control.Logic;
import model.Resident;
import javax.swing.JLabel;
import java.awt.Font;

public class Home extends RootLayout {
	
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
					frame = new Home();
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
	public Home() {
		
		userType = Logic.getInstance().getCurrentUserType();
		initComponents();
		SetChart();
		welcome();
	}
	//#eff8ff
	
	private void initComponents() {
		
		JPanel pAll = new JPanel();
		pAll.setBounds(0, 203, 143, 339);
		pAll.setBackground(Color.decode("#4D435D"));
		getContentPane().add(pAll);
		
		JPanel pHome = new JPanel();
		pHome.setBounds(0, 65, 143, 72);
		pHome.setBackground(Color.decode("#40384D"));
		getContentPane().add(pHome);
		
		pBorder = new JPanel();
		pBorder.setBounds(143, 52, 800, 594);
		getContentPane().add(pBorder);
		pBorder.setLayout(null);
		pBorder.setBackground(Color.decode("#EAE6EF"));
		Border border = BorderFactory.createLineBorder(Color.decode("#dedede"), 2);

		pOne = new JPanel();
		pOne.setBounds(0, 0, 393, 287);
		pOne.setBorder(border);
		pBorder.add(pOne);
		
		pTwo = new JPanel();
		pTwo.setBounds(407, 0, 393, 287);
		pTwo.setLayout(new BorderLayout(0, 0));
		pTwo.setBorder(border);
		pBorder.add(pTwo);
		
		pThree = new JPanel();
		pThree.setBounds(407, 304, 393, 287);
		pThree.setLayout(new BorderLayout(0, 0));
		pThree.setBorder(border);
		pBorder.add(pThree);
		
		pFour = new JPanel();
		pFour.setBounds(0, 304, 393, 287);
		pBorder.add(pFour);
		pFour.setLayout(new BorderLayout(0, 0));
		
	}
	
	private void SetChart(){
		Logic logic = Logic.getInstance();
		//String userType = logic.getCurrentUserType();
		ChartPanel one;
		ChartPanel two;
		ChartPanel three;
		ChartPanel four;
		//Admin Dashboard
		if(userType.equals("admin"))
		{
			one = Logic.getInstance().projectPerCity();
			pOne.setLayout(new BorderLayout(0, 0));
			pOne.add(one);
			one.setLayout(new BorderLayout(0, 0));
			pOne.validate();
			
			two = Logic.getInstance().ResidentsByCity();
			pTwo.setLayout(new BorderLayout(0, 0));
			pTwo.add(two);
			two.setLayout(new BorderLayout(0, 0));
			pTwo.validate();
			
			
			three = Logic.getInstance().numberOfWorkers();
			pThree.setLayout(new BorderLayout(0, 0));
			pThree.add(three);
			three.setLayout(new BorderLayout(0, 0));
			pThree.validate();
			
			four = Logic.getInstance().NumberOfAccByCategory();
			pFour.setLayout(new BorderLayout(0, 0));
			pFour.add(four);
			four.setLayout(new BorderLayout(0, 0));
			pFour.validate();
		}
		//Resident
		if(userType.equals("resident"))
		{
			Resident res = logic.getResident(logic.getCurrentUserId());
			one = Logic.getInstance().projectsByNeighborhoods(res.getCurrentCity());
			pOne.setLayout(new BorderLayout(0, 0));
			pOne.add(one);
			one.setLayout(new BorderLayout(0, 0));
			pOne.validate();
			
			two = Logic.getInstance().buildingsPerNeighborhood(res.getCurrentCity());
			pTwo.setLayout(new BorderLayout(0, 0));
			pTwo.add(two);
			two.setLayout(new BorderLayout(0, 0));
			pTwo.validate();
			
			three = Logic.getInstance().freeTpaByNeighborhood(res.getCurrentCity());
			pThree.setLayout(new BorderLayout(0, 0));
			pThree.add(three);
			three.setLayout(new BorderLayout(0, 0));
			pThree.validate();
			
			
			
			four = Logic.getInstance().numberOfSameEC(res.getId());
			pFour.setLayout(new BorderLayout(0, 0));
			pFour.add(four);
			four.setLayout(new BorderLayout(0, 0));
			pFour.validate();
		}
		
		//Contractor
		if(userType.equals("contractor"))
		{
			String id = logic.getCurrentUserId();
			one = Logic.getInstance().numberOfProjectsForContractor();
			pOne.setLayout(new BorderLayout(0, 0));
			pOne.add(one);
			one.setLayout(new BorderLayout(0, 0));
			pOne.validate();
			
			two = Logic.getInstance().projectPerCityForContractor(id);
			pTwo.setLayout(new BorderLayout(0, 0));
			pTwo.add(two);
			two.setLayout(new BorderLayout(0, 0));
			pTwo.validate();
			
			three = Logic.getInstance().participatesPerCity();
			pThree.setLayout(new BorderLayout(0, 0));
			pThree.add(three);
			three.setLayout(new BorderLayout(0, 0));
			pThree.validate();
			
			four = Logic.getInstance().numOfApaPerCity();
			pFour.setLayout(new BorderLayout(0, 0));
			pFour.add(four);
			four.setLayout(new BorderLayout(0, 0));
			pFour.validate();

		}
		//Participate
		if(userType.equals("participate"))
		{
			Resident res = logic.getResident(logic.getCurrentUserId());
			one = Logic.getInstance().rafflesPerCity();
			pOne.setLayout(new BorderLayout(0, 0));
			pOne.add(one);
			one.setLayout(new BorderLayout(0, 0));
			pOne.validate();
			
			two = Logic.getInstance().apaPerRaffle();
			pTwo.setLayout(new BorderLayout(0, 0));
			pTwo.add(two);
			two.setLayout(new BorderLayout(0, 0));
			pTwo.validate();
			
			three = Logic.getInstance().numberOfEC();
			pThree.setLayout(new BorderLayout(0, 0));
			pThree.add(three);
			three.setLayout(new BorderLayout(0, 0));
			pThree.validate();

			four = Logic.getInstance().numberOfParticipatesInSameRaffle(res.getId());
			pFour.setLayout(new BorderLayout(0, 0));
			pFour.add(four);
			four.setLayout(new BorderLayout(0, 0));
			pFour.validate();
		}

			
	}
	
	 private void welcome() {
		 String s = "Welcome back " + Logic.getInstance().getCurrentUserName();
		 setWelcome(s);
		 getWelcome().setVisible(true);
	 }
	 
	
///////////////////////////////////CLASS VARIABLES//////////////////////////////////////
	private static Home frame;
	private JPanel pOne;
	private JPanel pTwo;
	private JPanel pThree;
	private String userType;
	private JPanel pBorder;
	private JPanel pFour;
}