package com.chandravaibhav98.StackLibrary.Config;

import com.chandravaibhav98.StackLibrary.Entity.Book;
import com.chandravaibhav98.StackLibrary.Entity.Message;
import com.chandravaibhav98.StackLibrary.Entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {
	
	private String theAllowedOrigins = "https://localhost:3000";
	
	@Override
	public void configureRepositoryRestConfiguration ( RepositoryRestConfiguration config , CorsRegistry corsRegistry ) {
		
		HttpMethod[] theUnsupportedActions = { HttpMethod.POST , HttpMethod.PATCH , HttpMethod.DELETE , HttpMethod.PUT };
		
		config.exposeIdsFor( Book.class );
		config.exposeIdsFor( Review.class );
		config.exposeIdsFor( Message.class );
		
		disableHttpMethods( Book.class , config , theUnsupportedActions );
		disableHttpMethods( Review.class , config , theUnsupportedActions );
		disableHttpMethods( Message.class , config , theUnsupportedActions );
		
		corsRegistry.addMapping( config.getBasePath( ) + "/**" ).allowedOrigins( theAllowedOrigins );
		
	}
	
	private void disableHttpMethods ( Class theClass , RepositoryRestConfiguration config , HttpMethod[] theUnsupportedActions ) {
		
		config.getExposureConfiguration( ).forDomainType( theClass )
			  .withItemExposure( ( metadata , httpMethods ) -> httpMethods.disable( theUnsupportedActions ) )
			  .withCollectionExposure( ( metadata , httpMethods ) -> httpMethods.disable( theUnsupportedActions ) );
		
	}
	
}
