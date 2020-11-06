package CSCI201FinalProject;

import java.util.Date;

public class Post {
	private String userName;
	private Date date;
	private String title;
	private String post;
	private String paymentLink;
	
	// Initialize Post Object
	public Post(String userName, Date date, String title, String post, String paymentLink) {
		this.userName=userName;
		this.date=date;
		this.title=title;
		this.post=post;
		this.paymentLink=paymentLink;
	}
	
	/* Returns the string representation that 
	 * the Post will appear as in the UI */
	@Override
	public String toString(){
		StringBuffer str= new StringBuffer();
        
		str.append("----------------------------");
		str.append(System.lineSeparator());
		
        str.append("\"" + title + "\"" +"   -"+userName);
        str.append(System.lineSeparator());
        
        str.append("----------------------------");
        str.append(System.lineSeparator());
        
        str.append(post);
        str.append(System.lineSeparator()+System.lineSeparator());
        
        
        str.append("Donate: " +paymentLink);
        str.append(System.lineSeparator());
        
        str.append("*Posted: "+date.toString());
        str.append(System.lineSeparator());
        
        str.append("----------------------------\n");
        
		return str.toString();
	}
}
