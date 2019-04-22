/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.notification.dao;

import in.db.inventory.entity.Notification;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDaoImpl implements NotificationDao {

    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public Notification getNotification(Integer notificationId) {
        return sessionFactory.getCurrentSession().get(Notification.class,notificationId);
        
    }

    @Override
    public List<Notification> getNotificationsForUser(Integer userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Notification where notificationFor.userId=:userId and notificationStatus=:notificationStatus ")
                .setParameter("userId", userId)
                .setParameter("notificationStatus", Strings.NotificationStatusCompleted)
                .list();
    }

    @Override
    public List<Notification> getNotificationsByType(Integer userId, String type) {
       return sessionFactory.getCurrentSession()
                .createQuery("from Notification where notificationFor.userId=:userId and notificationType=:notificationType")
                .setParameter("userId", userId)
               .setParameter("notificationType", type)
                .list();
    }

    @Override
    public ResultDataMap pushNotification(Notification notification) {
         sessionFactory.getCurrentSession().save(notification);
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public ResultDataMap updateNotification(Notification notification) {
        sessionFactory.getCurrentSession().update(notification);
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }

    @Override
    public List<Notification> getAllNotificationsForUser(Integer userId) {
          return sessionFactory.getCurrentSession()
                .createQuery("from Notification where notificationFor.userId=:userId")
                .setParameter("userId", userId)
                .list();
    }

    @Override
    public List<Notification> getNotificationsByReferenceTableName(String refTableName, Integer userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Notification where notificationFor.userId=:userId and refTableName=:refTableName ")
                .setParameter("userId", userId)
                .setParameter("refTableName", refTableName)
                .list();
    }

    @Override
    public List<Notification> getNotificationsByReferenceTableIdName(Integer referenceTableId, String referenceTableName, Integer userId) {
       return sessionFactory.getCurrentSession()
                .createQuery("from Notification where notificationFor.userId=:userId and referenceTableName=:referenceTableName and referenceTableId=:referenceTableId ")
                .setParameter("userId", userId)
                .setParameter("referenceTableName", referenceTableName)
               .setParameter("referenceTableId", referenceTableId)
                .list();
    
    }
    
}
