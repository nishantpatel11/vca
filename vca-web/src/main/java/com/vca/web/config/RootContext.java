package com.vca.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@PropertySource({"classpath:systemconfig.properties"})
@ContextConfiguration("classpath:log4j.xml")
@ComponentScan(basePackages = {"com.vca"})
@Import({ /* HibernateConfig.class, */ServiceContext.class, WebMvcConfig.class/* ,AdviceContext.class */})
public class RootContext {
	
	
//	  @Bean("reloadableResourceBundleMessageSource")
//	   @Scope("singleton")
//	   public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
//		   
//		   ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
//		   reloadableResourceBundleMessageSource.setBasename("classpath:global");
//		   reloadableResourceBundleMessageSource.setCacheSeconds(60);
//		   reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
//	   return reloadableResourceBundleMessageSource;
//	   }
}