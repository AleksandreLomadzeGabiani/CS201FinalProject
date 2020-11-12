package CSCI201FinalProject;

/* 
* big credit for helping figure out socket networking:
* https://www.geeksforgeeks.org/socket-programming-in-java/
*
*/

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Scanner;

public class User {
	//Where user connects to the server Socket
	
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private String userName; //IF userName==null := user is a guest

    public User(String userName) {
    	this.userName=userName;
    }
    
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objOut=new ObjectOutputStream(clientSocket.getOutputStream());
            objIn= new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }
    
	public void stopConnection() {
	     try {
	    	 in.close();
	         out.close();
	         clientSocket.close();
	         objOut.close();
	         objIn.close();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	}
	
	public static void main(String[] args) {
		User user=new User(null);
		user.startConnection("127.0.0.1", 7777);
		Scanner sc=new Scanner(System.in);
		String command="";
		System.out.println("Welcome to the website");
		System.out.println("These are the commands");
		System.out.println("r - register");
		System.out.println("l - login");
		System.out.println("n - next post");
		System.out.println("p - create post");
		System.out.println("u - update page");
		System.out.println("q - quit");
		while(!command.equals("q")){
			//TODO: Handle user interaction/ command sending
			command=sc.nextLine();
			
			try {
				if("n".equals(command)) {
					user.out.println("n");
					Post in=(Post) user.objIn.readObject();
					if(!in.isNull()) {
						System.out.println((Post) in);
					}else {
						System.out.println("No next posts. Try updating to see if there are new posts!");
					}

				}else if ("l".equals(command)){
					//TODO: implement login
					user.out.println("l");
					
					String tempUserName;
					String tempPassword;
					
					System.out.println("Login");
					
					System.out.println("Enter username");
					tempUserName=sc.nextLine();
					
					System.out.println("Enter password");
					tempPassword=sc.nextLine();
					
					user.out.println(tempUserName);
					user.out.flush();
					user.out.println(tempPassword);
					user.out.flush();
					
					String responseMessage = user.in.readLine();
					if(responseMessage.equals(""))
					{
						System.out.println("Login failed, please try again");
                    }
					else
					{
						System.out.println("Succesfully logged in with username: "+responseMessage);
					}
					
					
				}else if ("r".equals(command)) {
					//TODO: implement register
					user.out.println("r");
					user.out.flush();
					
					String tempUserName;
					String tempPassword;
					
					System.out.println("Register");
					
					System.out.println("Enter username");
					tempUserName=sc.nextLine();
					
					System.out.println("Enter password");
					tempPassword=sc.nextLine();
					
					user.out.println(tempUserName);
					user.out.flush();
					user.out.println(tempPassword);
					user.out.flush();
					
					String responseMessage = user.in.readLine();
					if(responseMessage.equals(""))
					{
						System.out.println("Register failed, please try again");
                    }
					else
					{
						System.out.println("Succesfully registered with username: "+responseMessage);
					}
						
					
				}
				
				else if("u".equals(command))
				{
					user.out.println("u");
					user.out.flush();
				}
				
				else if ("p".equals(command)) {
					if(user.userName!=null) {
						//TODO: implement post creation by user
						user.out.println("p");
						user.out.flush();
						System.out.println("Creating new Post");
						Date currDate = new Date();
						String title;
						String postText;
						String link;
						
						System.out.println("=======Enter post name=======");
						title = sc.nextLine();
						System.out.println("=======Enter post description=======");
						postText = sc.nextLine();
						System.out.println("=======Enter link=======");
						link = sc.nextLine();
						
						
						//user.objOut.writeObject(new Post("test",new Date(),"yo boi", "Donate that $$$ moneyz I need to repair my ship ARRG", "www.crackhouse.com"));
						user.objOut.writeObject(new Post(user.userName,currDate,title,postText,link));
						System.out.println(user.in.readLine());
					}else {
						System.out.println("Guests cannot post. Please log in or register.");
					}
				}else if ("q".equals(command)) {
					System.out.println("Quitting application");
					break;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		user.stopConnection();
		sc.close();
	}
	
}
