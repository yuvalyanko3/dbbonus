package model;

public class Building {
	
	private Integer projectNO;
	private Integer buildingNo;
	private int floors;
	private String location;
	
	public Building(Integer projectNO, Integer buildingNo, int floors, String location) {
		super();
		this.projectNO = projectNO;
		this.buildingNo = buildingNo;
		this.floors = floors;
		this.location = location;
	}

	public Building(Integer projectNO, Integer buildingNo) {
		super();
		this.projectNO = projectNO;
		this.buildingNo = buildingNo;
	}

	public Integer getProjectNO() {
		return projectNO;
	}

	public Integer getBuildingNo() {
		return buildingNo;
	}

	public int getFloors() {
		return floors;
	}

	public String getLocation() {
		return location;
	}
	
	
	

}
