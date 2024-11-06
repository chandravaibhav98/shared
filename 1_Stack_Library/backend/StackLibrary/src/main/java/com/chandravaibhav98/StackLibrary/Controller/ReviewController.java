package com.chandravaibhav98.StackLibrary.Controller;

import com.chandravaibhav98.StackLibrary.RequestModels.ReviewRequest;
import com.chandravaibhav98.StackLibrary.Service.ReviewService;
import com.chandravaibhav98.StackLibrary.Utils.ExtractsJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin ( "http://localhost:3000" )
@RestController
@RequestMapping ( "/api/reviews" )
public class ReviewController {
	
	private ReviewService reviewService;
	
	public ReviewController ( ReviewService reviewService ) {
		this.reviewService = reviewService;
	}
	
	@PostMapping ( "/secure" )
	public void postReview (
			@RequestHeader ( value = "Authorization" ) String token , @RequestBody ReviewRequest reviewRequest ) throws Exception {
		
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		
		if ( userEmail == null ) {
			throw new Exception( "User Email is missing" );
		}
		
		reviewService.postReview( userEmail , reviewRequest );
		
	}
	
	@GetMapping ( "/secure/user/book" )
	public Boolean reviewBookByUser (
			@RequestHeader ( value = "Authorization" ) String token , @RequestParam Long bookId ) throws Exception {
		
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		
		if ( userEmail == null ) {
			throw new Exception( "User email is missing" );
		}
		
		return reviewService.userReviewListed( userEmail , bookId );
		
	}
	
}
