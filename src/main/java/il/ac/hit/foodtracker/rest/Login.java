package il.ac.hit.foodtracker.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import il.ac.hit.foodtracker.model.UserDAO;
import il.ac.hit.foodtracker.services.UserService;
import il.ac.hit.foodtracker.utils.ErrorUtils;

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
	 * @param userDAO User (checks only the username and password)
	 * @return Response jwt token
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserDAO userDAO) {
		Status status;
		Object message;

		try {
			//verifies that the user has a jwt token and checks if its valid -> if it is, returns a token
			String token = UserService.verifyUserLogin(userDAO);
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
