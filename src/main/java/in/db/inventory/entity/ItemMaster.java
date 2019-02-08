/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/**
 *
 * @author shivani
 */
@Entity
public class ItemMaster implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="itemId")
    private Integer itemId;
    @Column(name="itemName")
    private String itemName;
    @Column(name="description")
    private String description;
    @Column(name="manufacturer")
    private String manufacturer;
    @Column(name="price")
    private Integer price;
    @Column(name="dateOfEntry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfEntry;
    @Column(name="dateOFModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofModification;
    @Column(name="activeFlag")
    private Character activeFlag;

    @ManyToOne(     cascade = {CascadeType.DETACH,CascadeType.MERGE,
                            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="groupId")
    private MstGroup itemGroup;

    
    
    @ManyToOne(     cascade = {CascadeType.DETACH,CascadeType.MERGE,
                            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="classificationId")
    private MstGroup itemClassification;

    
    
    @OneToMany(mappedBy = "item")    
    private List<Issue> issueList;
    
      @OneToMany(mappedBy = "item")    
    private List<Receipt> receiptList;
    
    @OneToOne(mappedBy = "item",cascade = {
                                            CascadeType.ALL
                                           })
    private Stock stock;

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }
    
    
    
    
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    
   
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


   
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public Date getDateofModification() {
        return dateofModification;
    }

    public void setDateofModification(Date dateofModification) {
        this.dateofModification = dateofModification;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
    }

   public MstGroup getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(MstGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public MstGroup getItemClassification() {
        return itemClassification;
    }

    public void setItemClassification(MstGroup itemClassification) {
        this.itemClassification = itemClassification;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

   
    
    
    
}
