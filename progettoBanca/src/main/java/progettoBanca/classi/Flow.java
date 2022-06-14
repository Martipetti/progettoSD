package progettoBanca.classi;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import progettoBanca.ProgettoBancaApplication;

public class Flow {
	
	private double amount;
	private String ide;
	private String idAccount;
	
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
		ProgettoBancaApplication.database.createFlow(amount, ide, idAccount);
		//da finire, fare query ricerca cf
	}

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
	
	
	
}
