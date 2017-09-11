package hr.spring.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	@JsonProperty(value = "accountId", access = Access.WRITE_ONLY)
	private String accountId;
	
	@JsonProperty(value = "success", access = Access.READ_ONLY)
	private boolean success;
	
	@JsonProperty(value = "description", access = Access.READ_ONLY)
	private String description;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty(value = "password", access = Access.READ_ONLY)
	private String password;
	
	public String getAccountId() {
		return accountId;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public String getDescription() {
		return description;
	}

	public String getPassword() {
		return password;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
