package progettoBanca;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import progettoBanca.classi.Account;
import progettoBanca.controller.ControllerDatabase;

@SpringBootApplication
public class ProgettoBancaApplication {
	
	public static List<Account> account;
	public static ControllerDatabase database;

	public static void main(String[] args) {
		SpringApplication.run(ProgettoBancaApplication.class, args);
		
		database = new ControllerDatabase();
		database.createDatabase();
		
		account = new ArrayList<Account>();
		Account a1 = new Account("Martino", "Pettinari");
		Account a2 = new Account("Daniela", "Merlo");
		account.add(a1);
		account.add(a2);
	}

}
