package in.inventory.masters.controller;
import in.auth.user.service.ProjectService;
import in.auth.user.service.UserService;
import in.util.Utility;
import in.util.entity.ResultDataMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import in.db.inventory.entity.Project;
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
@RequestMapping("masters/project/")
public class ProjectMasterController {
    
    ResultDataMap result;
    @Autowired
    ProjectService projectService;
    
    @GetMapping("add")
    public String projectForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
         model.addAttribute("project", new Project());
        
        model.addAttribute("projectList",projectService.getAllProjectList());
        return "project-master";
    }
     @PostMapping("add")
    public String projectForm(
            
            @ModelAttribute("project")Project project,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm()");
        if(bindingResult.hasErrors())
        {
             System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm():: binding error"+bindingResult.getAllErrors());
       
            model.addAttribute("message", "Invalid values");
            model.addAttribute("project", project);
            model.addAttribute("projectList",projectService.getAllProjectList());
             return "project-master";
        }
        else
        {
            System.out.println("in.inventory.masters.controller.UserMasterController.userForm():: No bindign Error");
              Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
             
             System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm():: No bindign Error");
             System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm()"+project);
             ResultDataMap result=projectService.saveProject(project, Utility.getBaseUrl(request));
            System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm():: No bindign Error");
            if(result.getStatus())
            {
                System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm():: save success");
                 model.addAttribute("message", "Success");
                 model.addAttribute("projectList",projectService.getAllProjectList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                 System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm():: save fails");
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("project", project);
                model.addAttribute("projectList",projectService.getAllProjectList());
                
            }
        }
        return "project-master";
    }
    
    
  @GetMapping("update")
    public String projectUpdateForm(@RequestParam(name = "message",required = false)String message,
                            @RequestParam(name="projectId")Integer projectId,
                            Model model)
    {
         model.addAttribute("message",message);
        
          
          model.addAttribute("project",projectService.getProjectById(projectId));
        
        model.addAttribute("projectList",projectService.getAllProjectList());
        return "project-master-update";
    }
     @PostMapping("update")
    public String projectUpdateSave(
            
            @ModelAttribute("project")Project project,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        System.out.println("in.inventory.masters.controller.ProjectMasterController.projectForm()");
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("project", project);
            model.addAttribute("projectList",projectService.getAllProjectList());
             return "project-master";
        }else{
         
            ResultDataMap result=projectService.updateProject(project, Utility.getBaseUrl(request));
             if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("projectList",projectService.getAllProjectList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("project", project);
                model.addAttribute("projectList",projectService.getAllProjectList());
                
            }
        }
        return "project-master";
    }
     @PostMapping("deactivate/{projectId}")
    public @ResponseBody ResultDataMap userDeactivateSave(@PathVariable(name = "projectId")Integer projectId,
            
                                        Model model,HttpServletRequest request)
    {
        
       {
            
             result=projectService.deactivateProject(projectId);
           if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("projectList",projectService.getAllProjectList());
                 return result;
            }else{
                  model.addAttribute("message", result.getMessage());

                model.addAttribute("projectList",projectService.getAllProjectList());
                
            }
        }
        return result;
    }
    
   
}
