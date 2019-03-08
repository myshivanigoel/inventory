/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.config;

import in.auth.user.service.UserService;
import in.db.auth.entity.MstUser;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sun.security.provider.PolicyParser;

/**
 *
 * @author anuja
 */
@Component
@PropertySource("classpath:config.properties")

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	HttpSession httpSession;
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	       UserService userService ;
	
	//@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("in custom auth provioder");
		Collection<? extends GrantedAuthority> authorities = new ArrayList();
                System.out.println("userservice :"+userService);
                System.err.println("authentication :"+authentication);
		          MstUser user=userService.getUserByIdOrEmailOrMobile(authentication.getName());
		String password=(String) authentication.getCredentials();
		
		
		if (user == null) {
    		//request.getSession().setAttribute("errormsg", "");
    		//request.getSession().setAttribute("msgPass", "Bad Credentials.");
            throw new BadCredentialsException("Bad Credentials.");
            
        }else if(passwordEncoder.matches(password,user.getPassword()))
				{
                                    System.out.println("in.config.CustomAuthenticationProvider.authenticate()"+" :::  password mattched");
        			authorities = userService.getUserAuthorities(user.getUserId());
                                
                                        System.out.println("in.config.CustomAuthenticationProvider.authenticate()"+authorities);
        		
        			for (GrantedAuthority grantedAuthority : authorities) {
						System.out.println(grantedAuthority.getAuthority());
					}
        	/*Authorities aut=new Authorities();
        			aut.setName("EMPLOYEE");
        			authorities.add(aut);*/
        		//	request.getSession().setAttribute("userAuthorities", authorities);
				}
    	    
				else{
					user = null;
		        	//request.getSession().setAttribute("msgPass", "Bad credentials.");
		        	throw new BadCredentialsException("Bad credentials.");
				}
		
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}
	
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public CustomAuthenticationProvider(){}
	public CustomAuthenticationProvider(HttpServletRequest request) {
		super();
		this.request = request;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
