/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inverntory.controller;

import in.auth.user.service.UserService;
import in.db.auth.entity.MstUser;
import in.db.inventory.entity.Issue;
import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.Stock;
import in.inventory.service.ItemService;
import in.inventory.service.StockService;
import in.util.entity.ResultDataMap;
import in.utility.SantizingUtility;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author anuja
 */

@Controller
public class StockController {
    
    
     @Autowired
    private StockService stockService;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private UserService userService;
   
    @GetMapping("/")
    public String home(@RequestParam(name = "message",required = false)String message,Model model)
    {
        
        return "home";
    }
    
    @GetMapping("issue-form")
    public String issueForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
        model.addAttribute("message",message);
          model.addAttribute("groups",itemService.getAllGroups());
         model.addAttribute("users",userService.getAllUserList());
          model.addAttribute("issue", new Issue());
        return "issue-form";
    }
    
     @PostMapping("issue-form")
    public String issueSave(@Valid @ModelAttribute("issue")Issue issue,
                                    final BindingResult bindingResult,
                                    Model model) 
    {
         model.addAttribute("groups",itemService.getAllGroups());
          model.addAttribute("users",userService.getAllUserList());
          model.addAttribute("issue", new Issue());
         Integer quantity=issue.getQuantity();
         quantity=quantity==null?0:quantity;
         
           
        if (bindingResult.hasErrors()) {
                model.addAttribute("message", bindingResult.getFieldError().getField()+":: incorrect input");
        return "issue-form";
    }else if(!(issue.getItem()!=null
                    && issue.getItem().getItemId()!=null 
                    && issue.getItem().getItemId()!=0
                   ) )
    {
           model.addAttribute("message","please choose an item");
        return "issue-form";
    }else if(quantity==0 )
    {
        model.addAttribute("message","quantity can not be 0");
        return "issue-form";
    }
    
    ResultDataMap result=new ResultDataMap().setStatus(Boolean.FALSE).setMessage("ERROR");;
        SantizingUtility santizingUtility=new SantizingUtility();
        try {
             issue=(Issue)santizingUtility.validate(issue, "Issue");
             result=stockService.issueItem(issue);
             
                 model.addAttribute("result",result);
                if(result.getStatus())
                {
                    model.addAttribute("message",result.getMessage());
                     return "redirect:"+"issue-form"+"?message=saved successfully";
                }else{
                     model.addAttribute("message",result.getMessage());
                      return "issue-form";
                }
     
             
             
             
                 } catch (IllegalAccessException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IntrospectionException ex) {
            Logger.getLogger(PurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
         model.addAttribute("message","error");
                      return "issue-form";
    }
    
     @GetMapping("get-group-items")
    public @ResponseBody List<ItemMaster> getItemListByGroup(@RequestParam("groupId")Integer groupId)
    {
        if(groupId==null){groupId=0;}
        return itemService.getItemsList(groupId);
    }
     @GetMapping("get-group-available-items")
    public @ResponseBody List<ItemMaster> getAvailableItemListByGroup(@RequestParam("groupId")Integer groupId)
    {
        if(groupId==null){groupId=0;}
        return itemService.getAvailableItemsList(groupId);
    }
    @GetMapping("get-item-quantity")
    public @ResponseBody Double getAvailableQuantity(@RequestParam("itemId")Integer itemId,Model model)
    {
        Double availableQuantity=0.0;
        availableQuantity=stockService.getStockForItem(itemId).getAvailableQty();
        return availableQuantity;
    }
    
     @GetMapping("item-list")
    public String itemAdd()
    {
        return "item-list";
    }
    
    @GetMapping("stock-view")
    public String stockView(Model model)
    {
        boolean isAdmin=false;
        List<Stock> stockList=new ArrayList<>();
         Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
         Collection<? extends GrantedAuthority> authorities=authuntication.getAuthorities();
         for(GrantedAuthority authority:authorities)
         {
            if(authority.getAuthority().equalsIgnoreCase("ROLE_ADMIN"))
                {
                    isAdmin=true;
                  break;
                }
         }
         if(isAdmin){
         stockList=stockService.getAllItemsStock();
         model.addAttribute("stockList", stockList);
         return "stock";
         }else{
            MstUser user=(MstUser)authuntication.getPrincipal();
           Integer userId= user.getUserId();
           List<Issue> issueList=stockService.getIssuedStockByUserId(userId);
            model.addAttribute("issueList", issueList);
            return "issue-details";
         }
          
        
    }
   
}
