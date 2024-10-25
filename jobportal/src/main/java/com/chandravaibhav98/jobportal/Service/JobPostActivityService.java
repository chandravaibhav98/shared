package com.chandravaibhav98.jobportal.Service;

import com.chandravaibhav98.jobportal.Entity.*;
import com.chandravaibhav98.jobportal.Repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JobPostActivityService {
	
	private final JobPostActivityRepository jobPostActivityRepository;
	
	@Autowired
	public JobPostActivityService ( JobPostActivityRepository jobPostActivityRepository ) {
		this.jobPostActivityRepository = jobPostActivityRepository;
	}
	
	public JobPostActivity addNew ( JobPostActivity jobPostActivity ) {
		return jobPostActivityRepository.save( jobPostActivity );
	}
	
	public List< RecruiterJobs > getRecruiterJobs ( int recruiter ) {
		
		List< RecruiterJobsInterface > recruiterJobsInterface = jobPostActivityRepository.getRecruiterJobs( recruiter );
		
		List< RecruiterJobs > recruiterJobsInterfaceList = new ArrayList<>( );
		
		for ( RecruiterJobsInterface rec : recruiterJobsInterface ) {
			
			JobLocation loc = new JobLocation( rec.getLocationId( ) , rec.getCity( ) , rec.getState( ) , rec.getCountry( ) );
			JobCompany comp = new JobCompany( rec.getCompanyId( ) , rec.getName( ) , "" );
			
			recruiterJobsInterfaceList.add(
					new RecruiterJobs( rec.getTotalCandidates( ) , rec.getJob_post_id( ) , rec.getJob_title( ) , loc , comp ) );
			
			
		}
		
		return recruiterJobsInterfaceList;
		
	}
	
	public JobPostActivity getOne ( int id ) {
		
		return jobPostActivityRepository.findById( id ).orElseThrow( ( ) -> new RuntimeException( "Job not found" ) );
		
	}
	
	public List< JobPostActivity > getAll ( ) {
		
		return jobPostActivityRepository.findAll( );
		
	}
	
	public List< JobPostActivity > search ( String job , String location , List< String > type , List< String > remote ,
											LocalDate searchDate ) {
		
		return Objects.isNull( searchDate ) ? jobPostActivityRepository.searchWithoutDate( job , location , remote , type ) :
				jobPostActivityRepository.search( job , location , remote , type , searchDate );
		
	}
}