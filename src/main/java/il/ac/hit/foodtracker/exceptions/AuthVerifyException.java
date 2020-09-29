package il.ac.hit.foodtracker.exceptions;

/**
 * AuthVerifyException class
 * 
 * @author Carmi
 *
 */
public class AuthVerifyException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * this error will be thrown if there was a failure during jwt token validation
	 * 
	 * @param message error message
	 * 
	 */
	public AuthVerifyException(String message) {
		super(message);
	}
}
