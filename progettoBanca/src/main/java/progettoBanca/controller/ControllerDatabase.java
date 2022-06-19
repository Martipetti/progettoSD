package progettoBanca.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import progettoBanca.NotFoundException;
import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Account;
import progettoBanca.classi.Flow;
import progettoBanca.classi.Transazione;
import progettoBanca.classi.Transazione.Views;



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
		createTransationTable();
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
	private void createTransationTable() throws ClassNotFoundException { 

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			
			String transation = "CREATE TABLE IF NOT EXISTS transation ( " +
					"IDE TEXT PRIMARY KEY NOT NULL, " +
					"DATA TEXT NOT NULL," +
					"AMOUNT DOUBLE NOT NULL," +
					"ID1 TEXT NOT NULL, " +
					"CF1 VARCHAR(16) NOT NULL," +
					"ID2 TEXT NOT NULL, " +
					"CF2 VARCHAR(16) NOT NULL," +
					"FOREIGN KEY (ID1, CF1) REFERENCES account(ID, CF)," +
					"FOREIGN KEY (ID2, CF2) REFERENCES account(ID, CF) );";
			
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
			    account.add( new Account( id, name, surname, cf, balance ) );
				
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
	
	public Transazione getTransazione( String ide ) throws SQLException{
		
		Transazione t = null;
		String query = "SELECT ID1, ID2, AMOUNT FROM transation WHERE IDE = '" + ide +"'";
		String sender, receiver;
		double amount;
		
		try {
			openDatabase();
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
			    sender = rs.getString( "ID1" );
			    receiver = rs.getString( "ID2" );
			    amount = rs.getDouble( "AMOUNT" );
			    
			    t= new Transazione ( ide, sender, receiver, amount );
				
			}
			
			rs.close();
	      	stmt.close();
	      	c.close();
	      	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		
		return t;
	}
	
	public List<Object> getAccountTransation( String id ) throws SQLException{
		
		Account a;
		List<Object> info = new ArrayList<Object>();
		String query1 = "SELECT * FROM account WHERE ID = '" + id +"' ";
		String query2 = "SELECT * FROM transation WHERE ID1 = '" + id +"' ORDER BY DATA ASC";
		String query3 = "SELECT * FROM flow WHERE ID = '" + id +"' ";
		String ide, name, surname, cf, sender, receiver;
		double balance, amount;
		
		try {
			openDatabase();
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			
			while (rs.next()) {
			id = rs.getString( "ID" );
		    name = rs.getString( "NAME" );
		    surname = rs.getString( "SURNAME" );
		    cf = rs.getString( "CF" );
		    balance = rs.getDouble( "BALANCE" );
		    
		    a = new Account( id, name, surname, cf, balance );
		    info.add( a );
			}
			
			rs = stmt.executeQuery(query2);
			
			while (rs.next()) {
			    ide = rs.getString( "IDE" );
			    sender = rs.getString( "ID1" );
			    receiver = rs.getString( "ID2" );
			    amount = rs.getDouble( "AMOUNT" );
			    
			    Transazione t= new Transazione ( ide, sender, receiver, amount ); //costruttore : String ide, String sender, String receiver, double amount
			    info.add(t);

			}
			
			rs = stmt.executeQuery(query3);
			while (rs.next()) {
			    ide = rs.getString( "IDE" );
			    id = rs.getString( "ID" );
			    cf = rs.getString( "CF" );
			    amount = rs.getDouble( "AMOUNT" );
			    
			    Flow f= new Flow( amount, ide, id ); 
			    info.add(f);

			}
				
			rs.close();
	      	stmt.close();
	      	c.close();
	      	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		System.out.println("Operation done successfully");
		
		return info;
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
	
	public void deleteTransation ( String ide ) {
		
		String query1= "SELECT IDE FROM flow ";
		String query = "SELECT ID1, ID2, AMOUNT, DATA FROM transation WHERE IDE = '" + ide +"' ";
		String idS, idR, data, ide2;
		double amount = 0.0;
		double balanceR = 0.0;
		try {
			
			openDatabase();
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			

	        
	        while (rs.next()) {
		    ide2 = rs.getString( "IDE" );
		    
		    if(ide2.equals(ide)) {
	        	
	        	System.err.println ("Stai cercando di annullare un prelievo");
			    System.exit (0);
			    
	        }
	        }
	        
		    rs = stmt.executeQuery(query);
	        
		    while (rs.next()) {
		    idS = rs.getString( "ID1" );
		    idR = rs.getString( "ID2" );
		    amount = rs.getDouble( "AMOUNT" );
		    data= rs.getString( "DATA" );
		    
		    balanceR = getBalance( idR );
		    if(balanceR < amount ) {
	        	
	        	System.err.println ("Il saldo del conto del ricevente non è sufficente per annulare l'operazione");
			    System.exit (0);
			    
	        }
	
		    ide= UUID.randomUUID().toString();
		    createTransation(ide, data, amount, idR, idS);
		    
		    rs.close();
	      	stmt.close();
	      	c.close();
	        }
	        
		  	
		} catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
		System.out.println( "Operation done successfully" );

	}
	
	public void createFlow(double amount, String ide, String idAccount) {
		
		double balance = getBalance( idAccount );
		if(balance < amount) {
        	
        	System.err.println ("Il saldo del conto non è sufficente per fare il prelievo");
		    System.exit (0);
		    
        }
		
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
	
	public  void createTransation(String ide, String data, double amount, String from, String to) {
		
		double balanceS = getBalance(from);
		if( balanceS < amount || amount < 0 ) {
        	
        	System.err.println ("Il saldo del conto non è sufficente per fare il prelievo");
		    System.exit (0);
		    
        }
		
		updateBalance(amount, to);
		updateBalance(-amount, from);
		
		String cfS = getCf( from );
		String cfR = getCf( to );
		String query = "INSERT INTO transation ( IDE, DATA, AMOUNT, ID1, CF1, ID2, CF2 ) VALUES ( '" 
				+ ide + "', '" + data + "', '" + amount + "', '" + from + "', '" + cfS + "', '" + to + "', '" + cfR + "');";
		
		if( cfS == null || cfR == null) {
			throw new NotFoundException();
		}
		
		try {
			
			
			openDatabase();
			
			stmt = c.createStatement();
			stmt.executeUpdate (query);
			c.commit();
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
	
	private  void openDatabase() throws ClassNotFoundException, SQLException {
	   Class.forName("org.sqlite.JDBC");
       c = DriverManager.getConnection("jdbc:sqlite:database.db");
       c.setAutoCommit(false);
       System.out.println("Opened database successfully");
	}
   
	private void updateBalance( double amount, String id) {
	
	   double balance = getBalance(id);
	   
	   try {
		      openDatabase();

		      stmt = c.createStatement();
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
		     
		      c.commit();
		      stmt.close();
		      c.close();  
		      System.out.println("close database successfully");
		      
	    } catch (Exception e) {
		      System.err.println (e.getClass().getName() + ":" + e.getMessage());
		      System.exit (0);
	    }
	}
	
	public void updateValueAccount( String what, String value, String id) throws ClassNotFoundException, SQLException {
		
		openDatabase();

		try {
			stmt = c.createStatement ();
	      
	    	String query = "UPDATE account SET '" + what + "'= '" + value + "' WHERE ID = '" + id + "';";  
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
		      
		      String query = "UPDATE account set CF = '" + cf + "', NAME = '" 
		      + name + "', SURNAME = '" + surname + "' WHERE ID = '" + id + "';";  
		      
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






