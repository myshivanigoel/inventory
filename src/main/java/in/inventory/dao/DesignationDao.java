package in.inventory.dao;

import java.util.Collection;
import java.util.List;
import in.db.dashboard.entity.MenuGroup;
import in.db.auth.entity.Designation;
import in.util.entity.ResultDataMap;
import java.util.LinkedHashMap;
public interface DesignationDao {

	
        public List<Designation> getAllDesignationList();

        public ResultDataMap saveDesignation(Designation designation);
	
        public Designation getDesignationById(Integer designation);
        
        public ResultDataMap updateDesignationOnly(Designation designation);
}

