package il.ac.hit.foodtracker.model;

import javax.persistence.Column;

public class Test {
	private String username;
	
	public Test(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}