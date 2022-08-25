package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.AddToCartDto;
import com.example.demo.dto.CartDto;
import com.example.demo.model.User;
import com.example.demo.service.AuthenticationTokenService;
import com.example.demo.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	AuthenticationTokenService authenticationTokenService;
	

	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
												@RequestParam("token") String token ){
		
		authenticationTokenService.authenticate(token);
		User user = authenticationTokenService.getUser(token);

		
		cartService.addToCart(addToCartDto, user);
		return new ResponseEntity<>(new ApiResponse(true,"ajouter"),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){
		
		authenticationTokenService.authenticate(token);
		User user = authenticationTokenService.getUser(token);
		
		 CartDto cartDto = cartService.listCartItems(user);
		 return new ResponseEntity<>(cartDto, HttpStatus.OK);

	}
	
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
														@RequestParam("token") String token ){
		authenticationTokenService.authenticate(token);
		User user = authenticationTokenService.getUser(token);
		cartService.deleteCartItem(itemId, user);
		return new ResponseEntity<>(new ApiResponse(true,"item supprime"),HttpStatus.OK);
	}

}
