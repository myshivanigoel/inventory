package in.auth.user.serviceimpl;

import in.auth.user.service.DesignationService;
import in.inventory.dao.DesignationDao;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import in.db.auth.entity.Designation;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import in.util.Utility;
import in.utility.EmailServiceImpl;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
@Service
//@Transactional
public class DesignationServiceImpl implements DesignationService{

  
	@Autowired
	       DesignationDao designationDao;
	
	
        @Override
	public List<Designation> getAllDesignationList() {
		
		return designationDao.getAllDesignationList();
	}

        @Override
	public Designation getDesignationById(Integer designationId) {
		
		return designationDao.getDesignationById(designationId);
	}
        
         @Override
	public ResultDataMap saveDesignation(Designation designation,String contextPath) {
		ResultDataMap rdm;
		
		rdm=designationDao.saveDesignation(designation);
		rdm.setDataObject(designation);
				
		return rdm;
		
	}
	
        @Override
        public ResultDataMap deactivateDesignation(Integer designationId) {
        Designation designation=designationDao.getDesignationById(designationId);
        if(designation!=null)
        {
            designation.setActiveFlag('N');
            designation.setDateOfModification(new Date());
           return designationDao.updateDesignationOnly(designation);
        }else{
            return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
        }
       
    }
    
      public ResultDataMap updateDesignation(Designation department,String contextPath) {
               ResultDataMap rdm=new ResultDataMap();
               Designation designtn=designationDao.getDesignationById(department.getDesignationId());
               designtn.setDateOfModification(new Date());
               designtn.setDesignationName(department.getDesignationName());
               rdm=designationDao.updateDesignationOnly(designtn);
               return rdm;
      }
}
