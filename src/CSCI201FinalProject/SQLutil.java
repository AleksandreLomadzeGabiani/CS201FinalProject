package CSCI201FinalProject;

import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import java.util.List;

public class SQLutil {
	//CALLED EXCLUSIVELY BY SERVER
	
	//TODO: SYNCHRONIZATION WORK !!!!!
	private static String db;
	private static String user;
	private static String pwd;
	
	public static void loginDB(String newDB, String newUser, String newPwd) {
		db = newDB;
		user = newUser;
		pwd = newPwd;
	}
	
	public static void loginDB() {
		db = "jdbc:mysql://localhost/CSCI201FinalProject";
		user = "root";
		pwd = "root";
	}
	
	public static List<Post> updatePosts(int start) {
		//TODO: return list of posts from DB to server
		return new ArrayList<Post>();
	}
	
	public static boolean makePost(Post post) {
		//TODO: add post to the database
		return false;
	}
	//Output: 	username if successful login
	//			null if login failed
	//			(e.g: credentials do not match or the connection gets lost)
	public static String loginUser(String userName, String password) {
		String sql = "{CALL authenticate(?, ?)}"; 
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
			CallableStatement stmt = conn.prepareCall(sql);) {
			stmt.setString(1, userName);
			stmt.setString(1, password);
			//returns 1 for success, 0 for failure
			ResultSet rs = stmt.executeQuery();
			rs.next();
			if(rs.getBoolean(1)) {
				return userName;
			}else {
				return null;
			}
		} catch (SQLException ex) {
			System.out.println ("SQLException: " + ex.getMessage());
		}
		return null;
	}
	
	//Output: 	"T" if the user was able to be registered
	//			"F" if the could not be registered 
	//				(e.g: username already exists or the connection gets lost)
	public static String registerUser(String userName, String password) {
		//TODO: return "T"/"F" if you were able to register user
		String sql = "{CALL usernameExists(?)}"; 
		String sql2 = "{CALL registerUser(?, ?)}";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
			CallableStatement stmt = conn.prepareCall(sql);
			CallableStatement stmt2 = conn.prepareCall(sql2);) {
			stmt.setString(1, userName);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			//if the username already exists
			if(rs.getBoolean(1)) {
				return "F";
			}
			//added the user+pass to the DB
			stmt2.setString(1, userName);
			stmt2.setString(2, password);
			rs = stmt2.executeQuery();
			return "T";
		} catch (SQLException ex) {
			System.out.println ("SQLException: " + ex.getMessage());
		}
		return "F";
	}
}
