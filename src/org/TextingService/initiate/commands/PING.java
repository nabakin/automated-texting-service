package org.TextingService.initiate.commands;

import org.TextingService.initiate.DataMethods;

public class PING {

	public static void PING(){
		DataMethods.print("Pong");
	}
	
	public static String getDescription(){
		return "Checks the server and returns pong.";
	}
	
	public static String getUsage(){
		return "ping";
	}

}
