package il.ac.hit.foodtracker.utils;

import java.util.List;

import il.ac.hit.foodtracker.exceptions.AuthVerifyException;
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
	public static List<FoodEatingEvent> getEventsByTimeRange(String timeRange){
		
		String timeRangeToCheck;
		
		if(timeRange != null && (timeRange.equals("weekly") || timeRange.equals("monthly"))) {
			timeRangeToCheck = timeRange;
		}
		else {
			timeRangeToCheck = "weekly";
		}
		
		return FEVUtilsHibernate.getAllEventForTimeRange(timeRangeToCheck);
	}
	
	/**
	 * get all food eating events by id
	 * @param foodEventId food eating event id
	 * @return FoodEatingEvent foodEatingEvent
	 */
	public static FoodEatingEvent getFoodEventById(String foodEventId) {
		
		return FEVUtilsHibernate.getFoodEventById(Integer.parseInt(foodEventId));
	}
	
	/**
	 * add food eating event to the current user
	 * @param fev food eating event
	 * @param currentUser the current user
	 * @throws AuthVerifyException authVerifyException
	 * @throws Exception exception
	 */
	public static void addFoodEatingEvent(FoodEatingEvent fev, CurrentUser currentUser) throws AuthVerifyException,Exception {
		fev.validateAllRequiredFields();
		FEVUtilsHibernate.addFoodEvent(fev, currentUser.getId());
	}
}
