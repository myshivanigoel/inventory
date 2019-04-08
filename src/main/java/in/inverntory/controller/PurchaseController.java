/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inverntory.controller;

import in.inventory.service.ProjectService;
import in.inventory.service.PurchaseTypeService;
import in.db.auth.entity.MstUser;
import in.db.inventory.entity.Classification;
import in.db.inventory.entity.DtIndent;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.IndentStatus;
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

import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import in.utility.SantizingUtility;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
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
              
                   if(t.getItem()==null || t.getItem().getItemId()==null ||  t.getItem().getItemId()==0 )
                    {
                        t.setItem(new ItemMaster(1));
                    }
                    
                    
                    if( t.getItem().getItemId()==1)
                    {
                    
                     if(t.getDescriptionOfMaterial()==null || t.getDescriptionOfMaterial().trim().equals(""))
                        {
                             model.addAttribute("indent",indent);
                             model.addAttribute("message","please  add description");
                            return "indent-form";
                        }else{
                             
                            
                        }
                    }
                    if(t.getItemGroup()==null || t.getItemGroup().getGroupId()==null || t.getItemGroup().getGroupId()==0)
                    {
                        t.setItemGroup(new MstGroup(1));
                    }
                    if(t.getClassification()==null || t.getClassification().getClassificationId()==null || t.getClassification().getClassificationId()==0)
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

    
     @GetMapping("update-indent-form")
    public String updateIndentForm(@RequestParam("indentId")Integer indentId,
                                    @RequestParam(name = "message",required = false)String message,Model model)
    {
        System.out.println("Hello");
       HdIndent indent=purchaseService.getIndent(indentId);
       if(indent==null)
       {
           return "redirect:indent-form?message=incorrect indent Id";
       }
        model.addAttribute("purchaseTypeList",purchaseTypeService.getAllPurchaseTypeList());
        model.addAttribute("projectList",projectService.getAllProjectList());
        model.addAttribute("classList",itemService.getClassificationList());
        if(indent!=null && indent.getIndentDetailList()!=null && !indent.getIndentDetailList().isEmpty())
        {
            for (DtIndent dtIndent : indent.getIndentDetailList()) {
               if(dtIndent.getClassification()!=null && dtIndent.getClassification().getClassificationId()!=null)
               {
                   dtIndent.setGroupList(itemService.getGroupListByCLassification(dtIndent.getClassification().getClassificationId()));
               }
               if(dtIndent.getItemGroup()!=null && dtIndent.getItemGroup().getGroupId()!=null)
               {
                   dtIndent.setItemList(itemService.getItemsList(dtIndent.getItemGroup().getGroupId()));
               }
            }
        }
         MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       model.addAttribute("indentList",purchaseService.getIndentorsIndents(user));
       
       model.addAttribute("indent",indent);
         model.addAttribute("message", message);
        return "indent-form-update";
    }
    
    
    @PostMapping("update-indent-form")
    public String updateIndent(@Valid @ModelAttribute(name = "indent")HdIndent indent
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
              
                   if(t.getItem()==null || t.getItem().getItemId()==null ||  t.getItem().getItemId()==0 )
                    {
                        t.setItem(new ItemMaster(1));
                    }
                    
                    
                    if( t.getItem().getItemId()==1)
                    {
                    
                     if(t.getDescriptionOfMaterial()==null || t.getDescriptionOfMaterial().trim().equals(""))
                        {
                             model.addAttribute("indent",indent);
                             model.addAttribute("message","please  add description");
                            return "indent-form";
                        }else{
                             
                            
                        }
                    }
                    if(t.getItemGroup()==null || t.getItemGroup().getGroupId()==null || t.getItemGroup().getGroupId()==0)
                    {
                        t.setItemGroup(new MstGroup(1));
                    }
                    if(t.getClassification()==null || t.getClassification().getClassificationId()==null || t.getClassification().getClassificationId()==0)
                    {
                        t.setClassification(new Classification(1));
                    }
                    
                }  
             result=purchaseService.updateIndentForm(indent);
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
    
   /*  @GetMapping("indent-list")
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
    
    */
    @GetMapping("indent-view")
    public String indentView(@RequestParam("indentId")Integer indentId,Model model)
    {
        HdIndent indent=purchaseService.getIndent(indentId);
        model.addAttribute("indent", indent);
        model.addAttribute("authorizationStatusList",purchaseService.getauthorizationStatusList(indent));
        return "indent-view";
    }
    
     @GetMapping("indent-action")
    public String indentAction(@RequestParam("indentId")Integer indentId,
                                @RequestParam(name="message",required = false)String message,Model model)
    {
        HdIndent indent=purchaseService.getIndent(indentId);
        model.addAttribute("indent", indent);
        model.addAttribute("authorizationStatusList",purchaseService.getauthorizationStatusList(indent));
        return "indent-action";
    }
    @PostMapping("indent-action-accept")
    public @ResponseBody ResultDataMap indentActionAccept(@RequestParam("indentId")String sindentId,
                                                            @RequestParam(name="remarks",required = false)String remarks,Model model)
    {
        Integer indentId=Integer.valueOf(sindentId);
        MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        HdIndent indent=purchaseService.getIndent(indentId  );
        if(purchaseService.isSecondLastAuthenticator(indent.getIndentor().getUserId(),user.getUserId()) 
                && indent.getStatus().equals(Strings.IndentStatusFinanceRejected))
        {
            if(remarks==null || remarks.trim().equals(""))
            {
             return new ResultDataMap().setStatus(false).setMessage("Since finance has declined ,please write some remark before sending to Authority");  
            }else{
             return purchaseService.acceptIndent(indentId,user.getUserId(),remarks);
            }
        }
        
        
        IndentStatus i=purchaseService.ifUserAuthenticatedIndent(user.getUserId(), indentId);
        if(i==null)
        {
            return purchaseService.acceptIndent(indentId,user.getUserId());
        }else{
            return new ResultDataMap().setStatus(false).setMessage("indent already "+i.getStatus());
        }
        
        
        
    }
    @PostMapping("indent-action-reject")
    public @ResponseBody ResultDataMap indentActionReject(@RequestParam("indentId")Integer indentId,Model model)
    {
       MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         IndentStatus i=purchaseService.ifUserAuthenticatedIndent(user.getUserId(), indentId);
        if(i==null)
        {
            return purchaseService.rejectIndent(indentId,user.getUserId());
        }else{
            return new ResultDataMap().setStatus(false).setMessage("indent already "+i.getStatus());
        }
    }
    
     @GetMapping("get-purchasetype")
    public @ResponseBody List<PurchaseType> getPurchaseType()
    {
        
        return purchaseTypeService.getAllPurchaseTypeList();
    }
    
    @GetMapping("requested-indents")
    public String getRequestedIndentsList(Model model)
    {
        List<HdIndent> indents=new ArrayList<>();
        MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       
        indents=purchaseService.getRequestedIndentsList(user.getUserId());
        model.addAttribute("indentsList", indents);
        return "requested-indents-list";
    }
    
    
    @GetMapping("approved-indents")
    public String getApprovedIndentsList(Model model)
    {
        List<HdIndent> indents=new ArrayList<>();
        MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       
        indents=purchaseService.getApprovedIndentsList(user.getUserId());
        model.addAttribute("indentsList", indents);
        return "approved-indents-list";
    }
    
     @RolesAllowed(value = "ROLE_FINANCE")
     @GetMapping("finance-request-indents")
    public String getRequestsForFinanceApproval(Model model)
    {
        List<HdIndent> indents=new ArrayList<>();
        MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       
        indents=purchaseService.getRequestsForFinanceApproval(user.getUserId());
        model.addAttribute("indentsList", indents);
        return "indent-action-finance-list";
    }
    
            
           @RolesAllowed(value = "ROLE_FINANCE") 
             @GetMapping("indent-action_finance")
    public String indentActionFinacne(@RequestParam("indentId")Integer indentId,Model model)
    {
         HdIndent indent=purchaseService.getIndent(indentId);
        model.addAttribute("indent", indent);
        model.addAttribute("authorizationStatusList",purchaseService.getauthorizationStatusList(indent));
        return "indent-action-finance";
    }
    @RolesAllowed(value = "ROLE_FINANCE")
    @PostMapping("indent-action_finance")
    public String indentActionFinance(@RequestParam("indentId")Integer indentId,
                                        @RequestParam("remarks")String remarks,
                                        @RequestParam("financeStatus")String financeStatus
                                        ,Model model)
    {
        MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResultDataMap result=purchaseService.indentActionFinance(indentId,remarks,financeStatus);
        if(result.getStatus())
        {
            return "redirect:/finance-request-indents";
        }else{
            model.addAttribute("message",result.getMessage());
            return "indent-action-finance";
        }
        
        
    }
}
