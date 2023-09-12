import java.util.Scanner;

/**
 * 
 */

/**
 * @author diana
 *
 */
public class A0_Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in); //instantiate
		
		System.out.println("Welcome to the bookstore inventory system.\n\n"
				+ "Please enter the maximum number of books your bookstore can contain: ");
		
		int maxBooks = verifyInt(keyboard);
		
		Book.setMaxNumBooks(maxBooks);
		
		Book[] inventory = new Book[maxBooks];
		
		mainMenu();
		
		int input = 0;
		int counter = 0;
		int counter02 = 0;
		
		while (!(input == 5)) {
			System.out.println("Please enter your choice from 1 to 5: ");
			input = verifyInput(keyboard);
			//Option 1
			if (input == 1) {	
				counter = verifyPassword(keyboard, counter);
				System.out.println("How many books would you like to enter?");
				int numBooks = verifyInt(keyboard);
				int totalBooks = 0;
				
				//Sort new books into inventory array
				if ((Book.getMaxNumBooks()- Book.getTotalNumBooks() >= numBooks )){
					totalBooks = Book.getTotalNumBooks();
					
					for (int i = 0; i < numBooks; i++) {
						inventory[i + totalBooks] = new Book(); //parameters?
					}
					
					System.out.println("Books have been added successfully");
				}
				//Checks the amount of space left in the inventory and prompts user accordingly  
				else if (Book.getMaxNumBooks() - Book.getTotalNumBooks() < numBooks) {
					//No more inventory space
					if (Book.getMaxNumBooks() - Book.getTotalNumBooks() == 0) {
						System.out.println("There is no more inventory space for new books"); //would you like to remove one?
					}
					//Informs user of remaining space and prompts user for input
					else {
						System.out.println("There are only " + (Book.getMaxNumBooks() - Book.getTotalNumBooks())
								+ " spots left in your inventory. Would you like to add " + (Book.getMaxNumBooks() - Book.getTotalNumBooks()) 
								+ " books?\n");
						
						System.out.println("   1.   Yes\n   2.   No");
						int s = verifyInt(keyboard);
						while (!(s == 2)) {
							if (s == 1) {
								for (int i = 0; i < (Book.getMaxNumBooks() - Book.getTotalNumBooks()); i++) {
									inventory[i + totalBooks] = new Book(); //parameters?
								}
								System.out.println("Books have been added successfully");
								break;
							}
							//Input validation
							else {
								System.out.println("Please choose one of the available options");
								System.out.println("   1.   Yes\n   2.   No");
								s = verifyInt(keyboard);
							}
						}
						System.out.println("Returning to main menu...\n");
						mainMenu();
					}
				}
			}
			//Option 2
			else if (input == 2) {
				counter = verifyPassword(keyboard, counter02);
				int option = 0;
				
				do {
					System.out.println("Which book number do you wish to update?");//what if you choose last book? index.
					int bookNum = verifyInt(keyboard);
					
					if (inventory[bookNum] == null) {
						System.out.println("There is no book associated with this book number. " //what if input is bigger than maxbooks?
								+ "What would you like to do?\n"
								+ "   1.   Enter new book number\n"
								+ "   2.   Return to main menu");
						option = verifyInt(keyboard);
						if (option == 2) {
							break;
						}
						
						while (!(option == 1) && !(option == 2) ) {
							System.out.println("Please choose one of the available options");
							option = verifyInt(keyboard);
						}
					}
					else {
						System.out.println("Book # " + bookNum);
						System.out.println(inventory[bookNum].toString());
						int choice = 0;
						do {
							System.out.println("\nWhat information would you like to change?\n" //still need to change the things
									+ "   1.   author\n"
									+ "   2.   title\n"
									+ "   3.   ISBN\n"
									+ "   4.   price\n"
									+ "   5.   back to book selection\n" //there is an issue here somewhere
									+ "Enter your choice from 1 to 5: ");
							choice = verifyInt(keyboard);
						}
						while (!(choice == 5));	
					}
				}
				
				while (!(option == 2));
				
				System.out.println("Returning to main menu...\n");
				mainMenu();
				
			}
			//Option 3
			else if (input == 3) {
				System.out.println("Enter an author name: "); //Case sensitive?
				keyboard.nextLine();
				String author = keyboard.nextLine();
				Book.findBooksBy(inventory, author); //defs has an issue with null stuff
				System.out.println("\nReturning to main menu...\n");
				mainMenu();
			}
			//Option 4
			else if (input == 4) {
				System.out.println("Enter a maximum value: ");
				double price = 0;
				while (!keyboard.hasNextDouble()) {
					System.out.println("Invalid input. Please enter a number: ");
					keyboard.next();
				}
				
				price = keyboard.nextInt();
				
				Book.findCheaperThan(inventory, price);
				System.out.println("Returning to main menu...\n");
				mainMenu();
			}
		}
		
		System.out.println("Thank you for using the Bookstore Inventory Program. The program will now terminate.");
		System.exit(0);	
	}
	//Displays main menu
	public static void mainMenu() {
		System.out.println("What do you want to do?\n   "
				+ "1.   Enter new books (password required)\n   "
				+ "2.   Change information of a book (password required)\n   "
				+ "3.   Display all books by a specific author\n   "
				+ "4.   Display all books under a certain price\n   "
				+ "5.   Quit\n");
	}
	//Verifies that the user's input is an integer
	public static int verifyInt(Scanner keyboard) {
		int input = 0;

		while (!keyboard.hasNextInt()) {
			System.out.println("Invalid input. Please enter an integer: ");
			keyboard.next();
		}
		
		input = keyboard.nextInt();

		return input;
	}
	//Verifies that the user chooses one of the offered options
	public static int verifyInput(Scanner keyboard) {
		
		int input = verifyInt(keyboard);
		
		if (input < 1 || input > 5) {
			System.out.println("Invalid input.");
		}
		
		return input;
	}
	//Verifies password, and closes the program if the wrong password has been entered 12 times in a row
	public static int verifyPassword(Scanner keyboard, int counter) {
		
		String password = "249";
		String s;
		
		for (int i = 0; i < 3; i++) {
			System.out.println("Enter your password: ");
			s = keyboard.next();
			
			if (s.equals(password)) {
				System.out.println("Thank you for your input. ");	
				break;
			}
			else {
				counter++;
				if(i == 2) {
					if (counter == 12) {
						System.out.println("Program detected suspicious activities and will terminate immediately!");
						System.exit(0);
					}
					
					else {
						System.out.println("Too many attempts used. Redirecting to main menu...\n");
						A0_Driver.mainMenu();
					}
				} 
				else {
					System.out.println("Incorrect password. Try again.");
				}
			}
		}
		return counter;
	}
	
	
}
