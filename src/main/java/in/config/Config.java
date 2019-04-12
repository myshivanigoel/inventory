/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import in.db.auth.entity.MstUser;
import in.util.entity.Strings;
import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author anuja
 */

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages="in.cdac") // base package setup inn xml 
@PropertySource("classpath:persistence-mysql.properties")
@EnableTransactionManagement

public class Config {
    
    //set up a logger 
	private Logger logger=Logger.getLogger(getClass().getName());
	
	// variable to hold the properties
	@Autowired
	Environment env;
	
    @Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}


  
	  //set password Encoder
	    @Bean
	    public PasswordEncoder passwordEncoder()
	    {
	    	return new BCryptPasswordEncoder();
	    }
	    
            //define a bean for datasource
    @Bean
	public DataSource securityDataSource()
	{
		//create Connection Pool
		          ComboPooledDataSource securityDataSource =new ComboPooledDataSource();
		
		//set the jdbc driver
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		//	throw new RuntimeException(e);
		}
		
		//log the connection props
		logger.info(">>>jdbc Url = "+env.getProperty("jdbc.url"));
		logger.info(">>> jdbc user  = "+env.getProperty("jdbc.user"));
		
		//set the database connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		
		//set connection pool props
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDataSource;
	}
        
        
	//helper method
	//read environment property and convert to int
	
	private int getIntProperty(String propertyName)
	{
		String propVal=env.getProperty(propertyName);
		int intPropVal=Integer.parseInt(propVal);
		return intPropVal;
	}
	
	
	
	
	/* Hibernate Setup */	
	
	private Properties getHibernateProperties() {

		// set hibernate properties
		Properties props = new Properties();

		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                
		return props;				
	}

	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		
		// create session factorys
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		sessionFactory.setDataSource(securityDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hiberante.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
        
        //commented because new version of spring and  hibernate doesnt support this
        
//	@Bean
//	@Autowired
//	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//		
//		// setup transaction manager based on session factory
//		HibernateTransactionManager txManager = new HibernateTransactionManager();
//		txManager.setSessionFactory(sessionFactory);
//
//		return txManager;
//	}	
//	
	
	/* ENd Hibernate Setup*/
	
         
             @Bean(name = "mail-config")
	    public static PropertiesFactoryBean mailConfig() {
                
                 System.out.println("configurring mail config");
	            PropertiesFactoryBean bean = new PropertiesFactoryBean();
	            bean.setLocation(new ClassPathResource(
	                    "mail-config.properties"));
                       System.out.println("configurring mail config"+bean);
	            return bean;
	    }
            
             @Bean(name = "Log")
        public Logger getLog()
        {
            return Logger.getLogger(getClass().getName());
        }
	
        
        @Bean
public JavaMailSender getJavaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
     
    mailSender.setUsername("my.gmail@gmail.com");
    mailSender.setPassword("password");
     
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
     
    return mailSender;
}
	
      @Bean(name = "actor")
        @Scope(
          value = WebApplicationContext.SCOPE_SESSION, 
          proxyMode = ScopedProxyMode.TARGET_CLASS)
        public Actor actor() {
            return new Actor(Strings.ActorIndentor);
        }
}
