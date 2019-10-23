package com.vca.web.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;


public class WebServletConfiguration implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		
		// Create the 'root' Spring application context
	      AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	      
	      rootContext.register(RootContext.class);
	      
	      
	      // Manage the lifecycle of the root application context
	      servletContext.addListener(new ContextLoaderListener(rootContext));
	     
	      
	      // Register and map the dispatcher servlet
	      ServletRegistration.Dynamic dispatcher =
	      servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
	      dispatcher.setLoadOnStartup(1);
	      dispatcher.addMapping("/");
		
	      
	      
	      
	      
	    FilterRegistration.Dynamic crosFilter = servletContext.addFilter("CorsFilter", new CorsFilter());
		crosFilter.setAsyncSupported(true);
		crosFilter.setInitParameter("cors.allowed.origins", "*");
		crosFilter.setInitParameter("cors.allowed.headers", "Authorization,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
	//	crosFilter.setInitParameter("cors.allowed.methods", "GET, OPTIONS, HEAD, PUT, POST");
		
		crosFilter.addMappingForUrlPatterns(null, false, "/*");
		
	
		
		
	      
		FilterRegistration.Dynamic filter = servletContext.addFilter("shiroFilter", new DelegatingFilterProxy());
		filter.setAsyncSupported(true);
		filter.setInitParameter("targetFilterLifecycle", "true");

		filter.addMappingForUrlPatterns(null, false, "/*");

	}
	
	
	 

}