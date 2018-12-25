package model;

public class Bonus {
	
	private Integer serialNO;
	private String bonusDesc;
	private int maxAmount;
	
	public Bonus(Integer serialNO, String bonusDesc, int maxAmount) {
		super();
		this.serialNO = serialNO;
		this.bonusDesc = bonusDesc;
		this.maxAmount = maxAmount;
	}

	public Integer getSerialNO() {
		return serialNO;
	}

	public String getBonusDesc() {
		return bonusDesc;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

}
