package model;

public class Neighborhood {
	
	private String cityName;
	private String neighborhood;
	
	public Neighborhood(String cityName, String neighborhood) {
		super();
		this.cityName = cityName;
		this.neighborhood = neighborhood;
	}

	public String getCityName() {
		return cityName;
	}

	public String getNeighborhood() {
		return neighborhood;
	}
	

}
