package in.inventory.service;

import in.inventory.dao.PurchaseTypeDao;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import in.db.inventory.entity.PurchaseType;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import in.util.Utility;
import in.utility.EmailServiceImpl;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
@Service
//@Transactional
public class PurchaseTypeServiceImpl implements PurchaseTypeService{

  
	@Autowired
	       PurchaseTypeDao purchaseTypeDao;
	
	
        @Override
	public List<PurchaseType> getAllPurchaseTypeList() {
		
		return purchaseTypeDao.getAllPurchaseTypeList();
	}

        @Override
	public PurchaseType getPurchaseTypeById(Integer purchaseId) {
		
		return purchaseTypeDao.getPurchaseTypeById(purchaseId);
	}
        
         @Override
	public ResultDataMap savePurchaseType(PurchaseType purchase,String contextPath) {
		ResultDataMap rdm;
		
		rdm=purchaseTypeDao.savePurchaseType(purchase);
		rdm.setDataObject(purchase);
				
		return rdm;
		
	}
	
        @Override
        public ResultDataMap deactivatePurchase(Integer purchaseId) {
        PurchaseType purchase=purchaseTypeDao.getPurchaseTypeById(purchaseId);
        if(purchase!=null)
        {
            purchase.setActiveFlag('N');
            purchase.setDateOfModification(new Date());
           return purchaseTypeDao.updatePurchaseTypeOnly(purchase);
        }else{
            return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
        }
       
    }
    
      public ResultDataMap updatePurchaseType(PurchaseType purchase,String contextPath) {
               ResultDataMap rdm=new ResultDataMap();
               PurchaseType pType=purchaseTypeDao.getPurchaseTypeById(purchase.getPurchaseId());
               pType.setDateOfModification(new Date());
               pType.setPurchaseName(purchase.getPurchaseName());
               rdm=purchaseTypeDao.updatePurchaseTypeOnly(pType);
               return rdm;
      }
}
