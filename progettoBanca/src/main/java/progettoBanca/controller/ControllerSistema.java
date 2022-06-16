package progettoBanca.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import progettoBanca.NotFoundException;
import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Account;
import progettoBanca.classi.Flow;
import progettoBanca.classi.Transazione;

@RestController
public class ControllerSistema {
	
	//end-point : /
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String index() throws URISyntaxException, IOException {
		return manageHtml("Index.html");
	}
	
	//end-point : /api/account
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/account")
	public List<Account> getAccount() throws IOException, URISyntaxException, SQLException {
		
		return ProgettoBancaApplication.database.getAllAccount();
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/account")
	public String makeAccount(@RequestBody String bodyString) throws SQLException, ClassNotFoundException {
		
		Map<String, String> body = parseBody(bodyString);
		Account a = new Account(body.get("name"), body.get("surname"), body.get("cf"));	
		return a.getId();
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/api/account")
	public String removeAccount(@RequestParam(value = "id") String id) {

	    ProgettoBancaApplication.database.deleteAccount( id );	
		return "OK";
	
	}
	
	//end-point : /api/account/{ID}
	
	@RequestMapping(method=RequestMethod.GET, value = "/api/account/{ID}")
	public ResponseEntity<List<Object>> getAccount(@PathVariable String ID) throws SQLException {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.set( "X-Sistema-Bancario", ProgettoBancaApplication.database.getNomeCognom(ID) );
	    List<Object> list = ProgettoBancaApplication.database.getAccountTransation( ID );
	    return ResponseEntity.ok().headers(headers).body(list);
				
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/api/account/{ID}")
	public Flow getFlow(@PathVariable String ID, @RequestBody String bodyString) {
		Map<String, String> body = parseBody(bodyString);
		Flow f = new Flow( Double.parseDouble(body.get("amount")), ID);
		return f;
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/api/account/{ID}" )
	public ResponseEntity<?> putAccount(@PathVariable String ID, @RequestParam String name, @RequestParam String surname, @RequestParam String cf )  {
		ProgettoBancaApplication.database.updateAccount(name, surname, cf, ID);
		return ResponseEntity.ok("resource update");
	}
	
	@RequestMapping(method=RequestMethod.PATCH, value = "/api/account/{ID}")
	public ResponseEntity<?> patchAccount(@PathVariable String ID, @RequestParam String name) throws ClassNotFoundException, SQLException {
		ProgettoBancaApplication.database.updateValueAccount( "NAME", name, ID);
		return ResponseEntity.ok("resource update");
	}
	
	@RequestMapping(method=RequestMethod.HEAD, value="/api/account/{ID}")
	public ResponseEntity<String> headAccount( @PathVariable String ID ) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.set( "X-Sistema-Bancario", ProgettoBancaApplication.database.getNomeCognom(ID) );

	    return ResponseEntity.ok().headers(headers).body("Response with header using ResponseEntity");
	}
	
	//end-point : /api/transfer
	@RequestMapping(method=RequestMethod.POST, value = "/api/transfer")
	public Transazione postTransfer(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
		return new Transazione(from, to, amount);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/api/divert")
	public ResponseEntity<?> postDivert(@RequestParam String ide) {
		
		ProgettoBancaApplication.database.deleteTransation( ide );
		return ResponseEntity.ok("Transazione annullata");
	}

	//metodo per la costruzione della pagina html
	public String manageHtml(String name) throws URISyntaxException, IOException {
		URL res = getClass().getClassLoader().getResource(name);
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader (absolutePath));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
	//metodo per il parsing del body delle richieste
	public Map<String, String> parseBody(String str) {
		Map<String, String> body = new HashMap<>();
		  String[] values = str.split("&");
		  for (int i = 0; i < values.length; ++i)
		  {
			  String[] coppia = values[i].split("=");
			    if (coppia.length != 2) {
			    	continue;
			    }
			    else
			    {
			      body.put(coppia[0], coppia[1]);
			    }
		  }
		  return body;
	}


}
