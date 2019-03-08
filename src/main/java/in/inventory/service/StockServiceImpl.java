/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.Issue;
import in.db.inventory.entity.Stock;
import in.util.entity.ResultDataMap;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */

@Service

public class StockServiceImpl implements StockService {

    
    @Autowired
    private StockDao stockDao;
    
    @Override
    public Stock getStockForItem(Integer itemId) {
       return stockDao.getStockForItem(itemId);
    }

    
    @Override
    public Stock updateStockForItem(Stock stock) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stock saveNewItemInStock(Stock stock) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stock deleteItemFromStock(Integer itemId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultDataMap issueItem(Issue issue) {
        return stockDao.issueItem(issue);
    
    }

    @Override
    public List<Stock> getAllItemsStock() {
         return stockDao.getAllItemsStock();
    }

    @Override
    public List<Issue> getIssuedStockByUserId(Integer userId) {
         return stockDao.getIssuedStockByUserId(userId);    }
    
}
