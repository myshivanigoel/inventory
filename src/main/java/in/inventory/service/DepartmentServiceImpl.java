package in.inventory.service;

import in.inventory.dao.DepartmentDao;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import in.db.inventory.entity.Department;
import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import in.util.Utility;
import in.utility.EmailServiceImpl;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
@Service
//@Transactional
public class DepartmentServiceImpl implements DepartmentService{

  
	@Autowired
	       DepartmentDao departmentDao;
	
	
        @Override
	public List<Department> getAllDepartmentList() {
		
		return departmentDao.getAllDepartmentList();
	}

        @Override
	public Department getDepartmentById(Integer departmentId) {
		
		return departmentDao.getDepartmentById(departmentId);
	}
        
         @Override
	public ResultDataMap saveDepartment(Department department,String contextPath) {
		ResultDataMap rdm;
		
		rdm=departmentDao.saveDepartment(department);
		rdm.setDataObject(department);
				
		return rdm;
		
	}
	
        @Override
        public ResultDataMap deactivateDepartment(Integer departmentId) {
        Department department=departmentDao.getDepartmentById(departmentId);
        if(department!=null)
        {
            department.setActiveFlag('N');
            department.setDateOfModification(new Date());
           return departmentDao.updateDepartmentOnly(department);
        }else{
            return new ResultDataMap().setStatus(false).setMessage(Strings.InvalidData);
        }
       
    }
    
      public ResultDataMap updateDepartment(Department department,String contextPath) {
               ResultDataMap rdm=new ResultDataMap();
               Department dpt=departmentDao.getDepartmentById(department.getDepartmentId());
               dpt.setDateOfModification(new Date());
               dpt.setDepartmentName(department.getDepartmentName());
               rdm=departmentDao.updateDepartmentOnly(dpt);
               return rdm;
      }
}
