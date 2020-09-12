package il.ac.hit.foodtracker.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCTest {
public static void main (String[] args) {
	String jdbcUrl = "jdbc:mysql://localhost:3306/my-test-db?useSSL=false&serverTimezone=UTC";
	String username = "haim";
	String password = "michael";
	
	try {
		Connection myConn = DriverManager.getConnection(jdbcUrl,username,password);
		System.out.println(myConn);
		}
	catch(Exception e) {
		e.printStackTrace();
	}
}
}
