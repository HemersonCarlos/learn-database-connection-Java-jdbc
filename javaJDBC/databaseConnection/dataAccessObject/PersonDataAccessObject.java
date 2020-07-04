package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Person;

public class PersonDataAccessObject implements InterfaceDataAccessObject<Person> {
	
	// ------------------------------------------------
	// Insert Person
	// ------------------------------------------------
	public void insert( Person informedPerson ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	public List<Person> selectAll(){
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	public void update( Person informedPerson ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	public void delete( int informedPersonId ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
		PreparedStatement statementDeleteDatabase = null;
		
		try {
			String deletePersonIntoDatabase = "DELETE Person WHERE personId = ?";
			statementDeleteDatabase = connectionDatabase.prepareStatement(deletePersonIntoDatabase);
			statementDeleteDatabase.setInt(1, informedPersonId);
			statementDeleteDatabase.executeUpdate();
			
			System.out.println("( DELETE PERSON ) - Person successfully removed from the database!");
			
		} catch ( SQLException databaseDeleteError ) {
			throw new RuntimeException("( DELETE PERSON ) - Oops, There is an error to delete in database!", databaseDeleteError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementDeleteDatabase, null);
		}
	}
	// ------------------------------------------------
	
	
	// ------------------------------------------------
	// Select Person By ID
	// ------------------------------------------------
	public Person selectById( int informedPersonId ){
		Connection connectionDatabase = DatabaseAccess.getConnection();
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
	
	// Methods  
	private Person getPersonFromResultSelectDatabase( ResultSet informedResultSelectDatabase ) throws SQLException {
		Person createSelectPerson = new Person();
		createSelectPerson.setPersonId(informedResultSelectDatabase.getInt("personId"));
		createSelectPerson.setPersonName(informedResultSelectDatabase.getString("personName"));
		createSelectPerson.setPersonAge(informedResultSelectDatabase.getInt("personAge"));
		return createSelectPerson;
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