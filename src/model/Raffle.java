package model;

import java.sql.Date;

public class Raffle {
	
	private Integer raffleNO;
	private Date raffleDate;
	private Integer projectNO;
	
	public Raffle(Integer raffleNO, Date raffleDate) {
		super();
		this.raffleNO = raffleNO;
		this.raffleDate = raffleDate;
	}
	
	public Raffle(Integer raffleNO, Date raffleDate, Integer projectNO) {
		super();
		this.raffleNO = raffleNO;
		this.raffleDate = raffleDate;
		this.projectNO = projectNO;
	}

	public Integer getRaffleNO() {
		return raffleNO;
	}

	public Date getRaffleDate() {
		return raffleDate;
	}

	public Integer getProjectNO() {
		return projectNO;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raffleNO == null) ? 0 : raffleNO.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Raffle other = (Raffle) obj;
		if (raffleNO == null) {
			if (other.raffleNO != null)
				return false;
		} else if (!raffleNO.equals(other.raffleNO))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Raffle [raffleNO=" + raffleNO + ", raffleDate=" + raffleDate + ", projectNO=" + projectNO + "]";
	}
	
	
	
}
