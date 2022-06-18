package progettoBanca.classi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import progettoBanca.ProgettoBancaApplication;

public class Transazione {
	
	private String ideTransazione;
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
		this.ideTransazione = UUID.randomUUID().toString();
		this.from = sender;
		this.to = receiver;
		this.amount = amount;
		this.balanceS = ProgettoBancaApplication.database.getBalance( from );
		this.balanceR = ProgettoBancaApplication.database.getBalance( to );
		ProgettoBancaApplication.database.createTransation(ideTransazione, strDate, amount, from, to);
	}
	
	public Transazione( String ide, String sender, String receiver, double amount ) {
		this.ideTransazione = ide;
		this.from = sender;
		this.to = receiver;
		this.amount = amount;

	}

	@JsonView(Views.Internal.class)
	public String getFrom() {
		return from;
	}
	
	@JsonView(Views.Internal.class)
	public String getTo() {
		return to;
	}
	
	@JsonIgnore
	public double getAmount() {
		return amount;
	}

	@JsonView(Views.Public.class)
	public String getIdeTransazione() {
		return ideTransazione;
	}
    
	@JsonIgnore
	public Date getData() {
		return data;
	}
	
	@JsonView(Views.Internal.class)
	public double getBalanceS() {
		return ProgettoBancaApplication.database.getBalance( from );
	}

	@JsonView(Views.Internal.class)
	public double getBalanceR() {
		return ProgettoBancaApplication.database.getBalance( to );
	}
	
	public class Views {
		   public class Public {}
		   public class Internal extends Public {}
		}
	
	
	
}
