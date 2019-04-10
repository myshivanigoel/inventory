/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inverntory.controller;

import in.auth.user.service.UserService;
import in.config.Actor;
import in.db.auth.entity.EmployeeAuthorityLevel;
import in.db.auth.entity.MstUser;
import in.db.inventory.entity.HdIndent;
import in.inventory.service.PurchaseService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 *
 * @author anuja
 */

@Controller
public class DashboardController {
    
    @Autowired
    Actor actor;
    
    @Autowired
    UserService userService;
    
    @Autowired
    PurchaseService purchaseService;
    
    @GetMapping("dashboard")
    public String dashboard(@RequestParam(name="message",required = false)String message,Model model,HttpSession session)
    {
        session.setAttribute("actor", actor);
        System.out.println("actor ="+actor);
        Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
             MstUser user= (MstUser)authuntication.getPrincipal();
        List<HdIndent> indentsToApprove =purchaseService.getIndentsToBeAuthorizedByUser(user.getUserId());
        
        model.addAttribute("indentList",purchaseService.getMyPendingIndents(user.getUserId()));
        model.addAttribute("indentsToApprove",indentsToApprove);
        model.addAttribute("message",message);
        
        if(userService.getFinanceUser().getUserId().equals(user.getUserId()))
        {
            model.addAttribute("financeApprovalRequestCount",purchaseService.getRequestsForFinanceApproval(user.getUserId()).size());
        }
        
        return "home";
    }
}
