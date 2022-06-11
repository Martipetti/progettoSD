package progettoBanca.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import progettoBanca.ProgettoBancaApplication;
import progettoBanca.classi.Account;

@RestController
public class ControllerSistema {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/account")
	public List<Account> getAccount() throws IOException, URISyntaxException {
		return ProgettoBancaApplication.account;
//		return manageHtml("Index.html");
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/account")
	public String makeAccount(@RequestBody String bodyString) {
		Map<String, String> body = parseBody(bodyString);
		Account a = new Account(body.get("name"), body.get("surname"));
		ProgettoBancaApplication.account.add(a);
		if(ProgettoBancaApplication.account.contains(a))
			return a.getId();
		else
			return "Failed";
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/api/account/{ID}")
	public String removeAccount(@PathVariable String ID) {
	    Account a=null;
	    for(Account co : ProgettoBancaApplication.account) {
			if(co.getId().equals(ID)) {
				a = co;
				break;
			}
		}
		
	    if(a!=null) {
			if(ProgettoBancaApplication.account.remove(a))
				return "OK!";
			else
				return "Failed!";
		}
		else
			return "Failed";
	
	}
	
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
