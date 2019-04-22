/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.notification.dao;

import in.db.inventory.entity.Notification;
import in.util.entity.ResultDataMap;
import java.util.List;

/**
 *
 * @author anuja
 */
public interface NotificationDao {
     public Notification getNotification(Integer notificationId);
    public List<Notification> getNotificationsForUser(Integer userId);
    public List<Notification> getNotificationsByType(Integer userId,String type);
    
    public ResultDataMap pushNotification(Notification notification);
    public ResultDataMap updateNotification(Notification notification);

    public List<Notification> getAllNotificationsForUser(Integer userId);

    public List<Notification> getNotificationsByReferenceTableName(String refTableName, Integer userId);

    public List<Notification> getNotificationsByReferenceTableIdName(Integer id, String refTableName, Integer userId);
}
