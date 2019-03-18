/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.inventory.entity.Classification;
import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
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
                        .createQuery("from ItemMaster where activeFlag='Y'").list();
    }

    @Override
    public List<MstGroup> getAllGroups() {
        return sessionFactory.getCurrentSession()
                        .createQuery("from MstGroup where activeFlag='Y' order by groupName").list();
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

    @Override
    public List<Classification> getClassificationList() {
        return sessionFactory.getCurrentSession()
                        .createQuery("from Classification where activeFlag='Y' ").list();
    }

    @Override
    public ResultDataMap saveOrUpdateClassification(Classification classification) {
       sessionFactory.getCurrentSession().saveOrUpdate(classification);
       return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public Classification getClassification(Integer classificationId) {
        return sessionFactory.getCurrentSession().get(Classification.class, classificationId);
    }

    @Override
    public MstGroup getGroupById(Integer groupId) {
         return sessionFactory.getCurrentSession().get(MstGroup.class, groupId);
    }

    @Override
    public ResultDataMap saveOrUpdateGroup(MstGroup group) {
        sessionFactory.getCurrentSession().saveOrUpdate(group);
       return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public ResultDataMap saveOrUpdateItem(ItemMaster item) {
        sessionFactory.getCurrentSession().saveOrUpdate(item);
       return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public ItemMaster getItemById(Integer itemId) {
        return sessionFactory.getCurrentSession().get(ItemMaster.class, itemId);
    }

    @Override
    public List<MstGroup> getGroupListByCLassification(Integer classificationId) {
       return sessionFactory.getCurrentSession()
                        .createQuery("from MstGroup  where classificationId=:classificationId and activeFlag='Y'")
                        .setParameter("classificationId", classificationId)
                        .list();
    }

    @Override
    public boolean updateAble(Classification classification) {
        List list=sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT 1 FROM inventory.DtIndent where classification=:classificationId " +
                            " union "+
                            " SELECT 1 FROM inventory.MstGroup where classificationId=:classificationId "+
                            " union " +
                            " SELECT 1 FROM inventory.DtIndent " +
"                               where itemId in (select itemId from ItemMaster " +
"						where itemGroup in (Select groupId from MstGroup  " +
"											where classificationId = :classificationId))")
                            .setParameter("classificationId", classification.getClassificationId())
                            .list();
        
        if(list==null || list.isEmpty())
        {
            
              System.out.println("in.inventory.dao.ItemDaoImpl.updateAble()");
            return true;
           
        }else{
            System.out.println("in.inventory.dao.ItemDaoImpl.updateAble()"+list.get(0));
            return false;
        }
        
    }

    @Override
    public boolean updateAble(ItemMaster item) {
        List list=sessionFactory.getCurrentSession()
                .createSQLQuery("select 1 from DtIndent where itemId=:itemId " +
                                    "union " +
                                    "select 1 from Receipt where itemId=:itemId " +
                                    "union select 1 from Stock where itemId=:itemId ")
                            .setParameter("itemId", item.getItemId())
                            .list();
        
        if(list==null || list.isEmpty())
        {
            
              System.out.println("in.inventory.dao.ItemDaoImpl.updateAble() true");
            return true;
           
        }else{
            System.out.println("in.inventory.dao.ItemDaoImpl.updateAble() false"+list.get(0));
            return false;
        }
    }

    @Override
    public boolean updateAble(MstGroup group) {
         List list=sessionFactory.getCurrentSession()
                .createSQLQuery("SELECT 1 FROM DtIndent where itemGroup=:groupId " +
                            " union "+
                            " SELECT 1 FROM ItemMaster where groupId=:groupId "+
                            " union " +
                            " SELECT 1 FROM DtIndent " +
"                               where itemId in (select itemId from ItemMaster " +
"						where itemGroup =:groupId)")
                            .setParameter("groupId", group.getGroupId())
                            .list();
        
        if(list==null || list.isEmpty())
        {
            
              System.out.println("in.inventory.dao.ItemDaoImpl.updateAble()");
            return true;
           
        }else{
            System.out.println("in.inventory.dao.ItemDaoImpl.updateAble()"+list.get(0));
            return false;
        }
        
    }

    
    
    
}
