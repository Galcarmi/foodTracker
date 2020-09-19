package il.ac.hit.foodtracker.utils.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.utils.UserUtils;

public class UserUtilsHibernate {

	

	public static void createUser(User user) throws Exception {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {
			
			session.beginTransaction();

			session.save(user);
			
			session.getTransaction().commit();

			System.out.println("saved");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally {
			session.close();
			factory.close();
		}
	}
	
	public static User getUserById(int id) throws Exception {
		
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		
		try {
			
			session.beginTransaction();
			
			User userToReturn = session.get(User.class, id);
			
			session.getTransaction().commit();
			
			return userToReturn;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally {
			session.close();
			factory.close();
		}
				
	}
	

	public static User getUserByUsername(String username) throws Exception {
		
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		
		try {
			
			session.beginTransaction();
			
			User userToReturn = (User) session.createQuery("from User as user where user.username ='" +username+"'").getSingleResult();
			session.getTransaction().commit();
			
			return userToReturn;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally {
			session.close();
			factory.close();
		}
				
	}
	
	public static void main(String[] args) {
		try {
			UserUtils.registerUser("gal", "555");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}