package CSCI201FinalProject;

public class User extends Guest implements Runnable{
	String userName;
	
	public User(String userName) {
		super();
		this.userName=userName;
	}

	public boolean addPost(Post post) {
		// TODO: Write a function that adds post by the user
		return false;
	}
	
	@Override
	public void run() {
		// TODO: Write function to let logged in user use functionality during runtime
	}
	
}
