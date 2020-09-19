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

@Path("/login")
public class Login {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response login(User user) {
		try {
			String token = UserUtils.verifyUserLogin(user);
			
			return Response.status(Status.OK)
		               .entity(token)
		               .build();
		}
		catch(Exception e) {
			return Response.status(Status.UNAUTHORIZED).entity("login failed").build();
		}
		
	}
	
}
