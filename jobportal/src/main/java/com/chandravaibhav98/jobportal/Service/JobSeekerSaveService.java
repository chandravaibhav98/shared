package com.chandravaibhav98.jobportal.Service;

import com.chandravaibhav98.jobportal.Entity.JobPostActivity;
import com.chandravaibhav98.jobportal.Entity.JobSeekerProfile;
import com.chandravaibhav98.jobportal.Entity.JobSeekerSave;
import com.chandravaibhav98.jobportal.Repository.JobSeekerSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {
	
	private final JobSeekerSaveRepository jobSeekerSaveRepository;
	
	public JobSeekerSaveService ( JobSeekerSaveRepository jobSeekerSaveRepository ) {
		this.jobSeekerSaveRepository = jobSeekerSaveRepository;
	}
	
	public List< JobSeekerSave > getCandidatesJob ( JobSeekerProfile userAccountId ) {
		return jobSeekerSaveRepository.findByUserId( userAccountId );
	}
	
	public List< JobSeekerSave > getJobCandidates ( JobPostActivity job ) {
		return jobSeekerSaveRepository.findByJob( job );
	}
	
	public void addNew ( JobSeekerSave jobSeekerSave ) {
		jobSeekerSaveRepository.save( jobSeekerSave );
	}
}
