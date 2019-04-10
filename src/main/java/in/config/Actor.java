/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.config;

/**
 *
 * @author anuja
 */

public class Actor {

    public Actor(String actorName) {
        this.actorName = actorName;
    }

    
    
    private String actorName;

    @Override
    public String toString() {
        return "Actor{" + "actorName=" + actorName + '}';
    }

    
    
    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
        
        
    
    
}
