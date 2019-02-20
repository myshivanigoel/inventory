/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author shivani
 */

@Entity
public class Stock {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification;
    @Column(name="activeFlag")
    private Character activeFlag;
    @Column(name="availableQty")
    private Double availableQty;
     @Column(name="issuedQty")
    private Integer issuedQty;
     @Column(name="others")
    private String others;

     @OneToOne
     @JoinColumn(name = "itemId")
     private ItemMaster item;
     
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public Date getDateOfModification() {
        return dateOfModification;
    }

    public void setDateOfModification(Date dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Double getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(Double availableQty) {
        this.availableQty = availableQty;
    }

  
    public Integer getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(Integer issuedQty) {
        this.issuedQty = issuedQty;
    }

    
    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public ItemMaster getItem() {
        return item;
    }

    public void setItem(ItemMaster item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Stock{" + "id=" + id + ", dateOfEntry=" + dateOfEntry + ", dateOfModification=" 
                + dateOfModification + ", activeFlag=" 
                + activeFlag + ", availableQty=" 
                + availableQty +  ", issuedQty=" + issuedQty + ", others=" + others + ", item=" + item + '}';
    }
    
    
}
