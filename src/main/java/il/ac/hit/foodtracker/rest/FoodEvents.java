package il.ac.hit.foodtracker.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.rest.filters.AuthFilter;
import il.ac.hit.foodtracker.utils.hibernate.FoodEventsUtils;

@Path("/foodevents")
public class FoodEvents {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/")
	public String viewFoodEvents(@Context ContainerRequestContext crc) {
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/new")
	public Response addFoodEvent(FoodEatingEvent fev,@Context ContainerRequestContext crc) {
		CurrentUser currentUser = (CurrentUser)crc.getProperty("verifyResult");
		try {
			FoodEventsUtils.addFoodEvent(fev, currentUser.getId());
			return Response.status(Status.OK).entity("food event added").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
