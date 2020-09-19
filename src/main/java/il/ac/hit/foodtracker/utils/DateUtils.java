package il.ac.hit.foodtracker.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date getTommorowDate() {
		Date tommorow = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(tommorow);
		c.add(Calendar.DATE, 1);
		tommorow = c.getTime();

		return tommorow;
	}
}
