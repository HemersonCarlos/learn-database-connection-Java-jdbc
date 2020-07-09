package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Department;

public class DepartmentDataAccessObject implements InterfaceDataAccessObject<Department> {

	public DepartmentDataAccessObject() {
		
		try {
			createTableDepartment();
		} catch( SQLException createTableError ) {
			createTableError.printStackTrace();
		}
		
	}
	
	// Create table into database
	public void createTableDepartment() throws SQLException {
		final String sqlCreate = "IF NOT EXISTS (" 
				+ "SELECT * FROM sys.tables t JOIN sys.schemas s ON " 
				+ "(t.schema_id = s.schema_id) WHERE s.name = 'dbo'" 
				+ "AND t.name = 'Department')"
				+ "CREATE TABLE Department"
				+ " (departamentId	int	IDENTITY,"
				+ "  departmentName	VARCHAR(255),"
				+ "  PRIMARY KEY (departamentId))";
		
		Connection connectionDatabase = DatabaseAccess.getConnection();
		
		Statement statementCreateTableDepartment = connectionDatabase.createStatement();
		statementCreateTableDepartment.execute(sqlCreate);
	}
	
	// ------------------------------------------------
	// Insert Department
	// ------------------------------------------------
	public void insert( Department informedDepartment ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
		PreparedStatement statementInsertDatabase = null;
		ResultSet resultInsertDatabase = null;
		
		try {
			String insertDepartmentIntoDatabase = "INSERT INTO Department ( departmentName ) VALUES ( ?, ? )";
			statementInsertDatabase = connectionDatabase.prepareStatement(insertDepartmentIntoDatabase, Statement.RETURN_GENERATED_KEYS);
			statementInsertDatabase.setString(1, informedDepartment.getDepartmentName());
			statementInsertDatabase.executeUpdate();
			resultInsertDatabase = statementInsertDatabase.getGeneratedKeys();
			
			if( resultInsertDatabase.next() ) {
				informedDepartment.setDepartmentId(resultInsertDatabase.getInt(1));
			}
			
			System.out.println("( INSERT DEPARTMENT ) - Inserting data into Microsoft SQL Server successfully!");
			System.out.println("( INSERT DEPARTMENT ) - Department registered into database!");
			
		} catch ( SQLException databaseInsertError ) {
			throw new RuntimeException("( INSERT DEPARTMENT ) - Opss, There is an error to insert in database!");
		} finally {
			closeConnectionDatabase(connectionDatabase, statementInsertDatabase, resultInsertDatabase);
		}
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Select All Department
	// ------------------------------------------------
	public List<Department> selectAll(){
		Connection connectionDatabase = DatabaseAccess.getConnection();
		Statement statementSelectDatabase = null;
		ResultSet resultSelectDatabase = null;
		
		List<Department> department = new ArrayList<Department>();
		
		try {
			statementSelectDatabase = connectionDatabase.createStatement();
			String selectDepartmentIntoDatabase = "SELECT * FROM Department";
			resultSelectDatabase = statementSelectDatabase.executeQuery(selectDepartmentIntoDatabase);
			
			while( resultSelectDatabase.next() ) {
				Department createSelectDepartment = getDepartmentFromResultSelectDatabase(resultSelectDatabase);
				department.add(createSelectDepartment);
			}
			
			System.out.println("( SELECT ALL DEPARTMENT ) - Successful search!");
			
		} catch( SQLException databaseSelectError ) {
			throw new RuntimeException("( SELECT ALL DEPARTMENT ) - Oops, There is an error to select in database!", databaseSelectError);
		} finally {
			closeConnectionDatabase( connectionDatabase, statementSelectDatabase, resultSelectDatabase );
		}
		return department;
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Update Department
	// ------------------------------------------------
	public void update( Department informedDepartment ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
		PreparedStatement statementUpdateDatabase = null;
		ResultSet resultUpdateDatabase = null;
		
		try {
			String updateDepartmentIntoDatabase = "UPDATE Department SET departmentName = ? WHERE departamentId = ?";
			statementUpdateDatabase = connectionDatabase.prepareStatement(updateDepartmentIntoDatabase);
			statementUpdateDatabase.setString(1, informedDepartment.getDepartmentName());
			statementUpdateDatabase.setInt(2, informedDepartment.getDepartmentId());
			statementUpdateDatabase.executeUpdate();
			
			System.out.println("( UPDATE DEPARTMENT ) - Depertment successfully changed in the database!");
			
		} catch ( SQLException databaseUpdateError ) {
			throw new RuntimeException("( UPDATE DEPARTMENT ) - Oops, There is an error to update in database!", databaseUpdateError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementUpdateDatabase, resultUpdateDatabase);
		}	
	}
	// ------------------------------------------------
	
	
	
	// ------------------------------------------------
	// Delete Department
	// ------------------------------------------------
	public void delete( int informedDepartmentId ) {
		Connection connectionDatabase = DatabaseAccess.getConnection();
		PreparedStatement statementDeleteDatabase = null;
		
		try {
			String deleteDepartmentIntoDatabase = "DELETE Department WHERE personId = ?";
			statementDeleteDatabase = connectionDatabase.prepareStatement(deleteDepartmentIntoDatabase);
			statementDeleteDatabase.setInt(1, informedDepartmentId);
			statementDeleteDatabase.executeUpdate();
			
			System.out.println("( DELETE DEPARTMENT ) - Department successfully removed from the database!");
			
		} catch ( SQLException databaseDeleteError ) {
			throw new RuntimeException("( DELETE DEPARTMENT ) - Oops, There is an error to delete in database!", databaseDeleteError);
		} finally {
			closeConnectionDatabase(connectionDatabase, statementDeleteDatabase, null);
		}
	}
	// ------------------------------------------------
	
	
	// ------------------------------------------------
	// Select Department By ID
	// ------------------------------------------------
	public Department selectById( int informedDepartmentId ){
		Connection connectionDatabase = DatabaseAccess.getConnection();
		PreparedStatement statementSelectDatabase = null;
		ResultSet resultSelectDatabase = null;
		Department department = null;
		
		try {
			String selectPersonIntoDatabase = "SELECT * FROM Department WHERE personId = ?";
			statementSelectDatabase = connectionDatabase.prepareStatement(selectPersonIntoDatabase);
			statementSelectDatabase.setInt(1, informedDepartmentId);
			resultSelectDatabase = statementSelectDatabase.executeQuery();
			
			while( resultSelectDatabase.next() ) {
				department = getDepartmentFromResultSelectDatabase(resultSelectDatabase);
			}
			
			System.out.println("( SELECT DEPARTMENT BY ID ) - Successful search!");
			
		} catch( SQLException databaseSelectByIdError ) {
			throw new RuntimeException("( SELECT DEPARTMENT BY ID ) - Oops, There is an error when selecting the department in the database!", databaseSelectByIdError);
		} finally {
			closeConnectionDatabase( connectionDatabase, statementSelectDatabase, resultSelectDatabase );
		}
		return department;
	}
	// ------------------------------------------------
	
	// Methods  
	private Department getDepartmentFromResultSelectDatabase( ResultSet informedResultSelectDatabase ) throws SQLException {
		Department createSelectDepartment = new Department();
		createSelectDepartment.setDepartmentId(informedResultSelectDatabase.getInt("departamentId"));
		createSelectDepartment.setDepartmentName(informedResultSelectDatabase.getString("departmentName"));
		return createSelectDepartment;
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