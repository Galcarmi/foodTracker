package il.ac.hit.foodtracker.utils.hibernate;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.utils.DateUtils;
import il.ac.hit.foodtracker.utils.ServerConstants;

import java.text.MessageFormat;

/**
 * food eating events hibernate utils class
 * 
 * @author Carmi
 *
 */
public class FEVUtilsHibernate {

	/**
	 * add food eating event with hibernate
	 * 
	 * @param fev    foodeatingevent too add
	 * @param userId the user id
	 * @throws Exception exception
	 */
	public static void addFoodEvent(FoodEatingEvent fev, Integer userId) throws Exception {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			session.beginTransaction();

			User userToUpdate = session.get(User.class, userId);
			System.out.println(userToUpdate);
			Date now = new Date();
			fev.setCreated_date(now);
			fev.setUpdate_date(now);
			userToUpdate.addFoodEatingEvent(fev);
			session.save(fev);

			session.getTransaction().commit();

			System.out.println("updated");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			factory.close();
		}
	}

	/**
	 * get food eating event by id with hibernate
	 * 
	 * @param fevId food eating event id
	 * @return FoodEatingEvent returns matching food eating event to the id
	 */
	public static FoodEatingEvent getFoodEventById(int fevId) {
		/// creating hibernate session

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(FoodEatingEvent.class).addAnnotatedClass(User.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			// get food event by id
			session.beginTransaction();

			FoodEatingEvent fev = session.get(FoodEatingEvent.class, fevId);

			session.getTransaction().commit();

			return fev;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			factory.close();
		}
	}

	/**
	 * get all food eating event between timerange with hibernate
	 * 
	 * @param timeRange timerange (weekly,monthly)
	 * @return List food eating events list
	 */
	public static List<FoodEatingEvent> getAllEventForTimeRange(String timeRange) {
		/// creating hibernate session
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(FoodEatingEvent.class).addAnnotatedClass(User.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			session.beginTransaction();

			String dtToCheckStart;
			String dtToCheckEnd;
			/// get date objects by timerange queryparam
			if (timeRange.equals(ServerConstants.TimeRangeConstants.WEEKLY)) {
				dtToCheckStart = DateUtils.getStartOfTheWeek();
				dtToCheckEnd = DateUtils.getEndOfTheWeek();
			} else {
				dtToCheckStart = DateUtils.getStartOfTheDay();
				dtToCheckEnd = DateUtils.getEndOfTheDay();
			}

			/// create hibernate HQL query
			Object[] params = new Object[] { dtToCheckStart, dtToCheckEnd };
			String query = MessageFormat
					.format("from FoodEatingEvent as fev where fev.created_date between ''{0}'' and ''{1}''", params);

			@SuppressWarnings("unchecked")
			List<FoodEatingEvent> fevList = session.createQuery(query).getResultList();

			session.getTransaction().commit();

			for (FoodEatingEvent fev : fevList) {
				fev.setUser(null);
			}
			return fevList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			factory.close();
		}
	}
}
