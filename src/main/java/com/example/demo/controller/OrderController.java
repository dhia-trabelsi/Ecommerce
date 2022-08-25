package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CheckoutItemDto;
import com.example.demo.dto.StripeResponse;
import com.example.demo.service.AuthenticationTokenService;
import com.example.demo.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;


@RestController
@RequestMapping("/order")
public class OrderController{

	@Autowired
	AuthenticationTokenService authenticationTokenService;
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/create-checkout-session")
	public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkouItemDtoList) throws StripeException{
		
		Session session = orderService.createSession(checkouItemDtoList);
		StripeResponse stripeResponse = new StripeResponse(session.getId());
		
		return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
	}
}
