/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 *
 * @author shivani
 */
@Entity
public class MstGroup {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer groupId;
    private String groupName;
    private String valueType;
    private Character activeFlag;
    
    
    private Date dtEntryDate;
    private Date dtModificationDate;
    
    
   @ManyToOne(     cascade = {CascadeType.DETACH,CascadeType.MERGE,
                            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="classificationId")
    private Classification classification=new Classification();
    
  
    
    

  


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public String toString() {
        return "MstGroup{" + "groupId=" + groupId + ", groupName=" + groupName + ", valueType=" + valueType + ", activeFlag=" + activeFlag + ", dtEntryDate=" + dtEntryDate + ", dtModificationDate=" + dtModificationDate + ", classification=" + classification + '}';
    }

   

   

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Date getDtEntryDate() {
        return dtEntryDate;
    }

    public void setDtEntryDate(Date dtEntryDate) {
        this.dtEntryDate = dtEntryDate;
    }

    public Date getDtModificationDate() {
        return dtModificationDate;
    }

    public void setDtModificationDate(Date dtModificationDate) {
        this.dtModificationDate = dtModificationDate;
    }

    public MstGroup() {
    }

    public MstGroup(Integer groupId) {
        this.groupId = groupId;
    }

    
    
    
}
