/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.Receipt;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
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
    
    
}
