/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.MstUser;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author anuja
 */
@Entity
public class HdIndent implements Serializable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer indentId;
    private String prNo;
     private Date indentDate;
    private String budgetYear;
    private String ProjectCode;
    @Transient
    private  List<String> vendors;
    private String suggestedVendors;
    private String sourceData;
    private String modeOfDispatch;
  
    @ManyToOne
    @JoinColumn(name = "indentor")
    private MstUser indentor;
    private Character indentorAuthenticationFlag;
    private Date indentorAuthenticationDate;
 
    
    @ManyToOne
    @JoinColumn(name="sectionHead")
    private MstUser sectionHead;
     private Character sectionHeadAuthenticationFlag;
    private Date sectionHeadAuthenticationDate;
   
    
    @ManyToOne
    @JoinColumn(name="approvingAuthority")
    private MstUser approvingAuthority;
   private Character approvingAuthorityAuthenticationFlag;
    private Date approvingAuthorityAuthenticationDate;
 
    @OneToMany(mappedBy = "hdIndent")
    private List<DtIndent> indentDetailList;
    
    

    public Integer getIndentId() {
        return indentId;
    }

    public void setIndentId(Integer indentId) {
        this.indentId = indentId;
    }

    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public Date getIndentDate() {
        return indentDate;
    }

    public void setIndentDate(Date indentDate) {
        this.indentDate = indentDate;
    }

    public String getBudgetYear() {
        return budgetYear;
    }

    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear;
    }

    public String getProjectCode() {
        return ProjectCode;
    }

    public void setProjectCode(String ProjectCode) {
        this.ProjectCode = ProjectCode;
    }

    public List<String> getVendors() {
        return vendors;
    }

    public void setVendors(List<String> vendors) {
        this.vendors = vendors;
        vendorsListProcess();
    }

    public String getSuggestedVendors() {
        return suggestedVendors;
    }

    public void setSuggestedVendors(String suggestedVendors) {
        this.suggestedVendors = suggestedVendors;
    }

    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public String getModeOfDispatch() {
        return modeOfDispatch;
    }

    public void setModeOfDispatch(String modeOfDispatch) {
        this.modeOfDispatch = modeOfDispatch;
    }

    public MstUser getIndentor() {
        return indentor;
    }

    public void setIndentor(MstUser indentor) {
        this.indentor = indentor;
    }

    public Character getIndentorAuthenticationFlag() {
        return indentorAuthenticationFlag;
    }

    public void setIndentorAuthenticationFlag(Character indentorAuthenticationFlag) {
        this.indentorAuthenticationFlag = indentorAuthenticationFlag;
    }

    public Date getIndentorAuthenticationDate() {
        return indentorAuthenticationDate;
    }

    public void setIndentorAuthenticationDate(Date indentorAuthenticationDate) {
        this.indentorAuthenticationDate = indentorAuthenticationDate;
    }

    public MstUser getSectionHead() {
        return sectionHead;
    }

    public void setSectionHead(MstUser sectionHead) {
        this.sectionHead = sectionHead;
    }

    public Character getSectionHeadAuthenticationFlag() {
        return sectionHeadAuthenticationFlag;
    }

    public void setSectionHeadAuthenticationFlag(Character sectionHeadAuthenticationFlag) {
        this.sectionHeadAuthenticationFlag = sectionHeadAuthenticationFlag;
    }

    public Date getSectionHeadAuthenticationDate() {
        return sectionHeadAuthenticationDate;
    }

    public void setSectionHeadAuthenticationDate(Date sectionHeadAuthenticationDate) {
        this.sectionHeadAuthenticationDate = sectionHeadAuthenticationDate;
    }

    public MstUser getApprovingAuthority() {
        return approvingAuthority;
    }

    public void setApprovingAuthority(MstUser approvingAuthority) {
        this.approvingAuthority = approvingAuthority;
    }

    public Character getApprovingAuthorityAuthenticationFlag() {
        return approvingAuthorityAuthenticationFlag;
    }

    public void setApprovingAuthorityAuthenticationFlag(Character approvingAuthorityAuthenticationFlag) {
        this.approvingAuthorityAuthenticationFlag = approvingAuthorityAuthenticationFlag;
    }

    public Date getApprovingAuthorityAuthenticationDate() {
        return approvingAuthorityAuthenticationDate;
    }

    public void setApprovingAuthorityAuthenticationDate(Date approvingAuthorityAuthenticationDate) {
        this.approvingAuthorityAuthenticationDate = approvingAuthorityAuthenticationDate;
    }

    public List<DtIndent> getIndentDetailList() {
        return indentDetailList;
    }

    public void setIndentDetailList(List<DtIndent> indentDetailList) {
        this.indentDetailList = indentDetailList;
    }

    @Override
    public String toString() {
        return "HdIndent{" + "indentId=" + indentId + ", prNo=" + prNo + ", indentDate=" + indentDate + ", budgetYear=" + budgetYear + ", ProjectCode=" + ProjectCode + ", vendors=" + vendors + ", suggestedVendors=" + suggestedVendors + ", sourceData=" + sourceData + ", modeOfDispatch=" + modeOfDispatch + ", indentor=" + indentor + ", indentorAuthenticationFlag=" + indentorAuthenticationFlag + ", indentorAuthenticationDate=" + indentorAuthenticationDate + ", sectionHead=" + sectionHead + ", sectionHeadAuthenticationFlag=" + sectionHeadAuthenticationFlag + ", sectionHeadAuthenticationDate=" + sectionHeadAuthenticationDate + ", approvingAuthority=" + approvingAuthority + ", approvingAuthorityAuthenticationFlag=" + approvingAuthorityAuthenticationFlag + ", approvingAuthorityAuthenticationDate=" + approvingAuthorityAuthenticationDate + ", indentDetailList=" + indentDetailList + '}';
    }
   
    
    
    
    //convenience method to convert suggested vendor list into a String to save in one column.
    private void vendorsListProcess()
    {
        vendors.forEach(vendor->{this.suggestedVendors=this.suggestedVendors==null?this.suggestedVendors=vendor:this.suggestedVendors+", "+vendor;});
    }
    
}
