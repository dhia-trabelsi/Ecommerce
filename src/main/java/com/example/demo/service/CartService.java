package com.example.demo.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddToCartDto;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItemDto;
import com.example.demo.exceptions.CustomException;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepo;

@Service
public class CartService {
	@Autowired
	ProductService productService;
	
	@Autowired
	CartRepo cartRepo;

	public void addToCart(AddToCartDto addToCartDto, User user) {

	
		Product product = productService.findById(addToCartDto.getProductId());
		Cart cart = new Cart();
		cart.setProduct(product);
		cart.setUser(user);
		cart.setQuantity(addToCartDto.getQuantity());
		cart.setCreatedDate(new Date());
		
		cartRepo.save(cart);
	}

	public CartDto listCartItems(User user) {
		List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
		List<CartItemDto> cartItems = new ArrayList<>();
		double totalCost = 0;
		for(Cart cart: cartList) {
			CartItemDto cartItemDto = new CartItemDto(cart);
			totalCost = cartItemDto.getQuantity()*cart.getProduct().getPrice();
			cartItems.add(cartItemDto);
		}
		CartDto cartDto = new CartDto();
		cartDto.setTotalCost(totalCost);
		cartDto.setCartItems(cartItems);
		
		return cartDto;
	}

	public void deleteCartItem(Integer cartItemId, User user) {
		Optional<Cart> optionalCart = cartRepo.findById(cartItemId);
		
		if(optionalCart.isEmpty()) {
			throw new CustomException("cart item pas valide" + cartItemId );
		}
		
		Cart cart = optionalCart.get();
		
		if(cart.getUser() != user) {
			throw new CustomException("user pas compatible");
		}
		else
		cartRepo.delete(cart);
		
	}

	
}
