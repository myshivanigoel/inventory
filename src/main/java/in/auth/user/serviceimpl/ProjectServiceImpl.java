package in.auth.user.serviceimpl;

import in.auth.user.dao.ProjectDao;
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
import in.auth.user.service.ProjectService;
import in.auth.user.service.UserService;
import in.db.auth.entity.MstRole;
import in.db.auth.entity.Tocken;
import in.db.auth.entity.MstUser;
import in.db.dashboard.entity.MenuGroup;
import in.db.inventory.entity.Project;
import in.util.entity.Mail;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import in.util.Utility;
import in.utility.EmailServiceImpl;
import java.util.LinkedHashMap;
import org.springframework.transaction.annotation.Transactional;
@Service
//@Transactional
public class ProjectServiceImpl implements ProjectService{

  
	@Autowired
	       ProjectDao projectDao;
	
	
        @Override
	public List<Project> getAllProjectList() {
		
		return projectDao.getAllProjectList();
	}

        @Override
	public Project getProjectById(Integer projectId) {
		
		return projectDao.getProjectById(projectId);
	}
        
         @Override
	public ResultDataMap saveProject(Project project,String contextPath) {
		ResultDataMap rdm;
		
		rdm=projectDao.saveProject(project);
		rdm.setDataObject(project);
				
		return rdm;
		
	}
	
        @Override
    public ResultDataMap deactivateProject(Integer projectId) {
        Project project=projectDao.getProjectById(projectId);
        if(project!=null)
        {
            project.setActiveFlag('N');
            project.setDateOfModification(new Date());
           return projectDao.updateProjectOnly(project);
        }else{
            return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
        }
       
    }
    
      public ResultDataMap updateProject(Project project,String contextPath) {
               ResultDataMap rdm=new ResultDataMap();
               Project proj=projectDao.getProjectById(project.getProjectId());
               proj.setDateOfModification(new Date());
               proj.setName(project.getName());
               proj.setProjectCode(project.getProjectCode());
               rdm=projectDao.updateProjectOnly(proj);
               return rdm;
      }
}
