package il.ac.hit.foodtracker.model;

public class CurrentUser extends User{
	private boolean m_Verified;

	public CurrentUser(boolean i_Verified, Integer i_Id, String i_Username) {
		super(i_Username,null,null);
		this.setVerified(i_Verified);
		this.setId(i_Id);
	}

	public boolean isVerified() {
		return m_Verified;
	}

	public void setVerified(boolean i_Verified) {
		this.m_Verified = i_Verified;
	}

}
