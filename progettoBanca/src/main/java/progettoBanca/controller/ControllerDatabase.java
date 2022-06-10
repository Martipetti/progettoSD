package progettoBanca.controller;

import java.sql.Connection;
import java.sql.DriverManager;

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
	}
	
}