package il.ac.hit.foodtracker.utils.hibernate;

import java.text.MessageFormat;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;
import il.ac.hit.foodtracker.model.FoodEatingEvent;
import il.ac.hit.foodtracker.model.User;
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
	 * @param user the user details
	 * @throws ConstraintViolationException constraintViolationException
	 * @throws Exception                    exception
	 */
	public static void createUser(User user) throws ConstraintViolationException, DataException, JDBCConnectionException, LockAcquisitionException,
	PessimisticLockException, QueryTimeoutException, SQLGrammarException, GenericJDBCException  {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();
		try {

			session.beginTransaction();

			session.save(user);

			session.getTransaction().commit();

			System.out.println("saved");
		} catch (ConstraintViolationException | DataException | JDBCConnectionException | LockAcquisitionException
				| PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
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
	public static User getUserById(int id)
			throws ConstraintViolationException, DataException, JDBCConnectionException, LockAcquisitionException,
			PessimisticLockException, QueryTimeoutException, SQLGrammarException, GenericJDBCException {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			User userToReturn = session.get(User.class, id);

			session.getTransaction().commit();

			return userToReturn;
		} catch (ConstraintViolationException | DataException | JDBCConnectionException | LockAcquisitionException
				| PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
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
	public static User getUserByUsername(String username) throws Exception {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.addAnnotatedClass(FoodEatingEvent.class).buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {

			session.beginTransaction();

			Object[] params = new Object[] { username };
			String query = MessageFormat.format("from User as user where user.username = ''{0}''", params);

			User userToReturn = (User) session.createQuery(query).getSingleResult();
			session.getTransaction().commit();
			return userToReturn;
		} catch (ConstraintViolationException | DataException | JDBCConnectionException | LockAcquisitionException
				| PessimisticLockException | QueryTimeoutException | SQLGrammarException | GenericJDBCException e) {
			ErrorUtils.printPrettyError(e, "getUserByUsername");
			throw e;
		} finally {
			session.close();
			factory.close();
		}

	}

}
