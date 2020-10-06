package il.ac.hit.foodtracker.rest;

import org.hibernate.exception.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.services.UserService;
import il.ac.hit.foodtracker.utils.ErrorUtils;

/**
 * jersey route class for /rest/register
 * 
 * @author Carmi
 *
 */
@Path("/register")
public class Register {

	/**
	 * api path for register
	 * 
	 * @param user User
	 * @return Response jwt token
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		Status status;
		Object message;
		try {
			String username = user.getUsername();
			String password = user.getPassword();
			String token = UserService.registerUser(username, password);

			status = Status.OK;
			message = token;

		} catch (ConstraintViolationException e) {
			ErrorUtils.printPrettyError(e, "register");
			status = Status.UNAUTHORIZED;
			message = "user already exists";
		} catch (Exception e) {
			ErrorUtils.printPrettyError(e, "register");
			status = Status.UNAUTHORIZED;
			message = "register failed";
		}

		return Response.status(status).entity(message).build();

	}
}
