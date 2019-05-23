/*
 * AppMenu.java is the visual user interface for the sorting app.
 */
import java.util.Scanner;

public class AppMenu {
	private DatabaseHelper dbHelper; //used to access database
	private Boolean continueInMenu; //used to store menu exit variable
	private Scanner scanner; //used to get user keyboard input
	
	public AppMenu() {
		continueInMenu = true;
		
		//****Initial Start Menu*******************************************************************************************
		
		System.out.println("Welcome to Video Sorting App!");
		
		//initial menu allows user to pick their database
		System.out.println("\nChoose a database to start.");
		System.out.println("0 : New Database");
		System.out.println("1 : Select a db file");
		System.out.println("\nRecently used databases...");
		System.out.println("2 : test.db");
		System.out.println("3 : main.db");
		
		//get input from user
		scanner = new Scanner(System.in);
		int option = scanner.nextInt(); // looking for an integer input from user
		loadDb(option);
		
		//***********************************************************************************************************
		
		while(continueInMenu) { //keep displaying main menu until exit is selected
			mainMenu(); //display main menu
		}
		
		
	}
	
	private void loadDb(int option) {
		String dbName;
		
		switch (option) {
			case 0: 
				System.out.println("Enter db name:");
				scanner = new Scanner(System.in);
				dbName = scanner.next();
				break;
			case 1: 
				System.out.println("To be programmed later....");
				dbName = "test";
				break;
			case 2: // if 'other' is selected, let user type in db name
				dbName = "test";
				break;
			case 3: // if 'other' is selected, let user type in db name
				dbName = "main";
				break;
			default: dbName = "test";
		}
		
		dbHelper = new DatabaseHelper(dbName); //initialize database
		dbHelper.loadData();
		
		System.out.println(dbName + " db loaded.");
	}
	
	private void mainMenu() {
		System.out.println("Select an option.");
		System.out.println("1 : Display database");
		System.out.println("0 : Exit"); //exits menu
		
		scanner = new Scanner(System.in);
		int selection = scanner.nextInt();
		switch (selection) {
			case 0: exitMenu();
				break;
			case 1: displayDb();
				break;
			default: System.out.println("Invalid option. Try again."); //if invalid option is entered, display error text
				break;
		}
	}
	
	private void displayDb() {
		System.out.println(dbHelper.appDb);
	}
	
	private void exitMenu() {
		continueInMenu = false;
		
		System.out.println("Exiting App.");
		dbHelper.close();
	}
}
