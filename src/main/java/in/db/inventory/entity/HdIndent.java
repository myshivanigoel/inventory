/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.EmployeeAuthorityLevel;
import in.db.auth.entity.MstUser;
import in.util.entity.Strings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
public class HdIndent implements Serializable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer indentId;
    private String prNo;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date indentDate;
    private String budgetYear;
    
    @ManyToOne
     @JoinColumn(name="projectId")
    private Project project;
   
    private String sourceData;
    
    private String previousReferenceOfPurchaseIfAny;
    @ManyToOne
    @JoinColumn(name = "indentor")
    private MstUser indentor;
    private Character indentorAuthenticationFlag;
    @Temporal(TemporalType.DATE)
    private Date indentorAuthenticationDate;
 
    private String financeStatus;
    private String financeRemarks;
    private String specialApprovalRemark;
    private String submittedStatus=Strings.notSubmitted;
    
    @OneToMany(mappedBy = "hdIndent",cascade = CascadeType.ALL)
    private List<DtIndent> indentDetailList=new ArrayList<>();
    
     @OneToMany(mappedBy = "indent",cascade = CascadeType.ALL)
    private List<IndentStatus> indentStatusList=new ArrayList<>();
     
     @Transient
    List<EmployeeAuthorityLevel> authoritiesList=new ArrayList<>();
    
    private String status=Strings.IndentStatusInProcess;

    public HdIndent(Integer indentId) {
        this.indentId = indentId;
    }

    public HdIndent() {
    }

    public HdIndent(Integer indentId, String prNo, Date indentDate, String budgetYear, Project project, String sourceData, String previousReferenceOfPurchaseIfAny, MstUser indentor, Character indentorAuthenticationFlag, Date indentorAuthenticationDate) {
        this.indentId = indentId;
        this.prNo = prNo;
        this.indentDate = indentDate;
        this.budgetYear = budgetYear;
        this.project = project;
        this.sourceData = sourceData;
        this.previousReferenceOfPurchaseIfAny = previousReferenceOfPurchaseIfAny;
        this.indentor = indentor;
        this.indentorAuthenticationFlag = indentorAuthenticationFlag;
        this.indentorAuthenticationDate = indentorAuthenticationDate;
    }

    
    
    
    
    
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

   

   
    public String getSourceData() {
        return sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
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

   

    public List<DtIndent> getIndentDetailList() {
        return indentDetailList;
    }

    public void setIndentDetailList(List<DtIndent> indentDetailList) {
        this.indentDetailList = indentDetailList;
        indentDetailList.forEach((t) -> {
            t.setHdIndent(this);
        });
    }

  
    
    
    
   

    public String getPreviousReferenceOfPurchaseIfAny() {
        return previousReferenceOfPurchaseIfAny;
    }

    public void setPreviousReferenceOfPurchaseIfAny(String previousReferenceOfPurchaseIfAny) {
        this.previousReferenceOfPurchaseIfAny = previousReferenceOfPurchaseIfAny;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

   

    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<IndentStatus> getIndentStatusList() {
        return indentStatusList;
    }

    public void setIndentStatusList(List<IndentStatus> indentStatusList) {
        this.indentStatusList = indentStatusList;
    }

    public List<EmployeeAuthorityLevel> getAuthoritiesList() {
        return authoritiesList;
    }

    public void setAuthoritiesList(List<EmployeeAuthorityLevel>  authoritiesList) {
        this.authoritiesList = authoritiesList;
    }

    public String getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus;
    }

    public String getFinanceRemarks() {
        return financeRemarks;
    }

    public void setFinanceRemarks(String financeRemarks) {
        this.financeRemarks = financeRemarks;
    }

    public String getSpecialApprovalRemark() {
        return specialApprovalRemark;
    }

    public void setSpecialApprovalRemark(String specialApprovalRemark) {
        this.specialApprovalRemark = specialApprovalRemark;
    }

    public String getSubmittedStatus() {
        return submittedStatus;
    }

    public void setSubmittedStatus(String submittedStatus) {
        this.submittedStatus = submittedStatus;
    }

    

  
    
    
}
