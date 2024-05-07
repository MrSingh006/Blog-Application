package com.blogApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApplication.payload.ApiResponse;
import com.blogApplication.payload.CommentDto;
import com.blogApplication.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto, 
			@PathVariable int postId)
	{
		CommentDto savedComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(savedComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully", true)
				,HttpStatus.OK);
	}

}
