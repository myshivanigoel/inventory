/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.inventory.entity.Receipt;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;

/**
 *
 * @author anuja
 */
public interface PurchaseDao {
      public ResultDataMap saveIndentForm(Indent indent);

    public ResultDataMap saveReceiptForm(Receipt receipt);

   
}
