package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.exceptions.ProductNotExistException;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productRepo;

	public void createProduct(ProductDto productDto, Category category) {
		Product product = new Product();
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setCategory(category);
		productRepo.save(product);
		
		
	}
	public ProductDto getProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setDescription(product.getDescription());
		productDto.setImageURL(product.getImageURL());
		productDto.setName(product.getName());
		productDto.setPrice(product.getPrice());
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setId(product.getId());
		return productDto;
		
	}

	public List<ProductDto> getAllProducts() {
		List<Product> allProducts = productRepo.findAll();
		List<ProductDto> productDtos = new ArrayList<>();
		for(Product product: allProducts) {
			
			productDtos.add(getProductDto(product));
		}
		return productDtos;
	}
	public void updateProduct(ProductDto productDto, Integer productId) throws Exception  {
		Optional<Product> optionalProduct = productRepo.findById(productId);
		if(!optionalProduct.isPresent()) {
			throw new Exception("produit introuvable");
		}
		Product product = optionalProduct.get();
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		
		productRepo.save(product);
	}
	public Product findById(Integer productId) throws ProductNotExistException {
		Optional<Product> optionalProduct =  productRepo.findById(productId);
		if(optionalProduct.isEmpty()) {
			throw new ProductNotExistException("produit id pas valide" + productId);
			
		}
		return optionalProduct.get();
		
	}
	
	

}
