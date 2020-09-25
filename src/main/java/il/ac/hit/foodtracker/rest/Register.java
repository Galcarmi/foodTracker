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
import il.ac.hit.foodtracker.utils.UserUtils;

/**
 * jersey route class for /rest/register
 * 
 * @author Carmi
 *
 */
@Path("/register")
public class Register {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		Status status;
		Object message;
		try {
			String username = user.getUsername();
			String password = user.getPassword();
			String token = UserUtils.registerUser(username, password);

			status = Status.OK;
			message = token;

		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			status = Status.UNAUTHORIZED;
			message = "user already exists";
		} catch (Exception e) {
			e.printStackTrace();
			status = Status.UNAUTHORIZED;
			message = "register failed";
		}

		return Response.status(status).entity(message).build();

	}
}
