package com.chandravaibhav98.StackLibrary.Controller;

import com.chandravaibhav98.StackLibrary.Entity.Message;
import com.chandravaibhav98.StackLibrary.RequestModels.AdminQuestionRequest;
import com.chandravaibhav98.StackLibrary.Service.MessageService;
import com.chandravaibhav98.StackLibrary.Utils.ExtractsJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin ( "https://localhost:3000" )
@RestController
@RequestMapping ( "/api/messages" )
public class MessagesController {
	
	private MessageService messageService;
	
	@Autowired
	public MessagesController ( MessageService messageService ) {
		this.messageService = messageService;
	}
	
	@PostMapping ( "/secure/add/message" )
	public void postMessage ( @RequestHeader ( value = "Authorization" ) String token , @RequestBody Message messageRequest ) {
		
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		messageService.postMessage( messageRequest , userEmail );
		
	}
	
	@PutMapping ( "/secure/admin/message" )
	public void putMessage (
			@RequestHeader ( value = "Authorization" ) String token ,
			@RequestBody AdminQuestionRequest adminQuestionRequest ) throws Exception {
		
		String userEmail = ExtractsJWT.payloadJWTExtraction( token , "\"sub\"" );
		String admin = ExtractsJWT.payloadJWTExtraction( token , "\"userType\"" );
		
		if ( admin == null || ! admin.equals( "admin" ) ) {
			throw new Exception( "Admin Access Only" );
		}
		
		messageService.putMessage( adminQuestionRequest , userEmail );
		
	}
	
}
