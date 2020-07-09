package connectionJDBC;

import java.util.List;

import dataAccessObject.EmailDataAccessObject;
import dataAccessObject.PersonDataAccessObject;
import model.Department;
import model.Person;
import model.RegisteredPersonEmail;
import userInterface.MainTextMenu;

public class ConnectionFactory {

	public static void main(String[] args) {
		
		PersonDataAccessObject person = new PersonDataAccessObject();
		EmailDataAccessObject email = new EmailDataAccessObject();
		
		
		// ------------------------------------------------
		System.out.println("**Started connection with database!**");
		System.out.println("**(DATABASE) - Connected to Microsoft SQL Server!");
		// ------------------------------------------------
		
		/*
		 * // ------------------------------------------------ // Insert Person //
		 * ------------------------------------------------
		 * System.out.println("--------------------------------"); Person
		 * createInsertPersonOne = new Person( 1, "Carlos", 28 );
		 * System.out.println("**Inserting person 1: " +
		 * createInsertPersonOne.toString()); person.insert(createInsertPersonOne);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n");
		 * 
		 * Person createInsertPersonTwo = new Person(2, "Marlon", 27);
		 * System.out.println("**Inserting person 2: " +
		 * createInsertPersonTwo.toString()); person.insert(createInsertPersonTwo);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n");
		 * 
		 * Person createInsertPersonThree = new Person(3, "Fabricio", 30);
		 * System.out.println("**Inserting person 2: " +
		 * createInsertPersonThree.toString()); person.insert(createInsertPersonThree);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Select Person //
		 * ------------------------------------------------
		 * System.out.println("**People registered in the database!**");
		 * System.out.println("--------------------------------"); List<Person> people =
		 * person.selectAll(); printingPeople(people);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Update Person //
		 * ------------------------------------------------
		 * System.out.println("**People update in the database!**");
		 * System.out.println("--------------------------------");
		 * createInsertPersonOne.setPersonName("Flavio");
		 * createInsertPersonOne.setPersonAge(30); person.update(createInsertPersonOne);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Delete Person //
		 * ------------------------------------------------
		 * System.out.println("**People delete in the database!**");
		 * System.out.println("--------------------------------");
		 * System.out.println("Excluding person number 3!"); person.delete(3);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Select Person //
		 * ------------------------------------------------
		 * System.out.println("**People registered in the database!**");
		 * System.out.println("--------------------------------");
		 * printingPeople(people);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ##################################################
		 * 
		 * // ------------------------------------------------ // Intert E-mail Person
		 * // ------------------------------------------------
		 * System.out.println("--------------------------------"); RegisteredPersonEmail
		 * registeredEmailOne = new RegisteredPersonEmail( 1, "carlos123@hotmail.com" );
		 * System.out.println("**Inserting e-mail 1: " + registeredEmailOne.toString());
		 * email.insert(registeredEmailOne);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n");
		 * 
		 * System.out.println("--------------------------------"); RegisteredPersonEmail
		 * registeredEmailTwo = new RegisteredPersonEmail( 2, "marlon123@hotmail.com" );
		 * System.out.println("**Inserting e-mail 2: " + registeredEmailTwo.toString());
		 * email.insert(registeredEmailTwo);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n");
		 * 
		 * System.out.println("--------------------------------"); RegisteredPersonEmail
		 * registeredEmailThree = new RegisteredPersonEmail( 3,
		 * "fabricio123@hotmail.com" ); System.out.println("**Inserting e-mail 3: " +
		 * registeredEmailThree.toString()); email.insert(registeredEmailThree);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Select E-mail Person
		 * // -------------------------------------------------
		 * System.out.println("**E-mail registered in the database!**");
		 * System.out.println("--------------------------------");
		 * List<RegisteredPersonEmail> emails = email.selectAll();
		 * printingEmail(emails);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Update E-mail Person
		 * // ------------------------------------------------
		 * System.out.println("**E-mail update in the database!**");
		 * System.out.println("--------------------------------");
		 * registeredEmailOne.setPersonEmail("flavio123@hotmail.com");
		 * email.update(registeredEmailOne);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Delete E-mail Person
		 * // ------------------------------------------------
		 * System.out.println("**E-mails delete in the database!**");
		 * System.out.println("--------------------------------");
		 * System.out.println("Excluding e-mail number 3!"); email.delete(3);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 * 
		 * // ------------------------------------------------ // Select E-mail Person
		 * // ------------------------------------------------
		 * System.out.println("**E-mail registered in the database!**");
		 * System.out.println("--------------------------------");
		 * printingEmail(emails);
		 * System.out.println("--------------------------------");
		 * System.out.println("\n"); // ------------------------------------------------
		 */	
		
		MainTextMenu mainMenu = new MainTextMenu();
		mainMenu.execute();
	}
	
	// ------------------------------------------------
	private static void printingPeople( List<Person> informedPeople ) {
		System.out.println("List the people: ");
		for (Person person : informedPeople) {
			System.out.println(person);
		}
	}
	
	private static void printingEmail( List<RegisteredPersonEmail> informedEmail ) {
		System.out.println("List the e-mails: ");
		for (RegisteredPersonEmail email : informedEmail) {
			System.out.println(email);
		}
	}
	
	private static void printingDepartment( List<Department> informedDepartment ) {
		System.out.println("List the department: ");
		for(Department department : informedDepartment ) {
			System.out.println(department);
		}
	}
}