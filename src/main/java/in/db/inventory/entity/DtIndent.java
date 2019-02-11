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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @Override
    public String toString() {
        return "DtIndent{" + "hdIndent=" + hdIndent + ", dtIndentId=" + dtIndentId + ", classification=" + classification + ", itemGroup=" + itemGroup + ", sNo=" + sNo + ", partType=" + partType + ", descriptionOfMaterial=" + descriptionOfMaterial + ", manufacturer=" + manufacturer + ", quantity=" + quantity + ", pricePerUnit=" + pricePerUnit + ", unitForPrice=" + unitForPrice + ", expectedMonthYearOfDelivery=" + expectedMonthYearOfDelivery + ", Remarks=" + Remarks + '}';
    }
    
   
    
    
    
}
