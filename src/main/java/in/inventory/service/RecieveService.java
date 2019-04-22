/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.HdReceipt;
import in.util.entity.ResultDataMap;
import java.util.List;

/**
 *
 * @author anuja
 */
public interface RecieveService {
    
    public ResultDataMap saveHdReceipt(HdReceipt receipt,Integer userId);
    public ResultDataMap updateHdReceipt(HdReceipt receipt, Integer userId);
    
    
    public boolean isCompletedReceipt(Integer receiptId);
    public boolean isIndentorsReceipt(Integer indentorId,Integer reciptId);
    public boolean isUsersRecipt(Integer userId,Integer receiptId);
    
    
    public HdReceipt getReceiptById(Integer receiptId);
    public List<HdReceipt> getReceiptsByIndentId(Integer indentId);
    public List<HdReceipt> getReceiptsByReceiver(Integer receivedBy);
    
    
}
