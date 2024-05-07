package com.blogApplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogApplication.entity.User;
import com.blogApplication.exception.ResourceNotFoundException;
import com.blogApplication.repository.UserRepository;

/*We are making this class so that we can use our custom user-name and password from database. This class
 * also implements UserDetailsService interface, this interface loads user specific data*/
@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by user-name, PS: we are using email as user-name
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(()->new ResourceNotFoundException(0,"Email : "+username,"User"));
		
		/*
		 * We have to return UserDetails but we are getting user to correct it we will
		 * make changes in User entity class
		 */
		return user;
	}

}
