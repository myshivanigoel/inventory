package in.inventory.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
;
import in.db.dashboard.entity.MenuGroup;
import in.db.inventory.entity.Designation;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;


public interface DesignationService {
	
	public List<Designation> getAllDesignationList();
	
	public ResultDataMap saveDesignation(Designation designation,String contextPath);
        
        public Designation getDesignationById(Integer designationId);
        
        public ResultDataMap deactivateDesignation(Integer designationId);
        
        public ResultDataMap updateDesignation(Designation designation,String contextPath);
}
