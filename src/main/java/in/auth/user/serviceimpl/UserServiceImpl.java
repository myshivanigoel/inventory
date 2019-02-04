package in.auth.user.serviceimpl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import in.auth.user.dao.UserDao;
import in.auth.user.service.UserService;
import in.db.auth.entity.MstRole;
import in.db.auth.entity.Tocken;
import in.db.auth.entity.User;
import in.db.dashboard.entity.MenuGroup;
import in.util.entity.Mail;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import in.util.Utility;
import java.util.LinkedHashMap;
@Service
@Transactional
public class UserServiceImpl implements UserService{

    
    @Autowired
    Mail mail;
    
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserDao userdao;
	
	String password;
	String mailer;
	String tocken=null;
    @Override
	public List<MstRole> getUserRole(Integer userId)
	{
		
		return userdao.getUserRole(userId);
	}

    @Override
	public List<MenuGroup> getMenuGroups(Integer userId) {
		
		return userdao.getMenuGroups(userId);
	}

    @Override
	public User getUserById(Integer userId) {
		
		return userdao.getUserById(userId);
	}

    @Override
	public ResultDataMap saveUser(User user,Integer id,String contextPath) {
		ResultDataMap rdm;
		boolean newUser=false;
		if(id==null || id==0) {id=user.getUserId();}
		if(userdao.getUserByIdOrEmailOrMobile(user.getUserEmail())!=null)
		{
			
			return new ResultDataMap().setStatus(false).setMessage(Strings.EmailIdExist);
		}
		if(userdao.getUserByIdOrEmailOrMobile(user.getUserContactNo())!=null)	
		{
			
			return new ResultDataMap().setStatus(false).setMessage(Strings.MobileNoExist);
		}
		
		if(userdao.getUserById(user.getUserId())!=null)
		{
			
			user.setDateOfModification(new Date());
			
			user.setModifiedBy(id);
			
		
		}else {
			newUser=true;
			user.setActiveFlag('0');
			
			if(user.getPassword()!=null && user.getPassword().trim().equals(""))
			{
				 password=RandomStringUtils.randomAlphanumeric(8);
				 
				user.setPassword(encoder.encode(password));
				mailer=" User registered successfully your password is :"+password;
			}else {
				tocken=RandomStringUtils.randomAlphanumeric(30);
				user.setTocken(tocken);
				mailer="  Please click On the Link to verify your email Id <a href='http://"+contextPath+"/verify_user?tocken_no="+tocken+"' ></a>" ;
			}
			
			user.setDateofEntry(new Date());
			user.setRegisteredBy(id);
			
		}
		rdm=userdao.saveUser(user);
		rdm.setDataObject(user);
		if(rdm.getStatus() && newUser)
		{
			try {
				mail.sendSimpleMail(user.getUserEmail(),mailer, "Registered successfully");
			} catch (Exception e) {
				rdm.setMessage("User Registered, but failed to send email ");
				e.printStackTrace();
			}
		}
				
		return rdm;
		
	}

    @Override
	public List<User> getAllUserList() {
		
		return userdao.getAllUserList();
	}

	
    @Override
	public User getUserByIdOrEmailOrMobile(String userName) {
		
		return userdao.getUserByIdOrEmailOrMobile(userName);
	}
    @Override
	public Collection<? extends GrantedAuthority> getUserAuthorities(Integer userId) {
		return userdao.getUserAuthorities(userId);
	}
    @Override
	public ResultDataMap generatePasswordResetLink(User user, String contextPath) {
		
		Tocken tocken=new Tocken();
		tocken.setGeneratedDate(new Date());
		tocken.setStatus(Strings.NewTocken);
		tocken.setTypeOfTocken(Strings.PasswordResetTocken);
		tocken.setUserId(user.getUserId());
		tocken.setValidFlag('Y');
		String tockenNo=RandomStringUtils.randomAlphanumeric(30);
		tocken.setTocken(tockenNo);
		ResultDataMap rdm=userdao.saveOrUpdateTocken(tocken);
		if(rdm.getStatus())
		{
			String mailer="  Please click On the Link to verify your email Id <a href='http://"+contextPath+"/resetPassword?tocken_no="+tockenNo+"' ></a>" ;
			try {
				mail.sendSimpleMail(user.getUserEmail(),mailer, Strings.passwordResetMailSubject);
				rdm.setMessage("email sent to your email Id please click on the link to reset password ");
			} catch (Exception e) {
				rdm.setMessage("email Found, but failed to send email ");
				e.printStackTrace();
			}
		}else {
			rdm.setMessage(Strings.error);

		}
		
		return rdm;
	}

	public ResultDataMap resetPassword(String tockenNo, String password1) {
		ResultDataMap rdm=new ResultDataMap();
		Tocken tocken=ifValidTocken(tockenNo, Strings.PasswordResetTocken);
		if(tocken!=null)
		{
			Integer userId=tocken.getUserId();
			User user=userdao.getUserById(userId);
			user.setPassword(encoder.encode(password1));
			user.setModifiedBy(userId);
			user.setDateOfModification(new Date());
			 rdm=userdao.updateUserOnly(user);
			if(rdm.getStatus())
			{
			tocken.setStatus(Strings.VerifiedTocken);
			userdao.saveOrUpdateTocken(tocken);
			rdm.setStatus(true);
			rdm.setMessage(Strings.savedSuccessfully);
			}else {
				rdm.setStatus(false);
				rdm.setMessage(Strings.error);
			}
			
			
		}else {
			rdm.setStatus(false);
			rdm.setMessage(Strings.InvalidTocken);
		}
		return rdm ;
	}

	public Tocken ifValidTocken(String tockenNo,String type) {
		Tocken tocken=userdao.getTockenByTockenNo(tockenNo);
		if(tocken!=null
				&& tocken.getTypeOfTocken().equals(type)
				&& tocken.getStatus().equals(Strings.NewTocken)
				&& tocken.getValidFlag().equals('Y'))
		{
			return tocken;
		}else {
			return null;
		}
		
	}

            public ResultDataMap updateUser(User user,String contextPath) {
               ResultDataMap rdm=new ResultDataMap();
                 
		Tocken tockenObj=null;
                User dbUser=userdao.getUserById(user.getUserId());
                if(dbUser==null)
                {
                    return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
                }else{//check if email id or mobile NO is changed
                    if(!dbUser.getUserEmail().equals(user.getUserEmail()))
                    {
                         if(userdao.getUserByIdOrEmailOrMobile(user.getUserEmail())!=null)
                            {

                                    return new ResultDataMap().setStatus(false).setMessage(Strings.EmailIdExist);
                            }else{
                            tocken=RandomStringUtils.randomAlphanumeric(30);
                            tockenObj=new Tocken();
                                   
				tockenObj.setGeneratedDate(new Date());
				tockenObj.setStatus('N');// n=new
				tockenObj.setTocken(user.getTocken());
				tockenObj.setTypeOfTocken(Strings.emailVerificationTocken);
				tockenObj.setTocken(tocken);
				tockenObj.setUserId(dbUser.getUserId());
				tockenObj.setValidFlag('Y');
				user.setTocken(tocken);
				mailer="  Please click On the Link to verify your email Id <a href='http://"+contextPath+"/verify_user?tocken_no="+tocken+"' ></a>" ;

                         }
                    }//check if mobile NO is changed
                    if(!dbUser.getUserContactNo().equals(user.getUserContactNo()))
                    {
                          if(userdao.getUserByIdOrEmailOrMobile(user.getUserContactNo())!=null)
                            {

                                    return new ResultDataMap().setStatus(false).setMessage(Strings.MobileNoExist);
                            }else{
                              //verify mobile NO
                          }
                    }
                     dbUser.setActiveFlag(user.getActiveFlag());
                        dbUser.setAddress(user.getAddress());
                        dbUser.setDateOfModification(new Date());
                        dbUser.setDepartmentId(user.getDepartmentId());
                        dbUser.setModifiedBy(user.getUserId());
                        dbUser.setUserContactNo(user.getUserContactNo());
                        dbUser.setUserEmail(user.getUserEmail());
                        dbUser.setUserName(user.getUserName());
                        
                    if(tockenObj==null)
                    {
                       
                       
                        rdm=userdao.updateUserOnly(dbUser);
                    }else{
                         dbUser.setEmailVerifiedFlag('N');
                         rdm=userdao.updateUserOnly(dbUser);
                         rdm=userdao.saveOrUpdateTocken(tockenObj);
                         
                    }
                    
                    if(rdm.getStatus() && tockenObj!=null)
		{
			try {
				mail.sendSimpleMail(dbUser.getUserEmail(),mailer, "Registered successfully");
			} catch (Exception e) {
				rdm.setMessage("User Registered, but failed to send email ");
				e.printStackTrace();
			}
		}
                }
		return rdm;
	}

	public ResultDataMap changePassword(String password2, Integer userId) {
	
		User user=userdao.getUserById(userId);
			user.setPassword(encoder.encode(password2));
		user.setModifiedBy(userId);
		user.setDateOfModification(new Date());
		
		return userdao.updateUserOnly(user);
	}

    public LinkedHashMap<String, String> getLocationNames(Integer userId) {
        
        return userdao.getLocationNames(userId);
    }
	
}
