/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.MstUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class HdReceipt implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="receiptId")
    private Integer receiptId;
    
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry=new Date();
    
    @Column(name="dateOfModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfModification=new Date();
    
    @Column(name="activeFlag")
    private Character activeFlag;
      
    @Column(name="mrrNumber")
    private Integer mrrNumber;
    
    @Column(name="invoiceNumber")
    private Integer invoiceNumber;
   
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
    
     @Column(name="purchaseType")
    private PurchaseType purchaseType;
    
     @Column(name="receiptType")
     private String receiptType;
    
     @Column(name="projectCode")
    private Project project;
    
    
    private Date receiptCompletedDate;
    private  Date dtEntryDate;
    private Date dtModificationDate;
     @ManyToOne
    @JoinColumn(name="indent")
   
    private HdIndent indent;
    
    @ManyToOne
    @JoinColumn(name="receivedBy")
   
    private MstUser receivedBy;
    
    @ManyToOne
    @JoinColumn(name="checkedBy")
   
    private MstUser checkedBy;
    
    @Column(name="verified")
    private String verified;
    
    @ManyToOne
    @JoinColumn(name="approvedBy")
    
    private MstUser approvedBy;

    @OneToMany(mappedBy = "hdReceipt",cascade = CascadeType.ALL)
    private List<DtReceipt> dtReceipts=new ArrayList<>();
    
    
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getReceiptCompletedDate() {
        return receiptCompletedDate;
    }

    public void setReceiptCompletedDate(Date receiptCompletedDate) {
        this.receiptCompletedDate = receiptCompletedDate;
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

    public MstUser getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(MstUser receivedBy) {
        this.receivedBy = receivedBy;
    }

    public MstUser getCheckedBy() {
        return checkedBy;
    }

    public void setCheckedBy(MstUser checkedBy) {
        this.checkedBy = checkedBy;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public MstUser getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(MstUser approvedBy) {
        this.approvedBy = approvedBy;
    }

    public List<DtReceipt> getDtReceipts() {
        return dtReceipts;
    }

    public void setDtReceipts(List<DtReceipt> dtReceipts) {
        this.dtReceipts = dtReceipts;
    }

    public HdIndent getIndent() {
        return indent;
    }

    public void setIndent(HdIndent indent) {
        this.indent = indent;
    }

    
    
    
    @Override
    public String toString() {
        return "HdReceipt{" + "receiptId=" + receiptId + ", dateOfEntry=" + dateOfEntry + ", dateOfModification=" + dateOfModification + ", activeFlag=" + activeFlag + ", mrrNumber=" + mrrNumber + ", invoiceNumber=" + invoiceNumber + ", supplierName=" + supplierName + ", pONumber=" + pONumber + ", pODate=" + pODate + ", carrier=" + carrier + ", challanNo=" + challanNo + ", challanDate=" + challanDate + ", clearance=" + clearance + ", purchaseType=" + purchaseType + ", receiptType=" + receiptType + ", project=" + project + ", receiptCompletedDate=" + receiptCompletedDate + ", dtEntryDate=" + dtEntryDate + ", dtModificationDate=" + dtModificationDate + ", receivedBy=" + receivedBy + ", checkedBy=" + checkedBy + ", verified=" + verified + ", approvedBy=" + approvedBy + '}';
    }
     
     
     
    
}

