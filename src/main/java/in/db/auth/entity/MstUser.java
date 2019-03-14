/**
 * 
 */
package in.db.auth.entity;

import in.db.inventory.entity.Issue;
import in.db.inventory.entity.Receipt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import in.db.util.entity.Address;
import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

/**
 * @author anuja
 *
 */
@Component
@Entity
@Table
public class MstUser implements Serializable{

	
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer userId;
        
        private String userEmployeeId;
    	private String designation;
	@NotNull(message=" name is required")
	@Size(min=5,message="min size is 2")
	private String userName;
@Column(length = 100)
	private String password;
	private Character activeFlag;
	@NotNull(message=" Email is required")
	@Email@Size(min=3,message="min size is 2")

	private String userEmail;
	@NotNull(message=" Contact Number is required")
	@Size(min=10,max=10,message="Please give 10 digit phone no")
	@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Numbers are allowed only")
	private String userContactNo;
	private Integer registeredBy;
@Temporal(TemporalType.DATE)
        @Column(length = 10)
	private Date dateofEntry;
	private Integer modifiedBy;
@Temporal(TemporalType.DATE)
        @Column(length = 10)
	private Date dateOfModification;
	private Address address;
	private Integer departmentId;
	@NotNull
	@Min(value=1,message="please choose a role")
	private Integer userType;
	private Character emailVerifiedFlag;
        @OneToOne
        private MstUser hod;
        @OneToOne
        private MstUser authority;
        
        
	
          @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)    
            private List<Issue> issueList;

    
          @OneToMany(mappedBy = "user")    
            private List<Receipt> receiptList;

	@Transient
	private Collection<Authorities> authorities;
	@Transient
	private String tocken;
	
	@Transient
	private String roleName;
	
	
	public MstUser() {
		
	}
	public MstUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer userId,
			String designation, String userName2, String password2, Character activeFlag, String userEmail,
			String userContactNo, Date dateofEntry, Date dateOfModification, Address address, Integer userType,
			Collection<Authorities> authorities2) {
		super();
		this.userId = userId;
		this.designation = designation;
		userName = userName2;
		password = password2;
		this.activeFlag = activeFlag;
		this.userEmail = userEmail;
		this.userContactNo = userContactNo;
		this.dateofEntry = dateofEntry;
		this.dateOfModification = dateOfModification;
		this.address = address;
		this.userType = userType;
		authorities = authorities2;
	}
	/**
	 * @return the userId
	 */
   
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Character getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Character activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getUserContactNo() {
		return userContactNo;
	}
	public void setUserContactNo(String userContactNo) {
		this.userContactNo = userContactNo;
	}

	public Date getDateofEntry() {
		return dateofEntry;
	}
	public void setDateofEntry(Date dateofEntry) {
		this.dateofEntry = dateofEntry;
	}
	
	public Date getDateOfModification() {
		return dateOfModification;
	}
	public void setDateOfModification(Date dateOfModification) {
		this.dateOfModification = dateOfModification;
	}


	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


	


	


	public Integer getUserType() {
		return userType;
	}


	public void setUserType(Integer userType) {
		this.userType = userType;
	}


	


	public void setAuthorities(Collection<Authorities> authorities) {
		this.authorities = authorities;
	}

	public Integer getRegisteredBy() {
		return registeredBy;
	}
	public void setRegisteredBy(Integer registeredBy) {
		this.registeredBy = registeredBy;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Collection<Authorities> getAuthorities() {
		return authorities;
	}
	public String getTocken() {
		return tocken;
	}
	public void setTocken(String tocken) {
		this.tocken = tocken;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Character getEmailVerifiedFlag() {
		return emailVerifiedFlag;
	}
	public void setEmailVerifiedFlag(Character emailVerifiedFlag) {
		this.emailVerifiedFlag = emailVerifiedFlag;
	}

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public String getUserEmployeeId() {
        return userEmployeeId;
    }

    public void setUserEmployeeId(String userEmployeeId) {
        this.userEmployeeId = userEmployeeId;
    }

    

    public MstUser getHod() {
        return hod;
    }

    public void setHod(MstUser hod) {
        this.hod = hod;
    }

    public MstUser getAuthority() {
        return authority;
    }

    public void setAuthority(MstUser authority) {
        this.authority = authority;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.userId);
        hash = 23 * hash + Objects.hashCode(this.userEmployeeId);
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
        final MstUser other = (MstUser) obj;
        if (!Objects.equals(this.userEmployeeId, other.userEmployeeId)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return true;
    }

    

    

    

    

    

        
        
}


