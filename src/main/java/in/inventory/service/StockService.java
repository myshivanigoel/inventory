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

/**
 *
 * @author anuja
 */
public interface StockService {
    
    
    public Stock getStockForItem(Integer itemId);
    public Stock updateStockForItem(Stock stock);
    public Stock saveNewItemInStock(Stock stock);
    public Stock deleteItemFromStock(Integer itemId);

    public ResultDataMap issueItem(Issue sue);

    public List<Stock> getAllItemsStock();

    public List<Issue> getIssuedStockByUserId(Integer userId);

   
}
