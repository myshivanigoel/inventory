package in.auth.user.daoimpl;

import in.auth.user.dao.ProjectDao;
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
import in.db.dashboard.entity.Menu;
import in.db.dashboard.entity.MenuGroup;
import in.db.inventory.entity.Project;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.LinkedHashMap;
import javax.transaction.Transactional;
@Repository
@Transactional
public class ProjectDaoImpl implements ProjectDao {

	@Autowired
	 SessionFactory sessionFactory;

	public List<Project> getAllProjectList() {
		return sessionFactory.getCurrentSession().createQuery("from Project where activeFlag='Y'",Project.class).getResultList();
		
	}

        public ResultDataMap saveProject(Project project) {
		Session session=sessionFactory.getCurrentSession();
                                project.setActiveFlag('Y');
				session.save(project);
		return new ResultDataMap()
				.setDataObject(project)
				.setStatus(true)
				.setMessage(Strings.savedSuccessfully);
	}

	public Project getProjectById(Integer projectId) {
		Session session=sessionFactory.getCurrentSession();
		Project project=null;
		if(projectId!=null) {
			project=session.get(Project.class, projectId);
		}
		
		return project;
	}
        
        public ResultDataMap updateProjectOnly(Project project) {
                sessionFactory.getCurrentSession().update(project);
                return new ResultDataMap().setStatus(true).setMessage(Strings.savedSuccessfully);
	}
}
