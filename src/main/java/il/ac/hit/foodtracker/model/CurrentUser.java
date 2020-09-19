package il.ac.hit.foodtracker.model;

public class CurrentUser extends User{
	private boolean m_Verified;

	public CurrentUser(boolean i_Verified, String i_Id, String i_Username) {
		super(i_Username,null,null);
		this.setVerified(i_Verified);
		this.setId(Integer.parseInt(i_Id));
		//TODO maybe change id to string .. to many parseint..
	}

	public boolean isVerified() {
		return m_Verified;
	}

	public void setVerified(boolean i_Verified) {
		this.m_Verified = i_Verified;
	}

}
