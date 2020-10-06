package il.ac.hit.foodtracker.services;

import java.util.Date;
import javax.persistence.PersistenceException;
import javax.ws.rs.NotAuthorizedException;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.exceptions.MissingDataException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.utils.hibernate.UserUtilsHibernate;


/**
 * UserService class
 * 
 * @author Carmi
 *
 */
public class UserService {

	/**
	 * register a user
	 * 
	 * @param username username
	 * @param password password
	 * @return String jwt token
	 * @throws MissingDataException e
	 * @throws PersistenceException e
	 * @throws MissingDataException e
	 */
	public static String registerUser(String username, String password) throws MissingDataException, PersistenceException {

		try {
			/// validation checks on the username and password
			if (username == null || password == null || username.length()<6 || password.length()<6) {
				throw new MissingDataException("username or password can't be empty and need to be at least 6 characters");
			}

			///create new user with the username and password
			Date registrationDate = new Date();
			User user = new User(username, password, registrationDate);
			UserUtilsHibernate.createUser(user);

			///create new token for the registered user
			Integer userId = user.getId();
			String token = JwtService.createJWT(username, userId);

			return token;
		} catch (PersistenceException e) {
			throw e;
		}

	}

	/**
	 * verify the jwt token of the user
	 * 
	 * @param jwt jwt token
	 * @throws PersistenceException e
	 * @return CurrentUser current logged in user
	 */
	public static CurrentUser verifyUserLoggedIn(String jwt) throws PersistenceException {
		/// get user details from jwt
		CurrentUser currentUser = JwtService.getJwtDetails(jwt);

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
	 * @throws NotAuthorizedException e
	 * @throws PersistenceException e
	 */
	public static String verifyUserLogin(User user) throws NotAuthorizedException, PersistenceException {

		//get username from db
		User userFromDB = UserUtilsHibernate.getUserByUsername(user.getUsername());
		
		//checks if the username and password are matching to the user in the db
		if (!user.getPassword().equals(userFromDB.getPassword())) {
			throw new NotAuthorizedException("not authorized");
		}

		//creates jwt if we have a match
		String token = JwtService.createJWT(userFromDB.getUsername(), userFromDB.getId());

		return token;
	}
}
