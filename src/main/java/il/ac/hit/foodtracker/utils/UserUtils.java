package il.ac.hit.foodtracker.utils;

import java.util.Date;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import javax.ws.rs.NotAuthorizedException;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.exceptions.MissingDataException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.utils.hibernate.UserUtilsHibernate;


/**
 * user utils class
 * 
 * @author Carmi
 *
 */
public class UserUtils {

	/**
	 * register a user
	 * 
	 * @param username username
	 * @param password password
	 * @return String jwt token
	 * @throws MissingDataException 
	 * @throws ConstraintViolationException constraintViolationException
	 * @throws Exception                    exception
	 */
	public static String registerUser(String username, String password) throws MissingDataException, PersistenceException {

		try {
			/// validation checks on the username and password
			if (username == null || password == null) {
				throw new MissingDataException("username or password can't be null");
			}

			Date registrationDate = new Date();

			User user = new User(username, password, registrationDate);
			UserUtilsHibernate.createUser(user);

			Integer userId = user.getId();
			String token = JwtUtils.createJWT(username, userId);

			return token;
		} catch (PersistenceException e) {
			throw e;
		}

	}

	/**
	 * verify the jwt token of the user
	 * 
	 * @param jwt jwt token
	 * @return CurrentUser current logged in user
	 */
	public static CurrentUser verifyUserLoggedIn(String jwt) throws PersistenceException {
		/// get user details from jwt
		CurrentUser currentUser = JwtUtils.getJwtDetails(jwt);

		/// get user details
		Integer userId = currentUser.getId();
		String username = currentUser.getUsername();
		User userFromJwt = UserUtilsHibernate.getUserById(userId);

		/// check if the user from jwt is equals to the user from the userid
		if (userFromJwt.getId() == userId && userFromJwt.getUsername().equals(username)) {
			currentUser.setVerified(true);
		}

		return currentUser;

	}

	/**
	 * verifies the username and the password of a user in the DB
	 * 
	 * @param user the user details
	 * @return String jwt token
	 * @throws Exception exception
	 */
	public static String verifyUserLogin(User user) throws NotAuthorizedException, PersistenceException {

		User userFromDB = UserUtilsHibernate.getUserByUsername(user.getUsername());
		if (!user.getPassword().equals(userFromDB.getPassword())) {
			throw new NotAuthorizedException("not authorized");
		}

		String token = JwtUtils.createJWT(userFromDB.getUsername(), userFromDB.getId());

		return token;
	}
}
