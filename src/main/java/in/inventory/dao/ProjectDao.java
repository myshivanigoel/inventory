package in.inventory.dao;

import java.util.Collection;
import java.util.List;
import in.db.dashboard.entity.MenuGroup;
import in.db.inventory.entity.Project;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;
public interface ProjectDao {

	
        public List<Project> getAllProjectList();

        public ResultDataMap saveProject(Project project);
	
        public Project getProjectById(Integer projectId);
        
        public ResultDataMap updateProjectOnly(Project project);
}

