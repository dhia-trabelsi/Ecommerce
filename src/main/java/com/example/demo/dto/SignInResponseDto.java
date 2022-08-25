package com.example.demo.dto;

public class SignInResponseDto {
	
	private String Status;
	private String token;
	
	
	public SignInResponseDto(String status, String token) {
		super();
		Status = status;
		this.token = token;
	}


	public String getStatus() {
		return Status;
	}


	public void setStatus(String status) {
		Status = status;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	
	
	

}
