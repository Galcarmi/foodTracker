package il.ac.hit.foodtracker.utils.hibernate;

import java.text.MessageFormat;
import javax.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import il.ac.hit.foodtracker.model.FoodEventDAO;
import il.ac.hit.foodtracker.model.UserDAO;
import il.ac.hit.foodtracker.utils.ErrorUtils;

/**
 * user utils hibernate class
 * 
 * @author Carmi
 *
 */
public class UserUtilsHibernate {

	/**
	 * create user with hibernate
	 * 
	 * @param userDAO the user details
	 * @throws ConstraintViolationException constraintViolationException
	 * @throws Exception                    exception
	 */
	public static void createUser(UserDAO userDAO) throws PersistenceException {
		//creates new factory and hibernate session
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserDAO.class)
				.addAnnotatedClass(FoodEventDAO.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			session.beginTransaction();

			//save the user to the db
			session.save(userDAO);

			//commit our changes
			session.getTransaction().commit();

		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "createUser");
			throw e;
		} finally {
			session.close();
			factory.close();
		}
	}

	/**
	 * get user by id with hibernate
	 * 
	 * @param id user id
	 * @return User user matching to the id
	 * @throws Exception exception
	 */
	public static UserDAO getUserById(int id) throws PersistenceException {
		//creates new factory and hibernate session
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserDAO.class)
				.addAnnotatedClass(FoodEventDAO.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			//get the user with a spesific id
			UserDAO userToReturn = session.get(UserDAO.class, id);

			session.getTransaction().commit();

			return userToReturn;
		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "getUserById");

			throw e;
		} finally {
			session.close();
			factory.close();
		}

	}

	/**
	 * get user by username with hibernate
	 * 
	 * @param username username
	 * @return User user matching to the username
	 * @throws Exception exception
	 */
	public static UserDAO getUserByUsername(String username) throws PersistenceException {
		//creates new factory and hibernate session
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserDAO.class)
				.addAnnotatedClass(FoodEventDAO.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			//prepare new HQL statement
			Object[] params = new Object[] { username };
			String query = MessageFormat.format("from User as user where user.username = ''{0}''", params);

			//execute query
			UserDAO userToReturn = (UserDAO) session.createQuery(query).getSingleResult();
			session.getTransaction().commit();
			
			return userToReturn;
		} catch (PersistenceException e) {
			ErrorUtils.printPrettyError(e, "getUserByUsername");
			
			throw e;
		} finally {
			session.close();
			factory.close();
		}

	}

}
