package com.blogApplication.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
/*
 * We are implementing "UserDetails" class of spring framework so that we can
 * return UserDetails in "CustomUserDetailService" and add and modify its unimplemented methods
 */
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private int id;
	
	@Column(name="user_name")
	private String name;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="user_email")
	private String email;
	
	@Column(name="user_about")
	private String about;
	
	
	/* See category class for description */	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	
	/*
	 * We will create many to many relation with role class as one user can have
	 * many roles and vice-versa
	 */
	
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="user_role", 
	joinColumns = @JoinColumn(name ="user", referencedColumnName = "user_id"),
	inverseJoinColumns = @JoinColumn(name ="role", referencedColumnName = "role_id"))
	private Set<Role> roles =new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// We will take each and every role and change it in granted authority
		List<SimpleGrantedAuthority> authorities = this.roles.stream()
				.map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;
	}

	@Override
	public String getUsername() {
		// we are returning email as it is our userName
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
