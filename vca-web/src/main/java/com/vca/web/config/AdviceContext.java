package com.vca.web.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.cordis.vca")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AdviceContext {

	
	@Bean("beanNameAutoProxyCreator")
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator()
	{
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		beanNameAutoProxyCreator.setBeanNames( "*Dao*", "*Service*");
		beanNameAutoProxyCreator.setInterceptorNames("beforeAdvisor","afterAdvisor", "throwsAdvisor");
		beanNameAutoProxyCreator.setProxyTargetClass(true);
		return beanNameAutoProxyCreator;
	}
	
	
//		@Bean
//		public UserDataManager   userDataManager()
//		{
//			return new UserDataManager();
//		}
	

}
