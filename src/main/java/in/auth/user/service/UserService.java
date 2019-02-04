package in.auth.user.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import in.db.auth.entity.MstRole;
import in.db.auth.entity.Tocken;
import in.db.auth.entity.User;
import in.db.dashboard.entity.MenuGroup;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;


public interface UserService {
	
	public User getUserByIdOrEmailOrMobile(String userName);
	public Collection<? extends GrantedAuthority> getUserAuthorities(Integer userId);


	public List<MstRole> getUserRole(Integer userId);
	public List<MenuGroup> getMenuGroups(Integer userId);
	public User getUserById(Integer userId);

	public ResultDataMap saveUser(User user,Integer loggedInUserId,String contextPath);
	public List<User> getAllUserList();
	
	
	public ResultDataMap generatePasswordResetLink(User user, String contextPath);
	public ResultDataMap resetPassword(String tockenNo, String password1);
	public Tocken ifValidTocken(String tockenNo,String typeOfTocken);
	public ResultDataMap updateUser(User user,String contextPath);
	public ResultDataMap changePassword(String password2, Integer userId);

     public LinkedHashMap<String, String> getLocationNames(Integer userId);
	
}
