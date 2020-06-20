package connectionJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

	private final String DATABASE_CONNECTION_URL = "jdbc:sqlserver://localhost;databaseName=register_person;";
	private final String USER = "dbadm";
	private final String PASSWORD = "1234";
	private Connection connectionDatabase = null;
	
	
	// ------------------------------------------------
	// Connection into database
	// ------------------------------------------------
	private Connection getConnection() {
		
		try {
			
			if( connectionDatabase == null || connectionDatabase.isClosed() ) {
				connectionDatabase = DriverManager.getConnection(DATABASE_CONNECTION_URL, USER, PASSWORD);
			}
			
		} catch( SQLException errorConnectionIntoDatabase ) {
			throw new RuntimeException("(DATABASE) - Opss, There is an error to connected in database!");
		}
		return connectionDatabase;
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Insert Person
	// ------------------------------------------------
	public void insertPerson( Person informedPerson ) {
		Connection connectionDatabase = getConnection();
		PreparedStatement statementInsertDatabase = null;
		ResultSet resultInsertDatabase = null;
		
		try {
			String insertPersonIntoDatabase = "INSERT INTO Person ( personName, personAge ) VALUES ( ?, ? )";
			statementInsertDatabase = connectionDatabase.prepareStatement(insertPersonIntoDatabase, Statement.RETURN_GENERATED_KEYS);
			statementInsertDatabase.setString(1, informedPerson.getPersonName());
			statementInsertDatabase.setInt(2, informedPerson.getPersonAge());
			statementInsertDatabase.executeUpdate();
			resultInsertDatabase = statementInsertDatabase.getGeneratedKeys();
			
			if( resultInsertDatabase.next() ) {
				informedPerson.setPersonId(resultInsertDatabase.getInt(1));
			}
			
			System.out.println("( INSERT PERSON ) - Inserting data into Microsoft SQL Server successfully!");
			System.out.println("( INSERT PERSON ) - People registered into database!");
			
		} catch ( SQLException databaseInsertError ) {
			throw new RuntimeException("( INSERT PERSON ) - Opss, There is an error to insert in database!");
		} finally {
			closeConnectionDatabase(connectionDatabase, statementInsertDatabase, resultInsertDatabase);
		}
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Select All Person
	// ------------------------------------------------
	public List<Person> selectAllPeople(){
		Connection connectionDatabase = getConnection();
		Statement statementSelectDatabase = null;
		ResultSet resultSelectDatabase = null;
		
		List<Person> people = new ArrayList<Person>();
		
		try {
			statementSelectDatabase = connectionDatabase.createStatement();
			String selectPersonIntoDatabase = "SELECT * FROM Person";
			resultSelectDatabase = statementSelectDatabase.executeQuery(selectPersonIntoDatabase);
			
			while( resultSelectDatabase.next() ) {
				Person createSelectPerson = getPersonFromResultSelectDatabase(resultSelectDatabase);
				people.add(createSelectPerson);
			}
			
			System.out.println("( SELECT ALL PEOPLE ) - Successful search!");
			
		} catch( SQLException databaseSelectError ) {
			throw new RuntimeException("( SELECT ALL PEOPLE ) - Oops, There is an error to select in database!", databaseSelectError);
		} finally {
			closeConnectionDatabase( connectionDatabase, statementSelectDatabase, resultSelectDatabase );
		}
		return people;
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Update Person
	// ------------------------------------------------
	public void updatePerson( Person informedPerson ) {
		Connection connectionDatabase = getConnection();
		PreparedStatement statementUpdateDatabase = null;
		ResultSet resultUpdateDatabase = null;
		
		try {
			String updatePersonIntoDatabase = "UPDATE Person SET personName = ?, personAge = ? WHERE personId = ?";
			statementUpdateDatabase = connectionDatabase.prepareStatement(updatePersonIntoDatabase);
			statementUpdateDatabase.setString(1, informedPerson.getPersonName());
			statementUpdateDatabase.setInt(2, informedPerson.getPersonAge());
			statementUpdateDatabase.setInt(3, informedPerson.getPersonId());
			statementUpdateDatabase.executeUpdate();
			
			System.out.println("( UPDATE PERSON ) - Person successfully changed in the database!");
			
		} catch ( SQLException databaseUpdateError ) {
			throw new RuntimeException("( UPDATE PERSON ) - Oops, There is an error to update in database!", databaseUpdateError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementUpdateDatabase, resultUpdateDatabase);
		}	
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Delete Person
	// ------------------------------------------------
	public void deletePerson( int informedPersonId ) {
		Connection connectionDatabase = getConnection();
		PreparedStatement statementDeleteDatabase = null;
		
		try {
			String deletePersonIntoDatabase = "DELETE Person WHERE personId = ?";
			statementDeleteDatabase = connectionDatabase.prepareStatement(deletePersonIntoDatabase);
			statementDeleteDatabase.setInt(1, informedPersonId);
			statementDeleteDatabase.executeUpdate();
			
			System.out.println("( DELETE PERSON ) - Person successfully removed from the database!");
			
		} catch ( SQLException databaseDeleteError ) {
			throw new RuntimeException("( DELETE PERSON ) - Oops, Ther is an error to delete in database!", databaseDeleteError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementDeleteDatabase, null);
		}
	}
	// ------------------------------------------------
	
	
	// ------------------------------------------------
	// Select Person By ID
	// ------------------------------------------------
	public Person selectPersonById( int informedPersonId ){
		Connection connectionDatabase = getConnection();
		PreparedStatement statementSelectDatabase = null;
		ResultSet resultSelectDatabase = null;
		Person person = null;
		
		try {
			String selectPersonIntoDatabase = "SELECT * FROM Person WHERE personId = ?";
			statementSelectDatabase = connectionDatabase.prepareStatement(selectPersonIntoDatabase);
			statementSelectDatabase.setInt(1, informedPersonId);
			resultSelectDatabase = statementSelectDatabase.executeQuery();
			
			while( resultSelectDatabase.next() ) {
				person = getPersonFromResultSelectDatabase(resultSelectDatabase);
			}
			
			System.out.println("( SELECT PERSON BY ID ) - Successful search!");
			
		} catch( SQLException databaseSelectByIdError ) {
			throw new RuntimeException("( SELECT PERSON BY ID ) - Oops, There is an error when selecting the person in the database!", databaseSelectByIdError);
		} finally {
			closeConnectionDatabase( connectionDatabase, statementSelectDatabase, resultSelectDatabase );
		}
		return person;
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Intert E-mail Person
	// ------------------------------------------------
	public void insertEmailPerson( RegisteredPersonEmail informedEmailPerson ) {
		Connection connectionDatabase = getConnection();
		PreparedStatement statementInsertDatabase = null;
		ResultSet resultInsertDatabase = null;
		
		try {
			String insertEmailIntoDatabase = "INSERT INTO EmailRegistration ( personEmail ) VALUES ( ? )";
			statementInsertDatabase = connectionDatabase.prepareStatement(insertEmailIntoDatabase, Statement.RETURN_GENERATED_KEYS);
			statementInsertDatabase.setString(1, informedEmailPerson.getPersonEmail());
			statementInsertDatabase.executeUpdate();
			resultInsertDatabase = statementInsertDatabase.getGeneratedKeys();
			
			if( resultInsertDatabase.next() ) {
				informedEmailPerson.setEmailId(resultInsertDatabase.getInt(1));
			}
			
			System.out.println("( INSERT E-MAIL ) - Inserting data into Microsoft SQL Server successfully!");
			System.out.println("( INSERT E-MAIL ) - E-mail registered into database!");
			
		} catch ( SQLException databaseInsertError ) {
			throw new RuntimeException("( INSERT E-MAIL ) - Opss, There is an error to insert in database!", databaseInsertError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementInsertDatabase, resultInsertDatabase);
		}
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Select All E-mail Person
	// ------------------------------------------------
	public List<RegisteredPersonEmail> selectAllEmails() {
		Connection connectionDatabase = getConnection();
		Statement statementSelectDatabase = null;
		ResultSet resultSelectDatabase = null;
		
		List<RegisteredPersonEmail> emails = new ArrayList<RegisteredPersonEmail>();
		
		try {
			statementSelectDatabase = connectionDatabase.createStatement();
			String selectEmailIntoDatabase = "SELECT * FROM EmailRegistration";
			resultSelectDatabase = statementSelectDatabase.executeQuery(selectEmailIntoDatabase);
			
			while( resultSelectDatabase.next() ) {
				RegisteredPersonEmail createSelectEmailPerson = getEmailPersonFromResultSelectDatabase(resultSelectDatabase);
				emails.add(createSelectEmailPerson);
			}
			
			System.out.println("( SELECT ALL E-MAILS ) - Successful search!");
			
		} catch( SQLException databaseSelectError ) {
			throw new RuntimeException("( SELECT ALL E-MAILS ) - Oops, There is an error to select in database!", databaseSelectError);
		} finally {
			closeConnectionDatabase( connectionDatabase, statementSelectDatabase, resultSelectDatabase );
		}
		return emails;
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Update E-mail Person
	// ------------------------------------------------
	public void updateEmailPerson( RegisteredPersonEmail informedEmailPerson ) {
		Connection connectionDatabase = getConnection();
		PreparedStatement statementUpdateDatabase = null;
		ResultSet resultUpdateDatabase = null;
		
		try {
			String updateEmailIntoDatabese = "UPDATE EmailRegistration SET personEmail = ? WHERE emailId = ?";
			statementUpdateDatabase = connectionDatabase.prepareStatement(updateEmailIntoDatabese);
			
			statementUpdateDatabase.setString(1, informedEmailPerson.getPersonEmail());
			statementUpdateDatabase.setInt(2, informedEmailPerson.getEmailId());
			statementUpdateDatabase.executeUpdate();
			
			System.out.println("( UPDATE E-MAIL ) - Person successfully changed in the database:");
			
		} catch ( SQLException databaseUpdateError ) {
			throw new RuntimeException("( UPDATE PERSON ) - Oops, There is an error to update in database!", databaseUpdateError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementUpdateDatabase, resultUpdateDatabase);
		}	
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Delete E-mail Person
	// ------------------------------------------------
	public void deleteEmailPerson( int informedEmailId ) {
		Connection connectionDatabase = getConnection();
		PreparedStatement statementDeleteDatabase = null;
		
		try {
			String deleteEmailIntoDatabese = "DELETE EmailRegistration WHERE emailId = ?";
			statementDeleteDatabase = connectionDatabase.prepareStatement(deleteEmailIntoDatabese);
			statementDeleteDatabase.setInt(1, informedEmailId);
			statementDeleteDatabase.executeUpdate();
			
			System.out.println("( DELETE E-MAIL ) - Person successfully removed from the database!");
			
		} catch ( SQLException databaseDeleteError ) {
			throw new RuntimeException("( DELETE E-MAIL ) - Oops, Ther is an error to delete in database!", databaseDeleteError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementDeleteDatabase, null);
		}
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Select E-mail By ID
	// ------------------------------------------------
	public RegisteredPersonEmail selectEmailById( int informedEmailId ){
		Connection connectionDatabase = getConnection();
		PreparedStatement statementSelectDatabase = null;
		ResultSet resultSelectDatabase = null;
		RegisteredPersonEmail email = null;
		
		try {
			String selectPersonIntoDatabase = "SELECT * FROM EmailRegistration WHERE emailId = ?";
			statementSelectDatabase = connectionDatabase.prepareStatement(selectPersonIntoDatabase);
			statementSelectDatabase.setInt(1, informedEmailId);
			resultSelectDatabase = statementSelectDatabase.executeQuery();
			
			while( resultSelectDatabase.next() ) {
				email = getEmailPersonFromResultSelectDatabase(resultSelectDatabase);
			}
			
			System.out.println("( SELECT E-MAIL BY ID ) - Successful search!");
			
		} catch( SQLException databaseSelectByIdError ) {
			throw new RuntimeException("( SELECT E-MAIL BY ID ) - Oops, There is an error when selecting the e-amil in the database!", databaseSelectByIdError);
		} finally {
			closeConnectionDatabase( connectionDatabase, statementSelectDatabase, resultSelectDatabase );
		}
		return email;
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Select with join in table Person and EmailRegistration
	// ------------------------------------------------	

 	
	// ------------------------------------------------
	/**
	
	 */
	
	
	
	// ------------------------------------------------
	private Person getPersonFromResultSelectDatabase( ResultSet informedResultSelectDatabase ) throws SQLException {
		Person createSelectPerson = new Person();
		createSelectPerson.setPersonId(informedResultSelectDatabase.getInt("personId"));
		createSelectPerson.setPersonName(informedResultSelectDatabase.getString("personName"));
		createSelectPerson.setPersonAge(informedResultSelectDatabase.getInt("personAge"));
		return createSelectPerson;
	}
	
	private RegisteredPersonEmail getEmailPersonFromResultSelectDatabase( ResultSet informedResultSelectDatabase ) throws SQLException {
		RegisteredPersonEmail createSelectEmailPerson = new RegisteredPersonEmail();
		createSelectEmailPerson.setEmailId(informedResultSelectDatabase.getInt("emailId"));
		createSelectEmailPerson.setPersonEmail(informedResultSelectDatabase.getString("personEmail"));
		return createSelectEmailPerson;
	}
	
	private void closeConnectionDatabase( Connection informedConnectionDatabase, Statement informedStatementSelectDatabase, ResultSet informedResultSelectDatabase ) {
		try {
			
			if( informedConnectionDatabase != null ) { informedConnectionDatabase.close(); }
			if( informedStatementSelectDatabase != null ) { informedStatementSelectDatabase.close(); }
			if( informedResultSelectDatabase != null ) { informedResultSelectDatabase.close(); }
			
		} catch (Exception errorClosingResources) {
			throw new RuntimeException("Oops, There is an error to closing resources!", errorClosingResources);
		}
	}
	// ------------------------------------------------
	
}
