package com.chandravaibhav98.jobportal.Service;

import com.chandravaibhav98.jobportal.Entity.RecruiterProfile;
import com.chandravaibhav98.jobportal.Entity.Users;
import com.chandravaibhav98.jobportal.Repository.RecruiterProfileRepository;
import com.chandravaibhav98.jobportal.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {
	
	private final RecruiterProfileRepository recruiterProfileRepository;
	
	private final UsersRepository usersRepository;
	
	
	@Autowired
	public RecruiterProfileService ( RecruiterProfileRepository recruiterProfileRepository , UsersRepository usersRepository ) {
		this.recruiterProfileRepository = recruiterProfileRepository;
		this.usersRepository = usersRepository;
	}
	
	public RecruiterProfile addNew ( RecruiterProfile recruiterProfile ) {
		return recruiterProfileRepository.save( recruiterProfile );
	}
	
	public RecruiterProfile getCurrentRecruiterProfile ( ) {
		
		Authentication authentication = SecurityContextHolder.getContext( ).getAuthentication( );
		
		if ( ! ( authentication instanceof AnonymousAuthenticationToken ) ) {
			
			String currentUsername = authentication.getName( );
			
			Users users = usersRepository.findByEmail( currentUsername )
										 .orElseThrow( ( ) -> new UsernameNotFoundException( "User not found : " + currentUsername ) );
			
			Optional< RecruiterProfile > recruiterProfile = getOne( users.getUserId( ) );
			
			return recruiterProfile.orElse( null );
			
		}
		else
			return null;
		
	}
	
	public Optional< RecruiterProfile > getOne ( Integer id ) {
		return recruiterProfileRepository.findById( id );
		
	}
	
}