package in.auth.controller;
import in.auth.user.service.UserService;
import in.db.auth.entity.MstUser;
import in.util.Utility;
import in.util.entity.ResultDataMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anuja
 */
@Controller

public class ProfileMasterController {
    
    ResultDataMap result;
    @Autowired
    UserService userService;
    
    @GetMapping("/profile")
    public String userForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
         model.addAttribute("user", new MstUser());
        
        model.addAttribute("userList",userService.getAllUserList());
        return "user-profile";
    }
     @PostMapping("/profile")
    public String userForm(
            
            @ModelAttribute("user")MstUser user,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        System.out.println("in.inventory.masters.controller.UserMasterController.userForm()");
        if(bindingResult.hasErrors())
        {
             System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: binding error"+bindingResult.getAllErrors());
       
            model.addAttribute("message", "Invalid values");
            model.addAttribute("user", user);
            model.addAttribute("userList",userService.getAllUserList());
             return "user-master";
        }else{
            System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: No bindign Error");
              Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
             MstUser adminUser= (MstUser)authuntication.getPrincipal();
             System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: No bindign Error");
             System.out.println("in.inventory.masters.controller.UserMasterController.userForm()"+user);
            ResultDataMap result=userService.saveUser(user, adminUser.getUserId(), Utility.getBaseUrl(request));
            System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: No bindign Error");
            if(result.getStatus())
            {
                System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: save success");
                 model.addAttribute("message", "Success");
                 model.addAttribute("userList",userService.getAllUserList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                 System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: save fails");
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("user", user);
                model.addAttribute("userList",userService.getAllUserList());
                
            }
        }
        return "user-master";
    }
    
    
}
