package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.SignInDto;
import com.example.demo.dto.SignInResponseDto;
import com.example.demo.dto.SignupDto;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public ResponseDto signup( @RequestBody SignupDto signupDto) {
		return userService.signUp(signupDto);
		}
	@PostMapping("/signin")
	public SignInResponseDto SignIn(@RequestBody SignInDto signInDto) {
		return userService.SignIn(signInDto);
		
		
	}

}
