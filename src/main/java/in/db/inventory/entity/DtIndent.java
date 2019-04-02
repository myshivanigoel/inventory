/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author anuja
 */

@Entity
public class DtIndent implements Serializable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer dtIndentId;
     
    @ManyToOne
    @JoinColumn(name="hdIndent")
    private HdIndent hdIndent;
    
  
     @ManyToOne
     @JoinColumn(name="classification")
     private Classification classification;
    
     private String classificationNameManual;
     
     @ManyToOne
     @JoinColumn(name = "itemGroup")
     private MstGroup itemGroup;
    
     private Integer sNo;
    private String partType;
    private String descriptionOfMaterial;
    private String manufacturer;
    private Integer quantity;
    private Float pricePerUnit;
    private String unitForPrice;
    private String expectedMonthYearOfDelivery;
    private String Remarks;
 private String suggestedVendors;
     @Transient
    private  List<String> vendors;
   
    @ManyToOne
    @JoinColumn(name="itemId")
    private ItemMaster item;
    
    @ManyToOne
    @JoinColumn(name="purchaseId")
    private PurchaseType purchaseType;
    
    private String modeOfDispatch;
    public HdIndent getHdIndent() {
        return hdIndent;
    }

    public void setHdIndent(HdIndent hdIndent) {
        this.hdIndent = hdIndent;
    }

    public Integer getDtIndentId() {
        return dtIndentId;
    }

    public void setDtIndentId(Integer dtIndentId) {
        this.dtIndentId = dtIndentId;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public MstGroup getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(MstGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    
    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getDescriptionOfMaterial() {
        return descriptionOfMaterial;
    }

    public void setDescriptionOfMaterial(String descriptionOfMaterial) {
        this.descriptionOfMaterial = descriptionOfMaterial;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnitForPrice() {
        return unitForPrice;
    }

    public void setUnitForPrice(String unitForPrice) {
        this.unitForPrice = unitForPrice;
    }

    public String getExpectedMonthYearOfDelivery() {
        return expectedMonthYearOfDelivery;
    }

    public void setExpectedMonthYearOfDelivery(String expectedMonthYearOfDelivery) {
        this.expectedMonthYearOfDelivery = expectedMonthYearOfDelivery;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }

  
    public String getClassificationNameManual() {
        return classificationNameManual;
    }

    public void setClassificationNameManual(String classificationNameManual) {
        this.classificationNameManual = classificationNameManual;
    }

    public ItemMaster getItem() {
        return item;
    }

    public void setItem(ItemMaster item) {
        this.item = item;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }

   
   

    public String getSuggestedVendors() {
        return suggestedVendors;
    }

    public void setSuggestedVendors(String suggestedVendors) {
        this.suggestedVendors = suggestedVendors;
    }

   
    
    //convenience method to convert suggested vendor list into a String to save in one column.
    private void vendorsListProcess()
    {
        vendors.forEach(vendor->{this.suggestedVendors=this.suggestedVendors==null?this.suggestedVendors=vendor:this.suggestedVendors+", "+vendor;});
    }
    
     public List<String> getVendors() {
        return vendors;
    }

    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
        vendorsListProcess();
    }
    
     public String getModeOfDispatch() {
        return modeOfDispatch;
    }

    public void setModeOfDispatch(String modeOfDispatch) {
        this.modeOfDispatch = modeOfDispatch;
    }
    
}
