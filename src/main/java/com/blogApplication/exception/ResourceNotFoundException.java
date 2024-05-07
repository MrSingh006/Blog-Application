package com.blogApplication.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	
	private String resourceName;
	private String resourceValue;
	private long resourceId;
	
	public ResourceNotFoundException(long resourceId, String resourceName, String resourceValue) {
		super(String.format("%s not found with %s : %s", resourceValue,resourceName,resourceId));
		this.resourceName = resourceName;
		this.resourceValue = resourceValue;
		this.resourceId = resourceId;
	}
	
	

}
