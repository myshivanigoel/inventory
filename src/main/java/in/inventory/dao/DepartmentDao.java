package in.inventory.dao;

import java.util.Collection;
import java.util.List;
import in.db.dashboard.entity.MenuGroup;
import in.db.auth.entity.Department;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;
public interface DepartmentDao {

	
        public List<Department> getAllDepartmentList();

        public ResultDataMap saveDepartment(Department department);
	
        public Department getDepartmentById(Integer department);
        
        public ResultDataMap updateDepartmentOnly(Department department);
}

