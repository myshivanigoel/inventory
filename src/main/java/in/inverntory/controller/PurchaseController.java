/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inverntory.controller;

import in.inventory.service.PurchaseService;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import in.utility.SantizingUtility;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author anuja
 */

@Controller
@PropertySource("classpath:HtmlPages.properties")
public class PurchaseController {
    @Value("${IndentForm}")
    private String indentForm;
    
    @Value("$IndentSaveAck")
    private String IndentSaveAck;
    
    @Autowired
    private PurchaseService purchaseService;
    
    @GetMapping("indent-form")
    public String indentForm()
    {
        
       
        return indentForm;
    }
    
    
    ResultDataMap result;
    
    @PostMapping("indent-save")
    public String indentSave(@RequestParam("indent")Indent indent,Model model) 
    {
        
        SantizingUtility santizingUtility=new SantizingUtility();
        try {
             indent=(Indent)santizingUtility.validate(indent, "Indent");
             result=purchaseService.saveIndentForm(indent);
           model.addAttribute("result",result);
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
          
        return result.getForwardUrl();
    }

    
    
    
    public String getIndentForm() {
        return indentForm;
    }

    public void setIndentForm(String indentForm) {
        this.indentForm = indentForm;
    }

    public String getIndentSaveAck() {
        return IndentSaveAck;
    }

    public void setIndentSaveAck(String IndentSaveAck) {
        this.IndentSaveAck = IndentSaveAck;
    }

    public PurchaseService getPurchaseService() {
        return purchaseService;
    }

    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    
    
    
}
