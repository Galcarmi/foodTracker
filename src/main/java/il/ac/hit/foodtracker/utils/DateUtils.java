package il.ac.hit.foodtracker.utils;

import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

	public static final DateTimeFormatter dateFormet = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	public static Date getTommorowDate() {
		Date tommorow = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(tommorow);
		c.add(Calendar.DATE, 1);
		tommorow = c.getTime();

		return tommorow;
	}

	public static String getStartOfTheWeek() {
		DateTime now = new DateTime();
		
		return DateUtils.dateFormet.print(now.weekOfWeekyear().roundFloorCopy().minusDays(1).minusSeconds(1));
	}

	public static String getEndOfTheWeek() {
		DateTime now = new DateTime();
		
		return DateUtils.dateFormet.print(now.dayOfMonth().roundCeilingCopy().dayOfWeek().withMaximumValue());
	}
	
	public static String getEndOfTheMonth() {
		DateTime now = new DateTime();
		
		
		return DateUtils.dateFormet.print(now.dayOfMonth().roundCeilingCopy().dayOfMonth().withMaximumValue().plusDays(1));
	}
	
	public static String getStartOfTheMonth() {
		DateTime now = new DateTime();
		
		return DateUtils.dateFormet.print(now.dayOfMonth().withMinimumValue().dayOfWeek().roundFloorCopy().minusSeconds(1));
	}
}
