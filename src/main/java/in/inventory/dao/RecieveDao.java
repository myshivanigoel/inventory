/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.inventory.entity.HdReceipt;
import in.util.entity.ResultDataMap;
import java.util.List;

/**
 *
 * @author anuja
 */
public interface RecieveDao {

    public ResultDataMap saveReceipt(HdReceipt receipt);
    public ResultDataMap updateReceipt(HdReceipt receipt);
    public HdReceipt getReceiptById(Integer receiptId);
    public List<HdReceipt> getReceiptsByIndentId(Integer indentId);
    public List<HdReceipt> getReceiptsByReciverId(Integer reciverId);
    public List<HdReceipt> getAllReceipts();
    
}
