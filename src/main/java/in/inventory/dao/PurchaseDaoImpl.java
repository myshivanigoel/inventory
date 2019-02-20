/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.Stock;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anuja
 */

@Repository
public class PurchaseDaoImpl implements PurchaseDao{
      @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public ResultDataMap saveIndentForm(Indent indent) {
 
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(indent.getHdIndent());
        session.save(indent.getDtIndent());
        session.getTransaction().commit();
        
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public ResultDataMap saveReceiptForm(Receipt receipt) {
        // save in receipt table
        // update stock table
         Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();
        //receipt table
        session.save(receipt);
        //stock table
        Stock stock=session.get(Stock.class,receipt.getItem().getItemId());
        if(stock==null)
        {
            stock=new Stock();
            
            stock.setItem(receipt.getItem());
            stock.setDateOfEntry(new Date());
        }
            stock.setAvailableQty(stock.getAvailableQty()+receipt.getQuantity());
            stock.setDateOfModification(new Date());
           
        
        session.saveOrUpdate(stock);
        session.getTransaction().commit();
        
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
       
    }
}
