package com.blogApplication.service;

import com.blogApplication.payload.CommentDto;

public interface CommentService {
	public CommentDto createComment(CommentDto commentDto, int postId);
	public void deleteComment(int commentId);

}
