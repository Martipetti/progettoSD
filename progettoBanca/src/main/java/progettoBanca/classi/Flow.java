package progettoBanca.classi;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Flow {
	
	private double amount;
	private String ide;
	private String id;
	private String cf;
	
	public Flow(double amount, String ide, Account account) {
		super();
		this.amount = amount;
		this.ide = ide;
		this.id = account.getId();
		
	}
	

	public Flow(double amount, String id) {
		this.ide = ide;
		this.id = id;
		this.amount = amount;
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
		return id;
	}
	
	
	
}
