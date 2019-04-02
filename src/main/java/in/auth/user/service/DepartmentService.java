package in.auth.user.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
;
import in.db.dashboard.entity.MenuGroup;
import in.db.auth.entity.Department;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;


public interface DepartmentService {
	
	public List<Department> getAllDepartmentList();
	
	public ResultDataMap saveDepartment(Department department,String contextPath);
        
        public Department getDepartmentById(Integer departmentId);
        
        public ResultDataMap deactivateDepartment(Integer departmentId);
        
        public ResultDataMap updateDepartment(Department department,String contextPath);
}
