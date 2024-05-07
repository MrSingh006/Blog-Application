package com.blogApplication.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	
	private int categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Category tile should not be less than 4 characters!!")
	private String categoryTitle;
	
	@NotBlank(message = "Category Description should not be empty")
	private String categoryDescription;
}
