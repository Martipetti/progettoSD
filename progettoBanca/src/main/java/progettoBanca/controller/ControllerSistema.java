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

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import progettoBanca.NotFoundException;
import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Account;
import progettoBanca.classi.Flow;

@RestController
public class ControllerSistema {
	
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
	
	
	@RequestMapping(method=RequestMethod.GET, value = "/api/account/{ID}")
	public Account getAccount(@PathVariable String ID) throws SQLException {
		
		return ProgettoBancaApplication.database.getAllTransation( ID );
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/api/account/{ID}")
	public Flow getFlow(@PathVariable String ID, @RequestParam(value = "amount") double amount) {
		
		return new Flow(amount, ID);
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
