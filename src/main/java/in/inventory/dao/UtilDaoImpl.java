/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.inventory.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anuja
 */
@Repository
public class UtilDaoImpl implements UtilDao{
    
      @Autowired
    SessionFactory sessionFactory;
      @Override
    public void evictObject(Object object)
    {
        sessionFactory.getCurrentSession().evict(object);
    }
}
