package il.ac.hit.foodtracker.model;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateCreateFEV {
	
	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
			session.beginTransaction();
			
			User testUser = session.get(User.class, 1);
			Date now = new Date();
			FoodEatingEvent fev1 = new FoodEatingEvent("pizza",200,now,now);
			now = new Date();
			FoodEatingEvent fev2 = new FoodEatingEvent("ice-cream",400,now,now);
			
			testUser.addFoodEatingEvent(fev1);
			testUser.addFoodEatingEvent(fev2);
			
			session.save(fev1);
			session.save(fev2);
			
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
	
}
