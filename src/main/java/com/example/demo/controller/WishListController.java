package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import com.example.demo.service.AuthenticationTokenService;
import com.example.demo.service.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
	
	@Autowired
	WishListService wishListService;
	
	@Autowired
	AuthenticationTokenService authenticationTokenService; 
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,@RequestParam("token") String token){
		
		authenticationTokenService.authenticate(token);
		User user = authenticationTokenService.getUser(token);
		
		WishList wishList = new WishList(user, product);
		
		wishListService.createWishList(wishList);
		ApiResponse apiResponse = new ApiResponse(true,"ajouteé");
		
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{token}")
	public ResponseEntity<List<ProductDto>>getWishList(@PathVariable("token") String token){
		
		authenticationTokenService.authenticate(token);
		
		User user = authenticationTokenService.getUser(token);
		
		List<ProductDto> productDtos = wishListService.getWishListForUser(user);
		
	return new ResponseEntity<>(productDtos, HttpStatus.OK);
	}

}
