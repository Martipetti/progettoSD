package progettoBanca.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Account;
import progettoBanca.classi.Transazione;

public class ControllerDatabase {
	
	public Connection c = null;
	public static Statement stmt = null;
	
	//metodo per la creazione del database
	public void createDatabase() throws ClassNotFoundException{
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
		createAccountTable();
		createAccountTransition();
		
		System.out.println("Opened/created database successfully"); 
		
	}
	
	//metodo privato per la creazione della tabella account
	private void createAccountTable() throws ClassNotFoundException { 

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			String account = "CREATE TABLE IF NOT EXISTS account ( " +
					"ID TEXT NOT NULL, " +
					"NAME VARCHAR(255) NOT NULL, " + 
					"SURNAME VARCHAR(255) NOT NULL,"+
					"CF VARCHAR(16) NOT NULL," +
					"BALANCE INT NOT NULL," + 
					"PRIMARY KEY (ID, CF));";
			
			stmt = c.createStatement(); 
			stmt.executeUpdate(account);
			stmt.close();
         	c.close();
    	} catch ( SQLException e ) {
    		e.printStackTrace();
    		System.exit( 0 );
    	}
		
        System.out.println( "Created account successfully" );
        
	}
	
	//metodo privato creazione singola transazione
	private void createAccountTransition() throws ClassNotFoundException { 

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			String transation = "CREATE TABLE IF NOT EXISTS transation ( " +
					"IDE TEXT PRIMARY KEY  NOT NULL, " +
					"DATA DATE NOT NULL," +
					"ID TEXT NOT NULL, " +
					"CF VARCHAR(16) NOT NULL," +
					"FOREIGN KEY (ID, CF) REFERENCES account(ID, CF));" ;
			
			stmt = c.createStatement(); 
			stmt.executeUpdate(transation);
			stmt.close();
         	c.close();
    	} catch ( SQLException e ) {
    		e.printStackTrace();
    		System.exit( 0 );
    	}
		
        System.out.println( "Created transation successfully" );
        
	}
	
	public void creatAccount(String id, String name, String surname, String cf, double balance) throws ClassNotFoundException, SQLException {
		String query = "SELECT CF FROM account";
		String cf1= "";
		boolean b = true;
		try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:database.db");
	        c.setAutoCommit(false);
	        System.out.println("Opened database successfully");
	
	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next() && b) {
			    cf1 = rs.getString( "CF" );
			    if(! cf1.equals( cf ) )
			    	b = true;
			    else
			    	b = false;
	        }
	        	
    		if( b == true ) {
	    		query = "INSERT INTO account ( ID, NAME, SURNAME, CF, BALANCE ) VALUES ( '" 
	    				+ id + "', '" + name + "', '" + surname + "', '" + cf + "', '" + balance + "');";
		 				stmt.executeUpdate(query);
    		}
	        
			stmt.close();
			c.commit();
			c.close();
			
		} catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
	}
	

	public List<Account> getAllAccount() throws SQLException{
//		JSONArray allAccount = new JSONArray();
//		JSONObject account = new JSONObject();
		List<Account> account = new ArrayList<Account>();
		String query = "SELECT * FROM account";
		String id, name, surname, cf;
		double balance;
		
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
			    cf = rs.getString( "CF" );
			    balance = rs.getDouble( "BALANCE" );
			    
			    account.add(new Account( id, name, surname, cf, balance ));
			    
//			    account.add(new Account(id, name, surname, balance));	
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
		
		return account;
	}
	
	private List<Transazione> getTransation(String id) throws SQLException{
		
		List<Transazione> transazioni = new ArrayList<Transazione>();
		String query = "SELECT IDE, DATA FROM transation WHERE ID = '" + id +"' ";
		String ide;
		Date data;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully for query");
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
			    ide = rs.getString( "IDE" );
			    data = rs.getDate( "DATA" );
			    
			    transazioni.add(new Transazione( ide, data ));
				
			}
			
			rs.close();
	      	stmt.close();
	      	c.close();
	      	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		System.out.println("Operation done successfully");
		
		return transazioni;
	}
	
   public Account getAllTransation(String id) throws SQLException{
		
	    Account account = null;
		List<Transazione> transazioni = getTransation(id);
		String query = "SELECT NAME, SURNAME, BALANCE FROM account WHERE ID = '" + id + "' ";
		String  nome, cognome;
		double balance;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully for query");
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
			    nome = rs.getString( "NAME" );
			    cognome = rs.getString( "SURNAME" );
			    balance = rs.getDouble( "BALANCE" );
			    
			    account = new Account(nome, cognome, balance, transazioni);
				
			}
			
			rs.close();
	      	stmt.close();
	      	c.close();
	      	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		System.out.println("Operation done successfully");
		
		return account;
	}
	
	
	public void deleteAccount ( String id ) {
		String query = "DELETE FROM account WHERE ID = '" + id +"' ";
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully for query");
			
			stmt = c.createStatement();
			stmt.executeUpdate (query);
			c.commit ();
	      	stmt.close();
	      	c.close();
	      	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		System.out.println( "Operation done successfully" );

	}
	
}






