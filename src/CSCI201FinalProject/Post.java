package CSCI201FinalProject;

import java.util.Date;
import java.io.Serializable;

public class Post implements Serializable {
	private static final long serialVersionUID = 5950169519310163575L;
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
	
	public String getTitle() {
		return title;
	}
	
	public String getUsername() {
		return userName;
	}
	
	public String getPaylink() {
		return paymentLink;
	}
	
	public String getPost() {
		return post;
	}
	
	 public boolean equals(Object o) {
		 if (this == o) return true;
		 if (o == null || getClass() != o.getClass()) return false;

		 Post post = (Post) o;

		 if (date != post.date) return false;
		 if (date != null ? !date.equals(post.date) : post.date != null) return false;

		 return true;
		 }

		 public int hashCode() {
			 return 0;
		 }
	
	public boolean isNull() {
		if(userName==null && title==null && post==null && paymentLink==null &&date==null) {
			return true;
		}else {
			return false;
		}
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
