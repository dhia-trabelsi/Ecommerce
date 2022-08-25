package com.example.demo.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.AuthenticationFailException;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.User;
import com.example.demo.repository.AuthenticationTokenRepo;

@Service
public class AuthenticationTokenService {
	
	@Autowired
	AuthenticationTokenRepo authenticationTokenRepo;

	public  void SaveConfirmationToken(AuthenticationToken authenticationToken) {
		
		authenticationTokenRepo.save(authenticationToken);
		
	}

	public AuthenticationToken getToken(User user) {
		
		return authenticationTokenRepo.findByUser(user);
	}
	
	public User getUser(String token) {
		final AuthenticationToken authenticationToken = authenticationTokenRepo.findByToken(token);
		if(Objects.isNull(token)) {
			return null;
		}
		return authenticationToken.getUser();
	}
	
	public void authenticate(String token) throws AuthenticationFailException {
		
		if(Objects.isNull(token)) {
			throw new AuthenticationFailException("token introuvable");
		}
		if(Objects.isNull(getUser(token))){
			throw new AuthenticationFailException("token invalide");
		}
	}

}
