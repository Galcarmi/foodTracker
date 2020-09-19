package il.ac.hit.foodtracker.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import il.ac.hit.foodtracker.rest.filters.AuthFilter;

@Path("/foodevents")
public class FoodEvents {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/")
	public String viewFoodEvents(@Context ContainerRequestContext crc) {
		System.out.println(crc.getProperty("username"));
		return "foodevents";

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/{foodEventId}")
	public String viewFoodEvent(@PathParam("foodEventId") String foodEventId) {
		return "foodevent:" + foodEventId;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/new")
	public String addFoodEvent() {
		return "addFoodEvent";

	}

}
