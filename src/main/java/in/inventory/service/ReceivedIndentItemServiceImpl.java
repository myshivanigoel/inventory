/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.DtIndent;
import in.db.inventory.entity.DtReceipt;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.HdReceipt;
import in.inventory.dao.PurchaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shivani
 */
@Service
public class ReceivedIndentItemServiceImpl implements ReceivedIndentItemService{

    @Autowired
    PurchaseDao  purchaseDao;
            
    @Override
    public HdReceipt getReceivedItemDetails(HdIndent rindent) {
        
        
        HdIndent dbIndent = purchaseDao.getIndent(rindent.getIndentId());
        HdReceipt hdReceipt = new HdReceipt();
        hdReceipt.setIndent(dbIndent);
        for (DtIndent rdtIndent : rindent.getIndentDetailList()) 
        {
            if(rdtIndent.getAcceptedFlag())
            {
            for(DtIndent dbdtIndent : dbIndent.getIndentDetailList())
            {
                if(rdtIndent.getDtIndentId().equals(dbdtIndent.getDtIndentId()))
                {
                    DtReceipt dtReceipt = new DtReceipt();
                    dtReceipt.setItem(dbdtIndent.getItem());
                    dtReceipt.setRequestedQuantity(dbdtIndent.getQuantity());
                    
                    hdReceipt.getDtReceipts().add(dtReceipt);
                }
            }
            }
        }
        return hdReceipt;
    }
    
}
