package model;

import control.Logic;

public class UserLogin {
	
	private String userName;
	private String userID;
	private Logic logic;
	
	public UserLogin(String userName, String userID) {
		super();
		this.userName = userName;
		this.userID = userID;
		logic = Logic.getInstance();
	}

	public String getUserName() {
		return userName;
	}

	public String getUserID() {
		return userID;
	}

	@Override
	public String toString() {
		return "UserLogin [userName=" + userName + ", userID=" + userID + "]";
	}

}
