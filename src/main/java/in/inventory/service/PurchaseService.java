/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */


public interface PurchaseService {
    
    /**
     *
     * @param indent
     * @return ResultDataMap
     */
    public ResultDataMap saveIndentForm(Indent indent);

    public ResultDataMap saveReceiptForm(Receipt receipt);

 
    public ResultDataMap saveReceiptForm(ReceiptConsumable receipt);

    public ResultDataMap saveNonConsumableReceiptForm(Receipt receipt);
    
    
     public List<Indent> getIndentorsIndents(Integer userId);

    public List<Indent> getAllIndentsList();

    public List<Indent> getIndentsListToBeVerifiedByUser(Integer userId);

    public Object getIndent(Integer indentId);
   
    
}
