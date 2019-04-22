
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.MstUser;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author anuja
 */
@Entity
public class Notification implements Serializable {

    @Id
    private Integer notificationId;
    
    private String notificationType;
    
    private String notificationStatus;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationDate=new Date();
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationShownDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationCompletedDate;
    
    private String notificationMessage;
    
    @ManyToOne
    @JoinColumn(name="notificationFor")
    private MstUser notificationFor;
    
    @ManyToOne
    @JoinColumn(name="notificationGeneratedBy")
    private MstUser notificationGeneratedBy;
    
    private String referenceTableName;
    private Integer referenceTableId;

    public Notification() {
    }

    public Notification(String notificationType, String notificationStatus, String notificationMessage, MstUser notificationFor, MstUser notificationGeneratedBy) {
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
        this.notificationMessage = notificationMessage;
        this.notificationFor = notificationFor;
        this.notificationGeneratedBy = notificationGeneratedBy;
    }
    
    
    
    
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Date getNotificationShownDate() {
        return notificationShownDate;
    }

    public void setNotificationShownDate(Date notificationShownDate) {
        this.notificationShownDate = notificationShownDate;
    }

    public Date getNotificationCompletedDate() {
        return notificationCompletedDate;
    }

    public void setNotificationCompletedDate(Date notificationCompletedDate) {
        this.notificationCompletedDate = notificationCompletedDate;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public MstUser getNotificationFor() {
        return notificationFor;
    }

    public void setNotificationFor(MstUser notificationFor) {
        this.notificationFor = notificationFor;
    }

    public MstUser getNotificationGeneratedBy() {
        return notificationGeneratedBy;
    }

    public void setNotificationGeneratedBy(MstUser notificationGeneratedBy) {
        this.notificationGeneratedBy = notificationGeneratedBy;
    }

    public String getReferenceTableName() {
        return referenceTableName;
    }

    public void setReferenceTableName(String referenceTableName) {
        this.referenceTableName = referenceTableName;
    }

    public Integer getReferenceTableId() {
        return referenceTableId;
    }

    public void setReferenceTableId(Integer referenceTableId) {
        this.referenceTableId = referenceTableId;
    }
    
    
}
