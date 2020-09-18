package il.ac.hit.foodtracker.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class JerseyTest {
//
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "Hello from Jerseyyy";
	}
}
