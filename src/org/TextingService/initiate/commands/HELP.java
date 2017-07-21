package org.TextingService.initiate.commands;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;

import org.TextingService.initiate.DataMethods;
import org.TextingService.initiate.MainIndexSearch;

public class HELP {
	
	public static String helpArg;
	private static int messageArgsLength;
	
	public static void HELP() throws ServletException, IOException, ClassNotFoundException {
		messageArgsLength = DataMethods.getMessageArgsNumber();
		if(messageArgsLength > 1){
			System.out.println(messageArgsLength);
			DataMethods.print("Too many aguments!");
		} else if(messageArgsLength==1){
			helpArg = DataMethods.getMessageArgs()[0];
			if(!DataMethods.isCommand(helpArg)){
				DataMethods.print("This command does not exist.");
			} else {
				HELP.getInfo(helpArg);
			}
		} else {
			int commandListLength = DataMethods.getCommandListLength();
			Object[] commandList = DataMethods.getCommandList();
			String tempCommand;
			while(!(commandListLength==0)){
				commandListLength--;
				tempCommand = (String) commandList[commandListLength];
				HELP.getInfo(tempCommand);
			}
		}
	}
	
	public static String getDescription(){
		return "The help command.";
	}
	
	public static String getUsage(){
		return "help [command]";
	}
	
	
	public static void getInfo(String command){
		
		command = command.toUpperCase();
		
		if(DataMethods.isCommand(command)){
			MainIndexSearch info = new MainIndexSearch();
			
			Method usage = null;
			try {
				usage = MainIndexSearch.call(command, "getUsage");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				System.out.println("  There is no getUsage method for command " + command);
			}
			
			Method description = null;
			try {
				description = MainIndexSearch.call(command, "getDescription");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				System.out.println("  There is no getDescription method for command " + command);
			}
			if(usage==null||description==null){
				System.out.println("  The command has not been listed in this help command\n  do to the lack of description, and or usage of the command " + command);
			} else {
				try {
					DataMethods.print(command + "\nUsage: " + usage.invoke( info ) + "\nDescription: " + description.invoke( info ) + "\n");
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					System.out.println("  Usage and Description methods did not invoke properly for command " + command);
				}
			}
		}
	}

}
