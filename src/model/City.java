package model;

public class City {
	
	private String name;

	public City(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	
}
