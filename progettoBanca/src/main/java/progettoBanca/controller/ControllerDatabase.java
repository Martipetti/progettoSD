package progettoBanca.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import progettoBanca.ProgettoBancaApplication;

public class ControllerDatabase {
	
	public Connection c = null;
	public Statement stmt = null;

	public void createDatabase() throws ClassNotFoundException{
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
		createAccount();
		
		System.out.println("Opened/created database successfully"); 
	}

	private void createAccount() throws ClassNotFoundException { 

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			String account = "CREATE TABLE IF NOT EXISTS account ( " +
					"ID TEXT PRIMARY KEY, " +
					"NAME TEXT NOT NULL, " + 
					"SURNAME TEXT NOT NULL,"+
					"BALANCE INT NOT NULL);";
			
			stmt = c.createStatement(); 
			stmt.executeUpdate(account);
//			System.out.println( "executeUpdate() returned " + rv );
			stmt.close();
         	c.close();
    	} catch ( SQLException e ) {
    		e.printStackTrace();
    		System.exit( 0 );
    	}
        System.out.println( "Created account successfully" );
	}
	
}