package com.blogApplication.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private int categoryId;
	
	@Column(name= "category_Title", length = 100, nullable = false)
	private String categoryTitle;
	
	@Column(name ="category_desc")
	private String categoryDescription;
	
	/*
	 * One category can have many posts so we are using a list here and annotating
	 * it with @OneToMany annotation. We are using mappedBy to write the name of column
	 * with which it is mapped in Post class and mentioning cascade will change child
	 * if parent is changed
	 */
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

}
