package CSCI201FinalProject;

import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class TestSQLfunc {
	public static void main(String[] args) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		//too long >20 char
		//String username = "Bob Smith ("+dtf.format(now)+")";
		String username = ""+dtf.format(now);
		
		System.out.println("Test time: "+dtf.format(now));
		System.out.println("Make sure that the DB is online by first running the DBscript.sql script in MySQL");
		System.out.println("========================");
		System.out.println("===SQLutil.java TESTS===");
		System.out.println("========================\n");
		
		SQLutil.loginDB();
		
		System.out.println("===Valid Register===");
		String output = SQLutil.registerUser(username, "password");
		
		if(output.equals("T")) System.out.println("SUCCESS");
		else System.out.println("FAIL");
		System.out.println("Output: "+ output);
		System.out.println();
		
		
		
		System.out.println("===Invalid Register===");
		output = SQLutil.registerUser(username, "password");
		
		if(output.equals("F")) System.out.println("SUCCESS");
		else System.out.println("FAIL");
		System.out.println("Output: "+ output);
		System.out.println();
		
		
		
		System.out.println("===Valid Login===");
		output = SQLutil.loginUser(username, "password");
		
		if(output.equals(username)) System.out.println("SUCCESS");
		else System.out.println("FAIL");
		System.out.println("Output: "+ output);
		System.out.println();
		
				
		
		System.out.println("===Invalid Password Login===");
		output = SQLutil.loginUser(username, "incorrect password");
		
		if(output == null) System.out.println("SUCCESS");
		else System.out.println("FAIL");
		System.out.println("Output: "+ output);
		System.out.println();
		
		
		
		System.out.println("===Invalid username Login===");
		output = SQLutil.loginUser("invalid "+username, "password");
		
		if(output == null) System.out.println("SUCCESS");
		else System.out.println("FAIL");
		System.out.println("Output: "+ output);
		System.out.println();
		
		
//		User user=new User(null);
//		user.startConnection("127.0.0.1", 7777);
//		System.out.println(user.sendMessage("owo"));
//		user.stopConnection();
	}
}
