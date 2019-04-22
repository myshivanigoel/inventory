/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.IndentStatus;


import in.util.entity.ResultDataMap;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author anuja
 */
public interface PurchaseDao {
      public ResultDataMap saveIndentForm(HdIndent indent);

 

    public List<HdIndent> getIndentorsIndents(MstUser userId);

    public List<HdIndent> getAllIndentsList();

    public List<HdIndent> getRequestedIndentsList(Integer userId);

    public HdIndent getIndent(Integer indentId);

    public List<HdIndent> getMyPendingIndents(Integer userId);

    public IndentStatus ifUserAuthenticatedIndent(Integer userId, Integer indentId);

    public ResultDataMap updateHdIndent(HdIndent indent);

    public void saveIndentStatus(IndentStatus indentStatus);

    public List<HdIndent> getIndentsListToBeVerifiedByUser(MstUser userId);

    public List<HdIndent> getApprovedIndentsList(Integer userId);

    public List<HdIndent> getRequestsForFinanceApproval(Integer userId);

    public Collection<? extends HdIndent> getFinanceRejectedIndents(Integer userId);

    public void deleteOldDtIndentEntries(Integer indentId);

    public List<HdIndent> getDraftedIndentsList(Integer userId);

    public int isIndentorsIndent(Integer userId, Integer indentId);

    public List<HdIndent> getIndentListForFinance();

   
  
   

   

   
}
