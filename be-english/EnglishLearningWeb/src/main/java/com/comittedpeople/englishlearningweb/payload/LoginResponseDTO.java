package com.comittedpeople.englishlearningweb.payload;

public class LoginResponseDTO {

	private String accessToken;
	
	private String tokenType = "Bearer";
	
	public LoginResponseDTO(String accessToken) {
		this.accessToken = accessToken;
	}

	public LoginResponseDTO() {
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
