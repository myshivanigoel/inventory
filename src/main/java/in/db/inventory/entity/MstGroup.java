/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.db.inventory.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
/**
 *
 * @author shivani
 */
@Entity
public class MstGroup {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer groupId;
    private String groupName;
    private String valueType;
    private Character activeFlag;
    
  
    
    

    @OneToMany(mappedBy = "itemGroup",
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
   
    private List<ItemMaster> items;

    public List<ItemMaster> getItems() {
        return items;
    }

    public void setItems(List<ItemMaster> items) {
        this.items = items;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Character getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Character activeFlag) {
        this.activeFlag = activeFlag;
    }

    
    
}
