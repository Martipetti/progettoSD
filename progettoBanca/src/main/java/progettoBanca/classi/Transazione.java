package progettoBanca.classi;

public class Transazione {
	private String ide;
	public Transazione() {
		super();
		this.ide= createIDE();
	}
	
	//identificativo transizione di lunghezza 5
	String createIDE() {
		String str = "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(5);

		for (int i = 0; i < 5; i++) {
			int index = (int)(str.length()* Math.random());
			sb.append(str.charAt(index));
		}
		return sb.toString();
	}

}
