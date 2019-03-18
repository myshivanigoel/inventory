package in.inventory.masters.controller;
import in.auth.user.service.PurchaseTypeService;
import in.util.Utility;
import in.util.entity.ResultDataMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import in.db.inventory.entity.PurchaseType;
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
@RequestMapping("masters/purchase/")
public class PurchaseTypeMasterController {
    
    ResultDataMap result;
    @Autowired
    PurchaseTypeService purchaseService;
    
    @GetMapping("add")
    public String purchaseForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
         model.addAttribute("message",message);
         model.addAttribute("purchase", new PurchaseType());
        
        model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
        return "purchase-master";
    }
     @PostMapping("add")
    public String projectForm(
            
            @ModelAttribute("purchase")PurchaseType purchase,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("purchase", purchase);
            model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
             return "project-master";
        }
        else
        {
            ResultDataMap result=purchaseService.savePurchaseType(purchase, Utility.getBaseUrl(request));
            if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("purchase", purchase);
                model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
                
            }
        }
        return "purchase-master";
    }
    
    
  @GetMapping("update")
    public String purchaseUpdateForm(@RequestParam(name = "message",required = false)String message,
                            @RequestParam(name="purchaseId")Integer purchaseId,
                            Model model)
    {
         model.addAttribute("message",message);
          model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
       
         PurchaseType purchaseType=purchaseService.getPurchaseTypeById(purchaseId);
         System.out.println("in.inventory.masters.controller.PurchaseTypeMasterController.purchaseUpdateForm()"+purchaseType);
          if(purchaseType==null)
          {
               model.addAttribute("purchase",new PurchaseType());
                 return "purchase-master";
          }else{
               model.addAttribute("purchase",purchaseType);
                 return "purchase-master-update";
          }
         
       
    }
     @PostMapping("update")
    public String projectUpdateSave(
            
            @ModelAttribute("purchase")PurchaseType purchase,BindingResult bindingResult,Model model,HttpServletRequest request)
    {
        if(bindingResult.hasErrors())
        {
             model.addAttribute("message", "Invalid values");
            model.addAttribute("purchase", purchase);
            model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
             return "project-master";
        }else{
         
            ResultDataMap result=purchaseService.updatePurchaseType(purchase, Utility.getBaseUrl(request));
             if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
                 return "redirect:add?message="+result.getMessage();
            }else{
                  model.addAttribute("message", result.getMessage());
                model.addAttribute("purchase", purchase);
                model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
                
            }
        }
        return "purchase-master";
    }
     @PostMapping("deactivate/{purchaseId}")
    public @ResponseBody ResultDataMap userDeactivateSave(@PathVariable(name = "purchaseId")Integer purchaseId,
            
                                        Model model,HttpServletRequest request)
    {
        
       {
            
             result=purchaseService.deactivatePurchase(purchaseId);
           if(result.getStatus())
            {
                 model.addAttribute("message", "Success");
                 model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
                 return result;
            }else{
                  model.addAttribute("message", result.getMessage());

                model.addAttribute("purchaseList",purchaseService.getAllPurchaseTypeList());
                
            }
        }
        return result;
    }
    
   
}
