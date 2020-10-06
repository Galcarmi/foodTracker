package il.ac.hit.foodtracker.rest;

import java.util.List;
import javax.persistence.PersistenceException;
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
import il.ac.hit.foodtracker.exceptions.MissingDataException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.model.FoodEventDAO;
import il.ac.hit.foodtracker.rest.filters.AuthFilter;
import il.ac.hit.foodtracker.services.FEVService;
import il.ac.hit.foodtracker.utils.ErrorUtils;
import il.ac.hit.foodtracker.utils.ServerConstants;

/**
 * jersey route class for /rest/foodevents
 * 
 * @author Carmi
 *
 */
@Path("/foodevents")
public class FoodEvents {

	/**
	 * api path for viewing all food events by timerange
	 * 
	 * @param crc       ContainerRequestContext
	 * @param timeRange String
	 * @return Response all the food events according to the time range
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/")
	public Response viewFoodEvents(@Context ContainerRequestContext crc,
			@DefaultValue(ServerConstants.TimeRangeConstants.WEEKLY) @QueryParam("timerange") String timeRange) {
		Status status;
		Object message;
		try {
			CurrentUser currentUser = (CurrentUser) crc.getProperty("verifyResult");
			List<FoodEventDAO> fevList = FEVService.getEventsByTimeRange(timeRange, currentUser.getId());
			///food events list doesn't have descriptor so we need to wrap it with generic entity object
			GenericEntity<List<FoodEventDAO>> entity = new GenericEntity<List<FoodEventDAO>>(fevList) {
			};
			status = Status.OK;
			message = entity;

		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "addFoodEvent");
			status = Status.INTERNAL_SERVER_ERROR;
			message = "server error";
		}
		catch (Exception e) {
			ErrorUtils.printPrettyError(e, "viewFoodEvent");
			status = Status.INTERNAL_SERVER_ERROR;
			message = "server error";
		}

		return Response.status(status).entity(message).build();

	}

	/**
	 * api path for viewing single food event by id
	 * 
	 * @param foodEventId foodEventId
	 * @return Response food event
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/{foodEventId}")
	public Response viewFoodEvent(@PathParam("foodEventId") String foodEventId) {
		Status status;
		Object message;
		try {
			FoodEventDAO fev = FEVService.getFoodEventById(foodEventId);
			status = Status.OK;
			message = fev.getFoodEatingEventResponse();
		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "addFoodEvent");
			status = Status.INTERNAL_SERVER_ERROR;
			message = "server error";
		} catch (Exception e) {
			ErrorUtils.printPrettyError(e, "viewFoodEvent");
			status = Status.INTERNAL_SERVER_ERROR;
			message = "server error";
		}

		return Response.status(status).entity(message).build();
	}

	/**
	 * api path for adding food event
	 * 
	 * @param fev FoodEatingEvent
	 * @param crc ContainerRequestContext
	 * @return Response the food eating event by id
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@AuthFilter
	@Path("/new")
	public Response addFoodEvent(FoodEventDAO fev, @Context ContainerRequestContext crc) {
		//gets the current user from the auth filter
		CurrentUser currentUser = (CurrentUser) crc.getProperty("verifyResult");
		Status status;
		Object message;
		try {
			FEVService.addFoodEatingEvent(fev, currentUser);
			status = Status.OK;
			message = "food event added";
		} catch (MissingDataException e) {
			ErrorUtils.printPrettyError(e, "addFoodEvent");
			status = Status.BAD_REQUEST;
			message = e.getMessage();
		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "addFoodEvent");
			status = Status.INTERNAL_SERVER_ERROR;
			message = "server error";
		} catch (Exception e) {
			ErrorUtils.printPrettyError(e, "addFoodEvent");
			status = Status.INTERNAL_SERVER_ERROR;
			message = "server error";
		}

		return Response.status(status).entity(message).build();
	}
}
