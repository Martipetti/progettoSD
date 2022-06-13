package progettoBanca.classi;

import java.util.Date;
import java.util.UUID;

import progettoBanca.ProgettoBancaApplication;

public class Transazione {
	
	private String ide;
	private Date data;
	private String idSender;
	private String idReceiver;
	private String cfSender;
	private String cfReceiver;
	private double amount;
	
	public Transazione(Account sender, Account receiver, double amount) {
		//super();
		data = new Date(); 
		this.ide = UUID.randomUUID().toString();
		this.idSender = sender.getId();
		this.idReceiver = receiver.getId();
		this.cfSender = sender.getCf();
		this.cfReceiver = receiver.getCf();
		this.amount = amount;
	}
	
	public Transazione(String ide, Date data) {
		this.ide = ide;
		this.data = data;
	}

	public String getIde() {
		return ide;
	}

	public Date getData() {
		return data;
	}
	
}
