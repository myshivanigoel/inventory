/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.auth.entity.MstUser;
import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.IndentStatus;
import in.db.inventory.entity.Receipt;
import in.db.inventory.entity.ReceiptConsumable;

import in.util.entity.ResultDataMap;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */


public interface PurchaseService {
    
    /**
     *
     * @param indent
     * @return ResultDataMap
     */
    public ResultDataMap saveIndentForm(HdIndent indent);

    public ResultDataMap saveReceiptForm(Receipt receipt);

 
    public ResultDataMap saveReceiptForm(ReceiptConsumable receipt);

    public ResultDataMap saveNonConsumableReceiptForm(Receipt receipt);
    
    
     public List<HdIndent> getIndentorsIndents(MstUser userId);

    public List<HdIndent> getAllIndentsList();

    public List<HdIndent> getIndentsListToBeVerifiedByUser(MstUser userId);

    public HdIndent getIndent(Integer indentId);

    public List<HdIndent> getIndentsToBeAuthorizedByUser(Integer userId);

    public List<HdIndent> getMyPendingIndents(Integer userId);

    public ResultDataMap acceptIndent(Integer indentId, Integer userId);

    public ResultDataMap rejectIndent(Integer indentId, Integer userId);
   
    
    public IndentStatus ifUserAuthenticatedIndent(Integer authenticator,Integer indentId);

    public List<HdIndent> getRequestedIndentsList(Integer userId);

   
    public List<IndentStatus> getauthorizationStatusList(HdIndent indent);

    public List<HdIndent> getApprovedIndentsList(Integer userId);

    public List<HdIndent> getRequestsForFinanceApproval(Integer userId);

    public ResultDataMap indentActionFinance(Integer indentId, String remarks, String financeStatus);

    public boolean isSecondLastAuthenticator(Integer indentorId, Integer userId);

    public ResultDataMap acceptIndent(Integer indentId, Integer userId, String remarks);

    public ResultDataMap updateIndentForm(HdIndent indent);
    
}
