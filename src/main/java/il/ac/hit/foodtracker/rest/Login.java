package il.ac.hit.foodtracker.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.utils.ErrorUtils;
import il.ac.hit.foodtracker.utils.UserUtils;

/**
 * jersey route class for /rest/login
 * 
 * @author Carmi
 *
 */
@Path("/login")
public class Login {

	/**
	 * api path for login 
	 * @param user User (checks only the username and password)
	 * @return Response jwt token
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		Status status;
		Object message;
		
		try {
			String token = UserUtils.verifyUserLogin(user);
			message = token;
			status = Status.OK;
		} catch (Exception e) {
			ErrorUtils.printPrettyError(e, "login");
			message = "login failed";
			status = Status.UNAUTHORIZED;
		}

		return Response.status(status).entity(message).build();
	}
}
