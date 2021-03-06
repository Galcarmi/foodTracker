package il.ac.hit.foodtracker.rest.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import il.ac.hit.foodtracker.exceptions.AuthVerifyException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.services.UserService;
import il.ac.hit.foodtracker.utils.ErrorUtils;

/**
 * implements jersey filter - authenticate the user with a jwt token for each
 * request
 * 
 * @author Carmi
 *
 */
@AuthFilter
@Provider
public class AuthFilterImplementation implements ContainerRequestFilter {

	/**
	 * authenticate the user with a jwt token for each request
	 * 
	 * @param crc containerRequestContext
	 * @throws IOException exception
	 */
	@Override
	public void filter(ContainerRequestContext crc) throws IOException {
		try {
			//get authorization headers
			String authHeader = crc.getHeaderString(HttpHeaders.AUTHORIZATION);
			if (authHeader == null) {
				WebApplicationErrorThrower.throwError("Bearer missing");
			}

			//get jwt token from authorization header
			String token = parseToken(authHeader);
			
			//verifies the jwt token and returns currentUser state
			CurrentUser verifyResult = verifyToken(token);
			if (!verifyResult.isVerified()) {
				WebApplicationErrorThrower.throwError("Bearer error=\"invalid_token\"");
			} else {
				crc.setProperty("verifyResult", verifyResult);
			}

		} catch (AuthVerifyException e) {
			ErrorUtils.printPrettyError(e, "filter");
			WebApplicationErrorThrower.throwError(e.getMessage());
		}
	}

	/**
	 * get the jwt header from the token
	 * 
	 * @param header the authentication header
	 * @return the jwt token
	 * @throws AuthVerifyException authVerifyException
	 */
	private String parseToken(String header) throws AuthVerifyException {
		String[] tokenHeader = header.split(" ");

		if (!tokenHeader[0].equals("Bearer")) {
			throw new AuthVerifyException("token missing bearer prefix!");
		}

		return tokenHeader[1];
	}

	/**
	 * verify the jwt token
	 * 
	 * @param token jwt token
	 * @return CurrentUser current user that is logged in
	 */
	private CurrentUser verifyToken(String token) {

		return UserService.verifyUserLoggedIn(token);
	}
}