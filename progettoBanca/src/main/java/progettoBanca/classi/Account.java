package progettoBanca.classi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Transazione.Views;

public class Account {
	
	private String name;
	private String surname;
	private String cf;
	private String id;
	private double balance;
//	private List<Transazione> transazioni;
	
	
	public Account( String name, String surname, String cf ) throws ClassNotFoundException, SQLException {
		super();
		this.name = name;
		this.surname = surname;
		this.cf = cf;
		this.id = createId();
		this.balance = 0;
//		transazioni = new ArrayList<Transazione>();
		ProgettoBancaApplication.database.creatAccount( id, name, surname, cf, balance);
	}

	public Account( String id, String name, String surname, String cf, double balance ) {
		super();
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.cf = cf;
		this.balance = balance;
//		this.transazioni = transazioni;
	} 
	

	//funzione per generare codice esadecimale di lunghezza 20
	public String createId() {
		Random r;
		int n;
		String hexadecimal = "";
		String id;
	    
		for(int i=0; i<3; i++) {
			r = new Random();
			n = r.nextInt();
			hexadecimal += Integer.toHexString(n);
		}
		
		id = hexadecimal.substring(0, 20);
		return id;
	}
    
	@JsonView(Views.Public.class)
	public String getName() {
		return name;
	}

	@JsonView(Views.Public.class)
	public String getSurname() {
		return surname;
	}

	@JsonView(Views.Internal.class)
	public String getId() {
		return id;
	}
	
	@JsonView(Views.Public.class)
	public double getBalance() {
		return balance;
	}

	@JsonView(Views.Public.class)
    public String getCf() {
		return cf;
	}

 
	
	
	
	
}
