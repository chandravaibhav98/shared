package com.chandravaibhav98.StackLibrary.Controller;

import com.chandravaibhav98.StackLibrary.Entity.Message;
import com.chandravaibhav98.StackLibrary.Service.MessageService;
import com.chandravaibhav98.StackLibrary.Utils.ExtractsJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin ( "http://localhost:3000" )
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
	
}
