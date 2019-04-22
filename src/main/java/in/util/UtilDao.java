/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.util;

import in.util.entity.ResultDataMap;
import in.util.entity.Strings;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author anuja
 */
@Repository
public class UtilDao {
    
    @Autowired
    SessionFactory sessionFactory;
    
    public ResultDataMap saveObject(Object object)
    {
        sessionFactory.getCurrentSession().save(object);
        return new ResultDataMap().setStatus(Boolean.TRUE).setMessage(Strings.savedSuccessfully);
    }
}
