package CSCI201FinalProject;

import java.util.Date;

public class TestRunner {

	public static void main(String[] args) {
		Post test1=new Post("test",new Date(),"yo boi", "Donate that $$$ moneyz I need to repair my ship ARRG", "www.crackhouse.com");
		System.out.println(test1.toString());
		
		User user=new User(null);
		user.startConnection("127.0.0.1", 7777);
		System.out.println(user.sendMessage("owo"));
		user.stopConnection();
	}
}
