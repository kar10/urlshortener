package hr.spring.urlshortener.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hr.spring.urlshortener.dto.Account;
import hr.spring.urlshortener.dto.Register;
import hr.spring.urlshortener.service.ShortenerService;
import hr.spring.urlshortener.utils.CheckCredential;
import hr.spring.urlshortener.utils.UrlShortenerGenerator;

@RestController
public class UrlController {
	
	@Autowired
	private ShortenerService shortenerService;

	@PostMapping(value = "/account", consumes = "application/json", produces = "application/json")
	public Account openAccount(@RequestBody Account account) {
		return shortenerService.saveAccount(account);
	} 
	
	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Register> register(@RequestHeader("Authorization") String credential, @RequestBody Register register) {	
		
		CheckCredential checkCredential = new CheckCredential(shortenerService.findAllAccount());
		Account currentAccount = checkCredential.check(credential);
		if (currentAccount == null)
			return new ResponseEntity<Register>(HttpStatus.UNAUTHORIZED);
		
		register.setShortUrl(UrlShortenerGenerator.generate(register.getUrl()));
		
		if (shortenerService.saveRegister(register, currentAccount))
			return new ResponseEntity<Register>(register, HttpStatus.CREATED);
		else
			return new ResponseEntity<Register>(HttpStatus.CONFLICT);
	}
	
	@RequestMapping("/{hashedUrl:[\\d]+}")
	public void redirectToUrl(@RequestHeader("Authorization") String credential, @PathVariable final long hashedUrl, HttpServletResponse response) throws IOException {
		
		CheckCredential checkCredential = new CheckCredential(shortenerService.findAllAccount());
		Account currentAccount = checkCredential.check(credential);
		if (currentAccount == null)
			return;
		
		String shortUrl = "http://localhost:8080/" + String.valueOf(hashedUrl);
		Register register = shortenerService.findByShortUrl(shortUrl, currentAccount);
		
		if (register == null)
			return;
		else {
			response.sendRedirect(register.getUrl());
		}
	}

	@GetMapping(value = "/statistic/{accountId}", produces = "application/json")
	public String statistic(@RequestHeader("Authorization") String credential, @PathVariable String accountId) {
		
		CheckCredential checkCredential = new CheckCredential(shortenerService.findAllAccount());
		Account currentAccount = checkCredential.check(credential);
		if (currentAccount == null)
			return null;
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Integer> map = shortenerService.findByAccountId(accountId);
		String jsonResult = null;
		try {
			jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
		} catch (JsonProcessingException e) {}
		
		return jsonResult;
	}
	
	
	// (Ne)sretno u RestControlleru
	@GetMapping("/help")
	public ModelAndView help() {
	    ModelAndView view = new ModelAndView("help");
	    return view;
	}
}
