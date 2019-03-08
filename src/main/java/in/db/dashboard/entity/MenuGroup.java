package in.db.dashboard.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
@Entity
public class MenuGroup {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer groupId;
	
	private String menuGroupName;
	private Integer orederNo;
	
	private Integer enabled;
	
	@Override
	public String toString() {
		return "MenuGroup [groupId=" + groupId + ", menuGroupName=" + menuGroupName + ", orederNo=" + orederNo
				+ ", enabled=" + enabled + ", menuList=" + menuList + "]";
	}
	
	@Transient
	private List<Menu> menuList=new ArrayList();
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	public Integer getOrederNo() {
		return orederNo;
	}
	public void setOrederNo(Integer orederNo) {
		this.orederNo = orederNo;
	}
	
	public String getMenuGroupName() {
		return menuGroupName;
	}
	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuGroup other = (MenuGroup) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}
	
}
