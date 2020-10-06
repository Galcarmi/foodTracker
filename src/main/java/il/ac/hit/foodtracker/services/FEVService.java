package il.ac.hit.foodtracker.services;

import java.util.List;
import javax.persistence.PersistenceException;
import il.ac.hit.foodtracker.exceptions.MissingDataException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.model.FoodEventDAO;
import il.ac.hit.foodtracker.utils.hibernate.FEVUtilsHibernate;

/**
 * food eating event utils class
 * @author Carmi
 *
 */
public class FEVService {

	/**
	 * get all food eating events by time range
	 * @param timeRange timeRange
	 * @throws PersistenceException e
	 * @return List list of food eating events
	 */
	public static List<FoodEventDAO> getEventsByTimeRange(String timeRange, int userId) throws PersistenceException{
		
		return FEVUtilsHibernate.getAllEventForTimeRange(timeRange,userId);
	}
	
	/**
	 * get all food eating events by id
	 * @param foodEventId food eating event id
	 * @throws PersistenceException e
	 * @return FoodEatingEvent foodEatingEvent
	 */
	public static FoodEventDAO getFoodEventById(String foodEventId) throws PersistenceException{
		
		return FEVUtilsHibernate.getFoodEventById(Integer.parseInt(foodEventId));
	}
	
	/**
	 * add food eating event to the current user
	 * @param fev food eating event
	 * @param currentUser the current user
	 * @throws MissingDataException e
	 * @throws PersistenceException e
	 */
	public static void addFoodEatingEvent(FoodEventDAO fev, CurrentUser currentUser) throws PersistenceException, MissingDataException {
		fev.validateAllRequiredFields();
		FEVUtilsHibernate.addFoodEvent(fev, currentUser.getId());
	}
}
