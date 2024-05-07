package com.blogApplication.service;

import java.util.List;

import com.blogApplication.payload.PostDto;
import com.blogApplication.payload.PostResponse;

public interface PostService {
	
	/* Note: here we are returning post not its Dto class and inside createPost
	 *  we are adding userId and categoryId*/
	
	public PostDto createPost(PostDto postDto, int userId, int categoryId);
	
	public PostDto updatePost(PostDto postDto,int postDtoId);
	
	public PostDto getPostByID(int postDtoId);
	
	// we are changing return type to PostResponse instead of List<PostDto> for proper response
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	public void deletePost(int postDtoId);
	
	// get all posts by category
	public List<PostDto> getPostByCategory(int categoryId);
	
	// get all posts by user
	public List<PostDto> getPostByUser(int userId);
	
	// search posts using keyword
	public List<PostDto> searchPosts(String keyword);
	
}
