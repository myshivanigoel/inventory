/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.DtIndent;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.IndentStatus;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.db.inventory.entity.Stock;

import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.ArrayList;
import java.util.Collection;
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
                .createQuery("from HdIndent where indentor.userId=:indentor and submittedStatus=:submittedStatus order by  indentId desc")
                .setParameter("indentor", user.getUserId())
                .setParameter("submittedStatus", Strings.SubmittedIndent)
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
        
        return sessionFactory.getCurrentSession()
                .createQuery("from HdIndent order by  indentId desc").list();
        
    }

    /**
     *
     * @param userId
     * returns all indents that are supposed to be verified or sanctioned by user
     * @return
     */
    @Override
    public List<HdIndent> getRequestedIndentsList(Integer userId) {
        return sessionFactory.getCurrentSession()
                .createQuery(" FROM HdIndent where submittedStatus=:submittedStatus  and indentId in (SELECT indent.indentId FROM IndentStatus where authorizedEmployee.userId=:userId)")
                .setParameter("userId", userId)
                .setParameter("submittedStatus", Strings.SubmittedIndent)
                .list();
    }

    /**
     *
     * @param indentId
     * returns details of indent by indent Id
     * @return
     */
    @Override
    public HdIndent getIndent(Integer indentId) {
         HdIndent hdIndent= sessionFactory.getCurrentSession().load(HdIndent.class, indentId);
                
        return hdIndent;
    }

    /**
     *
     * @param userId
     * 
     * @return
     */
    @Override
    public List<HdIndent> getMyPendingIndents(Integer userId) {
        List<HdIndent> indentList=new ArrayList<>();
        indentList= sessionFactory.getCurrentSession()
                .createQuery("from HdIndent where indentor.userId=:indentor and submittedStatus=:submittedStatus and status=:status order by  indentId desc")
                .setParameter("indentor", userId)
                 .setParameter("submittedStatus", Strings.SubmittedIndent)
                .setParameter("status", Strings.IndentStatusInProcess)
                .list();
        System.out.println("in.inventory.dao.PurchaseDaoImpl.getIndentorsIndents()"+indentList);
        return indentList;
    }

    /**
     *
     * @param userId
     * @param indentId
     * 
     * 
     * 
     * @return
     */
    @Override
    public IndentStatus ifUserAuthenticatedIndent(Integer userId, Integer indentId) {
       return sessionFactory.getCurrentSession()
               .createQuery("from IndentStatus where authorizedEmployeeId=:userId and indentId=:indentId ",IndentStatus.class)
               .setParameter("userId", userId)
               
               .setParameter("indentId", indentId)
               .uniqueResult();
    }

    @Override
    public ResultDataMap updateHdIndent(HdIndent indent) {
       sessionFactory.getCurrentSession().update(indent);
       return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public void saveIndentStatus(IndentStatus indentStatus) {
         sessionFactory.getCurrentSession().save(indentStatus);
    }

    @Override
    public List<HdIndent> getIndentsListToBeVerifiedByUser(MstUser userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HdIndent> getApprovedIndentsList(Integer userId) {
          return sessionFactory.getCurrentSession()
                .createQuery(" FROM HdIndent where status='"+Strings.IndentStatusApproved+"'and  submittedStatus=:submittedStatus ")
                  .setParameter("submittedStatus", Strings.SubmittedIndent)
                .list();
    }

    @Override
    public List<HdIndent> getRequestsForFinanceApproval(Integer userId) {
      return sessionFactory.getCurrentSession()
                .createQuery(" FROM HdIndent where status='"+Strings.IndentStatusForFinanceApproval+"' and submittedStatus=:submittedStatus ")
 .setParameter("submittedStatus", Strings.SubmittedIndent)
                    .list();
    }

    @Override
    public Collection<? extends HdIndent> getFinanceRejectedIndents(Integer userId) {
       return sessionFactory.getCurrentSession()
                .createQuery(" FROM HdIndent where status='"+Strings.IndentStatusFinanceRejected+"' and  submittedStatus=:submittedStatus ")
                .setParameter("submittedStatus", Strings.SubmittedIndent)
               .list();
    }

    @Override
    public void deleteOldDtIndentEntries(Integer indentId) {
        
        System.out.println("indent Id"+indentId);
          int executeUpdate = sessionFactory.getCurrentSession()
                  .createQuery("delete from DtIndent where hdIndent.indentId=:indentId")
                  .setParameter("indentId", indentId)
                  .executeUpdate();System.out.println("rows deleted "+executeUpdate);
    }

    @Override
    public List<HdIndent> getDraftedIndentsList(Integer userId) {
        System.out.println("in.inventory.dao.PurchaseDaoImpl.getDraftedIndentsList()"+userId);
       return sessionFactory.getCurrentSession()
                  .createQuery("from HdIndent where indentor.userId=:userId and submittedStatus=:submittedStatus")
                  .setParameter("userId", userId)
                    .setParameter("submittedStatus", Strings.notSubmitted)
                .list();
    }

    @Override
    public int isIndentorsIndent(Integer userId, Integer indentId) {
        Object i=sessionFactory.getCurrentSession()
                  .createQuery("Select 1 from HdIndent where indentor.userId=:userId and indentId=:indentId")
                  .setParameter("userId", userId)
                    .setParameter("indentId", indentId)
                .uniqueResult();
        if(i!=null && i.equals(1))
        {
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public List<HdIndent> getIndentListForFinance() {
        return sessionFactory.getCurrentSession()
                  .createQuery("from HdIndent where financeStatus is not null ")
                 
                   
                .list();
    }

  

   
}
