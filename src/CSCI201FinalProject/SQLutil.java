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
	
	public static List<Post> updatePosts() {
		//TODO: return list of posts from DB to server
		String sql="set @last_id=?";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				  PreparedStatement ps = conn.prepareStatement(sql);){
			ps.setString(1,"-15");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql2="SELECT * FROM Posts  WHERE PostId>=last_id-15 order by Dateof limit 15";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				  PreparedStatement ps = conn.prepareStatement(sql);){
			ResultSet rs=ps.executeQuery();
			while (rs.next())
				System.out.println (
					rs.getString("username" ) + "\n" +
					rs.getString("Dateof") + "\t" +
					rs.getString("Title")  + "\n" +
					rs.getString("Post")+ "\n" +
					rs.getString("Paymentlink"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Post>();
	}
	
	public static boolean makePost(Post post) {
		//TODO: add post to the database
		String sql= "{CALL makePost(?,?,?,?)}";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				CallableStatement stmt = conn.prepareCall(sql);){
				stmt.setString(1, post.getPost());
				stmt.setString(2, post.getUsername());
				stmt.setString(3, post.getPaylink());
				stmt.setString(4, post.getTitle());
				stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
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
