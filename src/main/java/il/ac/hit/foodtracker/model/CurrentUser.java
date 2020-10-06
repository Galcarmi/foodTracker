package il.ac.hit.foodtracker.model;

/**
 * CurrentUser class - represents the current user logged in details
 * 
 * @author Carmi
 *
 */
public class CurrentUser extends User {

	private boolean m_Verified;

	public CurrentUser(boolean i_Verified, Integer i_Id, String i_Username) {
		super(i_Username, null, null);
		this.setVerified(i_Verified);
		this.setId(i_Id);
	}

	/**
	 * getter
	 * 
	 * @return boolean if the user is verified
	 */
	public boolean isVerified() {
		return m_Verified;
	}

	/**
	 * setter
	 * 
	 * @param i_Verified is the user is verified
	 */
	public void setVerified(boolean i_Verified) {
		this.m_Verified = i_Verified;
	}

}
