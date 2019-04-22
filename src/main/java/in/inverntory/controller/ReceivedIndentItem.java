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
import in.db.inventory.entity.HdReceipt;
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
import in.inventory.service.ReceivedIndentItemService;
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
public class ReceivedIndentItem {
  
    
    @Autowired
    private PurchaseService purchaseService;
     
    @Autowired
    private ReceivedIndentItemService receivedItemService;
    
    @GetMapping("received-indent-item")
    public String receivedIndentItem(@RequestParam("indentId")Integer indentId,
                                @RequestParam(name="message",required = false)String message,Model model)
    {
        
        HdIndent indent=purchaseService.getIndent(indentId);
        List<DtIndent> dtList=new ArrayList<>();
        for (DtIndent dtIndent : indent.getIndentDetailList()) {
          if(dtIndent.getAcceptedFlag())
            {
                dtList.add(dtIndent);
            }
        }
        indent.setIndentDetailList(dtList);
        model.addAttribute("indent", indent);
        model.addAttribute("authorizationStatusList",purchaseService.getauthorizationStatusList(indent));
        return "received-indentItem";
    }
    
    
    @PostMapping("received-indent-item")
    public String receivedIndentAccept(@ModelAttribute("indent")HdIndent rindent,
                                                           Model model)
    {
        HdReceipt hdReceipt = new HdReceipt();
        Integer indentId=rindent.getIndentId();
        MstUser user=(MstUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        HdIndent indent=purchaseService.getIndent(indentId  );
       
         int j=0;
        for (DtIndent dtIndent : rindent.getIndentDetailList()) {
          if(dtIndent.getAcceptedFlag())
          {
              j++;
              
          }
    }
        if(j==0){
         return "redirect:received-indentItem?message=Please select received item.";
        }
        else{
            hdReceipt=receivedItemService.getReceivedItemDetails(rindent);
              model.addAttribute("receipt",hdReceipt);
              model.addAttribute("indent", indent);
              return "receipt-form";
        }
}
} 