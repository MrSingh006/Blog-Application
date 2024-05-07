package com.blogApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogApplication.entity.Category;
import com.blogApplication.entity.Post;
import com.blogApplication.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
//	In this we will create custom finder methods according to our mapping
	
//	This method will give all posts associated with given user
	public List<Post> findByUser(User user);
	
//	This method will give all posts associated with given category
	public List<Post> findByCategory(Category category);
	
//	This method will allow us to search posts according to our title
	/*
	 * Note : Write the name very carefully "findByPostTitleContaining" there should
	 * be no spelling mistake in this word sequence should be same and since we are
	 * searching by postTitle we have to write "PostTitle" in name as it is
	 * otherwise code will not compile and it will throw error. If we want to search 
	 * by some other means then we need to write that name in method name.
	 */
	
	// original working
	public List<Post> findByPostTitleContaining(String postTitle);
	
	
//	if we want to write our specific query we do it like below
//	Note : if we do not write it still works fine
	@Query(value = "select * from Post where post_Title like :key",nativeQuery = true)
	public List<Post> searchByPostTitle(@Param("key") String postTitle);

}
