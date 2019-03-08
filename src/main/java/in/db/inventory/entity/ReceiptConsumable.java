/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.MstUser;
import java.io.Serializable;
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
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author shivani
 */

@Entity
public class ReceiptConsumable implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="receiptId")
    private Integer receiptId;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification;
    @Column(name="activeFlag")
    private Character activeFlag;
      @Column(name="supplierName")
    private String supplierName;
    @Column(name="pONumber")
    private Integer pONumber;
    @Column(name="pODate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date pODate;
    @Column(name="carrier")
    private String carrier;
    @Column(name="challanNo")
    private Integer challanNo;
    @Column(name="challanDate")
    @Temporal(TemporalType.TIMESTAMP)
     @DateTimeFormat(pattern = "yyyy-mm-dd")
   
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

    private Double acceptedQuantity;
    private Double receivedQuantity;
    private Double rejectedQuantity;
   
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

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
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

   
  
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    @Override
    public String toString() {
        return "ReceiptConsumable{" + "receiptId=" + receiptId + ", dateOfEntry=" + dateOfEntry + ", dateOfModification=" + dateOfModification + ", activeFlag=" + activeFlag + ", supplierName=" + supplierName + ",  carrier=" + carrier + ", challanNo=" + challanNo + ", challanDate=" + challanDate + ", clearance=" + clearance + ", mrrNumber=" + mrrNumber + ", invoiceNumber=" + invoiceNumber + ", purchaseType=" + purchaseType + ", receiptType=" + receiptType + ", projectCode=" + projectCode + ", receivedBy=" + receivedBy + ", checkedBy=" + checkedBy + ", verified=" + verified + ", approvedBy=" + approvedBy + ", item=" + item + ", user=" + user + '}';
    }

    public Double getAcceptedQuantity() {
        return acceptedQuantity;
    }

    public void setAcceptedQuantity(Double acceptedQuantity) {
        this.acceptedQuantity = acceptedQuantity;
    }

    public Double getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Double receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public Double getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(Double rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public Integer getpONumber() {
        return pONumber;
    }

    public void setpONumber(Integer pONumber) {
        this.pONumber = pONumber;
    }

    public Date getpODate() {
        return pODate;
    }

    public void setpODate(Date pODate) {
        this.pODate = pODate;
    }
   
    
    
    
}