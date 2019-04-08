/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inverntory.controller;

import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.inventory.service.ItemService;
import in.inventory.service.PurchaseService;
import in.inventory.service.StockService;
import in.util.entity.ResultDataMap;
import in.utility.SantizingUtility;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author anuja
 */
@PropertySource("classpath:HtmlPages.properties")
public class ReceiptController {
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
    
     
    
}
