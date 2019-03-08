/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.inventory.dao.ItemDao;
import in.db.inventory.entity.ItemMaster;
import in.db.inventory.entity.MstGroup;
import in.db.inventory.entity.Stock;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */
@Service
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
    public Object getConsumableGroups() {
      return itemDao.getConsumableGroups();  
    }

    @Override
    public Object getNonConsumableGroups() {
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
    
}
