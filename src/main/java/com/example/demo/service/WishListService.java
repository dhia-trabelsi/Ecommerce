package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import com.example.demo.repository.WishListRepo;

@Service
public class WishListService {
	
	@Autowired
	WishListRepo wishListRepo;
	
	@Autowired
	ProductService productService;

	public void createWishList(WishList wishList) {
		
		wishListRepo.save(wishList);
	}

	public List<ProductDto> getWishListForUser(User user) {
		List<WishList> wishLists = wishListRepo.findAllByUserOrderByCreatedDateDesc(user);
		List<ProductDto> productDtos = new ArrayList<>();
		for (WishList wishList: wishLists) {
			productDtos.add(productService.getProductDto(wishList.getProduct()));
		}
		return productDtos;
	}

}
