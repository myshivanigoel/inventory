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

    @Override
    public ResultDataMap issueItem(Issue issue) {
        Session session=sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        if(issue!=null && issue.getItem()!=null && issue.getItem().getItemId()!=null)
        {
                Stock stock=getStockForItem(issue.getItem().getItemId());
                Double availableQuantity=stock.getAvailableQty();
                availableQuantity=availableQuantity==null?0:availableQuantity;
                Integer issuedQuantity=issue.getQuantity();
                issuedQuantity=issuedQuantity==null?0:issuedQuantity;
                System.out.println("in.inventory.service.StockDaoImpl.issueItem()"+availableQuantity+"  issued quantity ="+issuedQuantity);
                if(availableQuantity>=issuedQuantity)
                {
                    session.save(issue);
                    stock.setAvailableQty(availableQuantity-issuedQuantity);
                    stock.setIssuedQty(issuedQuantity);
                    stock.setOthers(issue.getRemarks());
                    session.update(stock);
                    session.getTransaction().commit();
                }else{
                    session.getTransaction().rollback();
                    return new ResultDataMap().setStatus(Boolean.FALSE).setMessage("invalid Qunatity");
                }
        }
        else{
            return new ResultDataMap().setStatus(Boolean.FALSE).setMessage("Invalid Item");
        }
        
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage("Saved Successfully");
    }

    @Override
    public List<Stock> getAllItemsStock() {
       return sessionFactory.getCurrentSession()
               .createQuery("from Stock where activeFlag='Y'")
               .list();
    }

    @Override
    public List<Issue> getIssuedStockByUserId(Integer userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Issue where userId=:userId").setParameter("userId", userId)
                .list();
    
    }
    
    
}
