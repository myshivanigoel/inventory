package in.inventory.dao;

import java.util.Collection;
import java.util.List;
import in.db.dashboard.entity.MenuGroup;
import in.db.inventory.entity.PurchaseType;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;
public interface PurchaseTypeDao {

	
        public List<PurchaseType> getAllPurchaseTypeList();

        public ResultDataMap savePurchaseType(PurchaseType purchase);
	
        public PurchaseType getPurchaseTypeById(Integer purchaseId);
        
        public ResultDataMap updatePurchaseTypeOnly(PurchaseType purchase);
}

