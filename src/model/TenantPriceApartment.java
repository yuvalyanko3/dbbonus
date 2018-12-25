package model;

public class TenantPriceApartment {
	
	private Integer projectNO;
	private Integer buildingNO;
	private Integer apartmentNO;
	private String residentId;
	
	public TenantPriceApartment(Integer projectNO, Integer buildingNO, Integer apartmentNO, String residentId) {
		super();
		this.projectNO = projectNO;
		this.buildingNO = buildingNO;
		this.apartmentNO = apartmentNO;
		this.residentId = residentId;
	}

	public Integer getProjectNO() {
		return projectNO;
	}

	public Integer getBuildingNO() {
		return buildingNO;
	}

	public Integer getApartmentNO() {
		return apartmentNO;
	}

	public String getResidentId() {
		return residentId;
	}
	

}
