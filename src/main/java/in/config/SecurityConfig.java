package in.config;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package in.inventory.config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
///**
// *
// * @author anuja
// */
//
//@EnableWebSecurity
//@PropertySource("classpath:config.properties")
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    
//    @Value("${user.id}")
//    private String user;
//    
//    @Value("${user.password}")
//    private String password;
//    
//           
//           @Autowired
//	private CustomAuthenticationProvider authenticationProvider;
//	
//        @Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.authenticationProvider(authenticationProvider);
//        }
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//            
//		http
//			.authorizeRequests()
//                                .antMatchers("/admin/**").authenticated()
//				.antMatchers("/css/**", "/index").permitAll()		
//							
//				.and()
//			.formLogin()
//				.loginPage("/login").permitAll().successForwardUrl("/admin/")
//                                
//                                
//                                
//                        .and()
//                        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/admin/login");	
//	}
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            
//            
//            
//		auth
//			.inMemoryAuthentication()
//				.withUser("user").password("password").roles("ADMIN");
//	}
//}
