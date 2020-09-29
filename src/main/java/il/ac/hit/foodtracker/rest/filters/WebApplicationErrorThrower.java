package il.ac.hit.foodtracker.rest.filters;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class WebApplicationErrorThrower {
	/**
	 * throw string error to the user
	 * 
	 * @param errorDescription errorDescription
	 */
	public static void throwError(String errorDescription) {
		ResponseBuilder builder = null;
		builder = Response.status(Response.Status.UNAUTHORIZED).entity(errorDescription);
		throw new WebApplicationException(builder.build());
	}
}
