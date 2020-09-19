package il.ac.hit.foodtracker.utils.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.model.User;

public class FoodEventsUtils {

	public static void addFoodEvent(FoodEatingEvent fev, int userId) throws Exception {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).addAnnotatedClass(FoodEatingEvent.class)
				.buildSessionFactory();

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
//TODO check for null values from user
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
	
	public static FoodEatingEvent getFoodEventById(int fevId) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(FoodEatingEvent.class).addAnnotatedClass(FoodEatingEvent.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			session.beginTransaction();

			FoodEatingEvent fev = session.get(FoodEatingEvent.class, fevId);
			
			session.getTransaction().commit();

			System.out.println("updated");
			
			return fev;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			factory.close();
		}
	}
}
