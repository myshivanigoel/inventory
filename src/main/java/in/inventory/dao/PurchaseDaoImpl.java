/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

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
    public ResultDataMap saveIndentForm(Indent indent) {
        System.out.println("in.inventory.dao.PurchaseDaoImpl.saveIndentForm()"+indent);
        Session session=sessionFactory.getCurrentSession();
        Integer hdIndent=(Integer)session.save(indent.getHdIndent());
        Integer dtindent=(Integer)session.save(indent.getDtIndent());
        if(dtindent!=null && hdIndent!=null)
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
    public List<Indent> getIndentorsIndents(Integer userId) {
    return null;    
    }

    /**
     *
     * return all indents list 
     * @return
     */
    @Override
    public List<Indent> getAllIndentsList() {
        
        List<Indent> indentList=new ArrayList<>();
        List<HdIndent> hdIndentList= sessionFactory.getCurrentSession()
                .createQuery("from HdIndent").list();
        hdIndentList.forEach((hdIndent) -> {
            Indent indent=new Indent();
            indent.setHdIndent(hdIndent);
            indent.setDtIndent(hdIndent.getIndentDetailList().get(0));
            indentList.add(indent);
        });
        return indentList;
    }

    /**
     *
     * @param userId
     * returns all indents that are supposed to be verified or sanctioned by user
     * @return
     */
    @Override
    public List<Indent> getIndentsListToBeVerifiedByUser(Integer userId) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
