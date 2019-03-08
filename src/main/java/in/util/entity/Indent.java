/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.util.entity;

import in.db.inventory.entity.DtIndent;
import in.db.inventory.entity.HdIndent;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author anuja
 */


public class Indent {
    
    private HdIndent hdIndent;
    private DtIndent dtIndent;

    public HdIndent getHdIndent() {
        return hdIndent;
    }

    public void setHdIndent(HdIndent hdIndent) {
        this.hdIndent = hdIndent;
    }

    public DtIndent getDtIndent() {
        return dtIndent;
    }

    public void setDtIndent(DtIndent dtIndent) {
        this.dtIndent = dtIndent;
    }

    @Override
    public String toString() {
        return "Indent{" + "hdIndent=" + hdIndent + ", dtIndent=" + dtIndent + '}';
    }
    
    
   
    
}
