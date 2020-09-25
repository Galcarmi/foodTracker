package il.ac.hit.foodtracker.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.utils.UserUtils;

/**
 * jersey route class for /rest/login
 * 
 * @author Carmi
 *
 */
@Path("/login")
public class Login {
	Status status;
	Object message;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response login(User user) {
		try {
			String token = UserUtils.verifyUserLogin(user);
			message = token;
			status = Status.OK;
		} catch (Exception e) {
			message = "login failed";
			status = Status.UNAUTHORIZED;
			
			e.printStackTrace();
		}

		return Response.status(status).entity(message).build();
	}
}
