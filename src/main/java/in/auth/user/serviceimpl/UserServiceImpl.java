package in.auth.user.serviceimpl;

import java.util.Collection;
import java.util.Date;
import java.util.List;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import in.auth.user.dao.UserDao;
import in.auth.user.service.UserService;
import in.db.auth.entity.EmployeeAuthorityLevel;
import in.db.auth.entity.MstRole;
import in.db.auth.entity.Tocken;
import in.db.auth.entity.MstUser;
import in.db.dashboard.entity.MenuGroup;
import in.util.entity.Mail;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import in.util.Utility;
import in.util.entity.UserWrapper;
import in.utility.EmailServiceImpl;
import java.util.LinkedHashMap;
import org.springframework.transaction.annotation.Transactional;
@Service
//@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
     EmailServiceImpl email;
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
	public MstUser getUserById(Integer userId) {
		
		return userdao.getUserById(userId);
	}

    @Override
	public ResultDataMap saveUser(MstUser user,Integer id,String contextPath) {
		ResultDataMap rdm;
		if(id==null || id==0) {id=user.getUserId();}
		if(userdao.getUserByIdOrEmailOrMobile(user.getUserEmail())!=null)
		{
			
			return new ResultDataMap().setStatus(false).setMessage(Strings.EmailIdExist);
		}
		if(userdao.getUserByIdOrEmailOrMobile(user.getUserContactNo())!=null)	
		{
			
			return new ResultDataMap().setStatus(false).setMessage(Strings.MobileNoExist);
		}
                if(userdao.getUserByUserEmployeeId(user.getUserEmployeeId())!=null)	
		{
			
			return new ResultDataMap().setStatus(false).setMessage(Strings.EmployeeIdExist);
		}
		
		if(userdao.getUserById(user.getUserId())!=null)
		{
			
			return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
		
		}
			user.setActiveFlag('Y');
			user.setUserType(3);
                        user.setDateofEntry(new Date());
			user.setRegisteredBy(id);
			
                         password=RandomStringUtils.randomAlphanumeric(8);
                         //delete befor production
                         System.out.println("password :: "+password);
			 user.setPassword(encoder.encode(password));
                         mailer="  your password for Inventory is : "+password;
			
			
			
		
		rdm=userdao.saveUser(user);
		rdm.setDataObject(user);
		if(rdm.getStatus())
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
	public List<MstUser> getAllUserList() {
		
		return userdao.getAllUserList();
	}

	
    @Override
	public MstUser getUserByIdOrEmailOrMobile(String userName) {
		
		return userdao.getUserByIdOrEmailOrMobile(userName);
	}
    @Override
	public Collection<? extends GrantedAuthority> getUserAuthorities(Integer userId) {
            System.out.println("in.auth.user.serviceimpl.UserServiceImpl.getUserAuthorities()"+userId);
		return userdao.getUserAuthorities(userId);
	}
    @Override
	public ResultDataMap generatePasswordResetLink(MstUser user, String contextPath) {
		
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
                            
                           
                                email.sendSimpleMessage(user.getUserEmail(), mailer, Strings.passwordResetMailSubject);
				//mail.sendSimpleMail(user.getUserEmail(),mailer, Strings.passwordResetMailSubject);
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
			MstUser user=userdao.getUserById(userId);
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
            System.out.println("in.auth.user.serviceimpl.UserServiceImpl.ifValidTocken()"+tockenNo);
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

        
        public List<EmployeeAuthorityLevel> setLevels(List<EmployeeAuthorityLevel> list)
        {
            int counter=1;
            for (EmployeeAuthorityLevel employeeAuthorityLevel : list) {
                employeeAuthorityLevel.setAuthorityLevel(counter);
                counter++;
            }
            
            return list;
        }
        
        
        
            public ResultDataMap updateUser(UserWrapper userWrapper,String contextPath) {
                setLevels(userWrapper.getEmployeeAuthorityLevelList());
               ResultDataMap rdm=new ResultDataMap();
                 MstUser user=userWrapper.getUser();
		Tocken tockenObj=null;
                MstUser dbUser=userdao.getUserById(user.getUserId());
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
                    
                    //check if role changed 
                    if(!dbUser.getUserType().equals(user.getUserType()))
                    {
                        userdao.updateRole(user.getUserId(),user.getUserType());
                    }
                        if(user.getActiveFlag()!=null){
                            dbUser.setActiveFlag(user.getActiveFlag());
                        }
                        dbUser.setAddress(user.getAddress());
                        dbUser.setDateOfModification(new Date());
                        dbUser.setDepartment(user.getDepartment());
                        dbUser.setDesignation(user.getDesignation());
                        dbUser.setModifiedBy(user.getUserId());
                        dbUser.setUserContactNo(user.getUserContactNo());
                        dbUser.setUserEmail(user.getUserEmail());
                        dbUser.setUserName(user.getUserName());
                        dbUser.setUserType(user.getUserType());
                       
                       
                    if(tockenObj==null)
                    {
                       
                       
                      
                        
                    }else{
                         dbUser.setEmailVerifiedFlag('N');
                         
                         rdm=userdao.saveOrUpdateTocken(tockenObj);
                         
                    }
                      rdm=userdao.updateUserOnly(dbUser);
                      rdm=userdao.updateEmployeeAuthorityLevel(dbUser.getUserId(),userWrapper.getEmployeeAuthorityLevelList());
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
	
		MstUser user=userdao.getUserById(userId);
			user.setPassword(encoder.encode(password2));
		user.setModifiedBy(userId);
		user.setDateOfModification(new Date());
		
		return userdao.updateUserOnly(user);
	}

    public LinkedHashMap<String, String> getLocationNames(Integer userId) {
        
        return userdao.getLocationNames(userId);
    }

    @Override
    public MstUser getUserByUserEmployeeId(String userEmployeeId) {
        return userdao.getUserByUserEmployeeId(userEmployeeId);
    }

    @Override
    public List<MstRole> getAllRolesList() {
           return userdao.getAllRolesList();
    }

    @Override
    public ResultDataMap deactivateUser(Integer userId, Integer adminId) {
        MstUser user=userdao.getUserById(userId);
        if(user!=null)
        {
            user.setActiveFlag('N');
            user.setDateOfModification(new Date());
            user.setModifiedBy(adminId);
             return userdao.updateUserOnly(user);
        }else{
            return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
        }
       
    }
    
     @Override
    public ResultDataMap activateUser(Integer userId, Integer adminId) {
         System.out.println("in.auth.user.serviceimpl.UserServiceImpl.activateUser()"+userId);
        MstUser user=userdao.getUserById(userId);
        if(user!=null)
        {
            user.setActiveFlag('Y');
            user.setDateOfModification(new Date());
            user.setModifiedBy(adminId);
             System.out.println("in.auth.user.serviceimpl.UserServiceImpl.activateUser() updating ...");
             return userdao.updateUserOnly(user);
             
        }else{
            System.out.println("in.auth.user.serviceimpl.UserServiceImpl.activateUser() user not found ...");
            return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
        }
       
    }

    @Override
    public ResultDataMap sendOtp(MstUser user) {
      
        
		Tocken tocken=new Tocken();
		tocken.setGeneratedDate(new Date());
		tocken.setStatus(Strings.NewTocken);
		tocken.setTypeOfTocken(Strings.PasswordResetTocken);
		tocken.setUserId(user.getUserId());
		tocken.setValidFlag('Y');
		String tockenNo=RandomStringUtils.randomAlphanumeric(6);
		tocken.setTocken(tockenNo);
		ResultDataMap rdm=userdao.saveOrUpdateTocken(tocken);
		if(rdm.getStatus())
		{
			String mailer="OTP for password reset is :: "+tockenNo ;
			try {
                            
                           
                              //  email.sendSimpleMessage(user.getUserEmail(), mailer, Strings.passwordResetMailSubject);
				//mail.sendSimpleMail(user.getUserEmail(),mailer, Strings.passwordResetMailSubject);
				rdm.setMessage(Strings.OtpSent);
			} catch (Exception e) {
				rdm.setMessage("email Found, but failed to send email ");
				e.printStackTrace();
			}
		}else {
			rdm.setMessage(Strings.error);

		}
		
		return rdm;
    }

    @Override
    public List<EmployeeAuthorityLevel> getEmployeeAuthorityLevelList(Integer userId) {
        return userdao.getEmployeeAuthorityLevelList(userId);
    }

    @Override
    public List<MstUser> getMySubordinatesList(Integer userId) {
        return userdao.getMySubordinatesList(userId);
    }

    /**
     *
     * @param userId
     * @param authorityId
     * 
     * first paramaetr userId takes the subordinates Id 
     * second paramater takes its authority Id  to check if its top authority
     * @return
     */
    @Override
    public boolean ifLastAuthorityLevel(Integer userId, Integer authorityId) {
        return userdao.ifLastAuthorityLevel(userId,authorityId);

    }
	
    
     public MstUser getFinanceUser()
    {
        return userdao.getFinanceUser();
    }

    @Override
    public ResultDataMap updateProfile(MstUser user) {
        MstUser dbUser=getUserById(user.getUserId());
        if(!dbUser.getUserEmail().equals(user.getUserEmail()))
        {
            if(getUserByIdOrEmailOrMobile(user.getUserEmail())==null)
            {
                dbUser.setUserEmail(user.getUserEmail());
            }else{
                return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.EmailIdExist);
            }
            
        }
        if(!dbUser.getUserName().equals(user.getUserName()))
        {
            dbUser.setUserName(user.getUserName());
        }
        if(!dbUser.getUserContactNo().equals(user.getUserContactNo()))
        {
            if(getUserByIdOrEmailOrMobile(user.getUserContactNo())==null)
            {
                dbUser.setUserContactNo(user.getUserContactNo());
            }else{
                return new ResultDataMap().setStatus(Boolean.FALSE).setMessage(Strings.MobileNoExist);
            }
        }
        dbUser.setDateOfModification(new Date());
        dbUser.setModifiedBy(user.getUserId());
        return userdao.updateUserOnly(dbUser);
    }
}
