package model;

public class Apartment {
	
	private Integer projectNO;
	private Integer buildingNO;
	private Integer apartmentNO;
	private int price;
	private boolean change;

	public Apartment(Integer projectNO, Integer buildingNO, Integer apartmentNO, int price, boolean bit) {
		super();
		this.projectNO = projectNO;
		this.buildingNO = buildingNO;
		this.apartmentNO = apartmentNO;
		this.price = price;
		this.change = bit;
	}

	public Apartment(Integer projectNO, Integer buildingNO, Integer apartmentNO) {
		super();
		this.projectNO = projectNO;
		this.buildingNO = buildingNO;
		this.apartmentNO = apartmentNO;
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

	public int getPrice() {
		return price;
	}

	public boolean isChanged() {
		return change;
	}
	
}
