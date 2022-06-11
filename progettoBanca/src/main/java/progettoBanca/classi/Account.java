package progettoBanca.classi;

import java.util.ArrayList;
import java.util.List;

public class Account {
	
	private String name;
	private String surname;
	private String id;
	private List<Transazione> transazioni;
	
	public Account(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
		this.id = createId();
		transazioni= new ArrayList<Transazione>();
	}	

	//funzione per generare codice alfanumerico di lunghezza 20
	String createId() {
		String str = "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(20);

		for (int i = 0; i < 20; i++) {
			int index = (int)(str.length()* Math.random());
			sb.append(str.charAt(index));
		}
		return sb.toString();
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

    public List<Transazione> getTransazioni() {
		return transazioni;
	}

	public void setTransazioni(List<Transazione> transazioni) {
		this.transazioni = transazioni;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (transazioni == null) {
			if (other.transazioni != null)
				return false;
		} else if (!transazioni.equals(other.transazioni))
			return false;
		return true;
	}
	
	
	
	
}
