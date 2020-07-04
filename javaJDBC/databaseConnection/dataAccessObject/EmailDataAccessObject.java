package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.RegisteredPersonEmail;

public class EmailDataAccessObject implements InterfaceDataAccessObject<RegisteredPersonEmail> {

	
	// ------------------------------------------------
	// Intert E-mail Person
	// ------------------------------------------------
	public void insert( RegisteredPersonEmail informedEmailPerson ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	public List<RegisteredPersonEmail> selectAll() {
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	public void update( RegisteredPersonEmail informedEmailPerson ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	public void delete( int informedEmailId ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	public RegisteredPersonEmail selectById( int informedEmailId ){
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
}