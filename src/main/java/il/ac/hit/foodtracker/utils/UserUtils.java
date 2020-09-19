package il.ac.hit.foodtracker.utils;

import java.util.Date;
import org.hibernate.exception.ConstraintViolationException;
import javax.ws.rs.NotAuthorizedException;
import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.utils.hibernate.UserUtilsHibernate;

public class UserUtils {

	public static String registerUser(String username, String password) throws ConstraintViolationException, Exception {

		try {
			if (username == null || password == null) {
				throw new Exception("username or password can't be null");
			}

			Date registrationDate = new Date();

			User user = new User(username, password, registrationDate);

			Integer userId = user.getId();
			String token = JwtUtils.createJWT(username, userId.toString());

			UserUtilsHibernate.createUser(user);

			return token;
		} catch (Exception e) {
			throw e;
		}

	}

	public static CurrentUser verifyUserLoggedIn(String jwt) {
		CurrentUser currentUser = JwtUtils.getJwtDetails(jwt);
	
		try {
			int userId = currentUser.getId();
			String username = currentUser.getUsername();
			User userFromJwt = UserUtilsHibernate.getUserById(userId);
			//TODO change id to integer class
		
			if (userFromJwt.getId() == userId && userFromJwt.getUsername().equals(username)) {
				currentUser.setVerified(true);
			}

			return currentUser;
		} catch (Exception e) {
			e.printStackTrace();

			return currentUser;
		}
	}

	public static String verifyUserLogin(User user) throws Exception {

		User userFromDB = UserUtilsHibernate.getUserByUsername(user.getUsername());
		if (!user.getPassword().equals(userFromDB.getPassword())) {
			throw new NotAuthorizedException("not authorized");
		}

		String token = JwtUtils.createJWT(userFromDB.getUsername(), userFromDB.getId() + "");

		return token;
	}
}
