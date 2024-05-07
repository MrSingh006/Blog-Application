package com.blogApplication.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*This class is a sub resource which has two parent resources user and category class*/

@Entity
@Table(name ="post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name ="post_id")
	private int postId;
	
	@Column(name="post_title",length=100,nullable = false)
	private String postTitle;
	
	@Column(name="content")
	private String content;
	
	@Column(name="image_name")
	private String imageName;
	
	@Column(name="added_date")
	private Date addedDate;
	
	/*
	 * See diagram we need to map post to category and user, such that one post can
	 * have one category but one category can have multiple posts, same with user so 
	 * to do that we are creating a field of category and user here. @ManyToOne b/c
	 * many posts can have one category and user. @JoinColumn is to rename column 
	 * in post table of database.
	 */
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	

}
