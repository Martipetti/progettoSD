package progettoBanca.classi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import progettoBanca.ProgettoBancaApplication;

public class Account {
	
	private String name;
	private String surname;
	private String cf;
	private String id;
	private double balance;
	private List<Transazione> transazioni;
	
	
	public Account( String name, String surname, String cf ) throws ClassNotFoundException, SQLException {
		super();
		this.name = name;
		this.surname = surname;
		this.cf = cf;
		this.id = createId();
		this.balance = 0;
		transazioni = new ArrayList<Transazione>();
		ProgettoBancaApplication.database.creatAccount( id, name, surname, cf, balance );
	}

	public Account( String id, String name, String surname, String cf, double balance ) {
		this(id, name, surname, cf, balance, null);
	}

	public Account( String id, String name, String surname, String cf, double balance, List<Transazione> transazioni ) {
		super();
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.cf = cf;
		this.balance = balance;
		this.transazioni = transazioni;
	} 
	
	public Account( String name, String surname, double balance, List<Transazione> transazioni ) {
		this.name = name;
		this.surname = surname;
		this.balance = balance;
		this.transazioni = transazioni;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getId() {
		return id;
	}
	
	public void setBalance(double balance) {
		this.balance += balance;
	}
	
	public double getBalance() {
		return balance;
	}

    public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public List<Transazione> getTransazioni() {
		return transazioni;
	}

	public void setTransazioni(List<Transazione> transazioni) {
		this.transazioni = transazioni;
	}
	
	

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Account other = (Account) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (surname == null) {
//			if (other.surname != null)
//				return false;
//		} else if (!surname.equals(other.surname))
//			return false;
//		if (transazioni == null) {
//			if (other.transazioni != null)
//				return false;
//		} else if (!transazioni.equals(other.transazioni))
//			return false;
//		return true;
//	}
	
	
	
	
}
