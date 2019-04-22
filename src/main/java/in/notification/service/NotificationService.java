/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.notification.service;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.Notification;
import in.util.entity.ResultDataMap;
import java.util.List;

/**
 *
 * @author anuja
 * 
 *           Notification 
 * *********************************
 *  1         Type                     PushEvent(status)        Read Event      UpdateEvent(status)             for
 *  2        indentDrafted            indentSave(new)           Dashboard       submitIndent(completed)+1       indenter  dONE   NOT TESTED 
 *  3        IndentApprovalRequest    indentSubmit(new)         Dashboard       approve/reject(completed)       approver  
 *  4        IndentFinanceRequest     indentApproval(new)       Dashboard       accept/reject(Completed)+1      finance
 *  5       IndentRejected            indentRejectFin(new)      Dashboard       first Read                      indenter
 *  6       IndentApprovedFin         indentApprovedFin(new)    Dashboard       approval(ReceiveSubmit)         mmg
 *  
 *  7       receiveDrafted           receiveSave(new)           Dashboard       submitRecipt(completed)+1         mmg
 *  8       CheckItems                submitRecipt(new)         Dashboard       checkedSubmit                     indenter
 *  9       verifyItems               checkedSubmit             Dashboard       verified(completed)+            ---
 *  10      ApproveRecipt               verified                Dashboard       appoveRecipt(completed)+1       approver last
 *  11      itemsApproved               approve receipt         Dashboard       StockUpdate/checked/firstRead   mmg+indenter
 * 
 * 
 */
public interface NotificationService {

    /**
     *
     * @param notificationId
     * @return   Notification Object
     */
    public Notification getNotification(Integer notificationId);

    /**
     *
     * @param userId
     * @return list of Notification Objects with incomplete status intended for the user
     */
    public List<Notification> getNotificationsForUser(Integer userId);
    
    /**
     *
     * @param userId
     * @return list of all Notification Objects intended for user
     */
    public List<Notification> getAllNotificationsForUser(Integer userId);
    
    /**
     *
     * @param userId
     * @param type
     * @return  list of All notifications of given type for a user
     */
    public List<Notification> getNotificationsByType(Integer userId,String type);
    
    /**
     *
     * @param notification
     * @return ResultDataMap object with status and message
     */
    public ResultDataMap pushNotification(Notification notification);
    
    /**
     *
     * @param notificationType
     * @param notificationStatus
     * @param notificationMessage
     * @param notificationFor
     * @param notificationGeneratedBy
     * @return  ResultDataMap object with status and message
     */
    public ResultDataMap pushNotification(String notificationType, 
                                            String notificationStatus,
                                            String notificationMessage, 
                                            Integer notificationFor, 
                                            Integer notificationGeneratedBy); 

    /**
     *
     * @param notification
     * @return  ResultDataMap object with status and message
     */
    public ResultDataMap updateNotification(Notification notification);
    
    /**
     *
     * @param notificationType
     * @param notificationStatus
     * @param notificationMessage
     * @param notificationFor
     * @param notificationGeneratedBy
     * 
     * Creates a Notification class Object 
     * 
     * @return Notification Object
     */
    public Notification createNotificationObject(String notificationType, String notificationStatus, String notificationMessage, MstUser notificationFor, MstUser notificationGeneratedBy);
   
    
    
    public List<Notification> getNotificationsByReferenceTableName(String refTableName,Integer userId);
    
    public List<Notification> getNotificationsByReferenceTableIdName(Integer id,String name,Integer userId);
    
}
