package in.auth.user.daoimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import in.auth.user.dao.UserDao;
import in.db.auth.entity.Authorities;
import in.db.auth.entity.MstRole;
import in.db.auth.entity.RoleAuthorities;
import in.db.auth.entity.Tocken;
import in.db.auth.entity.MstUser;
import in.db.auth.entity.UserRole;
import in.db.dashboard.entity.Menu;
import in.db.dashboard.entity.MenuGroup;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.LinkedHashMap;
@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	 SessionFactory sessionFactory;

	public List<MstRole> getUserRole(Integer userId) {
		List<MstRole> roles=new ArrayList();
		MstRole role=new MstRole();
		Session session=sessionFactory.getCurrentSession();
   		/*authorities=(List<Authorities>) session.createQuery("from Authorities a,UserRole u,RoleAuthorities r " + 
				" where a.id=r.authorityId " + 
				" and r.roleId=u.roleId " + 
				" and u.userId=? ",Authorities.class)*/
		roles=(List<MstRole>) session.createQuery(" FROM MstRole r where r.roleId in (select roleId from UserRole where userId=:userId) ",MstRole.class)
				.setParameter("userId", userId)
				.getResultList();
	
		return roles;
	}

	public List<MenuGroup> getMenuGroups(Integer userId) {
		List<MenuGroup> menuGroups=new ArrayList();
		MstRole role=new MstRole();
		Session session=sessionFactory.getCurrentSession();
   		/*authorities=(List<Authorities>) session.createQuery("from Authorities a,UserRole u,RoleAuthorities r " + 
				" where a.id=r.authorityId " + 
				" and r.roleId=u.roleId " + 
				" and u.userId=? ",Authorities.class)*/
		List<Object[]> list=(List<Object[]>) session.createNativeQuery("SELECT distinct m.menuId,m.menuName,m.url,m.orederNo,m.MenuGroup,mg.menuGroupName FROM Menu m,UserRole ur, RoleAuthorities ra,MenuGroup mg " + 
																					"where m.authorityId=ra.authorityId " + 
																					"and ra.roleId=ur.roleId " + 
																					"and m.menuGroup=mg.groupId " + 
																					"and ur.userId=? order by m.orederNo")
																	.setParameter(1, userId)
																	.getResultList();
	
               
		List<Menu> menus=new ArrayList<Menu>();
                for (Object[] object : list) {
                        Menu menu=new Menu();
                        menu.setMenuId((Integer)object[0]);
                        menu.setMenuName((String)object[1]);
                        menu.setUrl((String)object[2]);
                        menu.setOrederNo((Integer)object[3]);
                        menu.setMenuGroup((Integer)object[4]);
                        menu.setMenuGroupName((String)object[5]);
                        menus.add(menu);
            }
              
                String ifSiteAdmin=(String)session.createNativeQuery("SELECT 'Y' FROM imsdb.UserRole where userId=:userId and roleId=2 ")
                                                .setParameter("userId", userId).uniqueResult();
                if(ifSiteAdmin!=null && ifSiteAdmin.equals("Y"))
                {
                    String urlName=(String)session.createNativeQuery("SELECT urlName FROM imsdb.SiteData where enteredBy=:userId").setParameter("userId", userId).uniqueResult();
                     Menu menu=new Menu();
                     menu.setMenuName("Site");
                     menu.setUrl("/loc/"+urlName);
                     menu.setMenuGroup(100);
                     menu.setMenuGroupName("View Site");
                     menu.setMenuId(900);
                     menu.setOrederNo(900);
                     menus.add(menu);
                      menu=new Menu();
                      menu.setMenuName("Site Default");
                     menu.setUrl("/");
                     menu.setMenuGroup(100);
                     menu.setMenuGroupName("View Site");
                     menu.setMenuId(901);
                     menu.setOrederNo(901);
                     menus.add(menu);
                }
                  
                 System.out.println("menu List :"+menus);
                menuGroups=getMenuGroups(menus);
                
              return menuGroups;
	}

	private List<MenuGroup> getMenuGroups(List<Menu> menus) {
	
		List<MenuGroup> menuGroups=new ArrayList();
		
		for (Menu menu : menus) {
                    // create a new MenuItem(menuitem)
			MenuGroup menuItem=new MenuGroup();
                        //set items groupId
			menuItem.setGroupId(menu.getMenuGroup());
                        //check if its group is already added to groupsList
			if(menuGroups.contains(menuItem))
			{
				
				menuGroups.get(
                                                menuGroups.indexOf(menuItem)//find menuGroup from List
                                               )
                                                .getMenuList()   // fetch its menuList
                                                .add(menu);      //add new MenuItem to menuList
			}else {
                                //enter new mneu Group name to menuItem(which is a group here)
				menuItem.setMenuGroupName(menu.getMenuGroupName());
                                //add entry in menulist of  menuitem (here group)
				menuItem.getMenuList().add(menu);
                                // add this new group to menuGroups
				menuGroups.add(menuItem);
			}
		}
		
		return menuGroups;
	}

	public MstUser getUserById(Integer userId) {
		Session session=sessionFactory.getCurrentSession();
		MstUser user=null;
		if(userId!=null) {
			user=session.get(MstUser.class, userId);
		}
		
		return user;
	}

	public ResultDataMap saveUser(MstUser user) {
		Session session=sessionFactory.getCurrentSession();
		Boolean newUser=false;
		MstUser userdb=null;
		if(user!=null ) 
		{
			
			if(user.getUserId()!=null)
			{
				userdb=session.get(MstUser.class, user.getUserId());
				
				if(userdb!=null)
				{
					// disable Old role if role is changed 
					if(!userdb.getUserType().equals(user.getUserType()))
					{
						
						
						session.createQuery("update  UserRole set enabled=0 where userId=:userId and enabled=1 and userType=:userType")
													.setParameter("userId", user.getUserId())
													.setParameter("userType",user.getUserType())	
													.executeUpdate();
						
					}
					
				}else {
					newUser=true;
				}
			}else {
				newUser=true;
			}
			
			// Save or Update MstUser
			session.saveOrUpdate(user);
			
			//add role Entry in case of new MstUser 
			//	or 
			// user ROle is changed
			if(newUser ||(userdb!=null && !user.getUserType().equals(userdb.getUserType())))
			{
				UserRole userRole=new UserRole();
				userRole.setRoleId(user.getUserType());
				userRole.setUserId(user.getUserId());
				userRole.setEnabled('1');
				session.save(userRole);
			}
			
			//save tocken if new self registerd user
			if(newUser && user.getTocken()!=null)
			{
				Tocken tocken=new Tocken();
				tocken.setUserId(user.getUserId());
				
				tocken.setGeneratedDate(new Date());
				tocken.setStatus('N');// n=new
				tocken.setTocken(user.getTocken());
				tocken.setTypeOfTocken(Strings.emailVerificationTocken);
				session.save(tocken);
			}
		}
		
		return new ResultDataMap()
				.setDataObject(user)
				.setStatus(true)
				.setMessage(Strings.savedSuccessfully);
	}


	public MstUser getUserByIdOrEmailOrMobile(String userName) {
		MstUser user=null;
		Session session=sessionFactory.getCurrentSession();
		user=session.createQuery("from MstUser where userEmail=:userName or userId=:userName or userContactNo=:userName",MstUser.class)
				.setParameter("userName", userName)
				.getResultList().stream().findFirst().orElse(null);
		
		//System.out.println(user);
		
		return user;
	}

	@SuppressWarnings("unchecked")
	public Collection<? extends GrantedAuthority> getUserAuthorities(Integer userId) {
		List<Authorities> authorities=new ArrayList();
		Session session=sessionFactory.getCurrentSession();
   		/*authorities=(List<Authorities>) session.createQuery("from Authorities a,UserRole u,RoleAuthorities r " + 
				" where a.id=r.authorityId " + 
				" and r.roleId=u.roleId " + 
				" and u.userId=? ",Authorities.class)*/
		authorities=(List<Authorities>) session.createNativeQuery("SELECT distinct a.* FROM Authorities a,UserRole ur,RoleAuthorities ra " + 
																		"where a.id=ra.authorityId  " + 
																		"and ra.roleId=ur.roleId " + 
																		"and ur.userId=? ",Authorities.class)
																	.setParameter(1, userId)
																	.getResultList();
		for (Authorities authorities2 : authorities) {
			System.out.println(authorities2.getName());
		}
		return authorities;
	}

	public List<MstUser> getAllUserList() {
		return sessionFactory.getCurrentSession().createQuery("from MstUser",MstUser.class).getResultList();
		
	}

	public ResultDataMap saveOrUpdateTocken(Tocken tocken) {
		sessionFactory.getCurrentSession().saveOrUpdate(tocken);

		return new ResultDataMap().setStatus(true).setMessage(Strings.savedSuccessfully);
	}

	public Tocken getTockenByTockenNo(String tockenNo) {
		

		return sessionFactory.getCurrentSession()
				.createQuery("from Tocken where tocken=:tockenNo",Tocken.class)
				.setParameter("tockenNo", tockenNo)
				.uniqueResult();
	}

	public ResultDataMap updateUserOnly(MstUser user) {
		sessionFactory.getCurrentSession().update(user);

		return new ResultDataMap().setStatus(true).setMessage(Strings.savedSuccessfully);
	}

    public LinkedHashMap getLocationNames(Integer userId) {
        LinkedHashMap<String,String> map= new LinkedHashMap<String, String>();
            List<Object[]> list = sessionFactory.getCurrentSession()
                    .createQuery("select locLevel1,locLevel2,locLevel3,locLevel4 "
                                   + " from SiteData where activeFlag='Y' and enteredBy=:userId")
                    .setParameter("userId", userId)
                    .list();
        for (Object[] object : list) {
            if(object[0]!=null && !object[0].toString().trim().equals(""))
            {
                map.put(Strings.locLevel1, object[0].toString());
            }
            if(object[1]!=null && !object[1].toString().trim().equals(""))
            {
                map.put(Strings.locLevel2, object[1].toString());
            }

            if(object[2]!=null && !object[2].toString().trim().equals(""))
            {
                map.put(Strings.locLevel3, object[2].toString());
            }
            if(object[3]!=null && !object[3].toString().trim().equals(""))
            {
                map.put(Strings.locLevel4, object[3].toString());
            }

              
        }
        return map;
    
    }

	
}
