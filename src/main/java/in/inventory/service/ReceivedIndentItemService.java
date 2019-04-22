/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.service;

import in.db.inventory.entity.HdIndent;
import in.db.inventory.entity.HdReceipt;

/**
 *
 * @author shivani
 */
public interface ReceivedIndentItemService {

    public HdReceipt getReceivedItemDetails(HdIndent rindent);
    
}
