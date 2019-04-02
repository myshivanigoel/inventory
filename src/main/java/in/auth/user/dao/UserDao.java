package in.auth.user.dao;

import in.db.auth.entity.EmployeeAuthorityLevel;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import in.db.auth.entity.MstRole;
import in.db.auth.entity.Tocken;
import in.db.auth.entity.MstUser;
import in.db.dashboard.entity.MenuGroup;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;
public interface UserDao {

	
	public List<MstRole> getUserRole(Integer userId);

	public List<MenuGroup> getMenuGroups(Integer userId);

	public MstUser getUserById(Integer userId);

	public ResultDataMap saveUser(MstUser user);

	public MstUser getUserByIdOrEmailOrMobile(String userName);

	public Collection<? extends GrantedAuthority> getUserAuthorities(Integer userId);

	public List<MstUser> getAllUserList();

	
	public Tocken getTockenByTockenNo(String tockenNo);

	public ResultDataMap updateUserOnly(MstUser user);

	public ResultDataMap saveOrUpdateTocken(Tocken tocken);

    public LinkedHashMap<String, String> getLocationNames(Integer userId);

    public MstUser getUserByUserEmployeeId(String userEmployeeId);

    public List<MstRole> getAllRolesList();

    public void updateRole(Integer userId, Integer userType);

   
    public ResultDataMap updateEmployeeAuthorityLevel(Integer userId,List<EmployeeAuthorityLevel> employeeAuthorityLevel);

    public List<EmployeeAuthorityLevel> getEmployeeAuthorityLevelList(Integer userId);

    public List<MstUser> getMySubordinatesList(Integer userId);

    public boolean ifLastAuthorityLevel(Integer userId, Integer authorityId);
	
}
