/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.notification.service;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.Notification;
import in.notification.dao.NotificationDao;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    
    @Autowired
    NotificationDao dao;
    
    @Override
    public Notification getNotification(Integer notificationId) {
        return dao.getNotification(notificationId);
    }

    @Override
    public List<Notification> getNotificationsForUser(Integer userId) {
        return dao.getNotificationsForUser(userId);
    }

    @Override
    public List<Notification> getNotificationsByType(Integer userId, String type) {
        return dao.getNotificationsByType(userId,type);
    }

    @Override
    public ResultDataMap pushNotification(Notification notification) {
         return dao.pushNotification(notification);
    }

    @Override
    public ResultDataMap updateNotification(Notification notification) {
        
       if(notification!=null && notification.getNotificationId()!=null)
       {
           Notification dbnotification=dao.getNotification(notification.getNotificationId());
           if(dbnotification!=null)
           {
               boolean modifiedFlag=false;
               if(!notification.getNotificationStatus().equals(notification.getNotificationStatus()))
               {
                   dbnotification.setNotificationStatus(notification.getNotificationStatus());
                   modifiedFlag=true;
               }
               if(!notification.getNotificationStatus().equals(Strings.NotificationStatusCompleted))
               {
                   dbnotification.setNotificationCompletedDate(new Date());
                   modifiedFlag=true;
               }
               if(modifiedFlag)
               {
                   return dao.updateNotification(notification);
               }else{
                   return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
               }
                   
               
           }else{
               return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
           }
           
       }else{
           return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.InvalidData);
       }
    }

    @Override
    public Notification createNotificationObject(String notificationType, String notificationStatus, String notificationMessage, MstUser notificationFor, MstUser notificationGeneratedBy) {
       return  new Notification(notificationType,notificationStatus,notificationMessage,notificationFor,notificationGeneratedBy);
    }

    @Override
    public ResultDataMap pushNotification(String notificationType, String notificationStatus, String notificationMessage, Integer notificationFor, Integer notificationGeneratedBy) {
       return pushNotification(createNotificationObject(notificationType,
                                                        notificationStatus,
                                                        notificationMessage,
                                                        new MstUser(notificationFor), 
                                                        new MstUser(notificationGeneratedBy)));
             
    }

    @Override
    public List<Notification> getAllNotificationsForUser(Integer userId) {
        return dao.getAllNotificationsForUser(userId);
    }

    @Override
    public List<Notification> getNotificationsByReferenceTableName(String refTableName,Integer userId) {
        return dao.getNotificationsByReferenceTableName(refTableName,userId);
    }

    @Override
    public List<Notification> getNotificationsByReferenceTableIdName(Integer id, String refTableName,Integer userId) {
        return dao.getNotificationsByReferenceTableIdName(id,refTableName,userId);
    }
    
    
    
}
