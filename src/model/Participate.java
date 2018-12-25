package model;

import java.sql.Date;

public class Participate {

	private Integer raffleNO;
	private String residentId;
	private Date registrationDate;
	private int winningPlace;
	
	
	
	public Participate(String residentId) {
		super();
		this.residentId = residentId;
	}
	
	
	
	public Participate(Integer raffleNO, Date registrationDate, int winningPlace) {
		super();
		this.raffleNO = raffleNO;
		this.registrationDate = registrationDate;
		this.winningPlace = winningPlace;
	}
	
	public Participate(Integer raffleNO, String residentId, Date registrationDate) {
		super();
		this.raffleNO = raffleNO;
		this.residentId = residentId;
		this.registrationDate = registrationDate;
	}
	
	public Participate(Integer raffleNO, String residentId, Date registrationDate, int winningPlace) {
		super();
		this.raffleNO = raffleNO;
		this.residentId = residentId;
		this.registrationDate = registrationDate;
		this.winningPlace = winningPlace;
	}

	public Integer getRaffleNO() {
		return raffleNO;
	}

	public String getResidentId() {
		return residentId;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public int getWinningPlace() {
		return winningPlace;
	}

	@Override
	public String toString() {
		return "Participate [raffleNO=" + raffleNO + ", residentId=" + residentId + ", registrationDate="
				+ registrationDate + ", winningPlace=" + winningPlace + "]";
	}
	

	
}
