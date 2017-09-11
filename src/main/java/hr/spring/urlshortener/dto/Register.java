package hr.spring.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Register {

	@JsonProperty(value = "url", access = Access.WRITE_ONLY)
	private String url;
	
	@JsonProperty(value = "accountId", required = false, defaultValue = "302", access = Access.WRITE_ONLY)
	private String redirectType;
	
	@JsonProperty(value = "shortUrl", access = Access.READ_ONLY)
	private String shortUrl;
	
	public String getUrl() {
		return url;
	}

	public String getRedirectType() {
		return redirectType;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
}
