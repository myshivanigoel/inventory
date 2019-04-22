/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.HdReceipt;
import in.inventory.dao.RecieveDao;
import in.notification.service.NotificationService;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecieveServiceImpl implements RecieveService {

    
    @Autowired
    RecieveDao recieveDao;
    
    @Autowired
    NotificationService notificationService;
    @Override
    public ResultDataMap saveHdReceipt(HdReceipt receipt, Integer userId) {
        
        
        if(receipt!=null && userId!=null)
        {
           receipt.setReceivedBy(new MstUser(userId));
          ResultDataMap result= recieveDao.saveReceipt(receipt);
          if(result.getStatus())
          {
           return notificationService.pushNotification(Strings.NotificationTypePendingDraftedReceipts,
                                                Strings.NotificationStatusNew, 
                                                Strings.NotificationMessagePendingDraftsReceipt, 
                                                receipt.getReceivedBy().getUserId(),
                                                receipt.getReceivedBy().getUserId());
           
          }else{
            return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.error);
        }
           
       }else{
           return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
       }
    }

    @Override
    public ResultDataMap updateHdReceipt(HdReceipt receipt, Integer userId) {
        
        if(receipt!=null && receipt.getReceiptId()!=null)
        {
            HdReceipt dbRecept=recieveDao.getReceiptById(receipt.getReceiptId());
            if(dbRecept!=null)
            {
                if( receipt.getCarrier()!=null &&  !dbRecept.getCarrier().equals(receipt.getCarrier()))
                {
                    dbRecept.setCarrier(receipt.getCarrier());
                }
                if( receipt.getChallanDate()!=null &&  !dbRecept.getChallanDate().equals(receipt.getChallanDate()))
                {
                    dbRecept.setChallanDate(receipt.getChallanDate());
                }
                if( receipt.getChallanNo()!=null &&  !dbRecept.getChallanNo().equals(receipt.getChallanNo()))
                {
                    dbRecept.setChallanNo(receipt.getChallanNo());
                }
                if( receipt.getClearance()!=null &&  !dbRecept.getClearance().equals(receipt.getClearance()))
                {
                    dbRecept.setClearance(receipt.getCarrier());
                }
               // to be continue/....
                
            }else{
                
            }
            
        }else{
            
        }
        return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.error);
    }

    @Override
    public boolean isCompletedReceipt(Integer receiptId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isIndentorsReceipt(Integer indentorId, Integer reciptId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isUsersRecipt(Integer userId, Integer receiptId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HdReceipt getReceiptById(Integer receiptId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HdReceipt> getReceiptsByIndentId(Integer indentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HdReceipt> getReceiptsByReceiver(Integer receivedBy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
