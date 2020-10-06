package il.ac.hit.foodtracker.utils.hibernate;

import java.util.Date;
import java.util.List;
import javax.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import il.ac.hit.foodtracker.model.FoodEventDAO;
import il.ac.hit.foodtracker.model.UserDAO;
import il.ac.hit.foodtracker.utils.DateUtils;
import il.ac.hit.foodtracker.utils.ErrorUtils;
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
	 * @throws PersistenceException e
	 */
	public static void addFoodEvent(FoodEventDAO fev, Integer userId) throws PersistenceException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserDAO.class)
				.addAnnotatedClass(FoodEventDAO.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			session.beginTransaction();

			UserDAO userToUpdate = session.get(UserDAO.class, userId);
			System.out.println(userToUpdate);
			Date now = new Date();
			fev.setCreated_date(now);
			fev.setUpdate_date(now);
			userToUpdate.addFoodEatingEvent(fev);
			session.save(fev);

			session.getTransaction().commit();
		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "addFoodEvent");

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
	 * @throws PersistenceException e
	 * @return FoodEatingEvent returns matching food eating event to the id
	 */
	public static FoodEventDAO getFoodEventById(int fevId) throws PersistenceException {
		/// creating hibernate session

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(FoodEventDAO.class).addAnnotatedClass(UserDAO.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			// get food event by id
			session.beginTransaction();

			FoodEventDAO fev = session.get(FoodEventDAO.class, fevId);

			session.getTransaction().commit();

			return fev;
		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "getFoodEventById");

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
	 * @throws PersistenceException e
	 * @return List food eating events list
	 */
	public static List<FoodEventDAO> getAllEventForTimeRange(String timeRange, int userId)
			throws PersistenceException {
		/// creating hibernate session
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(FoodEventDAO.class).addAnnotatedClass(UserDAO.class).buildSessionFactory();

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
			Object[] params = new Object[] { dtToCheckStart, dtToCheckEnd, userId };
			String query = MessageFormat.format(
					"from FoodEatingEvent as fev where fev.created_date between ''{0}'' and ''{1}'' and fev.user.id = {2} ",
					params);

			@SuppressWarnings("unchecked")
			List<FoodEventDAO> fevList = session.createQuery(query).getResultList();

			session.getTransaction().commit();

			for (FoodEventDAO fev : fevList) {
				fev.setUser(null);
			}
			return fevList;
		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "getAllEventForTimeRange");
			throw e;
		} finally {
			session.close();
			factory.close();
		}
	}
}
