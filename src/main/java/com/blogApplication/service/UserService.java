package com.blogApplication.service;

import java.util.List;

import com.blogApplication.entity.User;
import com.blogApplication.payload.UserDto;

public interface UserService {
	/*
	 * We can write like this also no problem but we will not write like this. We
	 * will create new UserDto(Data Transfer Object) class inside payLoad package
	 * and we will expose that class. Entity class is only to put data into database.
	 */
//	public User createUser();
	
	public UserDto createUser(UserDto userDto);
	public UserDto updateUser(UserDto userDto,int userDtoId);
	public UserDto getUserById(int userDtoId);
	public List<UserDto> getAllUsers();
	public void deleteUser(int userDtoId);

}
