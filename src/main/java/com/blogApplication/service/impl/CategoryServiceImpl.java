package com.blogApplication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.entity.Category;
import com.blogApplication.exception.ResourceNotFoundException;
import com.blogApplication.payload.CategoryDto;
import com.blogApplication.repository.CategoryRepository;
import com.blogApplication.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = dtoToCategory(categoryDto);
		Category savedCategory = this.catRepo.save(category);
		return categoryToDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int catDtoId) {
		Category existingCategory = this.catRepo.findById(catDtoId)
				.orElseThrow(()->new ResourceNotFoundException(catDtoId, "Id", "Category"));
		
		existingCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		existingCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.catRepo.save(existingCategory);
		
		return categoryToDto(updatedCategory);
	}

	@Override
	public CategoryDto getCategoryById(int catDtoId) {
		Category singleCategory = this.catRepo.findById(catDtoId)
				.orElseThrow(()->new ResourceNotFoundException(catDtoId, "Id", "Category"));
		return categoryToDto(singleCategory);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categoryList = this.catRepo.findAll();
		List<CategoryDto> categoryDtoList = categoryList.stream()
				.map((category) -> categoryToDto(category))
				.collect(Collectors.toList());
		return categoryDtoList;
	}

	@Override
	public void deleteCategory(int catDtoId) {
		Category existingCategory = this.catRepo.findById(catDtoId)
				.orElseThrow(()->new ResourceNotFoundException(catDtoId, "Category", "Id"));
		this.catRepo.delete(existingCategory);
	}
	
	private Category dtoToCategory(CategoryDto categoryDto)
	{
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	private CategoryDto categoryToDto(Category category)
	{
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
