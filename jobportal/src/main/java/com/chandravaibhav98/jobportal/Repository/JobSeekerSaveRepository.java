package com.chandravaibhav98.jobportal.Repository;

import com.chandravaibhav98.jobportal.Entity.JobPostActivity;
import com.chandravaibhav98.jobportal.Entity.JobSeekerProfile;
import com.chandravaibhav98.jobportal.Entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository< JobSeekerSave, Integer > {
	
	List< JobSeekerSave > findByUserId ( JobSeekerProfile userAccountId );
	
	List< JobSeekerSave > findByJob ( JobPostActivity job );
	
}
