package progettoBanca.classi;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import progettoBanca.ProgettoBancaApplication;

public class Transazione {
	
	private String ide;
	private Date data;
	private String idSender;
	private String idReceiver;
	private double amount;
	
	public Transazione(String sender, String receiver, double amount) {
		//super();
		data = new Date(); 
		this.ide = UUID.randomUUID().toString();
		this.idSender = sender;
		this.idReceiver = receiver;
		this.amount = amount;
		ProgettoBancaApplication.database.createTransation(ide, data, amount, idSender, idReceiver);
	}
	
	public Transazione(String ide) {
		this.ide = ide;
	}

	public String getIde() {
		return ide;
	}
    
	@JsonIgnore
	public Date getData() {
		return data;
	}
	
}
