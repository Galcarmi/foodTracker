package il.ac.hit.foodtracker.model;

public class CurrentUser extends User{
	private boolean verified;

	public CurrentUser(boolean verified, String id, String username) {
		super(username,null,null);
		this.verified = verified;
		this.setId(Integer.parseInt(id));
		//TODO maybe change id to string .. to many parseint..
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

}
