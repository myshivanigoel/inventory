/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import in.db.auth.entity.MstUser;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author anuja
 */
@Entity
public class IndentStatus implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "indentId")
    @ManyToOne
    private HdIndent indent;
    @JoinColumn(name="authorizedEmployeeId")
    @ManyToOne
    private MstUser authorizedEmployee;
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEntryDate;
    private Character activeFlag;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModificationDate;

    @Transient
    private String Remarks="";
    
    public IndentStatus() {
    }

    public IndentStatus(Integer id, HdIndent indent, MstUser authorizedEmployee, String status, Date dtEntryDate, Character activeFlag, Date dtModificationDate) {
        this.id = id;
        this.indent = indent;
        this.authorizedEmployee = authorizedEmployee;
        this.status = status;
        this.dtEntryDate = dtEntryDate;
        this.activeFlag = activeFlag;
        this.dtModificationDate = dtModificationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HdIndent getIndent() {
        return indent;
    }

    public void setIndent(HdIndent indent) {
        this.indent = indent;
    }

    public MstUser getAuthorizedEmployee() {
        return authorizedEmployee;
    }

    public void setAuthorizedEmployee(MstUser authorizedEmployee) {
        this.authorizedEmployee = authorizedEmployee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDtEntryDate() {
        return dtEntryDate;
    }

    public void setDtEntryDate(Date dtEntryDate) {
        this.dtEntryDate = dtEntryDate;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Date getDtModificationDate() {
        return dtModificationDate;
    }

    public void setDtModificationDate(Date dtModificationDate) {
        this.dtModificationDate = dtModificationDate;
    }

    @Override
    public String toString() {
        return "IndentStatus{" + "id=" + id + ", indent=" + indent + ", authorizedEmployee=" + authorizedEmployee + ", status=" + status + ", dtEntryDate=" + dtEntryDate + ", activeFlag=" + activeFlag + ", dtModificationDate=" + dtModificationDate + ", Remarks=" + Remarks + '}';
    }

   

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }
    
    
}
