/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inverntory.controller;

import in.auth.user.service.UserService;
import in.config.Actor;
import in.db.auth.entity.Authorities;
import in.db.auth.entity.EmployeeAuthorityLevel;
import in.db.auth.entity.MstRole;
import in.db.auth.entity.MstUser;
import in.db.inventory.entity.HdIndent;
import in.inventory.service.PurchaseService;
import in.util.entity.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 *
 * @author anuja
 */

@Controller
public class DashboardController {
    
   
    @Autowired
    UserService userService;
    
    @Autowired
    PurchaseService purchaseService;
    
    @GetMapping("dashboard")
    public String dashboard(@RequestParam(name="message",required = false)String message,Model model,HttpSession session)
    {
       
        
        Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
             MstUser user= (MstUser)authuntication.getPrincipal();
        List<HdIndent> indentsToApprove =purchaseService.getIndentsToBeAuthorizedByUser(user.getUserId());
        indentsToApprove=indentsToApprove==null?new ArrayList<>():indentsToApprove;
        model.addAttribute("indentList",purchaseService.getMyPendingIndents(user.getUserId()));
        model.addAttribute("indentsToApprove",indentsToApprove);
        model.addAttribute("message",message);
        List<HdIndent> indentListFinance=null;
        if(userService.getFinanceUser().getUserId().equals(user.getUserId()))
        {
             indentListFinance=purchaseService.getRequestsForFinanceApproval(user.getUserId());
            
            
        }
         indentListFinance=indentListFinance==null?new ArrayList<>():indentListFinance;
            model.addAttribute("indentListFinance",indentListFinance);
       
        Actor actorObject=(Actor)session.getAttribute("actor");
        
        if(actorObject==null)
        {
            
            actorObject=new Actor(Strings.ActorIndentor);
             session.setAttribute("actor", actorObject);
            
        }
        
        Set<Actor> actorsList=(Set)session.getAttribute("actorsList");
        if(actorsList==null){
        actorsList=getActorsList(user,actorObject);
         session.setAttribute("actorsList", actorsList);
        }
        
       
       
        return "home";
    }
    
    public Set<Actor> getActorsList(MstUser user,Actor actorString)
    {
        List<MstRole> list= userService.getUserRole(user.getUserId());
         Set<Actor> actorsList=new LinkedHashSet<>();
         System.out.println(list);
        // actorsList.add(actorString);
       
         actorsList.add(new Actor(Strings.ActorAprover));
         
      for (MstRole role : list) {
          String name=role.getRoleName();
          name=name.equals("Employee")?Strings.ActorIndentor:name;
           actorsList.add(new Actor(name));
         
            
        }
      return actorsList;
      
    }
    
    @GetMapping("actorUpdate")
    public @ResponseBody Boolean actorUpdate(@RequestParam("actor")String actor,HttpSession session)
    {
        session.setAttribute("actor", new Actor(actor));
        return true;
    }
}
