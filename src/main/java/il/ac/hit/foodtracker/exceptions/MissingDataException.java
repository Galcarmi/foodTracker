package il.ac.hit.foodtracker.exceptions;

/**
 * MissingDataException class
 * 
 * @author Carmi
 *
 */
public class MissingDataException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * this error will be thrown only if the intended class has partial fields
	 * 
	 * @param message error message
	 */
	public MissingDataException(String message) {
		super(message);
	}
}
