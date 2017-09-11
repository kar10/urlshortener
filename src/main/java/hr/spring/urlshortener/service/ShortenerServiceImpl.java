package hr.spring.urlshortener.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hr.spring.urlshortener.dto.Account;
import hr.spring.urlshortener.dto.PageCounterMap;
import hr.spring.urlshortener.dto.Register;
import hr.spring.urlshortener.utils.RandomPasswordGenerator;

@Service("shortenerService")
public class ShortenerServiceImpl implements ShortenerService {

	private List<Account> listOfAccounts;
	private List<Register> listOfRegisters;
	private Map<Account, PageCounterMap> stats;
	
	public ShortenerServiceImpl() {
		listOfAccounts = new ArrayList<>();
		listOfRegisters = new ArrayList<>();
		stats = new HashMap<>();
	}
	
	@Override
	public List<Account> findAllAccount() {
		return listOfAccounts;
	}

	@Override
	public Account saveAccount(Account account) {
		
		for (Account a : listOfAccounts) {
			if (account.getAccountId().equals(a.getAccountId())) {
				account.setSuccess(false);
				account.setDescription("Account with this ID already exists.");
				account.setPassword(null);
				return account;
			}
		}
		
		account.setSuccess(true);
		account.setDescription("Your account(" + account.getAccountId() +") is opened.");
		account.setPassword(RandomPasswordGenerator.generate());
		
		listOfAccounts.add(account);
		
		// Novi  korisnik stvara svoj PageCounterMap()
		stats.put(account, new PageCounterMap());
		return account;
	}
	
	@Override
	public List<Register> findAllRegister() {
		return listOfRegisters;
	}
	
	@Override
	public boolean saveRegister(Register register, Account account) {
		for (Register r : listOfRegisters) {
			if (register.getUrl().equals(r.getUrl())) {
				return false;
			}
		}
		
		listOfRegisters.add(register);
		
		// Novi url dodaje se u PageCounterMap() i counter inicijalizirana na 0
		for (Account a : stats.keySet()) {
			if (a.getAccountId().equals(account.getAccountId())) {
				PageCounterMap map = stats.get(a);
				map.getStats().put(register.getUrl(), 0);
			}
				
		}
		
		return true;
	}

	@Override
	public Register findByShortUrl(String shortUrl, Account account) {
		
		for (Register r : listOfRegisters) {		
			if (shortUrl.equals(r.getShortUrl())) {
				
				// Otvaranjem urla inkrementira se njegov counter prema određenom korisniku
				for (Account a : stats.keySet()) {
					if (a.getAccountId().equals(account.getAccountId())) {
						PageCounterMap map = stats.get(a);
						
						for (String url : map.getStats().keySet()) {
							if (url.equals(r.getUrl())) {
								Integer counter = map.getStats().get(url);
								counter++;
								map.getStats().put(url, counter);
							}	
						}
					}
						
				}	
				
				return r;
			}
		}
		
		return null;
	}
	
	@Override
	public Map<String, Integer> findByAccountId(String accountId) {
		
		for (Account a : stats.keySet()) {
			if (a.getAccountId().equals(accountId)) {
				return stats.get(a).getStats();				
			}			
		}
		
		return null;
	}
}
