package resources;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("rest")
public class RestApplication extends ResourceConfig {
	
	public RestApplication() {
		
		//super(io.swagger.jaxrs.listing.ApiListingResource.class,io.swagger.jaxrs.listing.SwaggerSerializers.class);
		packages("model;resources");
		//this.register(JacksonFeature.class);
		//BeanConfig bean=new BeanConfig();
		//bean.setVersion("1.0.0");
		//bean.setBasePath("rest");
		//bean.setSchemes(new String[] {"http"});
		//bean.setScan(true);		
		
		
		
	}
	
	
	

}


