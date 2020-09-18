package il.ac.hit.foodtracker.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtUtils {

	private static Key key;
	
	private static Key getKey() {
		if(key == null) {
			byte[] keyBytes = Decoders.BASE64.decode(ServerConstants.SECRET_KEY);
			key = Keys.hmacShaKeyFor(keyBytes);
		}
		
		return key;
	}
	
	public static String createJWT(String username, String id) {
		  
		String jws = Jwts.builder().setSubject(username).setIssuer(id).signWith(getKey()).compact();
		return jws;
	}
	
	public static void verify(String jwt) {
		System.out.println( Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody().getSubject());
		System.out.println(Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody().getIssuer());
		
	}
	
	public static void main(String[] args) {
		
		String jwt = JwtUtils.createJWT("gal", "2");
		
		System.out.println(jwt);
		JwtUtils.verify(jwt);
		
	}
	
}
