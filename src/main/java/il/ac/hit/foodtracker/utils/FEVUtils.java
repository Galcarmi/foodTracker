package il.ac.hit.foodtracker.utils;

import java.util.List;

import javax.persistence.PersistenceException;

import il.ac.hit.foodtracker.exceptions.AuthVerifyException;
import il.ac.hit.foodtracker.exceptions.MissingDataException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.utils.hibernate.FEVUtilsHibernate;

/**
 * food eating event utils class
 * @author Carmi
 *
 */
public class FEVUtils {

	/**
	 * get all food eating events by time range
	 * @param timeRange timeRange
	 * @return List list of food eating events
	 */
	public static List<FoodEatingEvent> getEventsByTimeRange(String timeRange) throws PersistenceException{
		
		return FEVUtilsHibernate.getAllEventForTimeRange(timeRange);
	}
	
	/**
	 * get all food eating events by id
	 * @param foodEventId food eating event id
	 * @return FoodEatingEvent foodEatingEvent
	 */
	public static FoodEatingEvent getFoodEventById(String foodEventId) throws PersistenceException{
		
		return FEVUtilsHibernate.getFoodEventById(Integer.parseInt(foodEventId));
	}
	
	/**
	 * add food eating event to the current user
	 * @param fev food eating event
	 * @param currentUser the current user
	 * @throws AuthVerifyException authVerifyException
	 * @throws MissingDataException 
	 */
	public static void addFoodEatingEvent(FoodEatingEvent fev, CurrentUser currentUser) throws PersistenceException, MissingDataException {
		fev.validateAllRequiredFields();
		FEVUtilsHibernate.addFoodEvent(fev, currentUser.getId());
	}
}
