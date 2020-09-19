package il.ac.hit.foodtracker.model;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateCreateUser {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			User user = new User("omer", "123", new Date());
			session.beginTransaction();

			session.save(user);

			session.getTransaction().commit();

			System.out.println("saved");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
