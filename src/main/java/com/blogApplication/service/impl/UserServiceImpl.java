package com.blogApplication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.entity.User;
import com.blogApplication.exception.ResourceNotFoundException;
import com.blogApplication.payload.UserDto;
import com.blogApplication.repository.UserRepository;
import com.blogApplication.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userDtoId) {
		User existingUser  = userRepo.findById(userDtoId)
				.orElseThrow(()->new ResourceNotFoundException(userDtoId,"Id","User"));
		
		existingUser.setName(userDto.getName());
		existingUser.setPassword(userDto.getPassword());
		existingUser.setEmail(userDto.getEmail());
		existingUser.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(existingUser);
		
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(int userDtoId) {
		User singleUser  = userRepo.findById(userDtoId)
				.orElseThrow(()->new ResourceNotFoundException(userDtoId,"Id","User"));
		return userToDto(singleUser);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList  = this.userRepo.findAll();
		//convert userList to userDtoList
		List<UserDto> userDtoList = userList.stream()
				.map(user->userToDto(user))
				.collect(Collectors.toList());
		return userDtoList;
	}

	@Override
	public void deleteUser(int userDtoId) {
		User existingUser = this.userRepo.findById(userDtoId)
				.orElseThrow(()->new ResourceNotFoundException(userDtoId,"User","ID"));
		this.userRepo.delete(existingUser);
	}
	
//	Creating new method to convert Dto to user
	private User dtoToUser(UserDto userDto)
	{
		/*
		 * ModelMapper map method takes 2 arguments 1 class which we want to convert and
		 * 2 to which we want to convert
		 */
		User user = this.modelMapper.map(userDto, User.class);
		
		
//		We were converting from Dto to user manually now we will use modelMapper to do so
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
		
		return user;
	}
	
//	Creating new method to convert user to Dto
	private UserDto userToDto(User user)
	{
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
//		We were converting from User to Dto manually now we will use modelMapper to do so
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
	}

}
