package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntitlementCategory {
	
	private Integer serialNO;
	private int minAge;
	private int maxAge;
	private String maritalStatus;
	private String catDescription;
	private int rowNum;
	
	
	public EntitlementCategory() {
		super();
	}

	public EntitlementCategory(Integer serialNO, int minAge, int maxAge, String maritalStatus, String catDescription) {
		super();
		this.serialNO = serialNO;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.maritalStatus = maritalStatus;
		this.catDescription = catDescription;
	}

	public EntitlementCategory(Integer serialNO, int minAge, int maxAge, String maritalStatus, String catDescription,
			int rowNum) {
		super();
		this.serialNO = serialNO;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.maritalStatus = maritalStatus;
		this.catDescription = catDescription;
		this.rowNum = rowNum;
	}

	private boolean checkAge() {	
		if(minAge > maxAge)
			return false;
		return true;
		
	}

	public Integer getSerialNO() {
		return serialNO;
	}

	public int getMinAge() {
		return minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getCatDescription() {
		return catDescription;
	}

	public void setSerialNO(Integer serialNO) {
		this.serialNO = serialNO;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setCatDescription(String catDescription) {
		this.catDescription = catDescription;
	}
	
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String errorsToString() {		
		String toReturn = "";
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dateS = dateFormat.format(date);
		//toReturn = "*******************************************************************************\n\n";
		toReturn = "Date: " + dateS + "\n";
		toReturn += "Failed to add entitlement category (ROW " + this.rowNum + "). Reason(s): \n";
		
		if(this.serialNO == -1)
			toReturn += "- SerialNO is invalid.\n";
		if((this.minAge != -1) && (this.maxAge != -1))
		{
			if(!checkAge())
				toReturn += "- Minimum age cannot be greater than maximum age.\n";
		}else {
			if(minAge == -1)
				toReturn += "- Minimum age is invalid.\n";
			if(maxAge == -1)
				toReturn += "- Maximum age is invalid.\n";
		}
		if(this.maritalStatus.equals("NULL"))
			toReturn += "- Marital status is invalid.\n";
		if(this.catDescription.equals("NULL"))
			toReturn += "- Description is invalid.\n";

		//toReturn += "\n\n*******************************************************************************\n\n";
		
		return toReturn;
	}
	
	public String existsError(String error) {		
		String toReturn = "";
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String dateS = dateFormat.format(date);
		//toReturn = "*******************************************************************************\n\n";
		toReturn = "Date: " + dateS + "\n";
		toReturn += "Failed to add entitlement category (ROW " + this.rowNum + "). Reason(s): \n";

		if(this.serialNO == -1)
			toReturn += "- SerialNO is invalid.\n";
		if((this.minAge != -1) && (this.maxAge != -1))
		{
			if(!checkAge())
				toReturn += "- Minimum age cannot be greater than maximum age.\n";
		}else {
			if(minAge == -1)
				toReturn += "- Minimum age is invalid.\n";
			if(maxAge == -1)
				toReturn += "- Maximum age is invalid.\n";
		}
		if(this.maritalStatus.equals("NULL"))
			toReturn += "- Marital status is invalid.\n";
		if(this.catDescription.equals("NULL"))
			toReturn += "- Description is invalid.\n";
		//toReturn += "\n\n*******************************************************************************\n\n";
		
		return toReturn;
	}
	
	@Override
	public String toString() {
		return "EntitlementCategory [serialNO=" + serialNO + ", minAge=" + minAge + ", maxAge=" + maxAge
				+ ", maritalStatus=" + maritalStatus + ", catDescription=" + catDescription + "]";
	}
	

}
