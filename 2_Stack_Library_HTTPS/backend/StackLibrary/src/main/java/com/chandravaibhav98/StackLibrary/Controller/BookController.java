package com.chandravaibhav98.StackLibrary.Controller;

import com.chandravaibhav98.StackLibrary.Entity.Book;
import com.chandravaibhav98.StackLibrary.ResponseModels.ShelfCurrentLoansResponse;
import com.chandravaibhav98.StackLibrary.Service.BookService;
import com.chandravaibhav98.StackLibrary.Utils.ExtractsJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin ( "https://localhost:3000" )
@RestController
@RequestMapping ( "/api/books" )
public class BookController {
	
	private BookService bookService;
	
	@Autowired
	public BookController ( BookService bookService ) {
		this.bookService = bookService;
	}
	
	@PutMapping ( "/secure/checkout" )
	public Book checkoutBook ( @RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		//		String userEmail = "test1@test.com";
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		return bookService.checkoutBook( userEmail , bookId );
		
	}
	
	@GetMapping ( "/secure/ischeckedout/byuser" )
	public Boolean checkoutBookByUser (
			@RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		//		String userEmail = "test1@test.com";
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		return bookService.checkoutBookByUser( userEmail , bookId );
		
	}
	
	@GetMapping ( "/secure/currentloans/count" )
	public int currentLoansCount ( @RequestHeader ( value = "Authorization" ) String token ) {
		
		//		String userEmail = "test1@test.com";
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		return bookService.currentLoansCount( userEmail );
		
	}
	
	@GetMapping ( "/secure/currentloans" )
	public List< ShelfCurrentLoansResponse > currentLoans ( @RequestHeader ( value = "Authorization" ) String token ) throws Exception {
		
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		return bookService.currentLoans( userEmail );
		
	}
	
	@PutMapping ( "/secure/return" )
	public void returnBook ( @RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		bookService.returnBook( userEmail , bookId );
		
	}
	
	@PutMapping ( "/secure/renew/loan" )
	public void renewLoan ( @RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		bookService.renewLoan( userEmail , bookId );
		
	}
	
}
