package in.inventory.dao;

import in.inventory.dao.DesignationDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import in.db.auth.entity.Designation;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.LinkedHashMap;
import javax.transaction.Transactional;

@Repository
@Transactional
public class DesignationDaoImpl implements DesignationDao {

	@Autowired
	 SessionFactory sessionFactory;

	public List<Designation> getAllDesignationList() {
		return sessionFactory.getCurrentSession().createQuery("from Designation where activeFlag='Y'",Designation.class).getResultList();
		
	}

        public ResultDataMap saveDesignation(Designation designation) {
		Session session=sessionFactory.getCurrentSession();
                                designation.setActiveFlag('Y');
				session.save(designation);
		return new ResultDataMap()
				.setDataObject(designation)
				.setStatus(true)
				.setMessage(Strings.savedSuccessfully);
	}

	public Designation getDesignationById(Integer designationId) {
		Session session=sessionFactory.getCurrentSession();
		Designation designation=session.get(Designation.class, designationId);
		return designation;
	}
        
        public ResultDataMap updateDesignationOnly(Designation designation) {
                sessionFactory.getCurrentSession().update(designation);
                return new ResultDataMap().setStatus(true).setMessage(Strings.savedSuccessfully);
	}
}
