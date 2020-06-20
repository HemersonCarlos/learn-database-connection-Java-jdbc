package connectionJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ConnectionFactory {

	public static void main(String[] args) {
		
		// Criação do login no SQL Server
		// login dbadm
		// senha : 1234
		
		String databaseConnectionUrl = "jdbc:sqlserver://localhost;databaseName=register_person;";
		String user = "dbadm";
		String password = "1234";
		DatabaseAccess database = new DatabaseAccess();
		
		String selectWithJoinIntoDatabase = "SELECT DISTINCT Person.personId, Person.personName, Person.personAge, emailId, personEmail "
										  + "FROM Person JOIN EmailRegistration "
				                          + "ON EmailRegistration.emailId = Person.personId";
		
		// ------------------------------------------------
		System.out.println("**Started connection with database!**");
		System.out.println("**(DATABASE) - Connected to Microsoft SQL Server!");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Insert Person
		// ------------------------------------------------	
		System.out.println("--------------------------------");
		Person createInsertPersonOne = new Person( 1, "Carlos", 28 );
		System.out.println("**Inserting person 1: " + createInsertPersonOne.toString());
		database.insertPerson(createInsertPersonOne);
		System.out.println("--------------------------------");
		System.out.println("\n");
		
		Person createInsertPersonTwo = new Person(2, "Marlon", 27);
		System.out.println("**Inserting person 2: " + createInsertPersonTwo.toString());
		database.insertPerson(createInsertPersonTwo);
		System.out.println("--------------------------------");
		System.out.println("\n");
		
		Person createInsertPersonThree = new Person(3, "Fabricio", 30);
		System.out.println("**Inserting person 2: " + createInsertPersonThree.toString());
		database.insertPerson(createInsertPersonThree);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select Person
		// ------------------------------------------------
		System.out.println("**People registered in the database!**");
		System.out.println("--------------------------------");
		List<Person> people = database.selectAllPeople();
		printingPeople(people);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Update Person
		// ------------------------------------------------
		System.out.println("**People update in the database!**");
		System.out.println("--------------------------------");
		createInsertPersonOne.setPersonName("Flavio");
		createInsertPersonOne.setPersonAge(30);
		database.updatePerson(createInsertPersonOne);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Delete Person
		// ------------------------------------------------
		System.out.println("**People delete in the database!**");
		System.out.println("--------------------------------");
		System.out.println("Excluding person number 3!");
		database.deletePerson(3);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select Person
		// ------------------------------------------------
		System.out.println("**People registered in the database!**");
		System.out.println("--------------------------------");
		printingPeople(people);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ##################################################
		
		// ------------------------------------------------
		// Intert E-mail Person
		// ------------------------------------------------
		System.out.println("--------------------------------");
		RegisteredPersonEmail registeredEmailOne = new RegisteredPersonEmail( 1, "carlos123@hotmail.com" );
		System.out.println("**Inserting e-mail 1: " + registeredEmailOne.toString());
		database.insertEmailPerson(registeredEmailOne);
		System.out.println("--------------------------------");
		System.out.println("\n");
		
		System.out.println("--------------------------------");
		RegisteredPersonEmail registeredEmailTwo = new RegisteredPersonEmail( 2, "marlon123@hotmail.com" );
		System.out.println("**Inserting e-mail 2: " + registeredEmailTwo.toString());
		database.insertEmailPerson(registeredEmailTwo);
		System.out.println("--------------------------------");
		System.out.println("\n");
		
		System.out.println("--------------------------------");
		RegisteredPersonEmail registeredEmailThree = new RegisteredPersonEmail( 3, "fabricio123@hotmail.com" );
		System.out.println("**Inserting e-mail 3: " + registeredEmailThree.toString());
		database.insertEmailPerson(registeredEmailThree);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select E-mail Person
		// -------------------------------------------------
		System.out.println("**E-mail registered in the database!**");
		System.out.println("--------------------------------");
		List<RegisteredPersonEmail> emails = database.selectAllEmails();
		printingEmail(emails);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Update E-mail Person
		// ------------------------------------------------
		System.out.println("**E-mail update in the database!**");
		System.out.println("--------------------------------");
		registeredEmailOne.setPersonEmail("flavio123@hotmail.com");
		database.updateEmailPerson(registeredEmailOne);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Delete E-mail Person
		// ------------------------------------------------
		System.out.println("**E-mails delete in the database!**");
		System.out.println("--------------------------------");
		System.out.println("Excluding e-mail number 3!");
		database.deleteEmailPerson(3);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select E-mail Person
		// ------------------------------------------------		
		System.out.println("**E-mail registered in the database!**");
		System.out.println("--------------------------------");
		printingEmail(emails);
		System.out.println("--------------------------------");
		System.out.println("\n");
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select with join in table Person and EmailRegistration
		// ------------------------------------------------	
		try( Connection connectionForSelectDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password); 
		     Statement statementSelectDatabase = connectionForSelectDatabase.createStatement(); 
		   ) {
				
			ResultSet resultSelectDatabase = statementSelectDatabase.executeQuery(selectWithJoinIntoDatabase);
			
			while( resultSelectDatabase.next() ) {
				
				RegisteredPersonEmail createSelectEmailPerson = new RegisteredPersonEmail();
				createSelectEmailPerson.setEmailId(resultSelectDatabase.getInt("emailId"));
				createSelectEmailPerson.setPersonEmail(resultSelectDatabase.getString("personEmail"));
				
				Person createSelectPerson = new Person();
				createSelectPerson.setPersonId(resultSelectDatabase.getInt("personId"));
				createSelectPerson.setPersonName(resultSelectDatabase.getString("personName"));
				createSelectPerson.setPersonAge(resultSelectDatabase.getInt("personAge"));
				
				System.out.println("--------------------------------");
				System.out.println("( E-MAIL ) - Emails registered into database:");
				System.out.println(createSelectEmailPerson);
				System.out.println(createSelectPerson);
				System.out.println("--------------------------------");
				System.out.println("\n");
			}
		} catch( SQLException databaseSelectError ) {
			System.out.print("( E-MAIL ) - Oops, There is an error to select in database: ");
			databaseSelectError.printStackTrace();
		}
		// ------------------------------------------------
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
}