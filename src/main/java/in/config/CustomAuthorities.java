/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.config;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author anuja
 */
public class CustomAuthorities implements GrantedAuthority {
    
private Integer id;
	private String name="ADMIN";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
        
    @Override
    public String getAuthority() {
        return "ADMIN";
    }
    
} 
