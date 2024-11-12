package com.chandravaibhav98.StackLibrary.Controller;

import com.chandravaibhav98.StackLibrary.RequestModels.AddBookRequest;
import com.chandravaibhav98.StackLibrary.Service.AdminService;
import com.chandravaibhav98.StackLibrary.Utils.ExtractsJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin ( "https://localhost:3000" )
@RestController
@RequestMapping ( "/api/admin" )
public class AdminController {
	
	private AdminService adminService;
	
	@Autowired
	public AdminController ( AdminService adminService ) {
		this.adminService = adminService;
	}
	
	@PostMapping ( "/secure/add/book" )
	public void postBook (
			@RequestHeader ( value = "Authorization" ) String token , @RequestBody AddBookRequest addBookRequest ) throws Exception {
		
		String admin = ExtractsJWT.payloadJWTExtraction( token , "\"userType\"" );
		
		if ( admin == null || ! admin.equals( "admin" ) ) {
			throw new Exception( "Admin Access Only" );
		}
		
		adminService.postBook( addBookRequest );
	}
	
	@PutMapping ( "/secure/increase/book/quantity" )
	public void increaseBookQuantity (
			@RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		String admin = ExtractsJWT.payloadJWTExtraction( token , "\"userType\"" );
		
		if ( admin == null || ! admin.equals( "admin" ) ) {
			throw new Exception( "Admin access only" );
		}
		
		adminService.increaseBookQuantity( bookId );
		
	}
	
	@PutMapping ( "/secure/decrease/book/quantity" )
	public void decreaseBookQuantity (
			@RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		String admin = ExtractsJWT.payloadJWTExtraction( token , "\"userType\"" );
		
		if ( admin == null || ! admin.equals( "admin" ) ) {
			throw new Exception( "Admin access only" );
		}
		
		adminService.decreaseBookQuantity( bookId );
		
	}
	
	@DeleteMapping ( "/secure/delete/book" )
	public void deleteBook ( @RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		String admin = ExtractsJWT.payloadJWTExtraction( token , "\"userType\"" );
		
		if ( admin == null || ! admin.equals( "admin" ) ) {
			throw new Exception( "Admin access only" );
		}
		
		adminService.deleteBook( bookId );
		
	}
}
