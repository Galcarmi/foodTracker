package il.ac.hit.foodtracker.model;

import java.util.HashMap;

public class VerifyUserLoggedInResult {
	private HashMap<String, String> jwtUserDetails;
	private boolean verified;

	public VerifyUserLoggedInResult(HashMap<String, String> jwtUserDetails, boolean verified) {
		super();
		this.jwtUserDetails = jwtUserDetails;
		this.verified = verified;
	}

	public HashMap<String, String> getJwtUserDetails() {
		return jwtUserDetails;
	}

	public void setJwtUserDetails(HashMap<String, String> jwtUserDetails) {
		this.jwtUserDetails = jwtUserDetails;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

}
