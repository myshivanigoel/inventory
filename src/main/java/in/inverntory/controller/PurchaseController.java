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
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private PurchaseTypeService purchaseTypeService;
    
    @Autowired
    private ItemService itemService;
          @Autowired
    private ProjectService projectService;
    
          
       public List<String> getBudgetYearList()
       {
           List<String> list=new ArrayList<>();
           Integer year = Calendar.getInstance().get(Calendar.YEAR);
         
           for(int i=0;i<10;i++)
           {
               Integer prev=year-1;
             
               String yearString=prev.toString()+"-"+year.toString();
               list.add(yearString);
               year=year+1;
           }
           System.out.println("in.inverntory.controller.PurchaseController.getBudgetYearList()\n"+list);
          return list;
       }
          
    @GetMapping("indent-form")
    public String indentForm(@RequestParam(name = "message",required = false)String message,Model model)
    {
        System.out.println("Hello");
        model.addAttribute("purchaseTypeList",purchaseTypeService.getAllPurchaseTypeList());
        model.addAttribute("projectList",projectService.getAllProjectList());
        model.addAttribute("classList",itemService.getClassificationList());
         MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       model.addAttribute("indentList",purchaseService.getDraftedIndents(user.getUserId()));
       model.addAttribute("budgetyearList",getBudgetYearList());
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
    
    public List<String> getPurchaseTypeList()
    {
        List<String> list=new ArrayList(Arrays.asList( "General","Specific brand","Proprietary" ));
       
        return list;
    }
    
    public List<String> getMOdeOfDispatchList()
    {
        List<String> list=new ArrayList(Arrays.asList(  "Any","AIR","SEA","ROAD","RAIL","POST","BY HAND"));
       
        return list;
    }
     @GetMapping("update-indent-form")
    public String updateIndentForm(@RequestParam("indentId")Integer indentId,
                                    @RequestParam(name = "message",required = false)String message,Model model)
    {
       MstUser loggedInUser= (MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!purchaseService.isIndentorsIndent(loggedInUser.getUserId(), indentId))
        {
            return "redirect:indent-form?message=incorrect indent Id";
        }
        System.out.println("Hello");
       HdIndent indent=purchaseService.getIndent(indentId);
       if(indent==null)
       {
           return "redirect:indent-form?message=incorrect indent Id";
       }
        model.addAttribute("budgetyearList",getBudgetYearList());
        model.addAttribute("purchaseTypeList",purchaseTypeService.getAllPurchaseTypeList());
        model.addAttribute("projectList",projectService.getAllProjectList());
        model.addAttribute("classList",itemService.getClassificationList());
         model.addAttribute("modeOfDispatchList",getMOdeOfDispatchList());
         model.addAttribute("purchaseTypeList",purchaseTypeService.getAllPurchaseTypeList());
         
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
    ResultDataMap result;
    
    @PostMapping("update-indent-form")
    public @ResponseBody ResultDataMap updateIndent(@Valid @ModelAttribute(name = "indent")HdIndent indent
                                                             ,final BindingResult bindingResult
                                                             ,Model model
                                                             ,HttpServletRequest request
                                                             ,RedirectAttributes redirectAttributes) 
    {
        MstUser loggedInUser= (MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         if(!purchaseService.isIndentorsIndent(loggedInUser.getUserId(), indent.getIndentId()))
        {
            return result.setStatus(Boolean.FALSE).setMessage("This is not your indent");
        }
        System.out.println("inden t :: "+indent);
        if(bindingResult.hasErrors())
        {
            
            return result.setStatus(Boolean.FALSE).setMessage(bindingResult.getFieldError().getField()+":: incorrect input");
        }
          MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          if(user==null)
          {
             return result.setStatus(Boolean.FALSE).setMessage(Strings.error);
      
          }
         indent.setIndentor(user);
     
        System.out.println("in.inverntory.controller.PurchaseController.indentSave()"+indent);
        ResultDataMap result=new ResultDataMap().setStatus(Boolean.FALSE).setMessage("ERROR");
        SantizingUtility santizingUtility=new SantizingUtility();
         
        try {
             indent=(HdIndent)santizingUtility.validate(indent, "Indent");
             if(indent==null){
                  
                       return result.setStatus(Boolean.FALSE).setMessage(Strings.error);
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
                             
                           return result.setStatus(Boolean.FALSE).setMessage("please  add description");
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
                    return result.setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
                     
                }else{
                    return result.setStatus(Boolean.FALSE).setMessage(Strings.error);
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
         return result.setStatus(Boolean.FALSE).setMessage(Strings.error);
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
    
    
    
    @GetMapping("indentors-indents-list")
    public String getDraftedIndents(Model model)
    {
        MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("indentList", purchaseService.getIndentorsIndents(user));
        return "indentors-indents-list";
    }
    
    @GetMapping("submit-indent")
    public String indentSubmitForm(@RequestParam("indentId")Integer indentId,Model model)
    {
        MstUser loggedInUser= (MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(purchaseService.isIndentorsIndent(loggedInUser.getUserId(), indentId)){
        model.addAttribute("indent", purchaseService.getIndent(indentId));
         return "indent-submit";
        }else{
             model.addAttribute("message","not user indent");
             return "drafted-indents-list";
        }
        
    }
    
    @PostMapping("submit-indent")
    public @ResponseBody ResultDataMap submitIndent(@RequestParam("indentId")Integer indentId,Model model)
    {
        System.out.println("in.inverntory.controller.PurchaseController.submitIndent() post");
        MstUser loggedInUser= (MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         if(purchaseService.isIndentorsIndent(loggedInUser.getUserId(),indentId))
         {
            return purchaseService.submitIndent(indentId);
             
         }else{
             return result.setStatus(Boolean.FALSE).setMessage(Strings.error);
         }
    }
     @RolesAllowed(value = "ROLE_FINANCE") 
             @GetMapping("indent-previous-finance")
    public String indentPreviousFinacne(Model model)
    {
        model.addAttribute("indentList", purchaseService.getIndentListForFinance());
        return "indent-previous-finance-list";
    }
    
    
}
