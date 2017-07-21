package org.TextingService.initiate.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import org.TextingService.initiate.DataMethods;



public class IP {

	public static void IP() throws ServletException, IOException {
		
		String ip = DataMethods.getIP();
		String[] args = DataMethods.getMessageArgs();
		DataMethods.print(ip);
		
	}
	
	public static String getDescription(){
		return "Gets the texting host's IP.";
	}
	
	public static String getUsage(){
		return "ip";
	}
	
}
