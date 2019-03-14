/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.MstUser;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author shivani
 */

@Entity
public class Issue {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="issueId")
    private Integer issueId;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification;
    @Column(name="activeFlag")
    private Character activeFlag;
    private Integer quantity;
    
    @Column(name="projectCode")
    private String projectCode;
    @Column(name="receivedBy")
    private String receivedBy;
    
    private Character checkedStatus;//C for checked and R rejected
    @Column(name="checkedBy")
    private String checkedBy;
    
    @Column(name="verified")
    private String verified;
    
    private Character approvedStatus;// A for approved and R for rejected
    @Column(name="approvedBy")
    private String approvedBy;
    private String remarks;
   
    @ManyToOne
    @JoinColumn(name = "itemId")
    private ItemMaster item;

    
    @ManyToOne
    @JoinColumn(name="userId")
    private MstUser user;

    public MstUser getUser() {
        return user;
    }

    public void setUser(MstUser user) {
        this.user = user;
    }
    
    
    
    
    public ItemMaster getItem() {
        return item;
    }

    public void setItem(ItemMaster item) {
        this.item = item;
    }
    
    
    
    
    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
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

 
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Character getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(Character checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    public Character getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(Character approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    @Override
    public String toString() {
        return "Issue{" + "issueId=" + issueId + ", dateOfEntry=" + dateOfEntry + ", dateOfModification=" + dateOfModification + ", activeFlag=" + activeFlag + ", quantity=" + quantity + ", projectCode=" + projectCode + ", receivedBy=" + receivedBy + ", checkedStatus=" + checkedStatus + ", checkedBy=" + checkedBy + ", verified=" + verified + ", approvedStatus=" + approvedStatus + ", approvedBy=" + approvedBy + ", remarks=" + remarks + ", item=" + item + ", user=" + user + '}';
    }

    
    
    
    
}