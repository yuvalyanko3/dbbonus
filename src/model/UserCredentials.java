package model;

public class UserCredentials {
	private String userName;
	private String userPass;
	private String userId;
	private String userType;
	
	public UserCredentials() {
		super();
	}
	
	public UserCredentials(String userName, String userPass, String userId, String userType) {
		super();
		this.userName = userName;
		this.userPass = userPass;
		this.userId = userId;
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	
	

}
