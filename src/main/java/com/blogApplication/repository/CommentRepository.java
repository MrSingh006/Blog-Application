package com.blogApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
