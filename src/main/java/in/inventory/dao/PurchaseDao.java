/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import java.util.List;

/**
 *
 * @author anuja
 */
public interface PurchaseDao {
      public ResultDataMap saveIndentForm(Indent indent);

    public ResultDataMap saveReceiptForm(Receipt receipt);

    public ResultDataMap saveReceiptForm(ReceiptConsumable receipt);

    public ResultDataMap saveNonConsumableReceiptForm(Receipt receipt);

    public List<Indent> getIndentorsIndents(MstUser userId);

    public List<Indent> getAllIndentsList();

    public List<Indent> getIndentsListToBeVerifiedByUser(MstUser userId);

    public Object getIndent(Integer indentId);

  
 
   

   

   
}
