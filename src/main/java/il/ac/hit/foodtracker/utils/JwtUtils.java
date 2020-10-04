package il.ac.hit.foodtracker.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import il.ac.hit.foodtracker.model.CurrentUser;
import il.ac.hit.foodtracker.rest.filters.WebApplicationErrorThrower;

@SuppressWarnings("deprecation")
public class JwtUtils {

	private static Key key;

	/**
	 * get a key representation of our secret key stored inside server constants
	 * 
	 * @return Key key
	 */
	private static Key getKey() {
		if (key == null) {
			byte[] keyBytes = Decoders.BASE64.decode(ServerConstants.SECRET_KEY);
			key = Keys.hmacShaKeyFor(keyBytes);
		}

		return key;
	}

	/**
	 * create jwt token
	 * 
	 * @param username username
	 * @param id       id
	 * @return String jwt token
	 */
	public static String createJWT(String username, Integer id) {
		Date tommorow = DateUtils.getTommorowDate();

		String jws = Jwts.builder().setSubject(username).setIssuer(id.toString()).signWith(getKey())
				.setExpiration(tommorow).compact();
		return jws;
	}

	/**
	 * get jwt details after decoding
	 * 
	 * @param jwt jwt
	 * @return CurrentUser current user details
	 */
	/// TODO check jwt when it isnt valid, why it isnt catching the exception
	public static CurrentUser getJwtDetails(String jwt) {
		try {

			String username = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody()
					.getSubject();
			String userId = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody()
					.getIssuer();

			CurrentUser currentUser = new CurrentUser(false, Integer.parseInt(userId), username);

			return currentUser;
		} catch (ExpiredJwtException | SignatureException | MalformedJwtException | IllegalArgumentException e) {
			ErrorUtils.printPrettyError(e, "getJwtDetails");
			WebApplicationErrorThrower.throwError(e.getMessage());
			return null;
		}
	}

}
