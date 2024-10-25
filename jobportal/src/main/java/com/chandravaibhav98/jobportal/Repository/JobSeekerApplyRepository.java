package com.chandravaibhav98.jobportal.Repository;

import com.chandravaibhav98.jobportal.Entity.JobPostActivity;
import com.chandravaibhav98.jobportal.Entity.JobSeekerApply;
import com.chandravaibhav98.jobportal.Entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerApplyRepository extends JpaRepository< JobSeekerApply, Integer > {
	
	List< JobSeekerApply > findByUserId ( JobSeekerProfile userId );
	
	List< JobSeekerApply > findByJob ( JobPostActivity job );
	
}
