package progettoBanca;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import progettoBanca.classi.Account;
import progettoBanca.classi.Transazione;
import progettoBanca.controller.ControllerDatabase;

@SpringBootApplication
public class ProgettoBancaApplication {
	
	public static List<Account> account;
	public static ControllerDatabase database;
    public static List<Transazione> transazioni;
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(ProgettoBancaApplication.class, args);
		
		database = new ControllerDatabase();
		database.createDatabase();
		
//		account = new ArrayList<Account>();
//		transazioni = new ArrayList<Transazione>();
		Account a1 = new Account("Martino", "Pettinari", "PTTMTN01C05F205O");
		Account a2 = new Account("Daniela", "Merlo", "MRLDNLXXXXXXXXX");
//		account.add(a1);
//		account.add(a2);
		
	}

}
