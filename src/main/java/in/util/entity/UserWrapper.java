/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.util.entity;

import in.db.auth.entity.EmployeeAuthorityLevel;
import in.db.auth.entity.MstUser;
import in.db.auth.entity.UserRole;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anuja
 */
public class UserWrapper {
    private MstUser user;
    private List<UserRole> userRoleList=new ArrayList<>();
    private List<EmployeeAuthorityLevel> employeeAuthorityLevelList;

    public MstUser getUser() {
        return user;
    }

    public void setUser(MstUser user) {
        this.user = user;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public List<EmployeeAuthorityLevel> getEmployeeAuthorityLevelList() {
        return employeeAuthorityLevelList;
    }

    public void setEmployeeAuthorityLevelList(List<EmployeeAuthorityLevel> employeeAuthorityLevelList) {
        this.employeeAuthorityLevelList = employeeAuthorityLevelList;
    }

   
  
    
    
}
