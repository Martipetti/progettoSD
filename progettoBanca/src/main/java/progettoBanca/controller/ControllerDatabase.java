package progettoBanca.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public List<Account> getAllAccount() throws SQLException{
		JSONArray allAccount = new JSONArray();
		JSONObject account = new JSONObject();
		List<Account> account1 = new ArrayList<Account>();
		String query = "SELECT * FROM account";
		String id, name, surname;
		double balance;
//		String account = "";
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully for query");
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
			    id = rs.getString( "ID" );
			    name = rs.getString( "NAME" );
			    surname = rs.getString( "SURNAME" );
			    balance = rs.getDouble( "BALANCE" );
			    
			    account1.add(new Account(id, name, surname, balance));
////			    
////			    account.add(new Account(id, name, surname, balance));	
//				account.put("name", name);
//				account.put("surname", surname);
//				account.put("id", id);
//				account.put("balance", balance);
//				
//				allAccount.put(account);
				
			}
			
			rs.close();
	      	stmt.close();
	      	c.close();
	      	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		System.out.println("Operation done successfully");
		
		return account1;
	}
	
}






