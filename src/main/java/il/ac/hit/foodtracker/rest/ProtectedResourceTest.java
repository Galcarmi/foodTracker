package il.ac.hit.foodtracker.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import il.ac.hit.foodtracker.rest.filters.AuthFilter;

@Path("/protected")
public class ProtectedResourceTest {
//
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	public String protectedResource() {
		try {
			return "protected";
		}
		catch(Exception e) {
			return "forbidden";
		}
	}
	
	
}
