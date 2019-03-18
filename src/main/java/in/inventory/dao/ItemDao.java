package in.inventory.dao;

import in.db.inventory.entity.Classification;
import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import in.util.entity.ResultDataMap;
import java.util.List;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author anuja
 */
public interface ItemDao {

    public List<ItemMaster> getItemsList(Integer groupId);

    public List<ItemMaster> getAllItemsList();

    public List<MstGroup> getAllGroups();

    public List<MstGroup> getConsumableGroups();

    public List<MstGroup> getNonConsumableGroups();

    public List<Classification> getClassificationList();

    public ResultDataMap saveOrUpdateClassification(Classification classification);

    public Classification getClassification(Integer classificationId);

    public MstGroup getGroupById(Integer groupId);

    public ResultDataMap saveOrUpdateGroup(MstGroup group);

    public ResultDataMap saveOrUpdateItem(ItemMaster item);

    public ItemMaster getItemById(Integer itemId);

    public List<MstGroup> getGroupListByCLassification(Integer classificationId);

    public boolean updateAble(Classification classification);

    public boolean updateAble(ItemMaster item);

    public boolean updateAble(MstGroup group);
    
    
}
