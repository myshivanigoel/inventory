package in.inventory.masters.controller;
import in.auth.user.service.DesignationService;
import in.util.Utility;
import in.util.entity.ResultDataMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import in.db.auth.entity.Designation;
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
@RequestMapping("masters/designation/")
public class DesignationMasterController {
    
    ResultDataMap result;
    @Autowired
    DesignationService designationService;
    
    @GetMapping("add")
    public String designationForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
         model.addAttribute("designation", new Designation());
        
        model.addAttribute("designationList",designationService.getAllDesignationList());
        return "designation-master";
    }
     @PostMapping("add")
    public String departmentForm(
            
            @ModelAttribute("designation")Designation designation,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("designation", designation);
            model.addAttribute("designationList",designationService.getAllDesignationList());
             return "designation-master";
        }
        else
        {
            ResultDataMap result=designationService.saveDesignation(designation, Utility.getBaseUrl(request));
            if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("designationList",designationService.getAllDesignationList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("designation", designation);
                model.addAttribute("designationList",designationService.getAllDesignationList());
                
            }
        }
        return "designation-master";
    }
    
    
  @GetMapping("update")
    public String designationUpdateForm(@RequestParam(name = "message",required = false)String message,
                            @RequestParam(name="designationId")Integer designationId,
                            Model model)
    {
         model.addAttribute("message",message);
          model.addAttribute("designationList",designationService.getAllDesignationList());
       
         Designation designation = designationService.getDesignationById(designationId);
       
          if(designation==null)
          {
               model.addAttribute("designation",new Designation());
                 return "designation-master";
          }else{
               model.addAttribute("designation",designation);
                 return "designation-master-update";
          }
         
       
    }
     @PostMapping("update")
    public String designationUpdateForm(
            
            @ModelAttribute("designation")Designation designation,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("designation", designation);
            model.addAttribute("DesignationList",designationService.getAllDesignationList());
             return "designation-master";
        }else{
         
            ResultDataMap result=designationService.updateDesignation(designation, Utility.getBaseUrl(request));
             if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("designationList",designationService.getAllDesignationList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("designation", designation);
                model.addAttribute("designationList",designationService.getAllDesignationList());
                
            }
        }
        return "designation-master";
    }
     @PostMapping("deactivate/{designationId}")
    public @ResponseBody ResultDataMap designationDeactivateSave(@PathVariable(name = "designationId")Integer designationId,
            
                                        Model model,HttpServletRequest request)
    {
        
       {
            
             result=designationService.deactivateDesignation(designationId);
           if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("designationList",designationService.getAllDesignationList());
                 return result;
            }else{
                  model.addAttribute("message", result.getMessage());

                model.addAttribute("designationList",designationService.getAllDesignationList());
                
            }
        }
        return result;
    }
    
   
}
