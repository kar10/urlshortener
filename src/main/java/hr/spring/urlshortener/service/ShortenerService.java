package hr.spring.urlshortener.service;

import java.util.List;
import java.util.Map;

import hr.spring.urlshortener.dto.Account;
import hr.spring.urlshortener.dto.Register;

public interface ShortenerService {
	
	public List<Account> findAllAccount();
	public Account saveAccount(Account account);

	public List<Register> findAllRegister();
	public boolean saveRegister(Register register, Account account);
	public Register findByShortUrl(String shortUrl, Account account);
	
	public Map<String, Integer> findByAccountId(String accountId);
}
