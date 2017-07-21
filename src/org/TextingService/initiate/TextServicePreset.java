package org.TextingService.initiate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//This is where the process starts and the message and phone variables are passed

public class TextServicePreset extends HttpServlet {
	
	private String msg2;
	private String numb2;
	private String query2;
	public static HttpServletRequest req;
    public static HttpServletResponse resp;
    
    //gets the variables needed from the Web server
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.req = req;
		this.resp = resp;
		
		//Just the query (the end of the web URL)
		query2 = req.getQueryString();
		//Gets the phonenumber and puts it into variable
		numb2 = req.getParameter("PhoneNumber");
		//Gets the message and puts it into variable
		msg2 = req.getParameter("Message");
		
		//Replaces those things for when you press return and goes to a new line from the msg
		msg2 = msg2.replaceAll("\\n", "");
		//Replaces something, I forgot
		msg2 = msg2.replaceAll("\\r", "");
		//Only make these commands again when you are using a texting service that includes a
		//unnessisary first word.
//		msg2 = msg2.replaceAll(msg2.substring(0, msg2.indexOf(" ")), "");
//		msg2 = msg2.substring(1);
		
//		try {
//		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
//		    Date date = new Date();
//		    out.println(date.toString() + ":\n  PhoneNumber: " + numb2 + "\n  Message: " + msg2);
//		    out.close();
//		} catch (IOException e) {
//		    //oh noes!
//		}
		
		//Setting the msg, phone number, and query into a class to store them
		//DataMethods is just a class with various methods contained inside that I use for different things
		DataMethods.setMessage(msg2);
		DataMethods.setPhoneNumber(numb2);
		DataMethods.setQueryString(query2);
		
		//Just prints out some data to the console
		System.out.println("  From:  " + DataMethods.getPhoneNumber() + "\n  Message:  " + DataMethods.getMessage());
		System.out.println("  Query Feed:  " + DataMethods.getQueryString());
		
		//trys to start a method that searches through the list of commands(classes)
		try {
			MainIndexSearch.searchCommands();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException e) {
			e.printStackTrace();
		}

	}
	
	//classes I forgot what they are for
	public static HttpServletRequest getServletRequest(){
		return req;
	}
	
	public static HttpServletResponse getServletResponse(){
		return resp;
	}
	
}
