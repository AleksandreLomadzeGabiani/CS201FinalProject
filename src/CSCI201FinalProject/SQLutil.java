package CSCI201FinalProject;

import java.util.ArrayList;
import java.util.List;

public class SQLutil {
	//CALLED EXCLUSIVELY BY SERVER
	
	public static List<Post> updatePosts() {
		//TODO: return list of posts from DB to server
		return new ArrayList<Post>();
	}
	
	public static boolean makePost(Post post) {
		//TODO: add post to the database
		return false;
	}
	
	public static String loginUser(String userName, String password) {
		//TODO: return userName if user is registered and password matches / else null
		return null;
	}
	
	public static String registerUser(String userName, String password) {
		//TODO: return "T"/"F" if you were able to register user
		return "F";
	}
}
