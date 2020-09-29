package il.ac.hit.foodtracker.utils;

/**
 * 
 * @author Carmi ErrorUtils class
 *
 */
public class ErrorUtils {

	/**
	 * utility function that prints the error to the console, should print it to a logger in future versions
	 * @param e            exception
	 * @param functionName functionName
	 * 
	 */
	public static void printPrettyError(Exception e, String functionName) {
		e.printStackTrace();
		System.out.println("Error in Function -> " + functionName + ", error message -> " + e.getMessage());
	}
}
