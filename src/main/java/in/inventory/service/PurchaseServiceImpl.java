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
import in.db.inventory.entity.Notification;

import in.inventory.dao.PurchaseDao;
import in.notification.service.NotificationService;

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
    
    @Autowired NotificationService notificationService;
    
    @Override
    public ResultDataMap saveIndentForm(HdIndent indent) {
 
        
        ResultDataMap result=new ResultDataMap();
        
        result= purchaseDao.saveIndentForm(indent);
        if(result.getStatus()){
        return notificationService.pushNotification(Strings.NotificationTypePendingDraftedIndent,
                                                            Strings.NotificationStatusNew,
                                                            Strings.NotificationMessagePendingDraftsIndent,
                                                            indent.getIndentor().getUserId(),
                                                            indent.getIndentor().getUserId());
        }else{
            return result;
        }
    }

   
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
      
            totalIndentsList.addAll(purchaseDao.getRequestedIndentsList(userId));
       
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
                         


            //check if he is the last authority
                         if(isLastAuthenticator(employeeAuthorityLevelList,userId))
                         {
                             
                             if( hdIndent.getFinanceStatus()==null)
                             {
                                 
                             }else{
                                 
                                  indentStatus=purchaseDao.ifUserAuthenticatedIndent(userId,hdIndent.getIndentId());
                                 if(indentStatus==null)
                                    {
                                        hdIndentList.add(hdIndent);
                                    }
                             }
                         }else{
                             //get indentstatus
                             indentStatus=purchaseDao.ifUserAuthenticatedIndent(userId,hdIndent.getIndentId());
                             if(indentStatus==null)
                             {
                                 hdIndentList.add(hdIndent);
                             }
                         }
                              
                                     
                ///////////////////////////////////////////         
                         
                         
                          
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
    
    private boolean isLastAuthenticator(List<EmployeeAuthorityLevel> list, Integer userId) {
          list.sort(new Comparator<EmployeeAuthorityLevel>(){
            @Override
		public int compare(EmployeeAuthorityLevel o1, EmployeeAuthorityLevel o2) {
			return o1.getAuthorityLevel()- o2.getAuthorityLevel();
		}
        });
          System.out.println("in.inventory.service.PurchaseServiceImpl.isLastAuthenticator()"+list);
        Integer previousUserId=null;
        int lastIndex=list.size()-1;
        EmployeeAuthorityLevel i=list.get(lastIndex);
        if(i.getAuthorizedEmployee().getUserId().equals(userId))
        {
            return true;
        }else{
            return false;
        }
       
    }

    
    @Override
    public List<HdIndent> getMyPendingIndents(Integer userId) {
        return purchaseDao.getMyPendingIndents(userId);
    }

      
    public ResultDataMap acceptIndent(HdIndent rindent, Integer userId,String remarks) {
        
        Integer indentId=rindent.getIndentId();
        HdIndent indent=purchaseDao.getIndent(indentId);
        if(indent.getStatus().equals(Strings.IndentStatusFinanceRejected))
        {
            indent.setStatus(Strings.IndentStatusInProcess);
            indent.setSpecialApprovalRemark(remarks);
            
        }
       
        for (DtIndent dtIndent : rindent.getIndentDetailList()) {
            for (DtIndent dbdtIndent1 : indent.getIndentDetailList()) {
                if(dbdtIndent1.getDtIndentId().equals(dtIndent.getDtIndentId()))
                {
                     dbdtIndent1.setAcceptedFlag(dtIndent.getAcceptedFlag());
                }
            }
           
          
        }
        purchaseDao.updateHdIndent(indent);
        
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }
    @Override
    public ResultDataMap acceptIndent(HdIndent rindent,String remarks, Integer userId) {
        Integer indentId=rindent.getIndentId();
        HdIndent indent=purchaseDao.getIndent(indentId);
        
        
        ResultDataMap result=new ResultDataMap();
        IndentStatus indentStatus=new IndentStatus();
        indentStatus.setActiveFlag('Y');
        indentStatus.setAuthorizedEmployee(new MstUser(userId));
        indentStatus.setDtEntryDate(new Date());
        indentStatus.setIndent(new HdIndent(indentId));
        indentStatus.setRemarks(remarks);
        
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
        
        for (DtIndent dtIndent : rindent.getIndentDetailList()) {
            for (DtIndent dbdtIndent1 : indent.getIndentDetailList()) {
                if(dbdtIndent1.getDtIndentId().equals(dtIndent.getDtIndentId()))
                {
                     dbdtIndent1.setAcceptedFlag(dtIndent.getAcceptedFlag());
                }
            }
           
          
        }
         purchaseDao.updateHdIndent(indent);
            return result.setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
        
        
        
    }

    @Override
    public ResultDataMap rejectIndent(Integer indentId,String remarks, Integer userId) {
        
        ResultDataMap result=new ResultDataMap();
        IndentStatus indentStatus=new IndentStatus();
        indentStatus.setActiveFlag('Y');
        indentStatus.setAuthorizedEmployee(new MstUser(userId));
        indentStatus.setDtEntryDate(new Date());
        indentStatus.setIndent(new HdIndent(indentId));
        indentStatus.setStatus(Strings.IndentStatusRejected);
        indentStatus.setRemarks(remarks);
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
        if(dbIndentStatusList.size()<=1 && authoritiesList.size()==1 && indent.getFinanceStatus()!=null)
        {
             IndentStatus nmIndent=new IndentStatus();
                                      nmIndent.setAuthorizedEmployee(userService.getFinanceUser());
                                     nmIndent.setIndent(indent);
                                     nmIndent.setStatus(indent.getFinanceStatus());
                                     nmIndent.setRemarks(indent.getFinanceRemarks());
                                     statusList.add(nmIndent);
        }
        for (EmployeeAuthorityLevel employeeAuthorityLevel : authoritiesList) {
            int temp=0;
            int finAdded=0;
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
                                finAdded=1;
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
                
                List<EmployeeAuthorityLevel> toSortList=new ArrayList<>();
                toSortList.addAll(authoritiesList);
                
                if(isLastAuthenticator(toSortList, employeeAuthorityLevel.getAuthorizedEmployee().getUserId()) 
                        && indent.getFinanceStatus()!=null  && finAdded==1)
                {
                     /////////
                                //Second last user approved it thats why finance user could see it
                               // statusList.get(tempvar).setStatus(Strings.IndentStatusApproved);
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
                }
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
        
      return  purchaseDao.updateHdIndent(dbIndent);
        
        
    }

    @Override
    public List<HdIndent> getDraftedIndents(Integer userId) {
        List<HdIndent> list= purchaseDao.getDraftedIndentsList(userId);
        System.out.println("in.inventory.service.PurchaseServiceImpl.getDraftedIndents()"+list);
        return list;
    }

    @Override
    public boolean isIndentorsIndent(Integer userId, Integer indentId) {
       int i= purchaseDao.isIndentorsIndent(userId,indentId);
       if(i==1)
       {
           return true;
       }else{
           return false;
       }
    }

    @Override
    public ResultDataMap submitIndent(Integer indentId) {
     
        HdIndent dbIndent=purchaseDao.getIndent(indentId);
        dbIndent.setStatus(Strings.IndentStatusInProcess);
        dbIndent.setSubmittedStatus(Strings.SubmittedIndent);
        List<EmployeeAuthorityLevel> list=userService.getEmployeeAuthorityLevelList(dbIndent.getIndentor().getUserId());
        if(list.size()==1)
        {
            dbIndent.setStatus(Strings.IndentStatusForFinanceApproval);
        }
        
        ResultDataMap result=purchaseDao.updateHdIndent(dbIndent);
        if(result.getStatus())
        {
            // update notification for indentor
            List<Notification>  listNotification= notificationService.getNotificationsByReferenceTableIdName(dbIndent.getIndentId(), Strings.NotificationTypePendingDraftedIndent, dbIndent.getIndentor().getUserId());
           Notification notification= listNotification.get(0);
           notification.setNotificationStatus(Strings.NotificationStatusCompleted);
            notification.setNotificationCompletedDate(new Date());
            notificationService.updateNotification(notification);
            // generate Notification for authenticator
            List<EmployeeAuthorityLevel> listEm=userService.getEmployeeAuthorityLevelList(dbIndent.getIndentor().getUserId());
            EmployeeAuthorityLevel authorizer=listEm.get(0);
            if(authorizer!=null)
            {
                Notification newNotification=notificationService.createNotificationObject(Strings.NotificationTypeApproveIndent, Strings.NotificationStatusNew, 
                                                                "YOu have pending request for approving indent of "+dbIndent.getIndentor().getUserName(), 
                                                                authorizer.getAuthorizedEmployee(), dbIndent.getIndentor());
                result= notificationService.pushNotification(newNotification);
            }
        }else{
            
        }
        
       
        return result;
        
    }

    @Override
    public List<HdIndent> getIndentListForFinance() {
        return purchaseDao.getIndentListForFinance();
    }

    
   

    
    
    
}
