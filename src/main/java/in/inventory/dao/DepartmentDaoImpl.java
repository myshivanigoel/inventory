package in.inventory.dao;

import in.inventory.dao.DepartmentDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import in.db.inventory.entity.Department;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.LinkedHashMap;
import javax.transaction.Transactional;

@Repository
@Transactional
public class DepartmentDaoImpl implements DepartmentDao {

	@Autowired
	 SessionFactory sessionFactory;

	public List<Department> getAllDepartmentList() {
		return sessionFactory.getCurrentSession().createQuery("from Department where activeFlag='Y'",Department.class).getResultList();
		
	}

        public ResultDataMap saveDepartment(Department department) {
		Session session=sessionFactory.getCurrentSession();
                                department.setActiveFlag('Y');
				session.save(department);
		return new ResultDataMap()
				.setDataObject(department)
				.setStatus(true)
				.setMessage(Strings.savedSuccessfully);
	}

	public Department getDepartmentById(Integer departmentId) {
		Session session=sessionFactory.getCurrentSession();
		Department department=session.get(Department.class, departmentId);
		return department;
	}
        
        public ResultDataMap updateDepartmentOnly(Department department) {
                sessionFactory.getCurrentSession().update(department);
                return new ResultDataMap().setStatus(true).setMessage(Strings.savedSuccessfully);
	}
}
