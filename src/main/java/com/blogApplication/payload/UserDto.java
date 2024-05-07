package com.blogApplication.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	/* We validate the class in which data is coming in our case it is dto class */
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 characters!!")
	private String name;
	
	@NotEmpty
	@Size(min =3, max =10, message = "Password must be min 3 & max 10 chars!!")
//	@Pattern(regexp = )
	private String password;
	
	@Email(message = "Email address is not valid!!")
	private String email;
	
	@NotEmpty
	private String about;

}
