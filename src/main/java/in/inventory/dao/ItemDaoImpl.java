/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anuja
 */
@Repository

public class ItemDaoImpl implements ItemDao{
    
   
      @Autowired
    SessionFactory sessionFactory;
    @Override
    public List<ItemMaster> getItemsList(Integer groupId) {
       return sessionFactory.getCurrentSession()
                        .createQuery("from ItemMaster where groupId=:groupId").setParameter("groupId", groupId).list();
    }

    @Override
    public List<ItemMaster> getAllItemsList() {
        
        return sessionFactory.getCurrentSession()
                        .createQuery("from ItemMaster").list();
    }

    @Override
    public List<MstGroup> getAllGroups() {
        return sessionFactory.getCurrentSession()
                        .createQuery("from MstGroup").list();
    }

    @Override
     public List<MstGroup> getConsumableGroups() {
        return sessionFactory.getCurrentSession()
                
                        .createQuery("from MstGroup where valueType='C'").list();
    }
   

    @Override
     public List<MstGroup> getNonConsumableGroups() {
        return sessionFactory.getCurrentSession()
                        .createQuery("from MstGroup  where valueType='N'").list();
    }

    
    
    
}
