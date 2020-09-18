package il.ac.hit.foodtracker.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserUtils {

	public static String registerUser(String username, String password) throws Exception {
		
			if(username == null || password == null) {
				throw new Exception("username or password can't be null");
			}
			
			Date registrationDate = new Date();
			Date tokenValidity = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(tokenValidity);
			c.add(Calendar.DATE, 1);
			tokenValidity = c.getTime();
			
			User user = new User(username, password, registrationDate);
			
			String token = UUID.randomUUID().toString().replace("-", "");
			user.setToken(token);
			user.setToken_valid_until(tokenValidity);
			
			UserUtils.createUser(user);
			
			return token;
		
	}

	private static void createUser(User user) {
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
