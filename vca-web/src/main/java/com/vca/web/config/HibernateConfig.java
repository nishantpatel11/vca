package com.vca.web.config;


import java.io.Serializable;
import java.util.Properties;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;


@Configuration
@EnableTransactionManagement
public class HibernateConfig<T, PK extends Serializable>{


	@Autowired
	private Environment env;


	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		//  factoryBean.setDataSource(getDataSource());

		Properties props=new Properties();
		props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.put("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));
		props.put("hibernate.c3p0.acquire_increment", env.getProperty("hibernate.c3p0.acquire_increment"));
		props.put("hibernate.c3p0.idle_test_period", env.getProperty("hibernate.c3p0.idle_test_period"));
		props.put("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		props.put("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
		props.put("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));


		props.put("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		props.put("hibernate.default_batch_fetch_size", env.getProperty("hibernate.default_batch_fetch_size"));
		props.put("hibernate.cache.region.factory_class", env.getProperty("hibernate.cache.region.factory_class"));
		props.put("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));
		props.put("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
		props.put("hibernate.cache.use_structured_entries", env.getProperty("hibernate.cache.use_structured_entries"));
		props.put("hibernate.cache.use_minimal_puts", env.getProperty("hibernate.cache.use_minimal_puts"));


		props.put("hibernate.connection.driver_class", env.getProperty("hibernate.connection.driver_class"));
		props.put("hibernate.connection.url", env.getProperty("hibernate.connection.url"));
		props.put("hibernate.connection.username", env.getProperty("hibernate.connection.username"));
		props.put("hibernate.connection.password", env.getProperty("hibernate.connection.password"));


		factoryBean.setHibernateProperties(props);
		factoryBean.setAnnotatedClasses(
				);

		return factoryBean;
	}



	@Bean("transactionManager")
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}


	@Bean
	@Scope("prototype")
	public TransactionProxyFactoryBean txAbstractProxy()
	{
		TransactionProxyFactoryBean transactionProxyFactoryBean = new TransactionProxyFactoryBean();
		transactionProxyFactoryBean.setTransactionManager(transactionManager());

		Properties transactionAttributes = new Properties();
		transactionAttributes.put("*", "PROPAGATION_REQUIRED");
		transactionProxyFactoryBean.setTransactionAttributes(transactionAttributes);
		transactionProxyFactoryBean.setTarget(abstractDao());
		// Finish FactoryBean setup
		transactionProxyFactoryBean.afterPropertiesSet();

		return transactionProxyFactoryBean;
	}



//	@Bean(name = "abstractDaoTarget")
//	@Scope("prototype")
//	public  GenericDao<T, Serializable> abstractDaoTarget(Class<T> type) {
//
//		GenericDaoHibernateImpl<T, Serializable> abstractDaoTarget = new GenericDaoHibernateImpl<T, Serializable>(type);
//		abstractDaoTarget.setSessionFactory(sessionFactory().getObject());
//
//		return abstractDaoTarget;
//	}

	@Bean(name = "abstractDao")
	@Scope("prototype")
	public static ProxyFactoryBean abstractDao() {
		return new ProxyFactoryBean();
	}


}

