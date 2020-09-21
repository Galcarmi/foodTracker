package il.ac.hit.foodtracker.tests;

import org.joda.time.DateTime;

import il.ac.hit.foodtracker.utils.DateUtils;

public class DateTest {

	public static void main(String[] args) {
		DateTime dt = new DateTime();
		System.out.println(dt.toDate());
		

		DateTime now = new DateTime();
		System.out.println(DateUtils.getStartOfTheMonth());
		System.out.println(DateUtils.getEndOfTheMonth());
		System.out.println(DateUtils.getStartOfTheWeek());
		System.out.println(DateUtils.getEndOfTheWeek());

	}

}
