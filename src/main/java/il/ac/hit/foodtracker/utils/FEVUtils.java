package il.ac.hit.foodtracker.utils;

import java.util.List;

import il.ac.hit.foodtracker.exceptions.AuthVerifyException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.utils.hibernate.FEVUtilsHibernate;

public class FEVUtils {

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
	
	public static FoodEatingEvent getFoodEventById(String foodEventId) {
		
		return FEVUtilsHibernate.getFoodEventById(Integer.parseInt(foodEventId));
	}
	
	public static void addFoodEatingEvent(FoodEatingEvent fev, CurrentUser currentUser) throws AuthVerifyException,Exception {
		fev.validateAllRequiredFields();
		FEVUtilsHibernate.addFoodEvent(fev, currentUser.getId());
	}
}
