/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.inventory.entity.HdReceipt;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecieveDaoImpl implements RecieveDao {

    
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public ResultDataMap saveReceipt(HdReceipt receipt) {
        sessionFactory.getCurrentSession().save(receipt);
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public ResultDataMap updateReceipt(HdReceipt receipt) {
    sessionFactory.getCurrentSession().update(receipt);
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public HdReceipt getReceiptById(Integer receiptId) {
        return sessionFactory.getCurrentSession().get(HdReceipt.class,receiptId);
        
    
    }

    @Override
    public List<HdReceipt> getReceiptsByIndentId(Integer indentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HdReceipt> getReceiptsByReciverId(Integer reciverId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HdReceipt> getAllReceipts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
