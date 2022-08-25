package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
		categoryService.createCategory(category);
		return new ResponseEntity<>(new ApiResponse (true,"UN NOUVEAU CATEGORIE EST CREE"), HttpStatus.CREATED);
		
	}
	
	@GetMapping("/list")
	public  List<Category> listCategory() {
		return categoryService.listCategory();
	}
	
	@PostMapping("/update/{categoryId}")
	public ResponseEntity<ApiResponse> updateGategory(@PathVariable("categoryId") int categoryId,@RequestBody Category category ) {
		if(!categoryService.findById(categoryId)) {
			return new ResponseEntity<>(new ApiResponse (false,"categorie introuvable"), HttpStatus.NOT_FOUND);

		}
		categoryService.editCategory(categoryId, category);
		return new ResponseEntity<>(new ApiResponse (true,"mise a jour complet"), HttpStatus.OK);
		
	}

}
