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
	//TODO: where the user connects to the server socket
	
	//TODO: Make runnable so that it supports multi-thredding
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
		User user=new User("a");
		user.startConnection("127.0.0.1", 7777);
		Scanner sc=new Scanner(System.in);
		String command="";
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
					
				}else if ("r".equals(command)) {
					//TODO: implement register
					
				}else if ("p".equals(command)) {
					if(user.userName!=null) {
						//TODO: implement post creation by user
						
						user.out.println("p");
						user.objOut.writeObject(new Post("test",new Date(),"yo boi", "Donate that $$$ moneyz I need to repair my ship ARRG", "www.crackhouse.com"));
						System.out.println(user.in.readLine());
					}else {
						System.out.println("Guests cannot post. Please log in or register.");
					}
				}else if ("q".equals(command)) {
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
