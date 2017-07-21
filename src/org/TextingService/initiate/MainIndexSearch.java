package org.TextingService.initiate;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.TextingService.initiate.commands.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.TextingService.initiate.commands.*;


//Class is only for searching through the list of commands and recognizing
//if the message is a command and if it matches one of the commands made
//then executing a method in the class that is called the same as the class
public class MainIndexSearch extends HttpServlet {
	
	private static String tempCommand;
	private static int classesArrayLength;
	private static Object[] classesArray;
	private static boolean commandBool = true;

	public static void searchCommands() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException{
		
		boolean isCommandDisabled = false;
		
		//This variable now contains all of the commands we have in an array by
		//taking the package specified and searching it for classes(each class is a command)
		//I found this method online, I don't even know how it works
		classesArray = MainIndexSearch.getClasses("org.TextingService.initiate.commands");
		
		//length of array(how many different vars stored)
		classesArrayLength = classesArray.length;
		
		//while there is still a command in the array to be searched and a
		//command hasn't been found it will continue indexing through all of the commands
		while(!(classesArrayLength==0)&&commandBool==true){
			
			//we have just started another loop so now we have one less command to go through
			classesArrayLength--;
			
			//takes the next command out of the array, makes it a string, and removes
			//unnessisary info from the command
			tempCommand = classesArray[classesArrayLength].toString().replaceAll("class org.TextingService.initiate.commands.", "");
			
			//saying that we are indexing this command to the console
			System.out.println("  Command Indexing:  " + tempCommand);
			
			//if the command in the message equals the command indexing and
			//we still haven't found the command yet
			if (DataMethods.getMessageCommand().equalsIgnoreCase("/" + tempCommand)&&commandBool==true){
				//instalizing itself, you don't need to do this you could instead just put
				//"MainIndexSearch" instead of "it"
				MainIndexSearch it = new MainIndexSearch();
				
				//checks to see if the command has a Usage method and command method
				//because I have made it so that if your command doesn't have one
				//it will be disabled. (for the help command to use)
				//I created this method, but if I try to break it down again I
				//will fail
				boolean usageExist = MainIndexSearch.check(tempCommand, "getUsage");
				boolean descriptionExist = MainIndexSearch.check(tempCommand, "getDescription");
				
				//if we have both methods
				if (usageExist==true&&descriptionExist==true){
				
					//saves the method inside the commands class that tells it what
					//to do to a variable to be invoked(to be run)
					//I made this method also but forgot
					Method someMethod = MainIndexSearch.call(tempCommand, tempCommand);
					
					//trying to run this command
					try {
						//invoking the command by this class
						someMethod.invoke(it);
					} catch (IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
					
					//prints to console that command was found and breaks the
					//loop so that we don't index through any more commands unnessisarily
					System.out.println("  Command Found:  " + tempCommand);
					commandBool = false;
				} else {
					//if we don't have the usage and description methods it prints that
					//the command has been ignored and confirms it with changing a var
					//to be used at a leter time
					System.out.println("  Command " + tempCommand + " has been ignored.");
					isCommandDisabled = true;
				}
			}
		}
		//this is now what to tell the user if something went wrong
		//or not to tell them anything at all if everything went right
		if (isCommandDisabled){
			//if the command was intentionally disabled it prints this text to the user
			DataMethods.print(DataMethods.getMessageCommand() + " does not have the required usage and/or description method, it has been disabled.");
		} else if (commandBool&&DataMethods.getMessageCommand().substring(0,1).equals("/")){
			//if the command was not a real command it prints this text to the user and the console
			DataMethods.print(DataMethods.getMessageCommand() + "  is not a valid command!\nType /help for a list of commands.");
			System.out.println("  Not a command:  " + DataMethods.getMessageCommand());
		} else if (commandBool){
			//if the command was a command it prints this text to the console
			DataMethods.print("  From:  " + DataMethods.getPhoneNumber() + "\n  Message:  " + DataMethods.getMessage());
			System.out.println("  Not a command:  " + DataMethods.getMessageCommand());
		}
		//resets the var for the next time the method is called
		commandBool = true;
		
		//just to make the console look nice
		System.out.println("\n");
		
	}
	
	public static Class[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
		
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    
	    ArrayList<Class> classes = new ArrayList<Class>();
	    
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    
	    DataMethods.setCommandArray(classes.toArray(new Class[classes.size()]));
	    
	    return classes.toArray(new Class[classes.size()]);
	    
	}
	
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		
	    List<Class> classes = new ArrayList<Class>();
	    
	    if (!directory.exists()) {
	        return classes;
	    }
	    
	    File[] files = directory.listFiles();
	    
	    for (File file : files) {
	    	
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	        
	    }
	    
	    return classes;
	    
	}
	
	public static Method call(String className, String methodName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Method tempMethod = null;
	    Object it2 = Class.forName("org.TextingService.initiate.commands." + className).newInstance();
	    try {
	    	tempMethod = it2.getClass().getMethod(methodName);
	    } catch (NoSuchMethodException e) {
	    	System.out.println("  Command " + className + " didn't have the " + methodName + " method.");
	    }
	    
	    return tempMethod;
	}
	
	public static boolean check(String className, String methodName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
	    Method tempMethod = null;
	    boolean isMethod = false;
	    Object it2 = Class.forName("org.TextingService.initiate.commands." + className).newInstance();
	    try {
	    	tempMethod = it2.getClass().getMethod(methodName);
	    	isMethod = true;
	    } catch (NoSuchMethodException e) {
	      System.out.println("  Command " + className + " doesn't have a " + methodName + " method.");
	    }
	    
	    if(!(tempMethod==null)&&!(tempMethod.getReturnType().toString().equals("class java.lang.String"))){
	    	isMethod = false;
	    	System.out.println("  Command " + className + " doesn't return any info for the " + methodName + " method.");
	    }
	    
	    return isMethod;
	}
	
}
