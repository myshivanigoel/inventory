package in.db.dashboard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Menu {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer menuId;

	private String menuName;
	private String url;
	private Integer authorityId;
	private Integer orederNo;
	private Integer MenuGroup;
	private Integer enabled;
	
	
	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", menuName=" + menuName + ", url=" + url + ", authorityId=" + authorityId
				+ ", orederNo=" + orederNo + ", MenuGroup=" + MenuGroup + ", enabled=" + enabled + ", menuGroupName="
				+ menuGroupName + "]";
	}

	@Transient
	private String menuGroupName;
	
	public String getMenuGroupName() {
		return menuGroupName;
	}
	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}
	public Integer getOrederNo() {
		return orederNo;
	}
	public void setOrederNo(Integer orederNo) {
		this.orederNo = orederNo;
	}
	public Integer getMenuGroup() {
		return MenuGroup;
	}
	public void setMenuGroup(Integer menuGroup) {
		MenuGroup = menuGroup;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
}
