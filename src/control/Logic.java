package control;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import model.City;
import model.EntitlementCategory;
import model.Participate;
import model.Raffle;
import model.Resident;
import model.TenantPriceApartment;
import model.UserCredentials;
import utils.*;

public class Logic {

	public static Logic instance;
	protected DriverManager dm;
	public static Connection conn;
	public static Statement s;
	private String userName;
	private String userID;

	public Logic() {
		try {
			initConn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Logic getInstance() {
		if (instance == null)
			instance = new Logic();
		return instance;
	}

	public void initConn() throws Exception {

		DriverManager.registerDriver(new com.microsoft.jdbc.sqlserver.SQLServerDriver());
		String serverName = "localhost";
		String dbName = "dbBonus";
		String userName = "sasa";
		String password = "12345";
		conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=dbBonus;", userName, password);
		s = conn.createStatement();

	}
	
	public void setCurrentUserInfo(String name, String id) {
		userName = name;
		userID = id;
	}
	
	public String getCurrentUserName() {
		return userName;
	}
	
	public String getCurrentUserId() {
		return userID;
	}
	
	public String getCurrentUserType() {
		return Logic.getInstance().getUserType(userName).toLowerCase();
	}

	public void isCategoryExists(ArrayList<EntitlementCategory> categories, ArrayList<String> discarded) {
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			String dateS = dateFormat.format(date);
			String error = "Date: " + dateS + "\n";
			ResultSet rs;
			PreparedStatement ps = null;
			ArrayList<String> s = new ArrayList<>();
			for(EntitlementCategory ec : categories)
			{	
				error += "Failed to add entitlement category (ROW " + ec.getRowNum() + "). Reason(s): \n";
				ps = conn.prepareStatement(Consts.entitlementCategorySerial);
				ps.setInt(1, ec.getSerialNO());
				rs = ps.executeQuery();
				//If there is no category with the same SerialNO.
				if(!rs.next())
				{
					ps = conn.prepareStatement(Consts.entitlementCategory);
					boolean isAdded = false;
					ps.setInt(1, ec.getMinAge());
					ps.setInt(2, ec.getMaxAge());
					ps.setString(3, ec.getMaritalStatus());
					rs = ps.executeQuery();

					// If there is no category with the same minimum age, maximum age and marital
					// status.
					if (!rs.next())
					{
						ps = conn.prepareStatement(Consts.entitlementCategoryDesc);
						ps.setString(1, ec.getCatDescription());
						rs = ps.executeQuery();
						// If there is no category with the same description.
						if (!rs.next())
						{
							addCategory(ec);
							isAdded = true;
						}
						else
						{
							error += "- Category with the same description already exists.\n";
							discarded.add(error);
						}
					}
					else
					{
						error += "- Category with the same minimim age, maximum age and marital status already exists.\n";
						discarded.add(error);
					}
				}
				else
				{
					error += "- Category with the same SerialNO already exists.\n";
					discarded.add(error);
				}
			} 
			if(!discarded.isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Some categories were not added.\nCheck the log file (Error/log.txt) for more inforamtion.", "Error", JOptionPane.ERROR_MESSAGE);
				createLog(discarded);
			}
			if(ps != null)
				ps.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addCategory(EntitlementCategory ec) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(Consts.insertEntitlementCategory);
			ps.setInt(1, ec.getSerialNO());
			ps.setInt(2, ec.getMinAge());
			ps.setInt(3, ec.getMaxAge());
			ps.setString(4, ec.getMaritalStatus());
			ps.setString(5, ec.getCatDescription());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Creates a log file with the errors the occurred during the insertion of "EntitlementCategory"
	 * @param ec
	 */
	public void createLog(ArrayList<String> ec) {
		try {
			String dirPath = System.getProperty("user.dir") + "/Error/";
			String fileName = "log.txt";
			File dir = new File(dirPath);
			File file = new File(dirPath + fileName);
			// If the directory does not exist, create it.
			if (!dir.exists())
				dir.mkdir();
			// If the log file does not exist, create it.
			if (!file.isFile())
				file.createNewFile();

			FileWriter fw = new FileWriter(dirPath + fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			out.print("*******************************************************************************\n\n");
			for(String e : ec)
				out.println(e);
			out.print("\n*******************************************************************************\n\n");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public ArrayList<EntitlementCategory> getCatagories() {
		ArrayList<EntitlementCategory> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getAllCatagories);
			rs = ps.executeQuery();
			while (rs.next()) {
				int i = 1;
				toReturn.add(new EntitlementCategory(rs.getInt(i++), rs.getInt(i++), rs.getInt(i++), rs.getString(i++),
						rs.getString(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<EntitlementCategory> getCatagoriesForResident(Resident r) {
		ArrayList<EntitlementCategory> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getResidentEntitlementCategories);
			ps.setInt(1, r.getAge());
			ps.setString(2, r.getFamilyStatus());
			rs = ps.executeQuery();
			while (rs.next()) {
				int i = 1;
				toReturn.add(new EntitlementCategory(rs.getInt(i++), rs.getInt(i++), rs.getInt(i++), rs.getString(i++),
						rs.getString(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Participate> getParticipates() {
		ArrayList<Participate> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getAllParticipates);
			rs = ps.executeQuery();
			while (rs.next()) {
				int i = 1;
				toReturn.add(new Participate(rs.getInt(i++), rs.getString(i++), rs.getDate(i++), rs.getInt(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Resident> getParticipatesNames() {
		ArrayList<Resident> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getParticipatesNames);
			rs = ps.executeQuery();
			while (rs.next()) {
				int i = 1;
				toReturn.add(new Resident(rs.getString(i++), rs.getString(i++), rs.getString(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	
	public ArrayList<Resident> getResidents() {
		ArrayList<Resident> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getAllResidents);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				toReturn.add(new Resident(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), 
						rs.getString(i++), rs.getString(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<City> getCities() {
		ArrayList<City> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getAllCities);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				toReturn.add(new City(rs.getString(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public Resident getResident(String id) {
		Resident toReturn = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getResident);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			//If resident exists.
			while (rs.next()) {
				int i = 1;
				toReturn = new Resident(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++),
						rs.getString(i++), rs.getString(i++));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean addResident(Resident r) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.insertResident);
			ps.setString(1, r.getId());
			ps.setString(2, r.getFirstName());
			ps.setString(3, r.getLastName());
			ps.setDate(4, r.getBirthDate());
			ps.setString(5, r.getCurrentCity());
			ps.setString(6, r.getFamilyStatus());
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean updateResident(Resident r) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.updateResident);
			ps.setString(1, r.getId());
			ps.setString(2, r.getFirstName());
			ps.setString(3, r.getLastName());
			ps.setDate(4, r.getBirthDate());
			ps.setString(5, r.getCurrentCity());
			ps.setString(6, r.getFamilyStatus());
			toReturn = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public int addEntiteld(Resident r, ArrayList<EntitlementCategory> c) {
		PreparedStatement ps;
		int toReturn = 0;
		try {
			ps = conn.prepareStatement(Consts.insertEntitled);
			for(EntitlementCategory cat : c)
			{
				ps.setString(1, r.getId());
				ps.setInt(2, cat.getSerialNO());
				ps.execute();
				toReturn++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean deleteEntiteld(Resident r) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.deleteResidentEntitled);
			ps.setString(1, r.getId());
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Raffle> getRaffels() {
		ArrayList<Raffle> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getAllRaffels);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				toReturn.add(new Raffle(rs.getInt(i++), rs.getDate(i++), rs.getInt(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	
	/**
	 * 
	 * @param r
	 * @return Returns raffels that the resident can sign up for
	 */
	public ArrayList<Raffle> getRaffelsForResident(Resident r) {
		ArrayList<Raffle> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ArrayList<EntitlementCategory> categories = getCatagoriesForResident(r);
			ps = conn.prepareStatement(Consts.getRaffelsForResident);
			for(EntitlementCategory ecCategory : categories)
			{
				ps.setInt(1, ecCategory.getSerialNO());
				ps.setString(2, r.getId());
				rs = ps.executeQuery();
				
				while (rs.next()) {
					int i = 1;
					toReturn.add(new Raffle(rs.getInt(i++), rs.getDate(i++), rs.getInt(i++)));
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean addParticipate(Participate p) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.InsertParticipate);
			ps.setInt(1, p.getRaffleNO());
			ps.setString(2, p.getResidentId());
			ps.setDate(3, p.getRegistrationDate());
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
		
	public ArrayList<Raffle> getParticipateRaffels(Participate p) {
		ArrayList<Raffle> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getParticipateRaffels);
			ps.setString(1, p.getResidentId());
			rs = ps.executeQuery();				
			while (rs.next()) {
					int i = 1;
					toReturn.add(new Raffle(rs.getInt(i++), rs.getDate(i++)));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Raffle> getPastRaffles(Participate p) {
		ArrayList<Raffle> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getPastRaffles);
			ps.setString(1, p.getResidentId());
			rs = ps.executeQuery();				
			while (rs.next()) {
					int i = 1;
					toReturn.add(new Raffle(rs.getInt(i++), rs.getDate(i++), rs.getInt(i++)));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Participate> GetParticipateRaffleDetails(Participate p) {
		ArrayList<Participate> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getParticipateRaffleDetails);
			ps.setString(1, p.getResidentId());
			rs = ps.executeQuery();				
			while (rs.next()) {
					int i = 1;
					toReturn.add(new Participate(rs.getInt(i++), rs.getDate(i++), rs.getInt(i++)));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public Participate getParticipate(String id) {
		Participate toReturn = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getParticipate);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			//If participate exists.
			while (rs.next()) {
				int i = 1;
				toReturn = new Participate(rs.getInt(i++), rs.getString(i++), rs.getDate(i++), rs.getInt(i++));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean deleteParticipateFromRaffle(Participate p, int rafNum) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.deleteParticipateFromRaffle);
			ps.setString(1, p.getResidentId());
			ps.setInt(2, rafNum);
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean deleteResidentFromAllRaffles(Participate p) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.deleteResidentFromAllRaffles);
			ps.setString(1, p.getResidentId());
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean uptateParticipateWinningPlace(Participate p, int raffleNum, int winningPlace) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.uptateParticipateWinningPlace);
			ps.setString(1, p.getResidentId());
			ps.setInt(2, raffleNum);
			ps.setInt(3, winningPlace);
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean deleteResidentFromApa(String id) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.deleteResidentFromApa);
			ps.setString(1, id);
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<TenantPriceApartment> getResidentApa(String id) {
		ArrayList<TenantPriceApartment> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getResidentApa);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				toReturn.add(new TenantPriceApartment(rs.getInt(i++), rs.getInt(i++), rs.getInt(i++), rs.getString(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean deleteResident(String id) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.deleteResident);
			ps.setString(1, id);
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Participate> getParticipateRaffles(Participate p) {
		ArrayList<Participate> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getParticipateRaffles);
			ps.setString(1, p.getResidentId());
			rs = ps.executeQuery();				
			while (rs.next()) {
					int i = 1;
					toReturn.add(new Participate(rs.getInt(i++), rs.getString(i++), rs.getDate(i++), rs.getInt(i++)));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean updateResidentInApa(int projectNum, int buildingNum, int apartmentNum, String id) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.updateResidentInApa);
			ps.setInt(1, projectNum);
			ps.setInt(2, buildingNum);
			ps.setInt(3, apartmentNum);
			ps.setString(4, id);
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public String getUserName(String name) {
		String toReturn = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getUserName);
			ps.setString(1, name);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				toReturn = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean insertUserCredentials(UserCredentials uc) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.insertUserCredentials);
			ps.setString(1, uc.getUserName());
			ps.setString(2, uc.getUserPass());
			ps.setString(3, uc.getUserId());
			ps.setString(4, uc.getUserType());
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public UserCredentials getUserCredentials(String name, String pass) {
		UserCredentials toReturn = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getUserCredential);
			ps.setString(1, name);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				toReturn = new UserCredentials(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public UserCredentials getUserCredentialsById(String id, String type) {
		UserCredentials toReturn = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getUserCredentialsById);
			ps.setString(1, id);
			ps.setString(2, type);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				toReturn = new UserCredentials(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean deleteUserCredentials(String id, String type) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.deleteUserCredentials);
			ps.setString(1, id);
			ps.setString(2, type);
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public boolean updateUserCredentialsType(String id, String type) {
		PreparedStatement ps;
		boolean toReturn = false;
		try {
			ps = conn.prepareStatement(Consts.updateUserCredentialsType);
			ps.setString(1, id);
			ps.setString(2, type);
			ps.execute();
			toReturn = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public String getUserType(String userName) {
		String toReturn = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.getUserType);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				toReturn = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	

	public ChartPanel projectPerCity() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.projectPerCity);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(projects, "Number of Projects", city);
				
			}
			JFreeChart jChart = ChartFactory.createBarChart("Projects by cities", "City", "Number of projects", dod, PlotOrientation.HORIZONTAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = jChart.getCategoryPlot();
			plot.setRangeGridlinePaint(Color.black);
			chartFra = new ChartPanel(jChart);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel projectsByNeighborhoods(String cityName) {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.projectsByNeighborhoods);
			ps.setString(1, cityName);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(projects, "Number of Projects", city);
				
			}
			JFreeChart jChart = ChartFactory.createBarChart("Projects by neighborhood", "Neighborhood", "Number of projects", dod, PlotOrientation.VERTICAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = jChart.getCategoryPlot();
			plot.setRangeGridlinePaint(Color.black);
			chartFra = new ChartPanel(jChart);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel freeTpaByNeighborhood(String cityName) {
		ChartPanel chartFra = null;
		DefaultCategoryDataset dodOne = new DefaultCategoryDataset();
		DefaultCategoryDataset dodTwo = new DefaultCategoryDataset();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.freeTpaByNeighborhood);
			ps.setString(1, cityName);
			rs = ps.executeQuery();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double apartments = rs.getDouble(i++);
	      		Double tpa = rs.getDouble(i++);
				dodOne.setValue(apartments, "Number of apartments", city);
				dodOne.setValue(tpa, "Number of TPA", city);
				
			}
			
			final JFreeChart chart = ChartFactory.createBarChart(
					"Number of Apartments and TPA",
					"Neighborhood",
					"Apartments",
					dodOne,
					PlotOrientation.VERTICAL,
					true,
					true,
					false);
			chart.setBackgroundPaint(Color.decode("#EAE6EF"));
	        final CategoryPlot plot = chart.getCategoryPlot();
	        plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));
	        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
	        plot.setDataset(1, dodTwo);
	        plot.mapDatasetToRangeAxis(1, 1);
	        final CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
	        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
	        plot.setRenderer(1, renderer2);
	        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
	        

	        chartFra = new ChartPanel(chart);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}

	public ChartPanel ResidentsByCity() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.ResidentsByCity);
			rs = ps.executeQuery();
			DefaultPieDataset dod = new DefaultPieDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(city,projects);
				
			}
			JFreeChart jChart = ChartFactory.createPieChart("Residents by city",dod,true,true,true);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			PiePlot plot = (PiePlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel numberOfWorkers() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.numberOfWorkers);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.addValue(projects, "Number of workers", city);
				
			}
			JFreeChart jChart = ChartFactory.createLineChart(" ", "Contractor", "Quantity", dod, PlotOrientation.HORIZONTAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = (CategoryPlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	
	public ChartPanel NumberOfAccByCategory() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.NumberOfAccByCategory);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.addValue(projects, "Accessories by Category", city);

			}
			
			JFreeChart jChart = ChartFactory.createAreaChart("Number of Accessories", "Category", "Quantity", dod, PlotOrientation.VERTICAL, true, true, false);//("Residents by city",dod,true);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = (CategoryPlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel numberOfSameEC(String id) {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.numberOfSameEC);
			ps.setString(1, id);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String categories = rs.getString(i++);
	      		Double numOfPepole = rs.getDouble(i++);
				dod.addValue(numOfPepole, "Entitlement categories", categories);

			}
			
			JFreeChart jChart = ChartFactory.createAreaChart("Number of pepole with the same entitlement categories", "Category", "Quantity", dod, PlotOrientation.VERTICAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = (CategoryPlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel numberOfProjectsForContractor() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.numberOfProjectsForContractor);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(projects, "Number of Projects per contractor", city);
				
			}
			JFreeChart jChart = ChartFactory.createBarChart("Projects per contractor", "Contractor", "Number of projects", dod, PlotOrientation.HORIZONTAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = jChart.getCategoryPlot();
			plot.setRangeGridlinePaint(Color.black);
			chartFra = new ChartPanel(jChart);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel projectPerCityForContractor(String id) {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.projectPerCityForContractor);
			ps.setString(1, id);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(projects, "Your projects", city);
				
			}
			JFreeChart jChart = ChartFactory.createBarChart("Your projects", "City", "Number of projects", dod, PlotOrientation.VERTICAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = jChart.getCategoryPlot();
			plot.setRangeGridlinePaint(Color.black);
			chartFra = new ChartPanel(jChart);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel numOfApaPerCity() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.numOfApaPerCity);
			rs = ps.executeQuery();
			DefaultPieDataset dod = new DefaultPieDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(city,projects);
				
			}
			JFreeChart jChart = ChartFactory.createPieChart("Apartments per city",dod,true,true,true);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			PiePlot plot = (PiePlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel participatesPerCity() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.participatesPerCity);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.addValue(projects, "", city);

			}
			
			JFreeChart jChart = ChartFactory.createAreaChart("Participates per city", "City", "Quantity", dod, PlotOrientation.HORIZONTAL, true, true, false);//("Residents by city",dod,true);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = (CategoryPlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel buildingsPerNeighborhood(String name) {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.buildingsPerNeighborhood);
			ps.setString(1, name);
			rs = ps.executeQuery();
			DefaultPieDataset dod = new DefaultPieDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(city,projects);
				
			}
			JFreeChart jChart = ChartFactory.createPieChart("Buildings per neighborhood",dod,true,true,true);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			PiePlot plot = (PiePlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel rafflesPerCity() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.rafflesPerCity);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(projects, "", city);
				
			}
			JFreeChart jChart = ChartFactory.createBarChart("Raffles per city", "Cities", "Number of raffles", dod, PlotOrientation.HORIZONTAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = jChart.getCategoryPlot();
			plot.setRangeGridlinePaint(Color.black);
			chartFra = new ChartPanel(jChart);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel numberOfParticipatesInSameRaffle(String id) {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.numberOfParticipatesInSameRaffle);
			ps.setString(1, id);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(projects, "", city);
				
			}
			JFreeChart jChart = ChartFactory.createBarChart("Number of participates in the same raffles", "Raffles", "Number of raffles", dod, PlotOrientation.VERTICAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = jChart.getCategoryPlot();
			plot.setRangeGridlinePaint(Color.black);
			chartFra = new ChartPanel(jChart);
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ChartPanel numberOfEC() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.numberOfEC);
			rs = ps.executeQuery();
			DefaultPieDataset dod = new DefaultPieDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.setValue(city,projects);
				
			}
			JFreeChart jChart = ChartFactory.createPieChart("Number of residents in entitlement categories",dod,true,true,true);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			PiePlot plot = (PiePlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}

	public ChartPanel apaPerRaffle() {
		ChartPanel chartFra = null;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.apaPerRaffle);
			rs = ps.executeQuery();
			DefaultCategoryDataset dod = new DefaultCategoryDataset();
			while (rs.next()) {
				int i = 1;
	      		String city = rs.getString(i++);
	      		Double projects = rs.getDouble(i++);
				dod.addValue(projects, "Number of apartments", city);
				
			}
			JFreeChart jChart = ChartFactory.createLineChart("Apartments Per Raffle", "Raffle", "Quantity", dod, PlotOrientation.VERTICAL, true, true, false);
			jChart.setBackgroundPaint(Color.decode("#EAE6EF"));
			CategoryPlot plot = (CategoryPlot)jChart.getPlot();
			plot.setForegroundAlpha(0.5f);
			
			chartFra = new ChartPanel(jChart);

			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chartFra;
		
	}
	
	public ArrayList<Resident> queryEight() {
		ArrayList<Resident> toReturn = new ArrayList<>();
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.queryEight);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				toReturn.add(new Resident(rs.getString(i++), rs.getString(i++)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Object[]> queryTwo() {
		ArrayList<Object[]> toReturn = new ArrayList<>();
		final int expected = 3;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.queryTwo);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				int j = 0;
				Object[] obj = new Object[expected];
				obj[j++] = rs.getInt(i++);
				obj[j++] = rs.getInt(i++);
				obj[j++] = rs.getString(i++);
				toReturn.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Object[]> queryFive() {
		ArrayList<Object[]> toReturn = new ArrayList<>();
		final int expected = 6;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.queryFive);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				int j = 0;
				Object[] obj = new Object[expected];
				obj[j++] = rs.getString(i++);
				obj[j++] = rs.getString(i++);
				obj[j++] = rs.getInt(i++);
				obj[j++] = rs.getString(i++);
				obj[j++] = rs.getInt(i++);
				obj[j++] = rs.getString(i++);
				toReturn.add(obj);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Object[]> querySix() {
		ArrayList<Object[]> toReturn = new ArrayList<>();
		final int expected = 2;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.querySix);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				int j = 0;
				Object[] obj = new Object[expected];
				obj[j++] = rs.getString(i++);
				obj[j++] = rs.getString(i++);
				toReturn.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public ArrayList<Object[]> queryFour() {
		ArrayList<Object[]> toReturn = new ArrayList<>();
		final int expected = 2;
		try {
			ResultSet rs;
			PreparedStatement ps;
			ps = conn.prepareStatement(Consts.queryFour);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				int j = 0;
				Object[] obj = new Object[expected];
				obj[j++] = rs.getString(i++);
				obj[j++] = rs.getString(i++);
				toReturn.add(obj);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	
}
