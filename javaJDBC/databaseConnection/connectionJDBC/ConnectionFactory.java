package connectionJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

	public static void main(String[] args) {
		
		// Criação do login no SQL Server
		// login dbadm
		// senha : 1234
		
		String databaseConnectionUrl = "jdbc:sqlserver://localhost;databaseName=register_person;";
		String user = "dbadm";
		String password = "1234";
		String insertPersonIntoDatabase = "INSERT INTO Person ( personId, personName, personAge ) VALUES ( ?, ?, ? )";
		String selectPersonIntoDatabase = "SELECT * FROM Person";
		String updatePersonIntoDatabase = "UPDATE Person SET personName = ?, personAge = ? WHERE personId = ?";
		String deletePersonIntoDatabase = "DELETE Person WHERE personId = ?";
		String insertEmailIntoDatabase = "INSERT INTO EmailRegistration ( emailId, personEmail ) VALUES ( ?, ? )";
		String selectEmailIntoDatabase = "SELECT * FROM EmailRegistration";
		String updateEmailIntoDatabese = "UPDATE EmailRegistration SET personEmail = ? WHERE emailId = ?";
		String deleteEmailIntoDatabese = "DELETE EmailRegistration WHERE emailId = ?";
		String selectWithJoinIntoDatabase = "SELECT DISTINCT Person.personId, Person.personName, Person.personAge, emailId, personEmail "
										  + "FROM Person JOIN EmailRegistration "
				                          + "ON EmailRegistration.emailId = Person.personId";
		
		// ------------------------------------------------
		
		try ( Connection connectionUrl = DriverManager.getConnection(databaseConnectionUrl, user, password) ) {

			System.out.println("(DATABASE) - Connected to Microsoft SQL Server!");
			System.out.println("\n");

		} catch (SQLException errorConnectionIntoDatabase) {
			System.out.print("(DATABASE) - Opss, There is an error to connected in database: ");
			errorConnectionIntoDatabase.printStackTrace();
		}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Intert Person
		// ------------------------------------------------
		
		try( Connection connectionForInsertDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password); 
			 PreparedStatement statementInsertDatabase = connectionForInsertDatabase.prepareStatement(insertPersonIntoDatabase);
		   ) {
			
			Person createInsertPersonOne = new Person( 1, "Carlos", 28 );
			
			statementInsertDatabase.setInt(1, createInsertPersonOne.getPersonId());
			statementInsertDatabase.setString(2, createInsertPersonOne.getPersonName());
			statementInsertDatabase.setInt(3, createInsertPersonOne.getPersonAge());
		
			statementInsertDatabase.executeUpdate();
			
			Person createInsertPersonTwo = new Person(2, "Marlon", 27);
			
			statementInsertDatabase.setInt(1, createInsertPersonTwo.getPersonId());
			statementInsertDatabase.setString(2, createInsertPersonTwo.getPersonName());
			statementInsertDatabase.setInt(3, createInsertPersonTwo.getPersonAge());
			
			statementInsertDatabase.executeUpdate();
			
			Person createInsertPersonThree = new Person(3, "Fabricio", 30);
			
			statementInsertDatabase.setInt(1, createInsertPersonThree.getPersonId());
			statementInsertDatabase.setString(2, createInsertPersonThree.getPersonName());
			statementInsertDatabase.setInt(3, createInsertPersonThree.getPersonAge());
			
			statementInsertDatabase.executeUpdate();
			
			System.out.println("--------------------------------");
			System.out.println("( PERSON ) - Inserting data into Microsoft SQL Server successfully!");
			System.out.println(createInsertPersonOne.toString());
			System.out.println(createInsertPersonTwo.toString());
			System.out.println(createInsertPersonThree.toString());
			System.out.println("--------------------------------");
			System.out.println("\n");
			
		} catch( SQLException databaseInsertError ){
			System.out.print("( PERSON ) - Opss, There is an error to insert in database: ");
			databaseInsertError.printStackTrace();
		}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select Person
		// ------------------------------------------------
		
		try( Connection connectionForSelectDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password); 
			 Statement statementSelectDatabase = connectionForSelectDatabase.createStatement(); 
		   ) {
			
			ResultSet resultSelectDatabase = statementSelectDatabase.executeQuery(selectPersonIntoDatabase);
			
			while( resultSelectDatabase.next() ) {
				
				Person createSelectPerson = new Person();
				createSelectPerson.setPersonId(resultSelectDatabase.getInt("personId"));
				createSelectPerson.setPersonName(resultSelectDatabase.getString("personName"));
				createSelectPerson.setPersonAge(resultSelectDatabase.getInt("personAge"));
				
				System.out.println("--------------------------------");
				System.out.println("( PERSON ) - People registered into database:");
				System.out.println(createSelectPerson);
				System.out.println("--------------------------------");
				System.out.println("\n");
			}
		} catch( SQLException databaseSelectError ) {
			System.out.print("( PERSON ) - Oops, There is an error to select in database: ");
			databaseSelectError.printStackTrace();
		}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Update Person
		// ------------------------------------------------
		
		try( Connection connectionForUpdateDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password);
			 PreparedStatement statementUpdateDatabase = connectionForUpdateDatabase.prepareStatement(updatePersonIntoDatabase);
		   ) {
			
			Person createUpdatePerson = new Person( 1, "Flavio", 23 );
			statementUpdateDatabase.setString(1, createUpdatePerson.getPersonName());
			statementUpdateDatabase.setInt(2, createUpdatePerson.getPersonAge());
			statementUpdateDatabase.setInt(3, createUpdatePerson.getPersonId());
			
			statementUpdateDatabase.executeUpdate();
			
			System.out.println("--------------------------------");
			System.out.println("( PERSON ) - Person successfully changed in the database:");
			System.out.println(createUpdatePerson.toString());
			System.out.println("--------------------------------");
			System.out.println("\n");
			
			
		} catch ( SQLException databaseUpdateError ) {
			System.out.print("( PERSON ) - Oops, There is an error to update in database: ");
			databaseUpdateError.printStackTrace();
		}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Delete Person
		// ------------------------------------------------
		
		try( Connection connectionForDeleteDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password);
			 PreparedStatement statementDeleteDatabase = connectionForDeleteDatabase.prepareStatement(deletePersonIntoDatabase);
		   ) {
			
			int InformedPersonIdForDelete = 3;
			
			statementDeleteDatabase.setInt(1, InformedPersonIdForDelete);
			statementDeleteDatabase.executeUpdate();
			
			System.out.println("( PERSON ) - Person successfully removed from the database!");
			
		} catch( SQLException databaseDeleteError ) {
			System.out.println("( PERSON ) - Oops, Ther is an error to delete in database: ");
			databaseDeleteError.printStackTrace();
		}
		
		// ------------------------------------------------
		// Select Person
		// ------------------------------------------------
		
		try( Connection connectionForSelectDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password); 
		     Statement statementSelectDatabase = connectionForSelectDatabase.createStatement(); 
		   ) {
				
				ResultSet resultSelectDatabase = statementSelectDatabase.executeQuery(selectPersonIntoDatabase);
				
				while( resultSelectDatabase.next() ) {
					
					Person createSelectPerson = new Person();
					createSelectPerson.setPersonId(resultSelectDatabase.getInt("personId"));
					createSelectPerson.setPersonName(resultSelectDatabase.getString("personName"));
					createSelectPerson.setPersonAge(resultSelectDatabase.getInt("personAge"));
					
					System.out.println("--------------------------------");
					System.out.println("( PERSON ) - People registered into database:");
					System.out.println(createSelectPerson);
					System.out.println("--------------------------------");
					System.out.println("\n");
				}
			} catch( SQLException databaseSelectError ) {
				System.out.print("( PERSON ) - Oops, There is an error to select in database: ");
				databaseSelectError.printStackTrace();
			}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Intert E-mail Person
		// ------------------------------------------------
		try( Connection connectionForInsertDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password); 
			 PreparedStatement statementInsertDatabase = connectionForInsertDatabase.prepareStatement(insertEmailIntoDatabase)
		   ) {
				
				RegisteredPersonEmail registeredEmailOne = new RegisteredPersonEmail( 1, "carlos123@hotmail.com" );
				
				statementInsertDatabase.setInt(1, registeredEmailOne.getEmailId());
				statementInsertDatabase.setString(2, registeredEmailOne.getPersonEmail());
			
				statementInsertDatabase.executeUpdate();
				
				RegisteredPersonEmail registeredEmailTwo = new RegisteredPersonEmail( 2, "marlon123@hotmail.com" );
				
				statementInsertDatabase.setInt(1, registeredEmailTwo.getEmailId());
				statementInsertDatabase.setString(2, registeredEmailTwo.getPersonEmail());
				
				statementInsertDatabase.executeUpdate();
				
				RegisteredPersonEmail registeredEmailThree = new RegisteredPersonEmail( 3, "fabricio123@hotmail.com" );
				
				statementInsertDatabase.setInt(1, registeredEmailThree.getEmailId());
				statementInsertDatabase.setString(2, registeredEmailThree.getPersonEmail());
				
				statementInsertDatabase.executeUpdate();
				
				System.out.println("--------------------------------");
				System.out.println("( E-MAIL ) - Inserting data into Microsoft SQL Server successfully!");
				System.out.println(registeredEmailOne.toString());
				System.out.println(registeredEmailTwo.toString());
				System.out.println(registeredEmailThree.toString());
				System.out.println("--------------------------------");
				System.out.println("\n");
				
			} catch( SQLException databaseInsertError ){
				System.out.print("( E-MAIL ) - Opss, There is an error to insert in database: ");
				databaseInsertError.printStackTrace();
			}
		
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select E-mail Person
		// ------------------------------------------------
				
		try( Connection connectionForSelectDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password); 
		     Statement statementSelectDatabase = connectionForSelectDatabase.createStatement(); 
		   ) {
				
				ResultSet resultSelectDatabase = statementSelectDatabase.executeQuery(selectEmailIntoDatabase);
				
				while( resultSelectDatabase.next() ) {
					
					RegisteredPersonEmail createSelectEmailPerson = new RegisteredPersonEmail();
					createSelectEmailPerson.setEmailId(resultSelectDatabase.getInt("emailId"));
					createSelectEmailPerson.setPersonEmail(resultSelectDatabase.getString("personEmail"));
					
					System.out.println("--------------------------------");
					System.out.println("( E-MAIL ) - Emails registered into database:");
					System.out.println(createSelectEmailPerson);
					System.out.println("--------------------------------");
					System.out.println("\n");
				}
			} catch( SQLException databaseSelectError ) {
				System.out.print("( E-MAIL ) - Oops, There is an error to select in database: ");
				databaseSelectError.printStackTrace();
			}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Update E-mail Person
		// ------------------------------------------------
		
		try( Connection connectionForUpdateDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password);
			 PreparedStatement statementUpdateDatabase = connectionForUpdateDatabase.prepareStatement(updateEmailIntoDatabese)
		   ) {
			
			RegisteredPersonEmail createUpdateEmailPerson = new RegisteredPersonEmail(1, "flavio123@hotmail.com");
			statementUpdateDatabase.setString(1, createUpdateEmailPerson.getPersonEmail());
			statementUpdateDatabase.setInt(2, createUpdateEmailPerson.getEmailId());
			
			statementUpdateDatabase.executeUpdate();
			
			System.out.println("--------------------------------");
			System.out.println("( E-MAIL ) - Person successfully changed in the database:");
			System.out.println(createUpdateEmailPerson.toString());
			System.out.println("--------------------------------");
			System.out.println("\n");
			
			
		} catch ( SQLException databaseUpdateError ) {
			System.out.print("( E-MAIL ) - Oops, There is an error to update in database: ");
			databaseUpdateError.printStackTrace();
		}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Delete Person
		// ------------------------------------------------
		
		try( Connection connectionForDeleteDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password);
			 PreparedStatement statementDeleteDatabase = connectionForDeleteDatabase.prepareStatement(deleteEmailIntoDatabese)
		   ) {
			
			int InformedEmailIdForDelete = 3;
			
			statementDeleteDatabase.setInt(1, InformedEmailIdForDelete);
			statementDeleteDatabase.executeUpdate();
			
			System.out.println("( E-MAIL ) - Person successfully removed from the database!");
			
		} catch( SQLException databaseDeleteError ) {
			System.out.println("( E-MAIL ) - Oops, Ther is an error to delete in database: ");
			databaseDeleteError.printStackTrace();
		}
		
		// ------------------------------------------------
		
		// ------------------------------------------------
		// Select E-mail Person
		// ------------------------------------------------
				
		try( Connection connectionForSelectDatabase = DriverManager.getConnection(databaseConnectionUrl, user, password); 
		     Statement statementSelectDatabase = connectionForSelectDatabase.createStatement(); 
		   ) {
				
				ResultSet resultSelectDatabase = statementSelectDatabase.executeQuery(selectEmailIntoDatabase);
				
				while( resultSelectDatabase.next() ) {
					
					RegisteredPersonEmail createSelectEmailPerson = new RegisteredPersonEmail();
					createSelectEmailPerson.setEmailId(resultSelectDatabase.getInt("emailId"));
					createSelectEmailPerson.setPersonEmail(resultSelectDatabase.getString("personEmail"));
					
					System.out.println("--------------------------------");
					System.out.println("( E-MAIL ) - Emails registered into database:");
					System.out.println(createSelectEmailPerson);
					System.out.println("--------------------------------");
					System.out.println("\n");
				}
			} catch( SQLException databaseSelectError ) {
				System.out.print("( E-MAIL ) - Oops, There is an error to select in database: ");
				databaseSelectError.printStackTrace();
			}
		
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
		
	}
}