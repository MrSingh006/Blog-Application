package com.blogApplication.service;

import java.util.List;

import com.blogApplication.payload.CategoryDto;

public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	public CategoryDto updateCategory(CategoryDto categoryDto, int catDtoId);
	
	public CategoryDto getCategoryById(int catDtoId);
	
	public List<CategoryDto> getAllCategories();
	
	public void deleteCategory(int catDtoId);

}
