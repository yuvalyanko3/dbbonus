package view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import utils.Consts;
import utils.FileChooser;
import utils.InvalidDataException;
import utils.TextFieldFocus;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import control.Logic;
import model.EntitlementCategory;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class AddCategory extends RootLayout {
	
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
					frame = new AddCategory();
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
	public AddCategory() {
		super();
		initComponents();
		refreshControls();
	}
	
	private void refreshControls() {
		createTable();
		
	}
	private void initComponents() {
		
		JPanel pAll = new JPanel();
		pAll.setBounds(0, 66, 143, 264);
		pAll.setBackground(Color.decode("#4D435D"));
		getContentPane().add(pAll);
		
		JPanel pMain = new JPanel();
		pMain.setBounds(143, 51, 800, 597);
		pMain.setBackground(Color.decode("#EAE6EF"));
		getContentPane().add(pMain);
		pMain.setLayout(null);
		
		
		
		JLabel lblCata = new JLabel("Catagories:");
		lblCata.setFont(new Font("Arial", Font.BOLD, 38));
		lblCata.setBounds(49, 29, 237, 73);
		pMain.add(lblCata);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(180, 113, 440, 215);
		pMain.add(scrollPane_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane_1.setViewportView(scrollPane);
		scrollPane.setViewportView(tableCat);
		
		JPanel pAdd = new JPanel();
		pAdd.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pAdd.setBounds(180, 381, 440, 145);
		pAdd.setBackground(Color.decode("#EAE6EF"));
		pMain.add(pAdd);
		pAdd.setLayout(null);
		
		JLabel lblAddNewCategory = new JLabel("Add new category:");
		lblAddNewCategory.setFont(new Font("Arial", Font.BOLD, 20));
		lblAddNewCategory.setBounds(10, 11, 183, 39);
		pAdd.add(lblAddNewCategory);
		
		JLabel lblToAdd = new JLabel("* To add a new category please select a valid XLS file.");
		lblToAdd.setBounds(10, 42, 306, 14);
		pAdd.add(lblToAdd);
		
		fileLoc = new JTextField();
		fileLoc.setEditable(false);
		fileLoc.setEnabled(false);
		fileLoc.setText("File location..");
		fileLoc.addFocusListener(new TextFieldFocus(fileLoc,"File location..", ""));
		fileLoc.setFont(new Font("Arial", Font.PLAIN, 13));
		fileLoc.setForeground(Color.LIGHT_GRAY);
		fileLoc.setBounds(10, 80, 262, 27);
		pAdd.add(fileLoc);
		fileLoc.setColumns(10);
		
		JButton btnSearch = new JButton("...");
		btnSearch.setBounds(282, 80, 37, 27);
		pAdd.add(btnSearch);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(440, 0, 0, 0);
		pAdd.add(textField);
		textField.setColumns(10);
		
		JButton btnAddCategory = new JButton("Add category");
		btnAddCategory.setEnabled(false);
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[][] data = readExcel(fileLoc.getText());
				if(data != null)
				{
					ArrayList<EntitlementCategory> categories = buildCategories(data);
					Logic.getInstance().isCategoryExists(categories,toDiscard);
					refreshControls();
				}
			}
		});
		btnAddCategory.setBounds(324, 111, 106, 23);
		pAdd.add(btnAddCategory);
		
		JPanel pCat = new JPanel();
		pCat.setBounds(0, 335, 143, 74);
		getContentPane().add(pCat);
		pCat.setBackground(Color.decode("#40384D"));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(77, 67, 93));
		panel.setBounds(0, 420, 143, 228);
		getContentPane().add(panel);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLocation();
				setButton(btnAddCategory);
			}
		});
				
		
	}
	
	private void setLocation() {
		try {
			final String fileExtOne = "xls";
			final String fileExtTwo = "xlsx";
			FileChooser fc = new FileChooser();
			String path = fc.getFilePath();
			if(!fc.getFileExtension(path).equals(fileExtOne) && !fc.getFileExtension(path).equals(fileExtTwo))
				throw new InvalidDataException("Please choose an xls or xlsx file.");
			fileLoc.setText(path);
			fileLoc.setForeground(Color.BLACK);
		}catch(InvalidDataException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void createTable() {
		
		Object[] columns = {"Serial Number", "Minimum age", "Maximum age", "Marital Status", "Description"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		tableCat.setModel(model);
		((DefaultTableModel)tableCat.getModel()).setRowCount(0);
		Object[] row = new Object[Consts.categoryValues.length];
		ArrayList<EntitlementCategory> categories = Logic.getInstance().getCatagories();
		for(EntitlementCategory ec : categories)
		{
			int i = 0;
			row[i++] = ec.getSerialNO();
			row[i++] = ec.getMinAge();
			row[i++] = ec.getMaxAge();
			row[i++] = ec.getMaritalStatus();
			row[i++] = ec.getCatDescription();
			
			model.addRow(row);	
		}
	}
	
	private void setButton(JButton add) {
		if(!fileLoc.getText().equalsIgnoreCase("file location.."))
			add.setEnabled(true);
		else
			add.setEnabled(false);
	}
	
	/**
	 * Reads an xls/xlsx file into a two dimensional array.
	 * @param path
	 * @return The content of an xls/xlsx file.
	 */
	private String[][] readExcel(String path) {
		String[][] data = null;
		try {
			Workbook workbook = WorkbookFactory.create(new File(path));
			Sheet sheet = workbook.getSheetAt(0);
			workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
			DataFormatter df = new DataFormatter();
			int colNum = Consts.categoryValues.length;
			int i = 0;
			if(checkFormat(sheet, df))
			{			
				data = new String[sheet.getPhysicalNumberOfRows()][colNum];
				for(Row row: sheet)
				{
					for(int j = 0, k = 0 ; k < colNum ; k++)
					{
						Cell cell = row.getCell(k, MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if(!(cell == null || cell.getCellTypeEnum() == CellType.BLANK))
						{
							String cellValue = df.formatCellValue(cell);
							data[i][j++] = cellValue;
						}
						else
							data[i][j++] = "NULL";
					}
					i++;
				}
			}
			else
				throw new InvalidDataException("xls/xlsx file is not is the correct foramt.");
		} catch (EncryptedDocumentException | InvalidFormatException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}catch (IOException s) {
			JOptionPane.showMessageDialog(null, "Please close the xls/xlsx file and try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return data;
	}
	
	
	/**
	 * Check if the xls/xlsx file is in the correct format.
	 * @param sheet
	 * @param df
	 * @return
	 */
	private boolean checkFormat(Sheet sheet, DataFormatter df) {
		boolean toReturn = true;
		Row firstRow = sheet.rowIterator().next();
		int i = 0;
		for(Cell cell : firstRow)
		{
			String val = df.formatCellValue(cell);
			if(!(Consts.categoryValues[i++].equalsIgnoreCase(val)))
				toReturn = false;
		}
		return toReturn;
	}
	
	/**
	 * Build an ArrayList of type "EntitlementCategory" from a two dimensional array.
	 * @param data
	 * @return ArrayList of type "EntitlementCategory"
	 */
	private ArrayList<EntitlementCategory> buildCategories(String[][] data) {
		ArrayList<EntitlementCategory> toReturn = new ArrayList<>();
		toDiscard = new ArrayList<>();
		for(int i = 1, j = 0 ; i< data.length ; i++)
		{
			boolean toAdd = true;
			EntitlementCategory ec = new EntitlementCategory();		
			if(data[i][j].equals("NULL") || !checkIfNumber(data[i][j]))
			{
				ec.setSerialNO(-1);
				toAdd = false;
			}
			else
				ec.setSerialNO(Integer.parseInt(data[i][j]));
			j++;
			if(data[i][j].equals("NULL") || !checkIfNumber(data[i][j]))
			{
				ec.setMinAge(-1);
				toAdd = false;
			}
				
			else {
				if(Integer.parseInt(data[i][j]) > 0)
					ec.setMinAge(Integer.parseInt(data[i][j]));
				else {
					ec.setMinAge(-1);
					toAdd = false;
				}
					
			}
			j++;
			if(data[i][j].equals("NULL") || !checkIfNumber(data[i][j]))
			{
				ec.setMaxAge(-1);
				toAdd = false;
			}
				
			else {
				if(Integer.parseInt(data[i][j]) > 0)
					ec.setMaxAge(Integer.parseInt(data[i][j]));
				else
				{
					ec.setMaxAge(-1);
					toAdd = false;
				}
					
			}
			j++;
			if(!validateMaritalStatus(data[i][j]))
			{
				ec.setMaritalStatus("NULL");
				toAdd = false;
			}
				
			else
				ec.setMaritalStatus(data[i][j]);
			j++;
			if(data[i][j].isEmpty())
			{
				ec.setCatDescription("NULL");
				toAdd = false;
			}
				
			else
				ec.setCatDescription(data[i][j]);
			ec.setRowNum(i + 1);
			j=0;
			if(toAdd)
				toReturn.add(ec);
			else
				toDiscard.add(ec.errorsToString());
		}
		return toReturn;
	}
	
	private boolean checkIfNumber(String s) {
		if(s.matches("[0-9]+"))
			return true;
		return false;
	}
	
	/**
	 *Checks if the marital status is one of the following characters: '', '', '', ''.
	 * @param status
	 * @return true if marital status is one of the said characters. false otherwise.
	 */
	private boolean validateMaritalStatus(String status) {
		boolean toReturn = false;
		String s = "SMDWsmdw";
		
		if(status.length() == 1)
		{
			for(Character c : s.toCharArray())
			{
				if(c.equals(status.charAt(0)))
					toReturn = true;
			}
		}
		
		return toReturn;
	}
///////////////////////////////////CLASS VARIABLES//////////////////////////////////////

	private static AddCategory frame;
	private JTable tableCat = new JTable();
	private JTextField fileLoc;
	private JTextField textField;
	private DefaultTableModel model;
	private ArrayList<String> toDiscard;
}




