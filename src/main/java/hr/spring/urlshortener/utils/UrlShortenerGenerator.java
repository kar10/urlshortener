package hr.spring.urlshortener.utils;

public class UrlShortenerGenerator {
	
	public static String generate(String url) {
		
		int hash = 7;
		for (int i = 0; i < url.length(); i++) {
			hash = hash*31 + url.charAt(i);
		}
		
		String hashString = String.valueOf(Math.abs(hash));
		
		String shortUrl = "http://localhost:8080/" + hashString;
		
		return shortUrl;
	}
}
