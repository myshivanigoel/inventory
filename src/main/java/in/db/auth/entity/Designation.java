/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.auth.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 *
 * @author shivani
 */
@Entity
@Table(name="designation")
public class Designation implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="designationId")
    private Integer designationId;
    @Column(name="designationName")
    private String designationName;
    @Column(name="activeFlag")
    private Character activeFlag;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification;

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
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

    @Override
    public String toString() {
        return "Designation{" + "designationId=" + designationId + ", designationName=" + designationName + ", activeFlag=" + activeFlag + ", dateOfEntry=" + dateOfEntry + ", dateOfModification=" + dateOfModification + '}';
    }
    
    
}
