package il.ac.hit.foodtracker.rest.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import il.ac.hit.foodtracker.exceptions.AuthVerifyException;
import il.ac.hit.foodtracker.utils.UserUtils;

@AuthFilter
@Provider
public class AuthFilterImplementation implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		try {
			String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
			if (authHeader == null) {
				WebApplicationErrorThrower.throwError("Bearer missing");
			}

			String token = parseToken(authHeader);
			if (verifyToken(token) == false) {
				WebApplicationErrorThrower.throwError("Bearer error=\"invalid_token\"");
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
		System.out.println("headerrr" + header);
		String[] tokenHeader = header.split(" ");

		if (!tokenHeader[0].equals("Bearer")) {
			throw new AuthVerifyException("token missing bearer prefix!");
		}

		return tokenHeader[1];
	}

	private boolean verifyToken(String token) {

		return UserUtils.verifyUserLoggedIn(token);
	}
}