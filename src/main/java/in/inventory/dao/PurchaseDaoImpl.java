/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.DtIndent;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.db.inventory.entity.Stock;
import in.util.entity.Indent;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anuja
 */

@Repository
@Transactional
public class PurchaseDaoImpl implements PurchaseDao{
      @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public ResultDataMap saveIndentForm(HdIndent indent) {
        System.out.println("in.inventory.dao.PurchaseDaoImpl.saveIndentForm()"+indent);
        Session session=sessionFactory.getCurrentSession();
        Integer count=(Integer)session.save(indent);
        indent.getIndentDetailList().forEach((t) -> {
            t.setHdIndent(indent);
              session.update(t);
      
        });
        if( count!=null)
        {
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
         
        }
        else{
             return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.error);
       
        }
       }

    @Override
    public ResultDataMap saveReceiptForm(Receipt receipt) {
        // save in receipt table
        // update stock table
         Session session=sessionFactory.getCurrentSession();
        //receipt table
        session.save(receipt);
        //stock table
        Stock stock=session.get(Stock.class,receipt.getItem().getItemId());
        if(stock==null)
        {
            stock=new Stock();
            
            stock.setItem(receipt.getItem());
            stock.setDateOfEntry(new Date());
        }
            stock.setAvailableQty(stock.getAvailableQty()+receipt.getQuantity());
            stock.setDateOfModification(new Date());
           stock.setActiveFlag('Y');
        
        session.saveOrUpdate(stock);
         
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
       
    }

    public Stock getStockByItemId(Integer itemId,Session session)
    {
        return  session.createQuery("from Stock where itemId=:itemId",Stock.class)
                   .setParameter("itemId", itemId)
                  .uniqueResult();
      
    }
    
    
    @Override
    public ResultDataMap saveReceiptForm(ReceiptConsumable receipt) {
      // save in receipt table
        // update stock table
         Session session=sessionFactory.getCurrentSession();
        //receipt table
        session.save(receipt);
        //stock table
        Stock stock=getStockByItemId(receipt.getItem().getItemId(),session);
        if(stock==null)
        {
            stock=new Stock();
            
            stock.setItem(receipt.getItem());
            stock.setDateOfEntry(new Date());
            stock.setActiveFlag('Y');
        }
            stock.setAvailableQty((stock.getAvailableQty()==null?0:stock.getAvailableQty())+(receipt.getAcceptedQuantity()==null?0:receipt.getAcceptedQuantity()));
            stock.setDateOfModification(new Date());
           
        
        session.saveOrUpdate(stock);
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
           }

    @Override
    public ResultDataMap saveNonConsumableReceiptForm(Receipt receipt) {
      // save in receipt table
        // update stock table
         Session session=sessionFactory.getCurrentSession();
        //receipt table
        session.save(receipt);
        //stock table
         Stock stock=getStockByItemId(receipt.getItem().getItemId(),session);
       if(stock==null)
        {
            stock=new Stock();
            
            stock.setItem(receipt.getItem());
            stock.setDateOfEntry(new Date());
        }
            stock.setAvailableQty((stock.getAvailableQty()==null?0:stock.getAvailableQty())+(receipt.getQuantity()==null?0:receipt.getQuantity()));
            stock.setDateOfModification(new Date());
           
        
        session.saveOrUpdate(stock);
        
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
         }

    /**
     *
     * @param userId
     * returns my indents filled by user
     * @return
     */
    @Override
    public List<HdIndent> getIndentorsIndents(MstUser user) {
        List<HdIndent> indentList=new ArrayList<>();
        System.out.println("in.inventory.dao.PurchaseDaoImpl.getIndentorsIndents()"+user.getUserId());
        indentList= sessionFactory.getCurrentSession()
                .createQuery("from HdIndent where indentor.userId=:indentor").setParameter("indentor", user.getUserId())
                .list();
        System.out.println("in.inventory.dao.PurchaseDaoImpl.getIndentorsIndents()"+indentList);
        return indentList;
    }

    /**
     *
     * return all indents list 
     * @return
     */
    @Override
    public List<HdIndent> getAllIndentsList() {
        
        List<Indent> indentList=new ArrayList<>();
        return sessionFactory.getCurrentSession()
                .createQuery("from HdIndent").list();
        
    }

    /**
     *
     * @param userId
     * returns all indents that are supposed to be verified or sanctioned by user
     * @return
     */
    @Override
    public List<HdIndent> getIndentsListToBeVerifiedByUser(MstUser userId) {
        return null;
    }

    /**
     *
     * @param indentId
     * returns details of indent by indent Id
     * @return
     */
    @Override
    public Object getIndent(Integer indentId) {
         HdIndent hdIndent= sessionFactory.getCurrentSession().get(HdIndent.class, indentId);
                Indent indent=new Indent();
                indent.setHdIndent(hdIndent);
                indent.setDtIndent(hdIndent.getIndentDetailList().get(0));
        return indent;
    }

   
}
