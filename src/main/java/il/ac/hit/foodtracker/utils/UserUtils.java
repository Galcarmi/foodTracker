package il.ac.hit.foodtracker.utils;

import java.util.Date;
import java.util.HashMap;
import org.hibernate.exception.ConstraintViolationException;
import javax.ws.rs.NotAuthorizedException;

import il.ac.hit.foodtracker.model.User;
import il.ac.hit.foodtracker.model.VerifyUserLoggedInResult;
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

	public static VerifyUserLoggedInResult verifyUserLoggedIn(String jwt) {
		HashMap<String, String> userDetails = JwtUtils.getJwtDetails(jwt);
		String userId = userDetails.get("userId");
		String username = userDetails.get("username");
		boolean verified = false;

		try {
			User userFromJwt = UserUtilsHibernate.getUserById(Integer.parseInt(userId));
			if (userFromJwt.getId() == Integer.parseInt(userId) && userFromJwt.getUsername().equals(username)) {
				verified = true;
			}

			return new VerifyUserLoggedInResult(userDetails, verified);
		} catch (Exception e) {
			e.printStackTrace();

			return new VerifyUserLoggedInResult(null, false);
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
