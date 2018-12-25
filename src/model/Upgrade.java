package model;

public class Upgrade {
	
	private Integer projectNO;
	private Integer buildingNO;
	private Integer apartmentNO;
	private Integer arrivesWith;
	private Integer replaceWith;
	
	public Upgrade(Integer projectNO, Integer buildingNO, Integer apartmentNO, Integer arrivesWith,
			Integer replaceWith) {
		super();
		this.projectNO = projectNO;
		this.buildingNO = buildingNO;
		this.apartmentNO = apartmentNO;
		this.arrivesWith = arrivesWith;
		this.replaceWith = replaceWith;
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

	public Integer getArrivesWith() {
		return arrivesWith;
	}

	public Integer getReplaceWith() {
		return replaceWith;
	}
		

}
