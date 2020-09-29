package il.ac.hit.foodtracker.utils;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * date utils class
 * 
 * @author Carmi
 *
 */
public class DateUtils {

	public static final DateTimeFormatter dateFormet = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * get the date of tommorow
	 * 
	 * @return Date tommorow date
	 */
	public static Date getTommorowDate() {
		DateTime now = new DateTime();
		
		return now.plusDays(1).toDate();
	}

	/**
	 * get string that represents a minute before the start of the week
	 * 
	 * @return Date start of the week date
	 */
	public static String getStartOfTheWeek() {
		DateTime now = new DateTime();

		return DateUtils.dateFormet.print(now.weekOfWeekyear().roundFloorCopy().minusDays(1).minusSeconds(1));
	}

	/**
	 * get string that represents a minute after the end of the week
	 * 
	 * @return Date end of the week date
	 */
	public static String getEndOfTheWeek() {
		DateTime now = new DateTime();

		return DateUtils.dateFormet.print(now.dayOfMonth().roundCeilingCopy().dayOfWeek().withMaximumValue());
	}

	/**
	 * get string that represents a millisecond after the end of the day
	 * 
	 * @return Date end of the month date
	 */
	public static String getEndOfTheDay() {
		DateTime now = new DateTime();

		return DateUtils.dateFormet.print(now.dayOfWeek().roundCeilingCopy().plusMillis(1));
	}

	/**
	 * get string that represents a millisecond before the start of the day
	 * 
	 * @return Date start of the month date
	 */
	public static String getStartOfTheDay() {
		DateTime now = new DateTime();

		return DateUtils.dateFormet.print(now.dayOfWeek().roundFloorCopy().minusMillis(1));
	}
}
