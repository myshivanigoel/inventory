/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.auth.user.service.UserService;
import in.db.auth.entity.EmployeeAuthorityLevel;
import in.db.auth.entity.MstUser;
import in.db.inventory.entity.DtIndent;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.IndentStatus;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;
import in.inventory.dao.PurchaseDao;

import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */
@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    PurchaseDao purchaseDao;
    
    @Autowired
    UserService userService;
    
    
    @Override
    public ResultDataMap saveIndentForm(HdIndent indent) {
 
        
        
        return purchaseDao.saveIndentForm(indent);
    }

    @Override
    public ResultDataMap saveReceiptForm(Receipt receipt) {
        return purchaseDao.saveReceiptForm(receipt);
    }

    @Override
    public ResultDataMap saveReceiptForm(ReceiptConsumable receipt) {
        return purchaseDao.saveReceiptForm(receipt);
    }

    @Override
    public ResultDataMap saveNonConsumableReceiptForm(Receipt receipt) {
        return purchaseDao.saveNonConsumableReceiptForm(receipt);  }

    @Override
    public List<HdIndent> getIndentorsIndents(MstUser userId) {
        return purchaseDao.getIndentorsIndents(userId);
    }

    @Override
    public List<HdIndent> getAllIndentsList() {
       return purchaseDao.getAllIndentsList();
     }

    @Override
    public List<HdIndent> getIndentsListToBeVerifiedByUser(MstUser userId) {
       return purchaseDao.getIndentsListToBeVerifiedByUser(userId);
               
    }

    
    @Override
    public List<HdIndent> getRequestedIndentsList(Integer userId) {
        
    List<HdIndent> totalIndentsList=new ArrayList<>();
        
        //get users whoses indents this user authorize
       List<MstUser> subUsersList=userService.getMySubordinatesList(userId);
       // get list of indents of all these users
        for (MstUser subMstUser : subUsersList) {
            totalIndentsList.addAll(purchaseDao.getMyPendingIndents(subMstUser.getUserId()));
            totalIndentsList.addAll(purchaseDao.getRequestedIndentsList(userId));
        }
       
      
       return totalIndentsList;
               
    }
    
    @Override
    public HdIndent getIndent(Integer indentId) {
       
        HdIndent indent= purchaseDao.getIndent(indentId);
        List<EmployeeAuthorityLevel> employeeAuthorityLevelList = userService.getEmployeeAuthorityLevelList(indent.getIndentor().getUserId());
        System.out.println("in.inventory.service.PurchaseServiceImpl.getIndent()"+employeeAuthorityLevelList);
        indent.setAuthoritiesList(employeeAuthorityLevelList);
        return indent;
    }

    @Override
    public List<HdIndent> getIndentsToBeAuthorizedByUser(Integer userId) {
        System.out.println("in.inventory.service.PurchaseServiceImpl.getIndentsToBeAuthorizedByUser()");
        List<HdIndent> hdIndentList=new ArrayList<>();
    List<HdIndent> totalIndentsList=new ArrayList<>();
        List<HdIndent> specialIndentsList=new ArrayList<>();
        //get users whoses indents this user authorize
       List<MstUser> usersList=userService.getMySubordinatesList(userId);
       // get list of indents of all these users
        for (MstUser mstUser : usersList) {
            totalIndentsList.addAll(purchaseDao.getMyPendingIndents(mstUser.getUserId()));
            specialIndentsList.addAll(purchaseDao.getFinanceRejectedIndents(mstUser.getUserId()));
        }
       
       // check who is next person in authorization steps
        for (HdIndent hdIndent : totalIndentsList) {
            // get authoried users list sorted according to level
              List<EmployeeAuthorityLevel> employeeAuthorityLevelList=userService.getEmployeeAuthorityLevelList(hdIndent.getIndentor().getUserId());
              //
            
            //get previous authenticator 
            Integer previousAuthenticator=getPreviousAuthenticator(employeeAuthorityLevelList,userId);
                    //if found
                    IndentStatus indentStatus=null;
                     if(previousAuthenticator!=null)
                     {
                         //get indentstatus
                             indentStatus=purchaseDao.ifUserAuthenticatedIndent(previousAuthenticator,hdIndent.getIndentId());
                                                 if(indentStatus!=null)
                            {
                                //if approved
                                if(indentStatus.getStatus().equals(Strings.IndentStatusApproved))
                                {
                                      // add to returning list
                                      hdIndentList.add(hdIndent);
                                }
                                else{
                                    //previous person declined request so nothing to do 
                                }
                                
                            }
                           
                        //else
                            else{
                                //previous person hasnt yet taken any action
                                // do nothing
                            }
                            
                     }
                        
                   //else not found
                     else{
                         // he is the authority
                         //get indentstatus
                             indentStatus=purchaseDao.ifUserAuthenticatedIndent(userId,hdIndent.getIndentId());
                             if(indentStatus==null)
                             {
                                 hdIndentList.add(hdIndent);
                             }
                          
                     }
                       
              
                            
        }
       
       
        System.out.println("in.inventory.service.PurchaseServiceImpl.getIndentsToBeAuthorizedByUser()"+hdIndentList);
        
        hdIndentList.addAll(addSpecialCaseIndents(userId,specialIndentsList));
        
        
       return hdIndentList;
    }

    
     private Collection<? extends HdIndent> addSpecialCaseIndents(Integer userId, List<HdIndent> specialIndentsList) {
        
         List<HdIndent> list=new ArrayList<>();
         for (HdIndent hdIndent : specialIndentsList) {
             if(hdIndent.getStatus().equals(Strings.IndentStatusFinanceRejected))
             {
                 if(isSecondLastAuthenticator(hdIndent.getIndentor().getUserId(), userId))
                 {
                     list.add(hdIndent);
                 }
             }
         }
         return list;
    }
    
    Integer getPreviousAuthenticator(List<EmployeeAuthorityLevel> list,Integer userId)
    {
        list.sort(new Comparator<EmployeeAuthorityLevel>(){
            @Override
		public int compare(EmployeeAuthorityLevel o1, EmployeeAuthorityLevel o2) {
			return o1.getAuthorityLevel()- o2.getAuthorityLevel();
		}
        });
        Integer previousUserId=null;
        for (EmployeeAuthorityLevel employeeAuthorityLevel : list) {
           Integer currentUserId=employeeAuthorityLevel.getAuthorizedEmployee().getUserId();
            if(currentUserId.equals(userId)){
                break;
            }else{
                previousUserId=currentUserId;
            }
        }
        return previousUserId;
    }
    
    
    
    @Override
    public List<HdIndent> getMyPendingIndents(Integer userId) {
        return purchaseDao.getMyPendingIndents(userId);
    }

      
    public ResultDataMap acceptIndent(Integer indentId, Integer userId,String remarks) {
        HdIndent indent=purchaseDao.getIndent(indentId);
        if(indent.getStatus().equals(Strings.IndentStatusFinanceRejected))
        {
            indent.setStatus(Strings.IndentStatusInProcess);
            indent.setSpecialApprovalRemark(remarks);
            
        }
        purchaseDao.updateHdIndent(indent);
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }
    @Override
    public ResultDataMap acceptIndent(Integer indentId, Integer userId) {
        HdIndent indent=purchaseDao.getIndent(indentId);
        
        
        ResultDataMap result=new ResultDataMap();
        IndentStatus indentStatus=new IndentStatus();
        indentStatus.setActiveFlag('Y');
        indentStatus.setAuthorizedEmployee(new MstUser(userId));
        indentStatus.setDtEntryDate(new Date());
        indentStatus.setIndent(new HdIndent(indentId));
        
            indentStatus.setStatus(Strings.IndentStatusApproved);
        
        
         purchaseDao.saveIndentStatus(indentStatus);
         
       if(isSecondLastAuthenticator(indent.getIndentor().getUserId(),userId))
        {
             indent.setStatus(Strings.IndentStatusForFinanceApproval);
        }else{
             indent.setStatus(Strings.IndentStatusApproved);
        }
        if(userService.ifLastAuthorityLevel(indent.getIndentor().getUserId(),userId))
        {
            System.out.println("in.inventory.service.PurchaseServiceImpl.acceptIndent() :: last authority Level");
            indent.setStatus(Strings.IndentStatusApproved);
           
        }else{
            System.out.println("in.inventory.service.PurchaseServiceImpl.acceptIndent() :: not last authority Level");
           // return result.setStatus(Boolean.FALSE);
            
        }
         purchaseDao.updateHdIndent(indent);
            return result.setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
        
        
        
    }

    @Override
    public ResultDataMap rejectIndent(Integer indentId, Integer userId) {
        
        ResultDataMap result=new ResultDataMap();
        IndentStatus indentStatus=new IndentStatus();
        indentStatus.setActiveFlag('Y');
        indentStatus.setAuthorizedEmployee(new MstUser(userId));
        indentStatus.setDtEntryDate(new Date());
        indentStatus.setIndent(new HdIndent(indentId));
        indentStatus.setStatus(Strings.IndentStatusRejected);
        purchaseDao.saveIndentStatus(indentStatus);
        HdIndent indent=purchaseDao.getIndent(indentId);
        
        
        
        
            indent.setStatus(Strings.IndentStatusRejected);
            purchaseDao.updateHdIndent(indent);
       
        
        
        return result.setStatus(Boolean.TRUE);
    }

    @Override
    public IndentStatus ifUserAuthenticatedIndent(Integer authenticator, Integer indentId) {
       return  purchaseDao.ifUserAuthenticatedIndent(authenticator,indentId);
    }

   
    
    @Override
    public List<IndentStatus> getauthorizationStatusList(HdIndent indent) {
        
        List<IndentStatus> statusList=new ArrayList<>();
        List<EmployeeAuthorityLevel> authoritiesList =indent.getAuthoritiesList();
        List<IndentStatus> dbIndentStatusList=indent.getIndentStatusList();
        for (EmployeeAuthorityLevel employeeAuthorityLevel : authoritiesList) {
            int temp=0;
            for (IndentStatus indentStatus : dbIndentStatusList) {
                if(employeeAuthorityLevel.getAuthorizedEmployee().getUserId().equals(indentStatus.getAuthorizedEmployee().getUserId()))
                {
                    
                         statusList.add(indentStatus);
                         int tempvar=statusList.indexOf(indentStatus);
                
                    if(isSecondLastAuthenticator(indent.getIndentor().getUserId()
                                                , employeeAuthorityLevel.getAuthorizedEmployee().getUserId()
                                                )
                      )
                    {
                            if(indent.getFinanceStatus()!=null)
                            {
                                /////////
                                //Second last user approved it thats why finance user could see it
                                statusList.get(tempvar).setStatus(Strings.IndentStatusApproved);
                                //////////////
                                // add finance user action 
                                IndentStatus nmIndent=new IndentStatus();
                                      nmIndent.setAuthorizedEmployee(userService.getFinanceUser());
                                     nmIndent.setIndent(indent);
                                     nmIndent.setStatus(indent.getFinanceStatus());
                                     nmIndent.setRemarks(indent.getFinanceRemarks());
                                     statusList.add(nmIndent);
                                
                                if(indent.getFinanceStatus().equals(Strings.IndentFinanceStatusNotAcceptable))
                                {
                                    
                                    //check if secondLast user re approved or not
                                    if(indent.getSpecialApprovalRemark()!=null 
                                            && !indent.getSpecialApprovalRemark().trim().equals("")){
                                    
                                     nmIndent=new IndentStatus();
                                     nmIndent.setAuthorizedEmployee(employeeAuthorityLevel.getAuthorizedEmployee());
                                     nmIndent.setIndent(indent);
                                     nmIndent.setStatus("Approved");
                                     nmIndent.setRemarks(indent.getSpecialApprovalRemark());
                                     statusList.add(nmIndent);
                                }else if(indent.getStatus().equals(Strings.IndentStatusRejected)){
                                    //second last user rejected after fincance advice
                                      nmIndent=new IndentStatus();
                                     nmIndent.setAuthorizedEmployee(employeeAuthorityLevel.getAuthorizedEmployee());
                                     nmIndent.setIndent(indent);
                                     nmIndent.setStatus(indent.getStatus());
                                     
                                     statusList.add(nmIndent);
                                }
                            }else{
                                // finance user accepted so next users entry will be added in next iteration
                            }
                   }else{
                        // finance user has nt took any action yet 
                    }
                  
                }else{
                    // for nrml users status added already
                }
                     temp=1;
                    break;
            }// if end 
            }// searching in indent Status loop end
            if(temp==0)
            {
                    IndentStatus indentStatus=new IndentStatus();
                   indentStatus.setAuthorizedEmployee(employeeAuthorityLevel.getAuthorizedEmployee());
                   indentStatus.setIndent(indent);
                   indentStatus.setStatus("");
                   
                   statusList.add(indentStatus);
            }else{
                
            }
        }
        System.out.println("status List"+statusList);
        
        return statusList;
    }

    @Override
    public List<HdIndent> getApprovedIndentsList(Integer userId) {
          
            return purchaseDao.getApprovedIndentsList(userId);
       
    }

    @Override
    public List<HdIndent> getRequestsForFinanceApproval(Integer userId) {
        
        List<HdIndent> list=purchaseDao.getRequestsForFinanceApproval(userId);
        System.out.println("in.inventory.service.PurchaseServiceImpl.getRequestsForFinanceApproval()\n"+list);
        return list;
    }

    public boolean isSecondLastAuthenticator(Integer indentorId,Integer authenticatorId) {
       
                int index=0;
              List<EmployeeAuthorityLevel> employeeAuthorityLevelList=userService.getEmployeeAuthorityLevelList(indentorId);
              for (EmployeeAuthorityLevel employeeAuthorityLevel : employeeAuthorityLevelList) {
               
                  if(employeeAuthorityLevel.getAuthorizedEmployee().getUserId().equals(authenticatorId))
                {
                    
                    break;
                }
                   index++;
        }
              int total=employeeAuthorityLevelList.size();
              int temp=total-2;
              if(index==temp)
              {
                  return true;
              }
              return false;
    }

    @Override
    public ResultDataMap indentActionFinance(Integer indentId, String remarks, String financeStatus) {
        HdIndent indent=purchaseDao.getIndent(indentId);
        indent.setFinanceStatus(financeStatus);
        indent.setFinanceRemarks(remarks);
        if(indent.getFinanceStatus().equals(Strings.IndentFinanceStatusAcceptable))
        {
            indent.setStatus(Strings.IndentStatusInProcess);
        }else{
            indent.setStatus(Strings.IndentStatusFinanceRejected);
        }
        return purchaseDao.updateHdIndent(indent);
        
    }

    @Override
    public ResultDataMap updateIndentForm(HdIndent indent) {
        
        HdIndent dbIndent=purchaseDao.getIndent(indent.getIndentId());
        if(dbIndent==null)
        {
            return new ResultDataMap().setStatus(Boolean.FALSE);
        }
        
        for (DtIndent dtIndent : indent.getIndentDetailList()) {
            dtIndent.setHdIndent(indent);
        }
        
        purchaseDao.deleteOldDtIndentEntries(indent.getIndentId());
        dbIndent.setBudgetYear(indent.getBudgetYear());
        dbIndent.setIndentDetailList(indent.getIndentDetailList());
        dbIndent.setPreviousReferenceOfPurchaseIfAny(indent.getPreviousReferenceOfPurchaseIfAny());
        dbIndent.setProject(indent.getProject());
        dbIndent.setSourceData(indent.getSourceData());
        
        purchaseDao.updateHdIndent(indent);
        
        return new ResultDataMap().setStatus(Boolean.FALSE);
    }

   

    
    
    
}
