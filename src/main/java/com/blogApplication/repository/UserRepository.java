package com.blogApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.entity.User;
import java.util.List;


/*It is an interface which  extends JpaRepository interface, which has its implementation
 *  class SimpleJpaRepository which is already annotated with @Repository 
 *  and @Transactional annotation so we do not require to annotate our interface with
    these annotations*/

public interface UserRepository extends JpaRepository<User, Integer>{
	
	// we are making this method so that we can use email as user-name
	public Optional<User> findByEmail(String email);
}
