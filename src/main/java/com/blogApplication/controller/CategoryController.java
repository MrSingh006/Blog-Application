package com.blogApplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApplication.payload.ApiResponse;
import com.blogApplication.payload.CategoryDto;
import com.blogApplication.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryServiceImpl categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategoryDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryDtoId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryDtoId") int cId)
	{
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, cId);
		return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> getAllCategories = this.categoryService.getAllCategories();
		return ResponseEntity.ok(getAllCategories);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getcategoryById(@PathVariable("categoryId") int cid)
	{
		return ResponseEntity.ok(this.categoryService.getCategoryById(cid));
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int cid)
	{
		this.categoryService.deleteCategory(cid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true)
				, HttpStatus.OK);
	}


}
