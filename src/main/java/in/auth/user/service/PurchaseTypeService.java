package in.auth.user.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
;
import in.db.dashboard.entity.MenuGroup;
import in.db.inventory.entity.PurchaseType;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;


public interface PurchaseTypeService {
	
	public List<PurchaseType> getAllPurchaseTypeList();
	
	public ResultDataMap savePurchaseType(PurchaseType purchase,String contextPath);
        
        public PurchaseType getPurchaseTypeById(Integer purchaseId);
        
        public ResultDataMap deactivatePurchase(Integer purchaseId);
        
        public ResultDataMap updatePurchaseType(PurchaseType purchase,String contextPath);
}
