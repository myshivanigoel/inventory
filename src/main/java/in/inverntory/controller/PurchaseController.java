/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inverntory.controller;

import in.auth.user.service.ProjectService;
import in.auth.user.service.PurchaseTypeService;
import in.db.auth.entity.MstUser;
import in.db.inventory.entity.Classification;
import in.db.inventory.entity.DtIndent;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.Issue;
import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import in.db.inventory.entity.PurchaseType;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.db.inventory.entity.Stock;
import in.inventory.service.ItemService;
import in.inventory.service.PurchaseService;
import in.inventory.service.StockDao;
import in.inventory.service.StockService;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import in.utility.SantizingUtility;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author anuja
 */

@Controller
@PropertySource("classpath:HtmlPages.properties")
public class PurchaseController {
    @Value("${IndentForm}")
    private String indentForm;
     @Value("${receiptFormConsumable}")
    private String receiptFormConsumable;
      @Value("${indentListView}")
    private String indentListView;
    
    @Value("$IndentSaveAck")
    private String IndentSaveAck;
    
    @Autowired
    private PurchaseService purchaseService;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private ItemService itemService;
      @Autowired
    private PurchaseTypeService purchaseTypeService;
        @Autowired
    private ProjectService projectService;
    
    @GetMapping("indent-form")
    public String indentForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
        System.out.println("Hello");
        model.addAttribute("purchaseTypeList",purchaseTypeService.getAllPurchaseTypeList());
        model.addAttribute("projectList",projectService.getAllProjectList());
        model.addAttribute("classList",itemService.getClassificationList());
         MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       model.addAttribute("indentList",purchaseService.getIndentorsIndents(user));
       model.addAttribute("indent",new HdIndent());
         model.addAttribute("message", message);
        return indentForm;
    }
    
    
    @PostMapping("indent-save")
    public String indentSave(@Valid @ModelAttribute(name = "indent")HdIndent indent
                                                             ,final BindingResult bindingResult
                                                             ,Model model
                                                             ,HttpServletRequest request
                                                             ,RedirectAttributes redirectAttributes) 
    {
        
        System.out.println("inden t :: "+indent);
        if(bindingResult.hasErrors())
        {
            model.addAttribute("indent",indent);
            model.addAttribute("message", bindingResult.getFieldError().getField()+":: incorrect input");
            return "indent-form";
        }
          MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          if(user==null)
          {
              model.addAttribute("message","error");
                      return "indent-form";
          }
         indent.setIndentor(user);
     
        System.out.println("in.inverntory.controller.PurchaseController.indentSave()"+indent);
        ResultDataMap result=new ResultDataMap().setStatus(Boolean.FALSE).setMessage("ERROR");
        SantizingUtility santizingUtility=new SantizingUtility();
         
        try {
             indent=(HdIndent)santizingUtility.validate(indent, "Indent");
             if(indent==null){
                  model.addAttribute("indent",indent);
                model.addAttribute("message","error");
                      return "indent-form";
             }
                int error=0;
                for (DtIndent t :  indent.getIndentDetailList()) {
              
                    if(t.getItem()==null || t.getItem().getItemId()==null || t.getItem().getItemId()==0 || t.getItem().getItemId()==1)
                    {
                        if(t.getDescriptionOfMaterial()==null || t.getDescriptionOfMaterial().trim().equals(""))
                        {
                             model.addAttribute("indent",indent);
                             model.addAttribute("message","please  add description");
                            return "indent-form";
                        }else{
                             t.getItem().setItemId(1);
                            
                        }
                    }
                    if(t.getItemGroup()==null || t.getItemGroup().getGroupId()==null || t.getItemGroup().getGroupId()==0)
                    {
                        t.setItemGroup(new MstGroup(1));
                    }
                    if(t.getClassification()==null || t.getClassification().getClassificationId()==null || t.getClassification().getClassificationId()==1)
                    {
                        t.setClassification(new Classification(1));
                    }
                    
                }  
             result=purchaseService.saveIndentForm(indent);
                model.addAttribute("result",result);
                if(result.getStatus())
                {
                    model.addAttribute("message","saved successfully");
                     return "redirect:indent-form?message=saved successfully";
                }else{
                     model.addAttribute("message","error");
                      return "indent-form";
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
                      return "indent-form";
    }

    
    
   
   
    
     @GetMapping("receipt-consumable-form")
    public String receiptForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
        System.out.println("Hello");
        model.addAttribute("receipt", new ReceiptConsumable());
        model.addAttribute("groups",itemService.getConsumableGroups());
          model.addAttribute("message",message);
        model.addAttribute("items",itemService.getAllItemsList());
        return receiptFormConsumable;
    }
    
    
    
    @PostMapping("receipt-consumable-save")
    public String receiptSave(@Valid @ModelAttribute("receipt")ReceiptConsumable receipt,
                                    final BindingResult bindingResult,
                                    Model model) 
    {
         model.addAttribute("groups",itemService.getAllGroups());
      
          model.addAttribute("receipt", receipt);
         Double acceptedQuantity=receipt.getAcceptedQuantity();
         Double rejectedQuantity=receipt.getRejectedQuantity();
         Double receivedQuantity=receipt.getReceivedQuantity();
         acceptedQuantity=acceptedQuantity==null?0:acceptedQuantity;
         rejectedQuantity=rejectedQuantity==null?0:rejectedQuantity;
         receivedQuantity=receivedQuantity==null?0:receivedQuantity;
         
        System.out.println("in.inverntory.controller.PurchaseController.receiptSave()"+receipt.getItem()+receipt.getItem().getItemId());
        
        if (bindingResult.hasErrors()) {
             model.addAttribute("groups",itemService.getAllGroups());
      
            model.addAttribute("receipt", receipt);
            model.addAttribute("message", bindingResult.getFieldError().getField()+":: incorrect input");
        return receiptFormConsumable;
    }else if(!(receipt.getItem()!=null
                    && receipt.getItem().getItemId()!=null 
                    && receipt.getItem().getItemId()!=0
                   ) )
    {
           model.addAttribute("message","please choose an item");
        return receiptFormConsumable;
    }else if(acceptedQuantity==0 || receivedQuantity==0)
    {
        model.addAttribute("message","quantity can not be 0");
        return receiptFormConsumable;
    }
    else if(receivedQuantity!=acceptedQuantity+rejectedQuantity)
    {
               model.addAttribute("message","Incorrect Quantity values:: received quantity should be sum of accepted quantity and rejeceted quantity ");
            return receiptFormConsumable;
    }
    ResultDataMap result=new ResultDataMap().setStatus(Boolean.FALSE).setMessage("ERROR");;
        SantizingUtility santizingUtility=new SantizingUtility();
        try {
             
            
            receipt=(ReceiptConsumable)santizingUtility.validate(receipt, "ReceiptConsumable");
             result=purchaseService.saveReceiptForm(receipt);
             
                 model.addAttribute("result",result);
                if(result.getStatus())
                {
                    model.addAttribute("message","saved successfully");
                     return "redirect:"+receiptFormConsumable+"?message=saved successfully";
                }else{
                     model.addAttribute("message","error");
                      return receiptFormConsumable;
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
                      return receiptFormConsumable;
    }
    
    
    
      @GetMapping("receipt-nonconsumable-form")
    public String receiptNonConsumableForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
        System.out.println("Hello");
        model.addAttribute("receipt", new Receipt());
        model.addAttribute("groups",itemService.getNonConsumableGroups());
          model.addAttribute("message",message);
        model.addAttribute("items",itemService.getAllItemsList());
        return "receipt-nonconsumable-form";
    }
    
    
    
    @PostMapping("receipt-nonconsumable-save")
    public String receiptNonConsumableSave(@Valid @ModelAttribute("receipt")Receipt receipt,
                                    final BindingResult bindingResult,
                                    Model model) 
    {
         model.addAttribute("groups",itemService.getAllGroups());
      
          model.addAttribute("receipt", receipt);
         Integer acceptedQuantity=receipt.getQuantity();
         acceptedQuantity=acceptedQuantity==null?0:acceptedQuantity;
         
        System.out.println("in.inverntory.controller.PurchaseController.receiptSave()"+receipt.getItem()+receipt.getItem().getItemId());
        
        if (bindingResult.hasErrors()) {
             model.addAttribute("groups",itemService.getAllGroups());
      
            model.addAttribute("receipt", receipt);
            model.addAttribute("message", bindingResult.getFieldError().getField()+":: incorrect input");
        return "receipt-nonconsumable-form";
    }else if(!(receipt.getItem()!=null
                    && receipt.getItem().getItemId()!=null 
                    && receipt.getItem().getItemId()!=0
                   ) )
    {
           model.addAttribute("message","please choose an item");
        return "receipt-nonconsumable-form";
    }else if(acceptedQuantity==0 )
    {
        model.addAttribute("message","quantity can not be 0");
        return "receipt-nonconsumable-form";
    }
    
    ResultDataMap result=new ResultDataMap().setStatus(Boolean.FALSE).setMessage("ERROR");;
        SantizingUtility santizingUtility=new SantizingUtility();
        try {
             receipt=(Receipt)santizingUtility.validate(receipt, "Receipt");
             result=purchaseService.saveNonConsumableReceiptForm(receipt);
             
                 model.addAttribute("result",result);
                if(result.getStatus())
                {
                    model.addAttribute("message","saved successfully");
                     return "redirect:"+"receipt-nonconsumable-form"+"?message=saved successfully";
                }else{
                     model.addAttribute("message","error");
                      return "receipt-nonconsumable-form";
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
                      return "receipt-nonconsumable-form";
    }
    
     
    
     @GetMapping("indents-list-json")
    public @ResponseBody List<HdIndent> allIndentsList(Model model)
    {
        
         boolean isAuthenticated=false;
         Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
       
         Collection<? extends GrantedAuthority> authorities=authuntication.getAuthorities();
         for(GrantedAuthority authority:authorities)
         {
            if(authority.getAuthority().equalsIgnoreCase("ROLE_ADMIN") 
                    || authority.getAuthority().equalsIgnoreCase("ROLE_RECEIPT_CONSUMABLE")
                    || authority.getAuthority().equalsIgnoreCase("ROLE_RECEIPT_NON_CONSUMABLE")
                    || authority.getAuthority().equalsIgnoreCase("ROLE_STOCK_MANAGER")
                    || authority.getAuthority().equalsIgnoreCase("ROLE_IM")
                    )
                {
                    isAuthenticated=true;
                  break;
                }
         }
          if(isAuthenticated){
           List<HdIndent> indentList=purchaseService.getAllIndentsList();
            
            return indentList;
          }else{
              return null;
          }
    }
    
    
    @GetMapping("my-indents")
    public String myIndentsList(Model model)
    {
        
         boolean isAdmin=false;
        List<Stock> stockList=new ArrayList<>();
         Authentication authuntication=SecurityContextHolder.getContext().getAuthentication();
            MstUser user=(MstUser)authuntication.getPrincipal();
           System.out.println("in.inverntory.controller.PurchaseController.myIndentsList()"+user.getUserId());
           List<HdIndent> indentList=purchaseService.getIndentorsIndents(user);
            model.addAttribute("issueList", indentList);
            return "indent-list";
    }
    
     @GetMapping("indent-list")
    public String indentList(Model model)
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
            List<HdIndent> indentList=purchaseService.getAllIndentsList();
            model.addAttribute("indentList", indentList);
            return "indent-list";
         }else{
            MstUser user=(MstUser)authuntication.getPrincipal();
           Integer userId= user.getUserId();
            List<HdIndent> indentList=purchaseService.getIndentsListToBeVerifiedByUser(user);
            model.addAttribute("indentList", indentList);
            return "indent-list";
         }
        
    }
    
    
    @GetMapping("indent-view")
    public String indentView(@RequestParam("indentId")Integer indentId,Model model)
    {
        model.addAttribute("indent", purchaseService.getIndent(indentId));
        return "indent-view";
    }
    
     @GetMapping("get-purchasetype")
    public @ResponseBody List<PurchaseType> getPurchaseType()
    {
        
        return purchaseTypeService.getAllPurchaseTypeList();
    }
    
    
    
}
