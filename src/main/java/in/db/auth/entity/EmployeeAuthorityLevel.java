/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author anuja
 */
@Entity
public class EmployeeAuthorityLevel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer authorityLevel;
    @JsonIgnore
    @JoinColumn(name = "employeeId")
    @ManyToOne
    private MstUser employee;
    @JoinColumn(name = "authorizedEmployeeId")
    @ManyToOne
    private MstUser authorizedEmployee;
    private Character activeFlag='Y';
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEntryDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtModificationDate;

    public EmployeeAuthorityLevel(Integer id, Integer authorityLevel, MstUser employee, MstUser authorizedEmployee, Character activeFlag, Date dtEntryDate, Date dtModificationDate) {
        this.id = id;
        this.authorityLevel = authorityLevel;
        this.employee = employee;
        this.authorizedEmployee = authorizedEmployee;
        this.activeFlag = activeFlag;
        this.dtEntryDate = dtEntryDate;
        this.dtModificationDate = dtModificationDate;
    }

    public EmployeeAuthorityLevel() {
    }

    public EmployeeAuthorityLevel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(Integer authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

    public MstUser getEmployee() {
        return employee;
    }

    public void setEmployee(MstUser employee) {
        this.employee = employee;
    }

    public MstUser getAuthorizedEmployee() {
        return authorizedEmployee;
    }

    public void setAuthorizedEmployee(MstUser authorizedEmployee) {
        this.authorizedEmployee = authorizedEmployee;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
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

    @Override
    public String toString() {
        return "EmployeeAuthorityLevel{" + "id=" + id + ", authorityLevel=" + authorityLevel + ", employee=" + employee + ", authorizedEmployee=" + authorizedEmployee + ", activeFlag=" + activeFlag + ", dtEntryDate=" + dtEntryDate + ", dtModificationDate=" + dtModificationDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.authorityLevel);
        hash = 89 * hash + Objects.hashCode(this.employee);
        hash = 89 * hash + Objects.hashCode(this.authorizedEmployee);
        hash = 89 * hash + Objects.hashCode(this.activeFlag);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.employee==null)
        {
            return false;
        }
        if(this.authorizedEmployee==null)
        {
            return false;
        }
        final EmployeeAuthorityLevel other = (EmployeeAuthorityLevel) obj;
         if(other.employee==null)
        {
            return false;
        }
        if(other.authorizedEmployee==null)
        {
            return false;
        }
        
        if (!Objects.equals(this.authorityLevel, other.authorityLevel)) {
            return false;
        }
        if (!Objects.equals(this.employee.getUserId(), other.employee.getUserId())) {
            return false;
        }
        if (!Objects.equals(this.authorizedEmployee.getUserId(), other.authorizedEmployee.getUserId())) {
            return false;
        }
        if (!Objects.equals(this.activeFlag, other.activeFlag)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
