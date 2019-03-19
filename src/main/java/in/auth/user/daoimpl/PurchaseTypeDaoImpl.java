package in.auth.user.daoimpl;

import in.auth.user.dao.PurchaseTypeDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import in.db.inventory.entity.PurchaseType;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.LinkedHashMap;
import javax.transaction.Transactional;
@Repository
@Transactional
public class PurchaseTypeDaoImpl implements PurchaseTypeDao {

	@Autowired
	 SessionFactory sessionFactory;

	public List<PurchaseType> getAllPurchaseTypeList() {
		return sessionFactory.getCurrentSession().createQuery("from PurchaseType where activeFlag='Y'",PurchaseType.class).getResultList();
		
	}

        public ResultDataMap savePurchaseType(PurchaseType purchase) {
		Session session=sessionFactory.getCurrentSession();
                                purchase.setActiveFlag('Y');
				session.save(purchase);
		return new ResultDataMap()
				.setDataObject(purchase)
				.setStatus(true)
				.setMessage(Strings.savedSuccessfully);
	}

	public PurchaseType getPurchaseTypeById(Integer purchaseId) {
		Session session=sessionFactory.getCurrentSession();
		PurchaseType purchase=session.get(PurchaseType.class, purchaseId);
		
		
		return purchase;
	}
        
        public ResultDataMap updatePurchaseTypeOnly(PurchaseType purchase) {
                sessionFactory.getCurrentSession().update(purchase);
                return new ResultDataMap().setStatus(true).setMessage(Strings.savedSuccessfully);
	}
}
