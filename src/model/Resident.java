package model;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.jar.Attributes.Name;

import org.joda.time.LocalDate;
import org.joda.time.Years;

public class Resident {
	
	private String id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String currentCity;
	private String familyStatus;
	
	public Resident() {
		super();
	}

	public Resident(String id) {
		super();
		this.id = id;
	}
	
	public Resident(String id, String fullName) {
		super();
		String name[] = fullName.split("[ ]");
		this.id = id;
		this.firstName = name[0];
		this.lastName = name[1];
	}

	public Resident(String id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	
	
	public Resident(String id, String firstName, String lastName, Date birthDate, String currentCity,
			String familyStatus) {
		super();
		this.id = id;
		this.firstName = setUTF8(firstName);
		this.lastName = setUTF8(lastName);
		this.birthDate = birthDate;
		this.currentCity = setUTF8(currentCity);
		this.familyStatus = familyStatus;
	}
	
	private String setUTF8(String s) {
		byte[] toReturn = s.getBytes();
		return new String(toReturn, Charset.defaultCharset());
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public String getFamilyStatus() {
		return familyStatus;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public void setFamilyStatus(String familyStatus) {
		this.familyStatus = familyStatus;
	}
	
	public int getAge() {
		LocalDate now = LocalDate.now();
		LocalDate than = new LocalDate(getBirthDate());
		Years years = Years.yearsBetween(than, now);
		return years.getYears();
	}

	@Override
	public String toString() {
		return "Resident [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate
				+ ", currentCity=" + currentCity + ", familyStatus=" + familyStatus + "]";
	}

}
