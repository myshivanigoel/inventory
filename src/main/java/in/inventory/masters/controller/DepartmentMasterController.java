package in.inventory.masters.controller;
import in.auth.user.service.DepartmentService;
import in.util.Utility;
import in.util.entity.ResultDataMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import in.db.auth.entity.Department;
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
@RequestMapping("masters/department/")
public class DepartmentMasterController {
    
    ResultDataMap result;
    @Autowired
    DepartmentService departmentService;
    
    @GetMapping("add")
    public String departmentForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
         model.addAttribute("department", new Department());
        
        model.addAttribute("departmentList",departmentService.getAllDepartmentList());
        return "department-master";
    }
     @PostMapping("add")
    public String departmentForm(
            
            @ModelAttribute("department")Department department,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("department", department);
            model.addAttribute("departmentList",departmentService.getAllDepartmentList());
             return "department-master";
        }
        else
        {
            ResultDataMap result=departmentService.saveDepartment(department, Utility.getBaseUrl(request));
            if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("departmentList",departmentService.getAllDepartmentList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("department", department);
                model.addAttribute("departmentList",departmentService.getAllDepartmentList());
                
            }
        }
        return "department-master";
    }
    
    
  @GetMapping("update")
    public String departmentUpdateForm(@RequestParam(name = "message",required = false)String message,
                            @RequestParam(name="departmentId")Integer departmentId,
                            Model model)
    {
         model.addAttribute("message",message);
          model.addAttribute("departmentList",departmentService.getAllDepartmentList());
       
         Department department = departmentService.getDepartmentById(departmentId);
       
          if(department==null)
          {
               model.addAttribute("department",new Department());
                 return "department-master";
          }else{
               model.addAttribute("department",department);
                 return "department-master-update";
          }
         
       
    }
     @PostMapping("update")
    public String departmentUpdateForm(
            
            @ModelAttribute("department")Department department,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("department", department);
            model.addAttribute("departmentList",departmentService.getAllDepartmentList());
             return "department-master";
        }else{
         
            ResultDataMap result=departmentService.updateDepartment(department, Utility.getBaseUrl(request));
             if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("departmentList",departmentService.getAllDepartmentList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("department", department);
                model.addAttribute("departmentList",departmentService.getAllDepartmentList());
                
            }
        }
        return "department-master";
    }
     @PostMapping("deactivate/{departmentId}")
    public @ResponseBody ResultDataMap departmentDeactivateSave(@PathVariable(name = "departmentId")Integer departmentId,
            
                                        Model model,HttpServletRequest request)
    {
        
       {
            
             result=departmentService.deactivateDepartment(departmentId);
           if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("departmentList",departmentService.getAllDepartmentList());
                 return result;
            }else{
                  model.addAttribute("message", result.getMessage());

                model.addAttribute("departmentList",departmentService.getAllDepartmentList());
                
            }
        }
        return result;
    }
    
   
}
