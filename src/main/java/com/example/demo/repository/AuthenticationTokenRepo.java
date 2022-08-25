package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AuthenticationToken;
import com.example.demo.model.User;


@Repository
public interface AuthenticationTokenRepo  extends JpaRepository<AuthenticationToken , Integer> {

	 AuthenticationToken findByUser(User user);
	 AuthenticationToken findByToken(String token);
	 
}
