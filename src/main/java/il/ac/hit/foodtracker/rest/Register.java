package il.ac.hit.foodtracker.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import il.ac.hit.foodtracker.model.Test;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.model.UserUtils;

@Path("/register")
public class Register {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response register(User user) {
		try {
			String username = user.getUsername();
			String password = user.getPassword();
			
			String token = UserUtils.registerUser(username, password);
			
			return Response.status(Status.OK)
		               .entity(username+"-"+token)
		               .build();
		}
		catch(Exception e) {
			return Response.status(Status.UNAUTHORIZED).entity("register failed").build();
		}
		
	}
	
}
