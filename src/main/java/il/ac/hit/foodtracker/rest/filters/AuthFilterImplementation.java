package il.ac.hit.foodtracker.rest.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import il.ac.hit.foodtracker.utils.UserUtils;

@AuthFilter
@Provider
public class AuthFilterImplementation implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		String authHeader = ctx.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader == null) {
			WebApplicationErrorThrower.throwError("Bearer missing");
		}
			
		String token = parseToken(authHeader);
		if (verifyToken(token) == false) {
			WebApplicationErrorThrower.throwError("Bearer error=\"invalid_token\"");
		}
	}

	private String parseToken(String header) {
		//TODO fix parsetoken returning null
		System.out.println("headerrr"+ header);
		String[] tokenHeader = header.split(" ");

		if (tokenHeader[0] != "Bearer") {
			return null;
		}

		return tokenHeader[1];
	}

	private boolean verifyToken(String token) {

		return UserUtils.verifyUserLoggedIn(token);
	}
}