package com.chandravaibhav98.jobportal.Service;

import com.chandravaibhav98.jobportal.Entity.JobSeekerProfile;
import com.chandravaibhav98.jobportal.Entity.RecruiterProfile;
import com.chandravaibhav98.jobportal.Entity.Users;
import com.chandravaibhav98.jobportal.Repository.JobSeekerProfileRepository;
import com.chandravaibhav98.jobportal.Repository.RecruiterProfileRepository;
import com.chandravaibhav98.jobportal.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {
	
	private final UsersRepository usersRepository;
	
	private final JobSeekerProfileRepository jobSeekerProfileRepository;
	
	private final RecruiterProfileRepository recruiterProfileRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UsersService ( UsersRepository usersRepository , JobSeekerProfileRepository jobSeekerProfileRepository ,
						  RecruiterProfileRepository recruiterProfileRepository , PasswordEncoder passwordEncoder ) {
		this.usersRepository = usersRepository;
		this.jobSeekerProfileRepository = jobSeekerProfileRepository;
		this.recruiterProfileRepository = recruiterProfileRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Users addNewUser ( Users users ) {
		
		users.setActive( true );
		users.setRegistrationDate( new Date( System.currentTimeMillis( ) ) );
		users.setPassword( passwordEncoder.encode( users.getPassword( ) ) );
		
		Users newUser = usersRepository.save( users );
		int userTypeId = users.getUserTypeId( ).getUserTypeId( );
		if ( userTypeId == 1 ) {
			recruiterProfileRepository.save( new RecruiterProfile( newUser ) );
		}
		else {
			jobSeekerProfileRepository.save( new JobSeekerProfile( newUser ) );
		}
		
		return newUser;
		
	}
	
	public Optional< Users > getUserByEmail ( String email ) {
		return usersRepository.findByEmail( email );
	}
	
	public Object getCurrentUserProfile ( ) {
		
		Authentication authentication = SecurityContextHolder.getContext( ).getAuthentication( );
		
		if ( ! ( authentication instanceof AnonymousAuthenticationToken ) ) {
			
			String username = authentication.getName( );
			Users users = usersRepository.findByEmail( username )
										 .orElseThrow( ( ) -> new UsernameNotFoundException( "Could not find user : " + username ) );
			
			int userId = users.getUserId( );
			if ( authentication.getAuthorities( ).contains( new SimpleGrantedAuthority( "Recruiter" ) ) ) {
				
				RecruiterProfile recruiterProfile = recruiterProfileRepository.findById( userId ).orElse( new RecruiterProfile( ) );
				return recruiterProfile;
				
			}
			else {
				
				JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById( userId ).orElse( new JobSeekerProfile( ) );
				return jobSeekerProfile;
				
			}
			
		}
		
		return null;
		
	}
	
	public Users getCurrentUser ( ) {
		
		Authentication authentication = SecurityContextHolder.getContext( ).getAuthentication( );
		
		if ( ! ( authentication instanceof AnonymousAuthenticationToken ) ) {
			
			String username = authentication.getName( );
			Users user = usersRepository.findByEmail( username )
										.orElseThrow( ( ) -> new UsernameNotFoundException( "Could not find user : " + username ) );
			
			return user;
			
		}
		
		return null;
		
	}
	
	public Users findByEmail ( String currentUsername ) {
		return usersRepository.findByEmail( currentUsername )
							  .orElseThrow( ( ) -> new UsernameNotFoundException( "User not found : " + currentUsername ) );
	}
}
