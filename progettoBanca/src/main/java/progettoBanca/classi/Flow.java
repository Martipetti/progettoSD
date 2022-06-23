package progettoBanca.classi;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Transazione.Views;

public class Flow {
	
	private String idePV;
	private double amount;
	private String idAccount;
	private double bilancio;
	
	public Flow(double amount, String ide, String idAccount) {
		super();
		this.amount = amount;
		this.idePV = ide;
		this.idAccount = idAccount;
		
	}
	

	public Flow(double amount, String idAccount) {
		this.idePV = UUID.randomUUID().toString();
		this.idAccount = idAccount;
		this.amount = amount;
		this.bilancio = ProgettoBancaApplication.database.getBalance(idAccount);
		ProgettoBancaApplication.database.createFlow(amount, idePV, idAccount);
		
	}
	
	@JsonView(Views.Internal.class)
	public double getAmount() {
		return amount;
	}
    
	@JsonView(Views.Public.class)
	public String getIdePV() {
		return idePV;
	}

	@JsonIgnore
	public String getId() {
		return idAccount;
	}
	
	@JsonView(Views.Internal.class)
	public double getBilancio() {
		bilancio = ProgettoBancaApplication.database.getBalance(idAccount);
		return bilancio;
	}
	
	
}
