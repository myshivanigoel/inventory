/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.Classification;
import in.inventory.dao.ItemDao;
import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import in.db.inventory.entity.Stock;
import in.util.entity.ResultDataMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemDao itemDao;
    @Autowired 
    StockService stockService;
    @Override
    public List<ItemMaster> getItemsList(Integer groupId) {
        return itemDao.getItemsList(groupId);
    }

    @Override
    public List<ItemMaster> getAllItemsList() {
         return itemDao.getAllItemsList();
    }

    @Override
    public List<MstGroup> getAllGroups() {
        return itemDao.getAllGroups();
    }

    @Override
    public List<MstGroup> getConsumableGroups() {
      return itemDao.getConsumableGroups();  
    }

    @Override
    public List<MstGroup> getNonConsumableGroups() {
      return itemDao.getNonConsumableGroups();
    }

    @Override
    public List<ItemMaster> getAvailableItemsList(Integer groupId) {
        List<ItemMaster> list=itemDao.getItemsList(groupId);
        System.out.println(""+list);
        List<ItemMaster> listToRemove=new ArrayList<>();
        list.forEach((item) -> {
            Stock stock=stockService.getStockForItem(item.getItemId());
            if(stock==null || stock.getAvailableQty()<=0)
            {
                
                listToRemove.add(item);
            }else{
                
            }
            
        });
        list.removeAll(listToRemove);
        return list;
    }

    @Override
    public List<Classification> getClassificationList() {
        List<Classification> classificationList=new ArrayList<>();
        classificationList=itemDao.getClassificationList();
                return classificationList;
    }

    @Override
    public ResultDataMap saveClassification(Classification classification) {
        System.out.println("in.inventory.service.ItemServiceImpl.saveClassification()");
        if(classification.getClassificationId()==null)
        {
            System.out.println("in.inventory.service.ItemServiceImpl.saveClassification()2");
        
            classification.setDateOfEntry(new Date());
            classification.setActiveFlag('Y');
             return itemDao.saveOrUpdateClassification(classification);
        }else{
             System.out.println("in.inventory.service.ItemServiceImpl.saveClassification()3");
       
           Classification classificationDb=itemDao.getClassification(classification.getClassificationId());
           if(classification.getActiveFlag()!=null)
           {
            classificationDb.setActiveFlag(classification.getActiveFlag());
           
           }
           classificationDb.setDateOfModification(new Date());
           if(classification.getClassification()!=null && !classification.getClassification().trim().equals("")){
             classificationDb.setClassification(classification.getClassification());
           }
            return itemDao.saveOrUpdateClassification(classificationDb);
        }
       
    }

    @Override
    public Classification getClassification(Integer classificationId) {
        Classification classification=itemDao.getClassification(classificationId);
       
        return classification!=null?classification:new Classification();
    }

    @Override
    public MstGroup getGroupById(Integer groupId) {
       MstGroup group=itemDao.getGroupById(groupId);
       return group!=null?group:new MstGroup();
    }

    @Override
    public ResultDataMap saveGroup(MstGroup group) {
        if(group.getGroupId()==null)
        {
            group.setDtEntryDate(new Date());
            group.setActiveFlag('Y');
             return itemDao.saveOrUpdateGroup(group);
        }else{
           MstGroup groupdb=itemDao.getGroupById(group.getGroupId());
           if(group.getActiveFlag()!=null){
            groupdb.setActiveFlag(group.getActiveFlag());
           }
           groupdb.setDtModificationDate(new Date());
           if(group.getGroupName()!=null && !group.getGroupName().trim().equals("")){
             groupdb.setGroupName(group.getGroupName());
           }
            return itemDao.saveOrUpdateGroup(groupdb);
        }
    }

    @Override
    public ResultDataMap saveItem(ItemMaster item) {
        if(item.getItemId()==null)
        {
            item.setDateOfEntry(new Date());
            item.setActiveFlag('Y');
             return itemDao.saveOrUpdateItem(item);
        }else{
           ItemMaster itemdb=itemDao.getItemById(item.getItemId());
           if(item.getActiveFlag()!=null){
            itemdb.setActiveFlag(item.getActiveFlag());
           }
            if(item.getItemName()!=null && !item.getItemName().trim().equals("")){
             itemdb.setItemName(item.getItemName());
           }
            if(item.getDescription()!=null && item.getDescription().trim().equals(""))
             itemdb.setDescription(item.getDescription());
         
            return itemDao.saveOrUpdateItem(itemdb);
        }
    }

    @Override
    public List<MstGroup> getGroupListByCLassification(Integer classificationId) {
         return itemDao.getGroupListByCLassification(classificationId);    
    }

    @Override
    public boolean updateAble(Classification classification) {
         return itemDao.updateAble(classification);    
    }

    @Override
    public ItemMaster getItemById(Integer itemId) {
        return itemDao.getItemById(itemId);  
    }

    @Override
    public boolean updateAble(ItemMaster item) {
        return itemDao.updateAble(item);    
    }

    @Override
    public boolean updateAble(MstGroup group) {
       return itemDao.updateAble(group);    
    }
    
}
