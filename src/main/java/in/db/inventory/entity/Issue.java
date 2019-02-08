/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.User;
import java.util.Date;
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
    @Column(name="issuedTo")
    private String issuedTo;
     @Column(name="supplierName")
    private String supplierName;
    @Column(name="PONumber")
    private Integer PONumber;
    @Column(name="PODate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date PODate;
    @Column(name="carrier")
    private String carrier;
    @Column(name="challanNo")
    private Integer challanNo;
    @Column(name="challanDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date challanDate;
    @Column(name="clearance")
    private String clearance;
    @Column(name="mrrNumber")
    private Integer mrrNumber;
    @Column(name="invoiceNumber")
    private Integer invoiceNumber;
    @Column(name="purchaseType")
    private String purchaseType;
    @Column(name="receiptType")
    private String receiptType;
    @Column(name="projectCode")
    private String projectCode;
    @Column(name="receivedBy")
    private String receivedBy;
    @Column(name="checkedBy")
    private String checkedBy;
    @Column(name="verified")
    private String verified;
    @Column(name="approvedBy")
    private String approvedBy;

   
    @ManyToOne
    @JoinColumn(name = "itemId")
    private ItemMaster item;

    
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

  
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getPONumber() {
        return PONumber;
    }

    public void setPONumber(Integer PONumber) {
        this.PONumber = PONumber;
    }

    public Date getPODate() {
        return PODate;
    }

    public void setPODate(Date PODate) {
        this.PODate = PODate;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Integer getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(Integer challanNo) {
        this.challanNo = challanNo;
    }

    public Date getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(Date challanDate) {
        this.challanDate = challanDate;
    }

    public String getClearance() {
        return clearance;
    }

    public void setClearance(String clearance) {
        this.clearance = clearance;
    }

    public Integer getMrrNumber() {
        return mrrNumber;
    }

    public void setMrrNumber(Integer mrrNumber) {
        this.mrrNumber = mrrNumber;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
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
   
    
    
    
}