package model;

public class Accessory {
	
	private Integer serialNO;
	private String accDescription;
	private int price;
	private String category;
	
	public Accessory(Integer serialNO, String accDescription, int price, String category) {
		super();
		this.serialNO = serialNO;
		this.accDescription = accDescription;
		this.price = price;
		this.category = category;
	}

	public Integer getSerialNO() {
		return serialNO;
	}

	public String getAccDescription() {
		return accDescription;
	}

	public int getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}
	

}
