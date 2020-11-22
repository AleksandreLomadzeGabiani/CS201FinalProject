package CSCI201FinalProject;

import java.util.ArrayList;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.List;
import java.util.Date;

public class SQLutil {
	//CALLED EXCLUSIVELY BY SERVER
	
	//TODO: SYNCHRONIZATION WORK !!!!!
	
	//TODO: test server interaction / command sending
	
	
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
	
	public static int last_id() {
		String sql="{CALL last_id(?)}";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				CallableStatement stmt = conn.prepareCall(sql);){
			int id = 0;
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.executeUpdate();
			id=stmt.getInt(1);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static List<Post> updatePosts(int start) throws ParseException {
		//TODO: return list of posts from DB to server
		ArrayList posts= new ArrayList<Post>();
		
		String sql="SELECT * FROM Posts  WHERE PostId>=? order by Dateof limit 15";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				  PreparedStatement ps = conn.prepareStatement(sql);){
			ps.setInt(1,start-15);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
					String date=rs.getString("Dateof");
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
					Date date_= formatter.parse(date);
					Post post= new Post(rs.getString("username"), date_, rs.getString("Title"),
							rs.getString("Post"), rs.getString("Paymentlink"));
					posts.add(post);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posts;
	}
	
	public static Post getPost(int id) throws ParseException {
		String sql="SELECT * FROM Posts  WHERE PostId=?";
		Post post = new Post(null,null,null,null,null);
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
				  PreparedStatement ps = conn.prepareStatement(sql);){
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
					String date=rs.getString("Dateof");
					SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
					Date date_= formatter.parse(date);
					post= new Post(rs.getString("username"), date_, rs.getString("Title"),
							rs.getString("Post"), rs.getString("Paymentlink"));
					return post;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return post;
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
			return false;
		}
		return true;
	}
	//Output: 	username if successful login
	//			null if login failed
	//			(e.g: credentials do not match or the connection gets lost)
	public static String loginUser(String userName, String password) {
		String sql = "SELECT authenticate(?, ?)"; 
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
			CallableStatement stmt = conn.prepareCall(sql);) {
			stmt.setString(1, userName);
			stmt.setString(2, password);
			//returns 1 for success, 0 for failure
			ResultSet rs = stmt.executeQuery();
//			//TESTING DEBUGGING
//			System.out.println("hi1");
//			while(rs.next()) {
//				System.out.println(rs.getInt(1));
//			}
			rs.next();
			//returns: valid -> 1; invalid -> 0;
			if(rs.getInt(1) == 1) {
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
		String sql = "SELECT usernameExists(?)"; 
		String sql2 = "{CALL registerUser(?, ?)}";
		try (Connection conn = DriverManager.getConnection(db, user, pwd);
			CallableStatement stmt = conn.prepareCall(sql);
			CallableStatement stmt2 = conn.prepareCall(sql2);) {
//			System.out.println("hi12");
			stmt.setString(1, userName);
//			System.out.println("hi22");
			ResultSet rs = stmt.executeQuery();
//			//TESTING DEBUGGING
//			System.out.println("hi2");
//			while(rs.next()) {
//				System.out.println(rs.getInt(1));
//			}
			
			rs.next();
			//returns: username exists -> 1; the username does not exist -> 0;
			//if the username already exists
			if(rs.getInt(1) == 1) {
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
