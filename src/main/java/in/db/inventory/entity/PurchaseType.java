/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author shivani
 */

@Entity
@Table(name="purchaseType")
public class PurchaseType implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="purchaseId")
    private Integer purchaseId;
    @Column(name="purchaseName")
    private String purchaseName;
    @Column(name="activeFlag")
    private Character activeFlag;
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Date getDateOfModification() {
        return dateOfModification;
    }

    public void setDateOfModification(Date dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    @Override
    public String toString() {
        return "PurchaseType{" + "purchaseId=" + purchaseId + ", purchaseName=" + purchaseName + ", activeFlag=" + activeFlag + ", dateOfModification=" + dateOfModification + ", dateOfEntry=" + dateOfEntry + '}';
    }
    
    
    
}
