package org.TextingService.initiate.commands;

import java.util.ArrayList;
import java.util.List;

import org.TextingService.initiate.DataMethods;

public class MATH {
	public static void MATH(){
		int argsLength = DataMethods.getMessageArgsNumber();
		if(argsLength < 1){
			DataMethods.print("Usage: " + MATH.getUsage());
		} else {
			List<Integer> integers = new ArrayList<Integer>();
			List<String> symbols = new ArrayList<String>();
			String integerCount = "";
			String message = DataMethods.getMessage();
			int spacePos = message.indexOf(" ");
			message = message.substring(spacePos);
			message = message.replaceFirst(" ", "");
			message = message.replaceAll(" ", "");
			for(int i=0; message.length() > i; i++){
				String character = message.substring(i, i+1);
				if((character.equals("+") || character.equals("-") || character.equals("/") || character.equals("*")) && (i == 0 || i == message.length())){
					DataMethods.print("Syntax Error 001: " + message);
				} else {
					if(character.equals("+") || character.equals("-") || character.equals("/") || character.equals("*")){
						if(!(integerCount.equals(""))){
							integers.add(MATH.getNumber(integerCount));
							integerCount = "";
							symbols.add(character);
						} else {
							DataMethods.print("Syntax Error 002: " + message);
						}
					} else if(character.equals("0")|| character.equals("1") || character.equals("2") || character.equals("3") || character.equals("4") || character.equals("5") || character.equals("6") || character.equals("7") || character.equals("8") || character.equals("9")){
						integerCount = integerCount + character;
						if(i == message.length()-1){
							integers.add(MATH.getNumber(integerCount));
						}
					} else {
						DataMethods.print("Syntax Error 003: " + message);
					}
				}
			}
			Object[] integersArray = integers.toArray();
			Object[] symbolsArray = symbols.toArray();
			float first = (int) integersArray[0];
			for(int init=0; symbolsArray.length > init; init++){
				int second;
				if(symbolsArray[init].equals("+")){
					second = (int) integersArray[init+1];
					first = first + second;
				} else if (symbolsArray[init].equals("-")){
					second = (int) integersArray[init+1];
					first = first - second;
				} else if (symbolsArray[init].equals("/")){
					second = (int) integersArray[init+1];
					first = first / second;
				} else if (symbolsArray[init].equals("*")){
					second = (int) integersArray[init+1];
					first = first * second;
				}
			}
			DataMethods.print("" + first);
		}
	}
	
	public static int getNumber(String symbol){
		int symbolInt = Integer.parseInt(symbol);
		return symbolInt;
	}
	
	public static String getDescription(){
		return "Enter some math and it will solve it for you.";
	}
	
	public static String getUsage(){
		return "math [math equation here]";
	}
}
