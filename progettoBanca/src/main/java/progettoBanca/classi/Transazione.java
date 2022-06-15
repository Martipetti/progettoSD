package progettoBanca.classi;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import progettoBanca.ProgettoBancaApplication;

public class Transazione {
	
	private String ide;
	private Date data;
	private String from;
	private double SaldoSender;
	private String to;
	private double amount;
	private double SaldoReiver;
	
	public Transazione(String sender, String receiver, double amount) {
		//super();
		data = new Date(); 
		this.ide = UUID.randomUUID().toString();
		this.from = sender;
		this.to = receiver;
		this.amount = amount;
		this.SaldoSender = ProgettoBancaApplication.database.getBalance( from );
		this.SaldoReiver = ProgettoBancaApplication.database.getBalance( to );
		ProgettoBancaApplication.database.createTransation(ide, data, amount, from, to);
	}
	
	public Transazione(String ide) {
		this.ide = ide;
	}

	public String getIdSender() {
		return from;
	}
	
	public double getBilancioS() {
		SaldoSender = ProgettoBancaApplication.database.getBalance( from );
		return SaldoSender;
	}
	
	public String getIdReceiver() {
		return to;
	}
	
	public double getBilancioR() {
		SaldoReiver = ProgettoBancaApplication.database.getBalance( to );
		return SaldoReiver;
	}
	
	public String getIde() {
		return ide;
	}
    
	@JsonIgnore
	public Date getData() {
		return data;
	}
	
}
