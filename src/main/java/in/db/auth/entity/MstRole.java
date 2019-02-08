package in.db.auth.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Valid
public class MstRole {

    @Override
    public String toString() {
        return "MstRole{" + "roleId=" + roleId + ", roleName=" + roleName + '}';
    }

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer roleId;
        @NotNull(message = "name Required")
        @Size(min = 4,message = "minimum 4 characters are required")
	private String roleName;
        
         @ManyToMany(cascade = {CascadeType.REFRESH,
                                CascadeType.DETACH,
                                CascadeType.PERSIST,
                                CascadeType.MERGE},
                    fetch = FetchType.LAZY)
        @JoinTable(
                    name="UserRole",
                    joinColumns = @JoinColumn(name="roleId"),
                    inverseJoinColumns = @JoinColumn(name="userId")
                    )
        private List<MstUser> users;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

    public List<MstUser> getUsers() {
        return users;
    }

    public void setUsers(List<MstUser> users) {
        this.users = users;
    }
        
        
}
