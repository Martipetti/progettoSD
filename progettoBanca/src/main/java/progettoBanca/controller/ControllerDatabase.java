package progettoBanca.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Account;

public class ControllerDatabase {
	
	public Connection c = null;
	public static Statement stmt = null;

	public void createDatabase() throws ClassNotFoundException{
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
		createAccountTable();
		
		System.out.println("Opened/created database successfully"); 
	}

	private void createAccountTable() throws ClassNotFoundException { 

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
	
	public void creatAccount(String id, String name, String surname, double balance) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:database.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");
	
	        stmt = c.createStatement();
	
			String query = "INSERT INTO account ( ID, NAME, SURNAME, BALANCE ) VALUES ( '" 
					+ id + "', '" + name + "', '" + surname + "', '" + balance + "');";
			
			stmt.executeUpdate(query);
			
			stmt.close();
			c.commit();
			c.close();
		} catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
	}
	
	public static List<Account> getAllAccount() throws SQLException{
		String query = "SELECT * FROM account";
		ResultSet rs = stmt.executeQuery(query);
		List<Account> account = new ArrayList<Account>();
		
		while (rs.next()) {
		    String id = rs.getString( "ID" );
		    String name = rs.getString( "NAME" );
		    String surname = rs.getString( "SURNAME" );
		    double balance = rs.getDouble( "BALANCE" );
		    
		    account.add(new Account(id, name, surname, balance));		
	   }
		
		return account;
	}
	
}






