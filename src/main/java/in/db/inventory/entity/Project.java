/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author shivani
 */


@Entity
@Table(name="project")
public class Project implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="projectId")
    private Integer projectId;
    @Column(name="projectCode")
    private String projectCode;
    @Column(name="name")
    private String name;
    @Column(name="activeFlag")
    private Character activeFlag;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Project{" + "projectId=" + projectId + ", projectCode=" + projectCode + ", name=" + name + ", activeFlag=" + activeFlag + ", dateOfEntry=" + dateOfEntry + ", dateOfModification=" + dateOfModification + '}';
    }
    
    
    
    
}
