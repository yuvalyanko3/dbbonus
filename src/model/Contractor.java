package model;

import java.sql.Date;

public class Contractor {
	
	private String id;
	private String name;
	private Date establishedDate;
	private int numOfWorkers;
	
	public Contractor(String id, String name, Date establishedDate, int numOfWorkers) {
		super();
		this.id = id;
		this.name = name;
		this.establishedDate = establishedDate;
		this.numOfWorkers = numOfWorkers;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getEstablishedDate() {
		return establishedDate;
	}

	public int getNumOfWorkers() {
		return numOfWorkers;
	}
	

}
