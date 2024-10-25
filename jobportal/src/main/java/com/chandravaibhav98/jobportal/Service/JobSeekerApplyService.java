package com.chandravaibhav98.jobportal.Service;

import com.chandravaibhav98.jobportal.Entity.JobPostActivity;
import com.chandravaibhav98.jobportal.Entity.JobSeekerApply;
import com.chandravaibhav98.jobportal.Entity.JobSeekerProfile;
import com.chandravaibhav98.jobportal.Repository.JobSeekerApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyService {
	
	private final JobSeekerApplyRepository jobSeekerApplyRepository;
	
	@Autowired
	public JobSeekerApplyService ( JobSeekerApplyRepository jobSeekerApplyRepository ) {
		this.jobSeekerApplyRepository = jobSeekerApplyRepository;
	}
	
	public List< JobSeekerApply > getCandidatesJobs ( JobSeekerProfile userAccountId ) {
		return jobSeekerApplyRepository.findByUserId( userAccountId );
	}
	
	public List< JobSeekerApply > getJobCandidates ( JobPostActivity job ) {
		return jobSeekerApplyRepository.findByJob( job );
	}
	
	public void addNew ( JobSeekerApply jobSeekerApply ) {
		jobSeekerApplyRepository.save( jobSeekerApply );
	}
	
}
