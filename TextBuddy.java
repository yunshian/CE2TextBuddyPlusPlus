import java.util.*;
import java.io.*;

/**
 * @author Toh Yun Shian
 * 
 * TextBuddy is a program where user can conduct the following commands: add, delete, clear and display text.
 * All texts will be stored into an arraylist called allTexts.
 * The file will be saved to the disk after each user operation. 
 * 
 * The command format is given by the example interaction below:
		Welcome to TextBuddy. mytextfile.txt is ready for use
		command: add little brown fox
		added to mytextfile.txt: "little brown fox"
		command: display
		1. little brown fox
		command: add jumped over the moon
		added to mytextfile.txt: "jumped over the moon"
		command: display
		1. little brown fox
		2. jumped over the moon
		command: delete 2
		deleted from mytextfile.txt: "jumped over the moon"
		command: display
		1. little brown fox
		command: clear
		all content deleted from mytextfile.txt
		command: display
		mytextfile.txt is empty
		command: exit
		
 * Program Assumption
 	1. Invalid Commands -- Program will prompt user : "Please enter a valid command"
	  
	2. Invalid Command Format -- Program will prompt user : "Please enter a valid format for this command"
	   Eg 'add' instead of 'add hello world'.
 	  
	3. Index Out of Range -- Attempt to delete a text with an index of less than 0 or greater than the
 	   list size will be prompted with "The index to be deleted is out of the range".
 	  
	4. Command Letter Casing -- Program will be able to accept commands in any letter case: capital, small and mixed.
 	   Eg. ADD, add, Add
 */

public class TextBuddy {
	
	// Error Messages
	private static final String ERROR_NO_ARGUMENTS = "No arguments.";
	private static final String ERROR_MORE_THAN_ONE_ARGUMENTS = "You should only have one argument.";
	private static final String ERROR_INDEX_OUT_OF_RANGE= "The index to be deleted is out of the range.";
	private static final String ERROR_INVALID_COMMAND = "Please enter a valid command.";
	private static final String ERROR_INVALID_FORMAT = "Please enter a valid format for this command.";
	
	// Messages for user
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %s is ready for use.";
	private static final String MESSAGE_ADD = "added to %s: \"%s\".";
	private static final String MESSAGE_DELETE = "deleted from %s: \"%s\".";
	private static final String MESSAGE_CLEAR = "all content deleted from %s.";
	private static final String MESSAGE_EMPTY = "%s is empty.";

	// Commands
	private static final String ADD = "add";
	private static final String DISPLAY = "display";
	private static final String DELETE = "delete";
	private static final String CLEAR = "clear";
	private static final String EXIT = "exit";
	
	private static Scanner scanner = new Scanner(System.in);
	private static ArrayList <String> allTexts = new ArrayList<String>();
	private static String command;
	public static String filename;
	
	public static void main(String[] args)throws Exception {
		exitIfIncorrectArguments(args);
		filename = args[0];
		readFile(filename);
		printWelcomeMessage(args[0]);
		readCommandUntilExit();
	}
	
	// Dealing with incorrect number of arguments when running the program
	private static void exitIfIncorrectArguments(String[] args){
		if(args.length == 0) {
			exitWithErrorMessage(ERROR_NO_ARGUMENTS);
		} else if(args.length > 1) {
			exitWithErrorMessage(ERROR_MORE_THAN_ONE_ARGUMENTS);
		}
	}
	
	private static void exitWithErrorMessage(String errorMessage){
		System.out.println(errorMessage);
		System.exit(0);
	}
	
	// Read the contents of the file and store into allTexts arraylist before the user enters any commands
	public static void readFile(String filename)throws Exception {
		File file = new File (filename);
		if(!file.exists()){
			file.createNewFile();
		}
		
		Scanner fileScanner = new Scanner (file);
		while (fileScanner.hasNextLine()){
			allTexts.add(fileScanner.nextLine());
		}
		
		fileScanner.close();
	}
	
	// Write the texts stored in allTexts arraylist into the file
	private static void writeFile(String filename)throws Exception {
		File file = new File (filename);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter outputWriter = new OutputStreamWriter(fos);
		BufferedWriter bufferWriter = new BufferedWriter(outputWriter);
		
		for(int i = 0; i < allTexts.size(); i++) {
			int index = i + 1;
			bufferWriter.write(index + ". " + allTexts.get(i));
			bufferWriter.newLine();
		}
		
		bufferWriter.close();
	}
	
	private static void printWelcomeMessage(String filename){
		System.out.println("Welcome to TextBuddy. " + filename + " is ready for use");
	}
	
	/*
	 * Execution of all commands are done within the allTexts arraylist
	 * After each command user has entered, contents from allTexts arraylist 
	 * will then be written into the file.   
	 */
	private static void readCommandUntilExit()throws Exception{	
		
		while (true){
			String input = readCommand();
			String output = executeCommand(input);
			printMessage(output);
			writeFile(filename);
		}
	}
	
	private static String readCommand(){
		System.out.print("command: ");
		return scanner.nextLine();
	}
	
	public static String executeCommand(String input){
		String displayMessage = null;
		command = getFirstWord(input);
		
		if(command.equalsIgnoreCase(ADD)){
			displayMessage = addText(removeFirstWord(input));
		} else if(command.equalsIgnoreCase(DISPLAY)){
			displayMessage = displayText();
		} else if(command.equalsIgnoreCase(DELETE)){
			displayMessage = deleteText(removeFirstWord(input));
		} else if(command.equalsIgnoreCase(CLEAR)){
			displayMessage = clearText();
		} else if(command.equalsIgnoreCase(EXIT)){
			System.exit(0);
		} else {
			displayMessage = ERROR_INVALID_COMMAND;
		}
		
		return displayMessage;
	}
	 
	private static String removeFirstWord(String userInput) {
		String withoutFirstWord = userInput.replace(getFirstWord(userInput), "").trim();
		return withoutFirstWord;
	}

	private static String getFirstWord(String userInput) {
		String firstWord = userInput.trim().split("\\s+")[0];
		return firstWord;
	}
	
	public static String addText(String text){
		 text = text.trim();
		 String addResult = null;
		 
		if(text.equals("")) {
			addResult = ERROR_INVALID_FORMAT;
		} else {
			allTexts.add(text);
			addResult = String.format(MESSAGE_ADD, filename, text);
		}
		
		return addResult;
	}
	
	public static String displayText(){
		String displayResult = null;
		
		if(allTexts.size() == 0){
			displayResult = String.format(MESSAGE_EMPTY, filename);
		} else {
			displayResult = storeAllTexts();
		}
		
		return displayResult;
	}
	
	private static String storeAllTexts(){
		String mergeTexts = "";
		
		for(int i = 0; i < allTexts.size(); i++){
			int index = i + 1;
			
			if(i == allTexts.size() - 1){
				mergeTexts = mergeTexts + index + ". " + allTexts.get(i);
			}
			else {
				mergeTexts = mergeTexts + index + ". " + allTexts.get(i)+ "\n";
			}
		}
		
		return mergeTexts;
	}
	
	public static String deleteText(String text){
		try {
			String deleteResult = null;
			int index = Integer.parseInt(text);
			index = index - 1;
		
			if(allTexts.size() == 0) {
				deleteResult = String.format(MESSAGE_EMPTY, filename);
			} else if(index >= allTexts.size() || index < 0){
				deleteResult = ERROR_INDEX_OUT_OF_RANGE;
			} else {
				text = allTexts.get(index);
				allTexts.remove(index);
				deleteResult = String.format(MESSAGE_DELETE, filename, text);
			}
		
			return deleteResult;
			
		} catch (InputMismatchException e){
			return ERROR_INVALID_FORMAT;
		}
	}
	
	public static String clearText(){
		allTexts.clear();
		String clearResult = String.format(MESSAGE_CLEAR, filename);
		return clearResult;
	}
	
	private static void printMessage(String message){
		System.out.println(message);
	}
}