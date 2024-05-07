package com.blogApplication.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.entity.Comment;
import com.blogApplication.entity.Post;
import com.blogApplication.exception.ResourceNotFoundException;
import com.blogApplication.payload.CommentDto;
import com.blogApplication.repository.CommentRepository;
import com.blogApplication.repository.PostRepository;
import com.blogApplication.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepository postRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Post post =this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException(postId, "Id", "Post"));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment getComment = this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException(commentId, "Id", "Comment"));
		this.commentRepo.delete(getComment);
	}

}
