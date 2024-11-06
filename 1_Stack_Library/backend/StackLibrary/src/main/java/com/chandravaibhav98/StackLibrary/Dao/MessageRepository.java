package com.chandravaibhav98.StackLibrary.Dao;

import com.chandravaibhav98.StackLibrary.Entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageRepository extends JpaRepository< Message, Long > {
	
	Page< Message > findByUserEmail ( @RequestParam ( "user_email" ) String userEmail , Pageable pageable );
	
}
