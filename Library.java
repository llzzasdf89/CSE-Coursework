package Library;

import java.util.Scanner;
import java.util.regex.*;
public class Library {
	String[] categorys= {"IT","Arts", "Business", "Comics", "Cooking", "Sports"};
	private class Book {
		private String bookTitle,bookauthor,category;
		private int ISBN,copyNumber,copyAvailable;
		private Book Next;
	}
	
	public void userInterface() {
		System.out.println("Welcome to the library management system, functions provided include the following:"
				+ "Add 每 to add a new book\r\n" + 
				"Update 每 to update book info\r\n" + 
				"Search 每 to enquire about book info\r\n" + 
				"Delete 每 to delete a book\r\n" + 
				"Display每 to display book(s) info\r\n" + 
				"Quit 每 to exit from the current level of interactions");
		System.out.println("Enter your command here:");
		Scanner kb = new Scanner(System.in);
		String input = kb.next();
		switch(input) {
		case "add": addBook();
		break;
		case "update" : updateBook();
		break;
		case "search" : searchBook();
		break;
		case "delete": deleteBook();
		break;
		case "display": displayBook();
		break;
		case "quit": System.exit(0);
		default:
			System.out.println("your command is illegeal, please input the command listed above");
			userInterface();
		}
		}
	
	private void addBook() {
		int copyAvailable=0;
		String ISBN,author,category,copyNumber,title;
		System.out.println("Enter a new book ISBN:");
		Scanner kb = new Scanner(System.in);
		Pattern ptn = Pattern.compile("[0-9]{10,13}");
		ISBN=kb.next();
		while(true){
			if(ISBN.trim().toLowerCase()=="quit") userInterface();
			else if(!ISBN.matches(ptn.pattern())) {
				System.out.println("ISBN input illegal, please input 10-13 digits numbers or input 'quit' to exit");
				Scanner newkb = new Scanner(System.in);
				ISBN = newkb.next();
			}
			else break;
		}
		System.out.println("Enter the title:");
		kb= new Scanner(System.in);
		title = kb.next();
		ptn=Pattern.compile("[a-zA-Z\\s]+");
		while(true){
			if(title.trim().toLowerCase()=="quit") userInterface();
			else if(!title.matches(ptn.pattern())) {
				System.out.println("ISBN input illegal, please input English names or input 'quit' to exit");
				Scanner newkb = new Scanner(System.in);
				author = newkb.next();
			}
			else break;
		}
		System.out.println("Enter the author:");
		kb= new Scanner(System.in);
		author = kb.next();
		ptn=Pattern.compile("[a-zA-Z\\s]+");
		while(true){
			if(author.trim().toLowerCase()=="quit") userInterface();
			else if(!author.matches(ptn.pattern())) {
				System.out.println("ISBN input illegal, please input English names or input 'quit' to exit");
				Scanner newkb = new Scanner(System.in);
				author = newkb.next();
			}
			else break;
		}
		System.out.println("Please input the category from these: IT , Arts, Business, Comics, Cooking, Sports");
		while(true){
			try{
			kb= new Scanner(System.in);
			category= kb.next();
			boolean Iscategory = false;
			for(String cat : categorys) {
				if(cat.trim().toLowerCase().equals("quit")) userInterface();
				if(cat.equals(category)) {
					Iscategory = true;
					break;
				}
			}
			if(Iscategory) break;
			else throw new Exception();
			}
			catch(Exception e) {
				System.out.println("Please input one of the categories or input 'quit' to quit");
			}
		}
		
		System.out.println("Enter total copy number:");
		while(true){
			try{
			kb= new Scanner(System.in);
			copyNumber= kb.next();
			if(copyNumber.equals("quit")) userInterface();
			else if(Integer.valueOf(copyNumber)<=0) throw new Exception();
			else break;
			}
			catch(Exception e) {
				System.out.println("Please input a postive number, enter 'quit' to quit");
			}
		}
		copyAvailable = Integer.parseInt(copyNumber);
		System.out.println("Ready to add book: " + ISBN + ";" + title+ ";" + author + ";" + category + ";" + copyNumber + ";" + copyAvailable);
		System.out.println("Enter 'Y' to add new book. Anything else to quit");
		kb = new Scanner(System.in);
		String confirm = kb.next();
		if(!confirm.equals("Y")) userInterface();
		else {
			System.out.println("New book added successfully.");
			userInterface();
		}
	}
	private void updateBook() {
		
	}
	private void deleteBook() {
		
	}
	private void searchBook() {
		
	}
	private void displayBook() {
		
	}
	private void Validate() {
		
	}
}
