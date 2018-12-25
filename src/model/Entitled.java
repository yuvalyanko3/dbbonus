package model;

public class Entitled {
	
	private String residentId;
	private Integer serialNO;
	
	public Entitled(String residentId, Integer serialNO) {
		super();
		this.residentId = residentId;
		this.serialNO = serialNO;
	}

	public String getResidentId() {
		return residentId;
	}

	public Integer getSerialNO() {
		return serialNO;
	}

}
