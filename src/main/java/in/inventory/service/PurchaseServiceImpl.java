/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.inventory.dao.PurchaseDao;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
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
    
    
}
