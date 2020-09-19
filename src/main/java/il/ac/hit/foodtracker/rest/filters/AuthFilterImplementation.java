package il.ac.hit.foodtracker.rest.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import il.ac.hit.foodtracker.exceptions.AuthVerifyException;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.utils.UserUtils;

@AuthFilter
@Provider
public class AuthFilterImplementation implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext crc) throws IOException {
		try {
			String authHeader = crc.getHeaderString(HttpHeaders.AUTHORIZATION);
			if (authHeader == null) {
				WebApplicationErrorThrower.throwError("Bearer missing");
			}

			String token = parseToken(authHeader);
			CurrentUser verifyResult = verifyToken(token);
			if (!verifyResult.isVerified()) {
				WebApplicationErrorThrower.throwError("Bearer error=\"invalid_token\"");
			} else {
				crc.setProperty("verifyResult", verifyResult);
			}

		} catch (AuthVerifyException e) {
			e.printStackTrace();
			WebApplicationErrorThrower.throwError(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			WebApplicationErrorThrower.throwError("server error");
		}
	}

	private String parseToken(String header) throws AuthVerifyException {
		String[] tokenHeader = header.split(" ");

		if (!tokenHeader[0].equals("Bearer")) {
			throw new AuthVerifyException("token missing bearer prefix!");
		}

		return tokenHeader[1];
	}

	private CurrentUser verifyToken(String token) {

		return UserUtils.verifyUserLoggedIn(token);
	}
}