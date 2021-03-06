package Library;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;
public class Library {
	private String[] categorys= {"IT","Arts", "Business", "Comics", "Cooking", "Sports"};
	private Book head;
	private int Librarysize;
	/** Initalize the library ,set the linkedlist to null and librarysize to 0**/
	public Library() {
		head = null;
		Librarysize = 0;
	}
	public boolean isEmpty() {
		return this.Librarysize==0;
	}
	/** when the Library, go back to the userInterface*/
	public void Empty_back() {
		if(isEmpty()) {
			System.out.println("There are no books in the library!\n");
			System.out.println("========================================");
			userInterface();
		}
	}
	/** the node in the Linkedlist **/
	private class Book {//set the node as a inner class, to hide from the user
		private String bookTitle,bookauthor,category,ISBN;
		private int copyNumber,copyAvailable;
		private Book Next;//point to the next node
		private Book(String bookTitle,String bookauthor, String category, String ISBN,String copyNumber,int CopyAvailable, Book Next) {
			this.bookTitle=bookTitle;
			this.bookauthor=bookauthor;
			this.category = category;
			this.ISBN = ISBN;
			this.copyNumber= Integer.parseInt(copyNumber);
			this.copyAvailable=CopyAvailable;
			this.Next = Next;
		}
		private Book(String bookTitle,String bookauthor, String category, String ISBN,String copyNumber,int CopyAvailable) {
			this(bookTitle,bookauthor, category, ISBN,copyNumber,CopyAvailable,null);
		}
		@Override
		public String toString() {
			return ISBN + " \t/" + bookTitle + " \t/" + bookauthor + " \t/"+ category+ " \t/" +  copyNumber + " \t/" + copyAvailable;
		}
	}
	/** Here is the userInterface, index page**/
	public void userInterface() {
		System.out.println("Welcome to the library management system, functions provided include the following:\n"
							+"'Add/add' to add a new book\n" 
							+"'Update/update' to update book info\n" 
							+ "'Search/update' to enquire about book info\n"  
							+ "'Delete/delete' to delete a book\n"  
							+ "'Display/display' to display book(s) info\n"  
							+ "'Quit/quit' to exit the System");
		System.out.println("Enter your command here:");
		String input  = new Scanner(System.in).nextLine().trim().toLowerCase();
		switch(input) {
		case "add": addBook();break;
		case "update" :updateBook();break;
		case "search" :searchBook();break;
		case "delete": deleteBook();break;
		case "display": displayBook();break;
		case "quit":System.out.println("You have successfully logged out the System"); System.exit(0);
		default:System.out.println("your command is illegeal, please input the command listed above\n");userInterface();
		}
		}
	/** add book into Library **/
	private void addBook() {
		int copyAvailable=0;
		String ISBN,author,category,copyNumber,title;
		System.out.println("Enter a new book ISBN,the ISBN is 10 or 13 digit number or input 'quit' to quit");
		ISBN = validateISBN();
		System.out.println("Enter the title : only English letters and spacing allowed, or input 'quit' to quit");
		title=validateAuthorTitle();
		System.out.println("Enter the author : only English letters and spacing allowed, or input 'quit' to quit");
		author=validateAuthorTitle();
		if(validateBook(ISBN,title,author)) userInterface();
		System.out.println("Please input the category from the list: [IT , Arts, Business, Comics, Cooking, Sports], or input 'quit' to quit");
		category= validateCategory();
		System.out.println("Enter total copy number, or input 'quit' to quit");
		copyNumber= validateCopyNumber();
		copyAvailable = Integer.parseInt(copyNumber);
		System.out.println("Ready to add book: " + ISBN + ";  " + title+ ";  " + author + ";  " + category + ";  " + Integer.parseInt(copyNumber) + ";  " + copyAvailable);
		System.out.println("Enter 'Y/y' to add new book. Anything else to quit");
		String confirm = new Scanner(System.in).nextLine().trim().toLowerCase();
		if(!confirm.equals("y")) {
			System.out.println("quit to userInterface");
			userInterface();
		}
		else {
			Book book = new Book(title,author,category,ISBN,copyNumber, copyAvailable);// pass to the inner class to creat node
			book.Next = head;//make the node's next node point at the head of the LinkedList
			head = book;//update the head node to the current node
			Librarysize++;//maintain the length of the Linkedlist
			System.out.println("New book added successfully.");
			userInterface();
		}
	}
	/** update book **/
	private void updateBook() {
		Empty_back();
		System.out.println("Please input the ISBN or enter 'quit' to quit");
		String ISBN = validateISBN();
		String userinput;
		Book cur = head;
		boolean isFound = false;
		for(int i=0; i<Librarysize ; i++) {
			if(cur.ISBN .equals(ISBN)) {
				isFound = true;
				System.out.println(cur);
				break;
			}
			cur = cur.Next;
		}
		if(!isFound){
			System.out.println("Sorry the book you want to update does not exist in the Library. Now we will go back to the userInterface\n");
			userInterface();
		}
		System.out.println("Enter type of information you want to update, 'T' for title, 'A' for author, 'C' for\n" + 
				"category, 'TC' for total copy number, 'AC' for available number, anything else to quit");
		userinput = new Scanner(System.in).nextLine();
		switch(userinput.trim().toUpperCase()) {
		case "T" : System.out.println("Please input the title you want to change or 'quit' to quit ");
		String newTitle = validateAuthorTitle();
		if(validateBook(null,newTitle,cur.bookauthor)) {// pass the new book author and the title user input to check whether this book's information has conflict with another book in the Library
			System.out.println("the title you update has been confict with another same book\n");
			userInterface();
		};
		cur.bookTitle = newTitle;
		break;
		case "A":  System.out.println("Plese input the author you want to change or 'quit' to quit");
		String newAuthor = validateAuthorTitle();
		if(validateBook(null,cur.bookTitle,newAuthor)) {// pass the new book title and the author user input to check whether this book's information has conflict with another book in the Library
			System.out.println("the author you update has been confict with another same book\n");
			userInterface();
		};
		cur.bookauthor = newAuthor;
		break;
		case "C":  System.out.println("Please input the category from the list :[IT,Arts, Business, Comics, Cooking, Sports], or enter 'quit' to quit");
		String newCategory = validateCategory();
		cur.category = newCategory;
		break;
		case "TC": System.out.println("Please input the total number you want to change,quit to quit");
		String newTotalNumber = validateCopyNumber();
		while(true) {// prevent the situation in which the new TotalNumber < Available number of book in the laborary
			try{
			if(newTotalNumber.trim().toLowerCase().equals("quit")){
				System.out.println("back to the user Interface\n");
				userInterface();
			}
			else if(Integer.parseInt(newTotalNumber)<0) throw new Exception();
			else if(Integer.parseInt(newTotalNumber)<cur.copyAvailable) throw new ArithmeticException();
			else break;
			}
			catch(ArithmeticException e) {
				System.out.println("the copy number your input is less than the copy available, please check and input again, or input 'quit' to go back to user Interface");
				newTotalNumber = new Scanner(System.in).nextLine();
			}
			catch(Exception e) {
				System.out.println("input illegal, please input a postive number or input 'quit' to go back to the user Interface" );
				newTotalNumber = new Scanner(System.in).nextLine();
			}
		}
		cur.copyNumber = Integer.parseInt(newTotalNumber);
		break;
		case "AC": System.out.println("Please input the available number you want to change,quit to quit");
		String newAvailableNumber;
		while(true){
			try{
			newAvailableNumber= new Scanner(System.in).nextLine().trim().toLowerCase();
			if(newAvailableNumber.toLowerCase().trim().equals("quit")) userInterface();
			else if(Integer.parseInt(newAvailableNumber)>cur.copyNumber) throw new ArithmeticException();
			else if(Integer.parseInt(newAvailableNumber)<0) throw new IllegalArgumentException();
			else break;
			}
			catch(ArithmeticException e) {
				System.out.println("Available number can't be more than total number!");
				System.out.println("Please input a valuable number again, or input 'quit' to userInterface");
			}
			catch(IllegalArgumentException e){
				System.out.println("Please input a positive integer!!");
				System.out.println("Please input a valuable number again, or input 'quit' to userInterface");
			}
			catch(Exception e) {
				System.out.println("Please input a number");
				System.out.println("Please input a valuable number again, or input 'quit' to userInterface");
			}
		}
		cur.copyAvailable = Integer.parseInt(newAvailableNumber);
		break;
		default:System.out.println("quit the updating successfully"); userInterface();
		break;
		}
		System.out.println("Information update sucessfully!\n");
		userInterface();
	}
	/** delete book from library **/
	private void deleteBook() {
		Empty_back();
		System.out.println("Enter the book's ISBN or 'title + author':");
		String keyword = new Scanner(System.in).nextLine().trim().toLowerCase();
		Book cur = head;
		for(int i=0;i<Librarysize;i++) {
			/**two situations : 
			 * 1. The book is at the head of the Linkedlist 
			 * 2. The book is not at the head of the Linkedlist
			 */
			try {
			if(keyword.equals(head.bookTitle.trim().toLowerCase()+" + "+head.bookauthor.trim().toLowerCase())||keyword.equals(head.ISBN)) {
				if(head.copyAvailable!=head.copyNumber) {
					System.out.println("Sorry this book cannot be deleted. There are " + (head.copyNumber-head.copyAvailable) +" copies have been lent out");
					userInterface();
				}
				head = cur.Next;
				cur = null;
				Librarysize--;//after delete a book maintain the library size ;
				System.out.println("Delete successfully!\n");
				userInterface();
			}
			else if(keyword.equals(cur.Next.bookTitle.trim().toLowerCase()+" + "+cur.Next.bookauthor.trim().toLowerCase())||keyword.equals(cur.Next.ISBN)) {
				if(cur.Next.copyAvailable!=cur.Next.copyNumber) {
					System.out.println("Sorry this book cannot be deleted. There are " + (cur.Next.copyNumber-cur.Next.copyAvailable) +" copies have been lent out");
					userInterface();
				}
				Book DelBook = cur.Next;
				cur= DelBook.Next==null?null:DelBook.Next;
				DelBook = null;
				Librarysize--;//after delete a book maintain the library size ;
				System.out.println("Delete successfully!\n");
				userInterface();
				}
			cur = cur.Next;
		}catch(Exception e) {
			System.out.println("Sorry, the system does not find the book you search for\n now it will go back to userInterface\n");
			userInterface();
		}
			}
	}
	/** search book **/
	private void searchBook() {
		Empty_back();
		System.out.println("Enter 'Y/y' to search books, anything else to quit");
		String keywords,userinput;
		userinput = new Scanner(System.in).nextLine().toLowerCase();
		if(!userinput.trim().toLowerCase().equals("y")) userInterface();
		System.out.println("Please input the keywords of the books: author or bookname or category");
		keywords =validateAuthorTitle().toLowerCase().trim();
		Book cur = head;//travesal from the head
		boolean isFindbook = false;ArrayList<Book> books = new ArrayList<Book>();
		for(int i=0;i<Librarysize;i++) {
			if(cur.bookauthor.toLowerCase().contains(keywords)||cur.bookTitle.toLowerCase().contains(keywords)||cur.category.toLowerCase().contains(keywords)) {
				books.add(cur);
				isFindbook = true;
			}
			cur = cur.Next;//assign the next node to the current node	
		}
		if(isFindbook) {
			books.sort(new Comparator<Book> () {
				@Override
				public int compare(Book a, Book b) {
					return b.copyNumber - a.copyNumber;
				}
			});
			for (Book book : books) System.out.println(book);
		}
		else if(!isFindbook) System.out.println("No books found");
		System.out.println("==========================");
		System.out.println("Enter 'Y/y' to search other books, anything else to quit");
		userinput = new Scanner(System.in).nextLine().toLowerCase().trim();
		if(userinput.equals("y")) searchBook();
		else userInterface();
		}
	/** display book, traversal the library and print out the books exist in library **/
	private void displayBook() {
		Empty_back();
		System.out.println("Please choose the books' information display by:"
				+ "\nA:Author\nC:by Category, \nanything else to quit to Userinterface");
		String userinput = new Scanner(System.in).nextLine().toLowerCase().trim();
		switch(userinput) {
		case "c":displayBook("c");System.out.println("=======================");userInterface();break;
		case "a":displayBook("a");System.out.println("=======================");userInterface();break;
		default: System.out.println("=======================");userInterface();break;
		}
	}
	private void displayBook(String userchoice) {
		Book cur = head;
		System.out.println("Here are the books in the Library below");
		System.out.println("ISBN / TITLE/ AUTHOR/ CATEGORY/ TOTAL_COPIES/ AVAILABLE_COPIES");
		ArrayList<Book> sortBook = new ArrayList<Book>();
		for(int i=0; i<Librarysize; i++) {
			sortBook.add(cur);
			cur=cur.Next;
		}
		if(userchoice.equals("c")) sortBook.sort(new Comparator<Book>(){
			@Override
			public int compare(Book a, Book b) {
				return b.category.compareTo(a.category);
			}
		});	
		else if(userchoice.equals("a")) sortBook.sort(new Comparator<Book>(){
			@Override
			public int compare(Book a, Book b) {
				return a.bookauthor.compareTo(b.bookauthor);
			}
		});
		for(Book book : sortBook) System.out.println(book);
	}
	/** validate book's ISBN,title, author, to make sure there is no repeating book or the same book in library **/
	private boolean validateBook(String ISBN, String title, String author){
		if(isEmpty()) return false;
		Book cur = head;
		for (int i=0; i<Librarysize;i++) {
			if(cur.ISBN.equals(ISBN)) {
				System.out.println("this book's ISBN has already exists!");
				return true;
			}
			if(cur.bookTitle.equals(title)&&cur.bookauthor.equals(author)) {
				System.out.println("this book has already exists!");
				return true;
			}
			cur = cur.Next;
		}
		return false;
	}
	/** validate the author and title input by the user ,to make sure that consists only English letters and spacing**/
	private String validateAuthorTitle() {
		Pattern ptn=Pattern.compile("[a-zA-Z\\s]+");
		String input;
		input = new Scanner(System.in).nextLine();
		while(true){
			if(input.trim().toLowerCase().equals("quit")) userInterface();
			else if(!input.matches(ptn.pattern())) {
				System.out.println("input illegal, only English words and spacing allowed,  or input 'quit' to exit");
				input  = new Scanner(System.in).nextLine();
			}
			else break;
		}
		return input;
	}
	/** validate ISBN input by user, to make sure ISBN of 10 or 13 digits**/
	private String validateISBN() {
		String ISBN = new Scanner(System.in).nextLine();
		Pattern ptn = Pattern.compile("([0-9]{10})|([0-9]{13})");
		while(true){
			if(ISBN.trim().toLowerCase().equals("quit")) userInterface();
			else if(!ISBN.matches(ptn.pattern())) {
				System.out.println("ISBN input illegal, please input 10 or 13 digits numbers or input 'quit' to exit");
				ISBN = new Scanner(System.in).nextLine();
			}
			else break;
		}
		return ISBN;
	}
	/** validate Category , to make sure the category input by the user exists in the library **/
	private String validateCategory() {
		String category;
		while(true){
			try{
			category= new Scanner(System.in).nextLine();
			boolean categoryExist = false;
			for(String cat : categorys) {
				if(cat.trim().toLowerCase().equals("quit")) userInterface();
				if(cat.equals(category)) {
					categoryExist = true;
					break;
				}
			}
			if(categoryExist) break;
			else throw new Exception();
			}
			catch(Exception e) {
				System.out.println("Please input the category from the list :[IT,Arts, Business, Comics, Cooking, Sports], or enter 'quit' to quit");
			}
		}
		return category;
	}
	/** validate the copy number, to make sure it is a positive number **/
	private String validateCopyNumber() {
		String copyNumber;
		while(true){
			try{
			copyNumber= new Scanner(System.in).nextLine();
			if(copyNumber.equals("quit")) userInterface();
			else if(Integer.parseInt(copyNumber)<=0) throw new Exception();
			else break;
			}
			catch(Exception e) {
				System.out.println("Please input a number between 1 to 2147483647 or enter 'quit' to quit");
			}
		}
		return copyNumber;
	}
}
