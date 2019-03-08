/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import java.util.List;

/**
 *
 * @author anuja
 */
public interface ItemService {
    public List<ItemMaster> getItemsList(Integer groupId);
    public List<ItemMaster> getAllItemsList();

    public List<MstGroup> getAllGroups();

    public Object getConsumableGroups();

    public Object getNonConsumableGroups();

    public List<ItemMaster> getAvailableItemsList(Integer groupId);
    
}
