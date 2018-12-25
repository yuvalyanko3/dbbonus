package model;

public class Project {
	
	private Integer projectNO;
	private String projectName;
	private int apartmentsPrecent;
	private String contractorId;
	private String cityName;
	private String neighborhood;
	
	public Project(Integer projectNO, String projectName, int apartmentsPrecent, String contractorId, String cityName,
			String neighborhood) {
		super();
		this.projectNO = projectNO;
		this.projectName = projectName;
		this.apartmentsPrecent = apartmentsPrecent;
		this.contractorId = contractorId;
		this.cityName = cityName;
		this.neighborhood = neighborhood;
	}

	public Integer getProjectNO() {
		return projectNO;
	}

	public String getProjectName() {
		return projectName;
	}

	public int getApartmentsPrecent() {
		return apartmentsPrecent;
	}

	public String getContractorId() {
		return contractorId;
	}

	public String getCityName() {
		return cityName;
	}

	public String getNeighborhood() {
		return neighborhood;
	}
	
	
	
	
	
	
	

}
