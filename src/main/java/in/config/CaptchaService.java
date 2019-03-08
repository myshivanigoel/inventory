/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.config;

import java.net.URI;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

/**
 *
 * @author anuja
 */

@Service
public class CaptchaService  {
 
    @Autowired
    private CaptchaSettings captchaSettings;
 
    @Autowired
    private RestOperations restTemplate;
 
    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
 

    public boolean processResponse(String response) throws Exception {
        if(!responseSanityCheck(response)) {
            throw new BadCredentialsException("Response contains invalid characters");
        }
 
        URI verifyUri = URI.create(String.format(
          "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
          captchaSettings.getSecret(), response, null));
 
        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
 
        if(!googleResponse.isSuccess()) {
            throw new BadCredentialsException("reCaptcha was not successfully validated");
        }else{
            return true;
        }
    }
 
    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }
}