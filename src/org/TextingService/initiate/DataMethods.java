package org.TextingService.initiate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class DataMethods extends HttpServlet {
	
	private static String message;
	private static String messageArgsString;
	private static int messageArgsNumber;
	private static int classesArrayLength;
	private static String messageCommand;
	private static String phoneNumber;
	private static String queryString;
	private static HttpServletRequest resp;
	private static HttpServletRequest req;
	private static String ip;
	
	private static String[] messageArgs;
	private static List commandListList = new ArrayList();
	private static Object[] commandList;
	private static Class[] commandArray;
	
	//This method sets the message for every other method to use
	//and does a few other things
	public static void setMessage(String msg){
		message = msg;
		//figuring out if there is a command in the msg
		//spacePos is the position of the first space in the msg
		int spacePos = message.indexOf(" ");
		if (spacePos > 0){
			//gets the text after first space for arguments
			messageArgsString = message.substring(spacePos);
			//replacing unnessisary space
			messageArgsString = messageArgsString.replaceFirst(" ", "");
			//spliting the string for each argument
			messageArgs = messageArgsString.split("\\s+");
			//number of args
			StringTokenizer st = new StringTokenizer(messageArgsString);
			messageArgsNumber = st.countTokens();
			//getting the command in the msg
			messageCommand = message.substring(0, spacePos);
		} else {
			//if theres no space then that means that there is only a command, nothing else
			messageCommand = message;
			messageArgsNumber = 0;
			messageArgsString = null;
			messageArgs = null;
		}
	}

	//returns whole msg
	public static String getMessage(){
		return message;
	}
	
	//returns msg command if there is one
	public static String getMessageCommand(){
		return messageCommand;
	}
	
	//returns number of args
	public static int getMessageArgsNumber(){
		return messageArgsNumber;
	}
	
	//returns an array of the args
	public static String[] getMessageArgs(){
		return messageArgs;
	}
	
	//sets the phonenumber for all of the methods to use
	public static void setPhoneNumber(String numb){
		phoneNumber = numb;
	}
	
	//returns the phonenumber
	public static String getPhoneNumber(){
		return phoneNumber;
	}
	
	//sets the query string to be used through all of the methods
	public static void setQueryString(String query){
		queryString = query;
	}
	
	//returns the query string
	public static String getQueryString(){
		return queryString;
	}
	
	//prints to the user
	public static void print(String string){
		//trys to do command
		try {
			//this method is for the server to print to user
			TextServicePreset.getServletResponse().getWriter().println(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns the ip of the user who contacted the server
	public static String getIP(){
		//server gets ip var
		ip = TextServicePreset.getServletRequest().getRemoteAddr();
		return ip;
	}
	
	//checks if a command is or is not a command
	public static boolean isCommand(String command){
		boolean commandBool = true;
		boolean isCommandBool = false;
		//smaller version of MainIndexSearch
		int classesArrayLength = commandList.length;
		
		while(!(classesArrayLength==0)||(commandBool)){
			classesArrayLength--;
			String tempCommand = commandList[classesArrayLength].toString();
			
			if (command.equalsIgnoreCase(tempCommand)){
				isCommandBool = true;
				commandBool = false;
			}
			
		}
		return isCommandBool;
	}
	
	//returns the command list in an array
	public static Object[] getCommandList() throws ClassNotFoundException, IOException{
		//smaller version of MainIndexSearch
		classesArrayLength = commandArray.length;
		int classesArrayLengthThis = classesArrayLength;
		while(!(classesArrayLengthThis==0)){
			classesArrayLengthThis--;
			commandListList.add(commandArray[classesArrayLengthThis].toString().replaceAll("class org.TextingService.initiate.commands.", ""));
		}
		
		commandList = commandListList.toArray();
		
		return commandList;
	}
	
	//sets the command array for all of the methods
	public static void setCommandArray(Class[] commandArrayLocal){
		commandArray = commandArrayLocal;
	}
	
	//returns the command array
	public static Class[] getCommandArray(){
		return commandArray;
	}
	
	//returns the length of the command array
	public static int getCommandListLength(){
		int classesLength = commandArray.length;
		return classesLength;
	}
	
}
