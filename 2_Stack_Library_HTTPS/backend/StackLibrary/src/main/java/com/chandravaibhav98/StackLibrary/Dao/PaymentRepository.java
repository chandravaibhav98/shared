package com.chandravaibhav98.StackLibrary.Dao;

import com.chandravaibhav98.StackLibrary.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository< Payment, Long > {
	
	Payment findByUserEmail ( String userEmail );
	
}
