package model;

public class CanGet {
	
	private Integer raffleNO;
	private Integer ecSerialNO;
	private Integer BSerialNO;
	
	public CanGet(Integer raffleNO, Integer ecSerialNO, Integer bSerialNO) {
		super();
		this.raffleNO = raffleNO;
		this.ecSerialNO = ecSerialNO;
		BSerialNO = bSerialNO;
	}

	public Integer getRaffleNO() {
		return raffleNO;
	}

	public Integer getEcSerialNO() {
		return ecSerialNO;
	}

	public Integer getBSerialNO() {
		return BSerialNO;
	}

}
