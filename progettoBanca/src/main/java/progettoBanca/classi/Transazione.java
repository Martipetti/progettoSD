package progettoBanca.classi;

import java.util.Date;
import java.util.UUID;

public class Transazione {
	private UUID ide;
	private Date data;
	public Transazione() {
		super();
		data = new Date(); 
		this.ide= UUID.randomUUID();
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

	public UUID getIde() {
		return ide;
	}

	public Date getData() {
		return data;
	}
	

}
