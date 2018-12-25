package utils;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
	
	private static final String fileExtOne = "xls";
	private static final String fileExtTwo = "xlsx";
	
	public String getFilePath() {
		String toReturn = "";
		JButton open = new JButton();
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fc.setDialogTitle("Please choose an xls/xlsx file.");
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
		fc.addChoosableFileFilter(filter);
		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
			toReturn = fc.getSelectedFile().getAbsolutePath();
		}
		return toReturn;
	}
	
	public String getFileExtension(String fileName) {
		String toReturn = "";
		int i = fileName.lastIndexOf('.');
		if(i > 0)
			toReturn = fileName.substring(i+1);
		return toReturn;
	}

}
