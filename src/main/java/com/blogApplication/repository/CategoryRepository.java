package com.blogApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
