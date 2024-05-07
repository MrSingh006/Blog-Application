package com.blogApplication.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApplication.config.AppConstants;
import com.blogApplication.payload.ApiResponse;
import com.blogApplication.payload.PostDto;
import com.blogApplication.payload.PostResponse;
import com.blogApplication.service.FileService;
import com.blogApplication.service.impl.PostServiceImpl;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private FileService fileService;
	
//	this "project.image" is from application.properties file
	@Value("${project.image}")
	private String path;
	
	// Create User
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createdPost(@RequestBody PostDto postDto,
			@PathVariable int userId,
			@PathVariable int categoryId)
	{
		PostDto createdPost =  this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	//Update Post
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postId)
	{
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK); 
	}
	
	// Get by User
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getpostsByUser(@PathVariable int userId)
	{
		List<PostDto> allPosts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(allPosts,HttpStatus.OK);
	}
	
	// Get by Category
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getpostsByCategory(@PathVariable int categoryId)
	{
		List<PostDto> allPosts = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(allPosts,HttpStatus.OK);
	}
	
	// Get post By Id
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId)
	{
		PostDto singelPost = this.postService.getPostByID(postId);
		return new ResponseEntity<PostDto>(singelPost,HttpStatus.OK);
	}
	
	// get all Posts
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
	{
		PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	// Delete Post
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted Successfully", true)
				,HttpStatus.OK);
	}
	
	//search Posts
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword)
	{
		List<PostDto> result  = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable int postId) throws IOException
	{
		PostDto postDto = this.postService.getPostByID(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	// method to serve files
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
