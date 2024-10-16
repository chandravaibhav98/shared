package com.chandravaibhav98.jobportal.Service;

import com.chandravaibhav98.jobportal.Entity.UsersType;
import com.chandravaibhav98.jobportal.Repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {
	
	private final UsersTypeRepository usersTypeRepository;
	
	@Autowired
	public UsersTypeService ( UsersTypeRepository usersTypeRepository ) {
		this.usersTypeRepository = usersTypeRepository;
	}
	
	public List< UsersType > getAll ( ) {
		return usersTypeRepository.findAll( );
	}
}
