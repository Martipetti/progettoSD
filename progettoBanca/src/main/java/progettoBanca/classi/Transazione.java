package progettoBanca.classi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import progettoBanca.ProgettoBancaApplication;

public class Transazione {
	
	private String ide;
	private Date data;
	private String from;
	private String to;
	private double amount; 
	private double balanceS, balanceR;

	public Transazione(String sender, String receiver, double amount) {
		//super();
		data = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(data);  
		this.ide = UUID.randomUUID().toString();
		this.from = sender;
		this.to = receiver;
		this.amount = amount;
		this.balanceS = ProgettoBancaApplication.database.getBalance( from );
		this.balanceR = ProgettoBancaApplication.database.getBalance( to );
		ProgettoBancaApplication.database.createTransation(ide, strDate, amount, from, to);
	}
	
	public Transazione( String ide, String sender, String receiver, double amount ) {
		this.ide = ide;
		this.from = sender;
		this.to = receiver;
		this.amount = amount;

	}

	public String getFrom() {
		return from;
	}
	

	public String getTo() {
		return to;
	}
	
	@JsonIgnore
	public double getAmount() {
		return amount;
	}


	public String getIde() {
		return ide;
	}
    
	@JsonIgnore
	public Date getData() {
		return data;
	}

	public double getBalanceS() {
		return ProgettoBancaApplication.database.getBalance( from );
	}

	public double getBalanceR() {
		return ProgettoBancaApplication.database.getBalance( to );
	}
	
	
	
}
