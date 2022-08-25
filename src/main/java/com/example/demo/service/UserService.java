package com.example.demo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.SignInDto;
import com.example.demo.dto.SignInResponseDto;
import com.example.demo.dto.SignupDto;
import com.example.demo.exceptions.AuthenticationFailException;
import com.example.demo.exceptions.CustomException;
import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuthenticationTokenService authenticationTokenService;
	
	@Transactional
	public ResponseDto signUp(SignupDto signupDto) {
		if(Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))) {
			throw new CustomException("utilisateur existe deja");
		}
		String encryptedPassword = signupDto.getPassword();
		try {
			encryptedPassword = hashPassword(signupDto.getPassword());
		}catch(NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				
			}
		
		User user = new User(signupDto.getFirstName(),signupDto.getLastName(),signupDto.getEmail(),encryptedPassword);

		userRepo.save(user);
		
		final AuthenticationToken authenticationToken = new AuthenticationToken(user);
		authenticationTokenService.SaveConfirmationToken(authenticationToken);
		
		
		
		
		ResponseDto responseDto = new ResponseDto("success","utilisateur cree");
		return responseDto;
	}

	private String hashPassword(String password) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String hash = DatatypeConverter
				.printHexBinary(digest).toUpperCase();
		return hash;
	}

	public SignInResponseDto SignIn(SignInDto signInDto) {
		
		User user = userRepo.findByEmail(signInDto.getEmail());
		if(Objects.isNull(user)) {
			throw new AuthenticationFailException("utilisateur pas valide");
		}
		try {
			if(!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
				throw new AuthenticationFailException("mot de passe est incorrecte");
			}
			}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			}
		
		AuthenticationToken token = authenticationTokenService.getToken(user);
		
		if(Objects.isNull(token)) {
			throw new CustomException("token introuvable");
		}
		
		return new SignInResponseDto("success",token.getToken());
	}

}
