package userInterface;

import java.util.Scanner;

public class MainTextMenu {

	private static final int PEOPLE = 1;
//	private static final int DEPARTMENT = 2;
	
	private static final int ADD = 1;
	private static final int LIST = 2;
	private static final int EDIT = 3;
	private static final int DELETE = 4;
	
	private enum Status {MAIN, PEOPLE, DEPARTMENT};
	
	private Status currentStatus;
	private Scanner inputScanner;
	
	public MainTextMenu() {
		currentStatus = Status.MAIN;
		inputScanner = new Scanner(System.in); 
	}
	
	private void printsMainMenu() {
		System.out.println("1 - People Administration");
		System.out.println("2 - Department Administration");
	}
	
	private void printsSecondaryMenu(String informedTypeMenu) {
		System.out.println( informedTypeMenu + " Administration");
		System.out.println();
		System.out.println("1 - Add");
		System.out.println("2 - List");
		System.out.println("3 - Edit");
		System.out.println("4 - Delete");
	}
	
	public void execute() {
		int option;
		SpecificTextMenu specificTextMenu;
		
		do {
			System.out.println("Human Resources(RH) Administration");
			System.out.println();
			
			switch(currentStatus) {
			case PEOPLE:
				printsSecondaryMenu("People");
				break;
			case DEPARTMENT:
				printsSecondaryMenu("Department");
				break;
			default:
				printsMainMenu();
			}
			
			System.out.println();
			System.out.println("0 - Exit");
			System.out.println();
			System.out.print("Choose an option: ");
	
			option = inputScanner.nextInt();
			inputScanner.nextLine();
			
			System.out.println("You chose the option: " + option);
				
			if (currentStatus == Status.MAIN) {
				switch (option) {
				case PEOPLE:
					currentStatus = Status.PEOPLE;
					break;
				}
			} else {
				specificTextMenu = new PersonTextMenu();

				switch (option) {
					case ADD:
						specificTextMenu.add();
						break;
					case EDIT:
						specificTextMenu.edit();
						break;
					case DELETE:
						specificTextMenu.delete();
						break;
					case LIST:
						specificTextMenu.listAll();
						break;
					default:
						System.out.println("Invalid option. Try again!");
				}
			}
		} while (option != 0);
		
	}
	
}