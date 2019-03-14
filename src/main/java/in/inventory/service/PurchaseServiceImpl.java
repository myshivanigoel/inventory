/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.inventory.dao.PurchaseDao;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */
@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    PurchaseDao purchaseDao;
    
  
    @Override
    public ResultDataMap saveIndentForm(Indent indent) {
 
        
        
        return purchaseDao.saveIndentForm(indent);
    }

    @Override
    public ResultDataMap saveReceiptForm(Receipt receipt) {
        return purchaseDao.saveReceiptForm(receipt);
    }

    @Override
    public ResultDataMap saveReceiptForm(ReceiptConsumable receipt) {
        return purchaseDao.saveReceiptForm(receipt);
    }

    @Override
    public ResultDataMap saveNonConsumableReceiptForm(Receipt receipt) {
        return purchaseDao.saveNonConsumableReceiptForm(receipt);  }

    @Override
    public List<Indent> getIndentorsIndents(MstUser userId) {
        return purchaseDao.getIndentorsIndents(userId);
    }

    @Override
    public List<Indent> getAllIndentsList() {
       return purchaseDao.getAllIndentsList();
     }

    @Override
    public List<Indent> getIndentsListToBeVerifiedByUser(MstUser userId) {
       return purchaseDao.getIndentsListToBeVerifiedByUser(userId);
               
    }

    @Override
    public Object getIndent(Integer indentId) {
       return purchaseDao.getIndent(indentId);
    }

   
    
    
}
