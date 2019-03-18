/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author shivani
 */

@Entity
@Table(name="classification")
public class Classification {
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="classificationId")
    private Integer classificationId;
    @Column(name="classification")
    private String classification;
    @Column(name="activeFlag")
    private Character activeFlag;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification;

    
    
     @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  
    private List<ItemMaster> items;

    public Integer getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Integer classificationId) {
        this.classificationId = classificationId;
    }

  

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }
    
    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
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

    public List<ItemMaster> getItems() {
        return items;
    }

    public void setItems(List<ItemMaster> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Classification{" + "classificationId=" + classificationId + ", classification=" + classification + ", activeFlag=" + activeFlag + ", dateOfEntry=" + dateOfEntry + ", dateOfModification=" + dateOfModification + ", items=" + items + '}';
    }

    public Classification(Integer classificationId) {
        this.classificationId = classificationId;
    }

    public Classification() {
    }
    
    
}
