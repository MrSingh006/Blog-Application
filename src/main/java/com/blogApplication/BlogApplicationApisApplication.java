package com.blogApplication;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApplicationApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationApisApplication.class, args);
	}
	/*
	 * This class contains @SpringBootApplication annotation so we van declare our
	 * custom beans in this class or we can create a separate class to declare all
	 * our beans
	 */
	
//	We will create bean of modelMapper class here
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * We will write the value that we want to encode in "" eg xyz this is written
		 * in database password column of user table
		 */
		System.out.println(this.passwordEncoder.encode("abc"));
		
	}
//	Encoded password that we got from console store it in database
	
//	abc = $2a$10$LAVAJrOrY.GEEClTUG/zquTF3FdYz83JgUvTE.FpGU25I8EzIlmOi

}
