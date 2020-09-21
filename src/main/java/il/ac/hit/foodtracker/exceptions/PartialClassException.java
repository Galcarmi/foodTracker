package il.ac.hit.foodtracker.exceptions;

/**
 * PartialClassException class
 * @author Carmi
 *
 */
public class PartialClassException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * this error will be thrown only if the intended class has partial fields
	 * @param message error message
	 */
	public PartialClassException(String message) {
		super(message);
	}
}
