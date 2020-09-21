package il.ac.hit.foodtracker.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import il.ac.hit.foodtracker.exceptions.AuthVerifyException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.rest.filters.AuthFilter;
import il.ac.hit.foodtracker.utils.FEVUtils;

@Path("/foodevents")
public class FoodEvents {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/")
	public Response viewFoodEvents(@Context ContainerRequestContext crc, @DefaultValue("weekly") @QueryParam("timerange") String timeRange) {
		List<FoodEatingEvent> fevList = FEVUtils.getEventsByTimeRange(timeRange);
		GenericEntity<List<FoodEatingEvent>> entity = new GenericEntity<List<FoodEatingEvent>>(fevList) {};
		
		return Response.status(Status.OK).entity(entity).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/{foodEventId}")
	public Response viewFoodEvent(@PathParam("foodEventId") String foodEventId) {
		try {
			FoodEatingEvent fev = FEVUtils.getFoodEventById(foodEventId);
			
			return Response.status(Status.OK).entity(fev.getFoodEatingEventResponse()).build();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			return Response.status(Status.OK).entity("server error").build();
		}
		
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/new")
	public Response addFoodEvent(FoodEatingEvent fev,@Context ContainerRequestContext crc) {
		CurrentUser currentUser = (CurrentUser)crc.getProperty("verifyResult");
		try {
			FEVUtils.addFoodEatingEvent(fev, currentUser);
			
			return Response.status(Status.OK).entity("food event added").build();
		}catch (AuthVerifyException e) {
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}	
		catch (Exception e) {
			e.printStackTrace();
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
