package progettoBanca.classi;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import progettoBanca.ProgettoBancaApplication;
import progettoBanca.controller.ControllerDatabase;

public class Flow {
	
	private double amount;
	private String ide;
	private String idAccount;
	private double bilancio;
	
	public Flow(double amount, String ide, String idAccount) {
		super();
		this.amount = amount;
		this.ide = ide;
		this.idAccount = idAccount;
		
	}
	

	public Flow(double amount, String idAccount) {
		this.ide = UUID.randomUUID().toString();
		this.idAccount = idAccount;
		this.amount = amount;
		this.bilancio = ProgettoBancaApplication.database.getBalance(idAccount);
		ProgettoBancaApplication.database.createFlow(amount, ide, idAccount);
		
	}
	
	@JsonIgnore
	public double getAmount() {
		return amount;
	}

	public String getIde() {
		return ide;
	}

	@JsonIgnore
	public String getId() {
		return idAccount;
	}
	
	public double getBilancio() {
		bilancio = ProgettoBancaApplication.database.getBalance(idAccount);
		return bilancio;
	}
	
	
}
