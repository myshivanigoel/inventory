/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.Stock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anuja
 */

@Repository
public class StockDaoImpl implements StockDao{

    
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public Stock getStockForItem(Integer itemId) {
       
        
        Session session=sessionFactory
                .getCurrentSession();
        
        
        Stock stock=(Stock)session
                .createQuery("from Stock where itemId=:itemId",Stock.class)
                .setParameter("itemId", itemId)
                .uniqueResult();
        System.out.println("stock "+stock);
        
        System.out.println("stock "+stock);
        return stock;
        
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
    
    
}
