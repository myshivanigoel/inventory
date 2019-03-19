package in.auth.user.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
;
import in.db.dashboard.entity.MenuGroup;
import in.db.inventory.entity.Project;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;


public interface ProjectService {
	
	public List<Project> getAllProjectList();
	
	public ResultDataMap saveProject(Project project,String contextPath);
        
        public Project getProjectById(Integer projectId);
        
        public ResultDataMap deactivateProject(Integer projectId);
        
        public ResultDataMap updateProject(Project project,String contextPath);
}
