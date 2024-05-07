package com.blogApplication.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy;

import com.blogApplication.entity.Category;
import com.blogApplication.entity.Post;
import com.blogApplication.entity.User;
import com.blogApplication.exception.ResourceNotFoundException;
import com.blogApplication.payload.PostDto;
import com.blogApplication.payload.PostResponse;
import com.blogApplication.repository.CategoryRepository;
import com.blogApplication.repository.PostRepository;
import com.blogApplication.repository.UserRepository;
import com.blogApplication.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/* In order to get user and category we will make their repo objects as well */
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		/* We will fetch user and category as well */
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException(userId,"ID","User"));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException(categoryId,"Id","Category"));
		
		/*
		 * in postDto we have only 2 variables but in post we have many, so we need to
		 * set them ourselves
		 */
		
		Post post = this.modelMapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = this.postRepo.save(post);
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postDtoId) {
		Post existingPost = this.postRepo.findById(postDtoId)
				.orElseThrow(()->new ResourceNotFoundException(postDtoId, "ID", "Post"));
		
		existingPost.setPostTitle(postDto.getPostTitle());
		existingPost.setContent(postDto.getContent());
		
		Post upadatedPost = this.postRepo.save(existingPost);
		
		return this.modelMapper.map(upadatedPost, PostDto.class);
	}

	@Override
	public PostDto getPostByID(int postDtoId) {
		Post singlePost = this.postRepo.findById(postDtoId)
				.orElseThrow(()->new ResourceNotFoundException(postDtoId, "ID", "Post"));
		return this.modelMapper.map(singlePost, PostDto.class);
	}

	@Override
	// we are changing return type to PostResponse instead of List<PostDto> for proper response
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// List<Post> getAllPosts = this.postRepo.findAll();
		
		/* Here we will apply pagination to get posts */
		
		Sort sort = sortDir.equalsIgnoreCase("asc")?
				Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(page);
		List<Post> getAllPosts = pagePost.getContent();
		
		List<PostDto> getAllPostDto = getAllPosts.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		/*
		 * now we have to return postResponse for that we need to add all properties.
		 * All properties are inside pagePost
		 */
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(getAllPostDto); 
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public void deletePost(int postDtoId) {
		Post existingPost = this.postRepo.findById(postDtoId)
				.orElseThrow(()-> new ResourceNotFoundException(postDtoId, "ID", "Post"));
		this.postRepo.delete(existingPost);
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException(categoryId, "Id", "Category"));
		
		List<Post> postList = this.postRepo.findByCategory(category);
		
		List<PostDto> postDtoList = postList.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException(userId, "Id", "User"));
		
		List<Post> postList  = this.postRepo.findByUser(user);
		
		List<PostDto> postDtoList = postList.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtoList;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
//		List<Post> postList = this.postRepo.findByPostTitleContaining(keyword);
		
//		Modification done for custom query
		List<Post> postList = this.postRepo.searchByPostTitle("%"+keyword+"%");
		
		List<PostDto> postDtoList = postList.stream()
				.map(post->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtoList;
	}
	
}
