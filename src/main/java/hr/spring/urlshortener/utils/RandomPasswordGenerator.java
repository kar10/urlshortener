package hr.spring.urlshortener.utils;

import java.security.SecureRandom;

public class RandomPasswordGenerator {

	private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final int SIZE = 8;
	private static SecureRandom random = new SecureRandom();

	public static String generate() {
	   StringBuilder sb = new StringBuilder(SIZE);
	   
	   for(int i = 0; i < SIZE; i++ ) 
	      sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
	   
	   return sb.toString();
	}
}
