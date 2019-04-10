/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import org.springframework.stereotype.Repository;

/**
 *
 * @author anuja
 */

public interface UtilDao {
     public void evictObject(Object object);
}
