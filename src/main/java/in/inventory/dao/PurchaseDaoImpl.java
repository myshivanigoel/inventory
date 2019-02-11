/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
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
}
