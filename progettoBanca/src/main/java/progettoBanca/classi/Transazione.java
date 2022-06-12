package progettoBanca.classi;

import java.util.Date;
import java.util.UUID;

import progettoBanca.ProgettoBancaApplication;

public class Transazione {
	private String ide;
	private Date data;
	public Transazione() {
		super();
		data = new Date(); 
		this.ide = UUID.randomUUID().toString();
	}
	
	//identificativo transizione di lunghezza 5
	/*String createIDE() {
		String str = "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(5);

		for (int i = 0; i < 5; i++) {
			int index = (int)(str.length()* Math.random());
			sb.append(str.charAt(index));
		}
		return sb.toString();
	}*/
	
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
