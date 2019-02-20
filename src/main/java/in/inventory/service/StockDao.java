/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.Stock;

/**
 *
 * @author anuja
 */
public interface StockDao {
     public Stock getStockForItem(Integer itemId);
   public Stock updateStockForItem(Stock stock);
    public Stock saveNewItemInStock(Stock stock);
    public Stock deleteItemFromStock(Integer itemId);
}
