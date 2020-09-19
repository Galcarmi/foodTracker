package il.ac.hit.foodtracker.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtUtils {

	private static Key key;

	private static Key getKey() {
		if (key == null) {
			byte[] keyBytes = Decoders.BASE64.decode(ServerConstants.SECRET_KEY);
			key = Keys.hmacShaKeyFor(keyBytes);
		}

		return key;
	}

	public static String createJWT(String username, String id) {
		Date tommorow = DateUtils.getTommorowDate();

		String jws = Jwts.builder().setSubject(username).setIssuer(id).signWith(getKey()).setExpiration(tommorow)
				.compact();
		return jws;
	}

	public static HashMap<String, String> getJwtDetails(String jwt) {
		String username = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody()
				.getSubject();
		String userId = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody().getIssuer();

		HashMap<String, String> userDetails = new HashMap<String, String>();
		userDetails.put("username", username);
		userDetails.put("userId", userId);

		return userDetails;
	}

	public static void main(String[] args) {

		String jwt = JwtUtils.createJWT("gal", "2");

		System.out.println(jwt);
		JwtUtils.getJwtDetails(jwt);

	}

}
