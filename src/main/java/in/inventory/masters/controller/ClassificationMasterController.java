/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.masters.controller;

import in.db.inventory.entity.Classification;
import in.inventory.service.ItemService;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author anuja
 */

@Controller
@RequestMapping("/masters/classification/")
public class ClassificationMasterController {
    
    ResultDataMap result=new ResultDataMap();
    
    @Autowired
    ItemService itemService;
    
    @GetMapping("add")
    public String addClassificationForm(@RequestParam(name="message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
        model.addAttribute("classList",itemService.getClassificationList());
        model.addAttribute("classification",new Classification());
        
        return "classification-master";
    }
    
    @PostMapping("add")
    public String addClassificationSave(@ModelAttribute("classification")Classification classification,Model model)
    {
         model.addAttribute("classList",itemService.getClassificationList());
        System.out.println("in.inventory.masters.controller.ItemMasterController.addClassificationSave()"+classification);
        if(classification==null 
                || classification.getClassification()==null 
                || classification.getClassification().trim().equals("") )
        {
             model.addAttribute("class",classification);
           System.out.println("in.inventory.masters.controller.ItemMasterController.addClassificationSave()"+classification);
            model.addAttribute("message", Strings.InvalidData);
            return "classification-master";
        }else{
                System.out.println("in.inventory.masters.controller.ItemMasterController.addClassificationSave()"+classification);
            result=itemService.saveClassification(classification);
            if(result.getStatus())
            {
                return "redirect:add?message="+result.getMessage();
            }else{
                 model.addAttribute("class",classification);
       
            model.addAttribute("message", Strings.error);
            return "classification-master";
            }
        }
        
       
       
    }
    
    
      @GetMapping("update/{classificationId}")
    public String updateClassificationForm(@PathVariable("classificationId")Integer classificationId,
                                            @RequestParam(name="message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
        model.addAttribute("classList",itemService.getClassificationList());
        Classification classification=itemService.getClassification(classificationId);
        if(itemService.updateAble(classification))
                {
                    model.addAttribute("classification",classification);
                   
                    return "classification-master-update";
                }else{
                        model.addAttribute("classification",new Classification());
                         model.addAttribute("message",Strings.ClassificationInUse);
                        return "classification-master";
                        }
         
        
    }
    
     @PostMapping("update")
    public String updateClassificationSave(@ModelAttribute("classification")Classification classification,Model model)
    {
           System.out.println("in.inventory.masters.controller.ItemMasterController.addClassificationSave()"+classification);
           
         model.addAttribute("classList",itemService.getClassificationList());
        
        if(classification==null 
                || classification.getClassification()==null 
                || classification.getClassification().trim().equals("") 
                 || !itemService.updateAble(classification))
        {
             model.addAttribute("class",classification);
          System.out.println("in.inventory.masters.controller.ItemMasterController.addClassificationSave()"+classification);
           
            model.addAttribute("message", Strings.InvalidData);
            return "classification-master-update";
        }else{
               System.out.println("in.inventory.masters.controller.ItemMasterController.addClassificationSave()"+classification);
           
            result=itemService.saveClassification(classification);
            if(result.getStatus())
            {
                   System.out.println("in.inventory.masters.controller.ItemMasterController.addClassificationSave()"+classification);
           
                return "redirect:add?message="+result.getMessage();
            }else{
                 model.addAttribute("class",classification);
       
            model.addAttribute("message", Strings.error);
            return "classification-master-update";
            }
        }
        
       
       
    }
    
    
     @PostMapping("activate/{classificationId}")
    public @ResponseBody ResultDataMap activateClassificationSave(@PathVariable("classificationId")Integer classificationId,Model model)
    {
         model.addAttribute("classList",itemService.getClassificationList());
        
        if(classificationId==null)
        {
          
            return result.setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
        }else if( !itemService.updateAble(new Classification(classificationId)))
        {
            return result.setStatus(Boolean.FALSE).setMessage(Strings.ClassificationInUse);
        }
        else{
               Classification classification=new Classification();
               classification.setClassificationId(classificationId);
               classification.setActiveFlag('Y');
               return result=itemService.saveClassification(classification);
            
            }
        }
        
        @PostMapping("deactivate/{classificationId}")
    public @ResponseBody ResultDataMap deactivateClassificationSave(@PathVariable("classificationId")Integer classificationId,Model model)
    {
         model.addAttribute("classList",itemService.getClassificationList());
         
        if(classificationId==null)
        {
          
            return result.setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
        }else if( !itemService.updateAble(new Classification(classificationId)))
        {
            
            return result.setStatus(Boolean.FALSE).setMessage(Strings.ClassificationInUse);
        }
        
        else{
              Classification classification=new Classification();
               classification.setClassificationId(classificationId);
               classification.setActiveFlag('N');
               return result=itemService.saveClassification(classification);
            
            }
        }
        
       
       
    
}
