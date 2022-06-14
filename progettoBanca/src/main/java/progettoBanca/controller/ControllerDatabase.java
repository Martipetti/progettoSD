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

import progettoBanca.NotFoundException;
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
		createFlowTable();
		
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
	
	//metodo privato creazione tabella flow
	private void createFlowTable() throws ClassNotFoundException {
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			String flow = "CREATE TABLE IF NOT EXISTS flow ( " +
					"IDE TEXT PRIMARY KEY  NOT NULL, " +
					"ID TEXT NOT NULL, " +
					"CF VARCHAR(16) NOT NULL," +
					"AMOUNT DOUBLE NOT NULL," +
					"FOREIGN KEY (ID, CF) REFERENCES account(ID, CF));" ;
			
			stmt = c.createStatement(); 
			stmt.executeUpdate(flow);
			stmt.close();
         	c.close();
    	} catch ( SQLException e ) {
    		e.printStackTrace();
    		System.exit( 0 );
    	}
		
        System.out.println( "Created flow successfully" );
        
	}
	
	public void creatAccount(String id, String name, String surname, String cf, double balance) throws ClassNotFoundException, SQLException {
		String query = "SELECT CF FROM account";
		String cf1= "";
		boolean b = true;
		try {
			openDatabase();
	
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
			openDatabase();
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
			    id = rs.getString( "ID" );
			    name = rs.getString( "NAME" );
			    surname = rs.getString( "SURNAME" );
			    cf = rs.getString( "CF" );
			    balance = rs.getDouble( "BALANCE" );
			    
			    account.add( new Account( id, name, surname, cf, balance, null ) );
				
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
			openDatabase();
			
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
			openDatabase();
			
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
			
			openDatabase();
			
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
	
	public void createFlow(double amount, String ide, String idAccount) {
		
		String cf = getCf( idAccount );
		updateBalance(amount, idAccount);
		String query = "INSERT INTO flow ( IDE, ID, CF, AMOUNT ) VALUES ( '" 
				+ ide + "', '" + idAccount + "', '" + cf + "', '" + amount + "');";
		
		if( cf == null) {
			throw new NotFoundException();
		}
		
		try {
			
			openDatabase();
			
			stmt = c.createStatement();
			stmt.executeUpdate (query);
			c.commit ();
	      	stmt.close();
	      	c.close();
	      	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
	}
	
	private String getCf(String idAccount) {
		
		String query = "SELECT CF FROM account WHERE ID = '" + idAccount + "';"; 
		String cf = null;
		
		try {
			
			openDatabase();
	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
		    cf = rs.getString( "CF" );
	        }
	        
		    rs.close();
	      	stmt.close();
	      	c.close();
			    
		} catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
		
		return cf;
	}
	
	public double getBalance(String idAccount) {
			
		String query = "SELECT BALANCE FROM account WHERE ID = '" + idAccount + "';"; 
		double balance = 0.0;
		
		try {
			
			openDatabase();
			stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
		    balance = rs.getDouble( "BALANCE" );
	        }
	        
		    rs.close();
	      	stmt.close();
	      	c.close();
			    
		} catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
		
		return balance;
	}
	
	public String getNomeCognom(String idAccount) {
		
		String query = "SELECT NAME, SURNAME FROM account WHERE ID = '" + idAccount + "';"; 
		String response = null;
		
		try {
			
			openDatabase();
	        stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	response = rs.getString( "NAME" ) + ";" + rs.getString( "SURNAME" );
	        }
	        
		    rs.close();
	      	stmt.close();
	      	c.close();
			    
		} catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
		
		return response;
	}
	
	private void openDatabase() throws ClassNotFoundException, SQLException {
	   Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:database.db");
       c.setAutoCommit(false);
       System.out.println("Opened database successfully");
	}
   
	private void updateBalance( double amount, String id) {
	   
	   double balance = getBalance(id);
	   try {
		      openDatabase();

		      stmt = c.createStatement ();
		      if( amount < 0 ) {
		    	  
			        if(balance < amount) {
			        	
			        	System.err.println ("Il saldo del conto non è sufficente per fare il prelievo");
					    System.exit (0);
					    
			        }
			        else {
			        	String query = "UPDATE account set BALANCE = BALANCE + '" + amount + "' WHERE ID = '" + id + "';";  
					    stmt.executeUpdate ( query );
			        }
			        
		      }
		      else {
		      
		      String query = "UPDATE account set BALANCE = BALANCE + '" + amount + "' WHERE ID = '" + id + "';";  
		      stmt.executeUpdate ( query );
		      
		      }
		      
		      c.commit ();
		      stmt.close ();
		      c.close ();
		      
	    } catch (Exception e) {
		      System.err.println (e.getClass().getName() + ":" + e.getMessage());
		      System.exit (0);
	    }
	}
	
	public void updateValueAccount( String what, String value, String id) throws ClassNotFoundException, SQLException {
		
		openDatabase();

		try {
			stmt = c.createStatement ();
	      
	    	String query = "UPDATE account SET NAME = '" + value + "' WHERE ID = '" + id + "';";  
		    stmt.executeUpdate ( query );

		    c.commit ();
		    stmt.close ();
		    c.close ();
	      
		} catch (Exception e) {
			System.err.println (e.getClass().getName() + ":" + e.getMessage());
			System.exit (0);
		}
	}
	
	public void updateAccount( String name, String surname, String cf, String id) {
		try {
		      openDatabase();
		      
		      stmt = c.createStatement ();
		      
		      String query = "UPDATE account set ID = '" + id + "', NAME = '" 
		      + name + "', SURNAME = '" + surname + "', CF = '" 
		    		  + cf + "' WHERE ID = '" + id + "';";  
		      
		      stmt.executeUpdate ( query );
		      
		      c.commit ();
		      stmt.close ();
		      c.close ();
		      
	    } catch (Exception e) {
		      System.err.println (e.getClass().getName() + ":" + e.getMessage());
		      System.exit (0);
	    }
	}
}






