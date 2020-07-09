package dataAccessObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccess {

	// Criação do login no SQL Server
	// login: dbadm
	// senha : 1234
	
	private static final String DATABASE_CONNECTION_URL = "jdbc:sqlserver://localhost;databaseName=register_person;";
	private static final String USER = "dbadm";
	private static final String PASSWORD = "1234";
	private static Connection connectionDatabase = null;
	
	// ------------------------------------------------
	// Connection into database
	// ------------------------------------------------
	public static Connection getConnection() {
		
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
}