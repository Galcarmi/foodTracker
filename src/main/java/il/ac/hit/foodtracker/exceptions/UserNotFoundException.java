package il.ac.hit.foodtracker.exceptions;

/**
 * UserNotFoundException class
 * 
 * @author Carmi
 *
 */
public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * this error will be thrown only if the intended class has partial fields
	 * 
	 * @param message error message
	 */
	public UserNotFoundException(String message) {
		super(message);
	}
}
