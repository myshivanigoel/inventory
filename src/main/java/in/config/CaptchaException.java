/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.config;

import org.springframework.security.authentication.BadCredentialsException;


/**
 *
 * @author anuja
 */
public class CaptchaException extends BadCredentialsException{
    
    public CaptchaException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public CaptchaException(String msg)
    {
        super(msg,new Throwable());
    }
}
