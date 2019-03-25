package in.inventory.masters.controller;
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
@RequestMapping("masters/users/")
public class UserMasterController {
    
    ResultDataMap result;
    @Autowired
    UserService userService;
    
    @GetMapping("add")
    public String userForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
         model.addAttribute("user", new MstUser());
        
        model.addAttribute("userList",userService.getAllUserList());
        return "user-master";
    }
     @PostMapping("add")
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
    
    
      @GetMapping("update")
    public String userUpdateForm(@RequestParam(name = "message",required = false)String message,
                            @RequestParam(name="userId")Integer userId,
                            Model model)
    {
         model.addAttribute("message",message);
         MstUser user=userService.getUserById(userId);
         if(user==null)
         {
              model.addAttribute("message","User Not Found");
               model.addAttribute("userFound","N");
              user=new MstUser();
         }else{
              List list=userService.getAllRolesList();
         System.out.println("in.inventory.masters.controller.UserMasterController.userForm()"+list);
         model.addAttribute("userTypeList",list);
             model.addAttribute("userFound",'Y');
         }
              model.addAttribute("user", user);
         
        
        model.addAttribute("userList",userService.getAllUserList());
        return "user-master-update";
    }
     @PostMapping("update")
    public String userUpdateSave(
            
            @ModelAttribute("user")MstUser user,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        System.out.println("in.inventory.masters.controller.UserMasterController.userForm()");
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("user", user);
            model.addAttribute("userList",userService.getAllUserList());
             return "user-master";
        }else{
              Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
             MstUser adminUser= (MstUser)authuntication.getPrincipal();
            ResultDataMap result=userService.updateUser(user, Utility.getBaseUrl(request));
            System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: No bindign Error");
            if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("userList",userService.getAllUserList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("user", user);
                model.addAttribute("userList",userService.getAllUserList());
                
            }
        }
        return "user-master";
    }
     @PostMapping("deactivate/{userId}")
    public @ResponseBody ResultDataMap userDeactivateSave(@PathVariable(name = "userId")Integer userId,
            
                                        Model model,HttpServletRequest request)
    {
        System.out.println("in.inventory.masters.controller.UserMasterController.userForm()");
       {
              Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
             MstUser adminUser= (MstUser)authuntication.getPrincipal();
             result=userService.deactivateUser(userId,adminUser.getUserId());
            System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: No bindign Error");
            if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("userList",userService.getAllUserList());
                 return result;
            }else{
                  model.addAttribute("message", result.getMessage());

                model.addAttribute("userList",userService.getAllUserList());
                
            }
        }
        return result;
    }
    
     @PostMapping("activate/{userId}")
    public  @ResponseBody ResultDataMap  userActivateSave(@PathVariable(name = "userId")Integer userId,
            
                                        Model model,HttpServletRequest request)
    {
        System.out.println("in.inventory.masters.controller.UserMasterController.userForm()");
       {
              Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
             MstUser adminUser= (MstUser)authuntication.getPrincipal();
             result=userService.activateUser(userId,adminUser.getUserId());
             if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("userList",userService.getAllUserList());
                 return result;
            }else{
                  model.addAttribute("message", result.getMessage());

                model.addAttribute("userList",userService.getAllUserList());
                
            }
        }
        return result;
    }
}
