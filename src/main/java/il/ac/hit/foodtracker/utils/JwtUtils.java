package il.ac.hit.foodtracker.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import il.ac.hit.foodtracker.model.CurrentUser;

public class JwtUtils {

	private static Key key;

	private static Key getKey() {
		if (key == null) {
			byte[] keyBytes = Decoders.BASE64.decode(ServerConstants.SECRET_KEY);
			key = Keys.hmacShaKeyFor(keyBytes);
		}

		return key;
	}

	public static String createJWT(String username, Integer id) {
		Date tommorow = DateUtils.getTommorowDate();

		String jws = Jwts.builder().setSubject(username).setIssuer(id.toString()).signWith(getKey()).setExpiration(tommorow)
				.compact();
		return jws;
	}

	public static CurrentUser getJwtDetails(String jwt) {
		String username = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody()
				.getSubject();
		String userId = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody().getIssuer();

		CurrentUser currentUser = new CurrentUser (false,Integer.parseInt(userId),username);

		return currentUser;
	}
}
