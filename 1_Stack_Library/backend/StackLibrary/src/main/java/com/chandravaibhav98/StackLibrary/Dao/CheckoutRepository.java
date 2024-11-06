package com.chandravaibhav98.StackLibrary.Dao;

import com.chandravaibhav98.StackLibrary.Entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository< Checkout, Long > {
	
	Checkout findByUserEmailAndBookId ( String userEmail , Long bookId );
	
	List< Checkout > findBooksByUserEmail ( String userEmail );
	
}
