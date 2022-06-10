package progettoBanca.classi;

public class Account {
	
	private String name;
	private String surname;
	private String id;
	
	public Account(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
		this.id = createId();
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
	
	
}
