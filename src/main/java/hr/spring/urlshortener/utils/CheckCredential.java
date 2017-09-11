package hr.spring.urlshortener.utils;

import java.util.Base64;
import java.util.List;

import hr.spring.urlshortener.dto.Account;

public class CheckCredential {
	
	List<Account> listsOfAccounts;

	public CheckCredential(List<Account> listOfAccounts) {
		this.listsOfAccounts = listOfAccounts;
	}
	
	public Account check(String credential) {
		
		for (Account account : listsOfAccounts) {		
		    String notEncoded = account.getAccountId() + ":" + account.getPassword();
		    String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
		   
			if (encodedAuth.equals(credential))
				return account;
		}
		
		return null;
	}
}
