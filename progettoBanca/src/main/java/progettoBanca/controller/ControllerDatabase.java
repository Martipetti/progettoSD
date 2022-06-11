package progettoBanca.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import progettoBanca.ProgettoBancaApplication;

public class ControllerDatabase {
	
	public Connection c = null;

//	ProgettoBancaApplication.c = null;
	public void createDatabase(){
		
		try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:database.db");
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Opened database successfully"); 
      
      String account = "CREATE TABLE IF NOT EXISTS test ( " +
                       "ID TEXT PRIMARY KEY, " +
                       "NAME TEXT NOT NULL, " + 
                       "SURNAME TEXT NOT NULL)";
      
      try ( Statement stmt = c.createStatement(); ) {
            int rv = stmt.executeUpdate(account );
            System.out.println( "executeUpdate() returned " + rv );
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println( "Created database successfully" );
	}
	
}