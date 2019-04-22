/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package in.db.inventory.entity;

import in.db.auth.entity.MstUser;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author anuja
 */
@Entity
public class DtReceipt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dtReceiptId;
    
    @ManyToOne
    @JoinColumn(name = "hdReceipt")
    private HdReceipt hdReceipt;
     
   @Transient
    private Integer requestedQuantity;
    private Integer acceptedQuantity;
    private Integer receivedQuantity;
    private Integer rejectedQuantity;
    
    
    private String status;
   
    @ManyToOne
    @JoinColumn(name = "item")
    private ItemMaster item;

    
    @Column(name="challanNo")
    private Integer challanNo;
    
  @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date challanDate;
  //  private Date 
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date dtEntryDate;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dtModificationDate;

 private Integer pageNo;
 
 
    public Integer getDtReceiptId() {
        return dtReceiptId;
    }

    public void setDtReceiptId(Integer dtReceiptId) {
        this.dtReceiptId = dtReceiptId;
    }

    public Integer getAcceptedQuantity() {
        return acceptedQuantity;
    }

    public void setAcceptedQuantity(Integer acceptedQuantity) {
        this.acceptedQuantity = acceptedQuantity;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public Integer getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(Integer rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ItemMaster getItem() {
        return item;
    }

    public void setItem(ItemMaster item) {
        this.item = item;
    }

    public Integer getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(Integer challanNo) {
        this.challanNo = challanNo;
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

    public HdReceipt getHdReceipt() {
        return hdReceipt;
    }

    public void setHdReceipt(HdReceipt hdReceipt) {
        this.hdReceipt = hdReceipt;
    }

    @Override
    public String toString() {
        return "DtReceipt{" + "dtReceiptId=" + dtReceiptId + ", acceptedQuantity=" + acceptedQuantity + ", receivedQuantity=" + receivedQuantity + ", rejectedQuantity=" + rejectedQuantity + ", status=" + status + ", item=" + item + ", challanNo=" + challanNo + ", dtEntryDate=" + dtEntryDate + ", dtModificationDate=" + dtModificationDate + '}';
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Date getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(Date challanDate) {
        this.challanDate = challanDate;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
            
    
}
