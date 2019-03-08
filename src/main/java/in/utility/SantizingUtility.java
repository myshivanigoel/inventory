/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.utility;

//import in.cdac.website.admin.model.News;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

/**
 *
 * @author anuja
 */
@Service
public class SantizingUtility {
    
    public static void main(String[] args) throws IntrospectionException, IllegalAccessException,
                                                        	IllegalArgumentException, InvocationTargetException,
                                				InstantiationException,ClassNotFoundException
    {
    
     }
    
    public static String santize(String text){
        PolicyFactory santizer=Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.IMAGES).and(Sanitizers.LINKS).and(Sanitizers.STYLES);
        
        return santizer.sanitize(text);
    
    }
    
    /**
     *
     * @param myObj
     * @param clas
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static Object validate(Object myObj,String clas) throws IntrospectionException, IllegalAccessException,
                                                        	IllegalArgumentException, InvocationTargetException,
                                				InstantiationException,ClassNotFoundException
	{
		
		boolean status=false;
	
		//	Object myObj=Class.forName(clas).newInstance();
			System.out.println("class name of object for validation : "+myObj.getClass());
			         BeanInfo beanInfo = Introspector.getBeanInfo(myObj.getClass());
                     	for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
			    String propertyName = propertyDesc.getName();
			    System.out.println(propertyName);
			    Class<?> type=propertyDesc.getPropertyType();
			    Object value = null;
				
					value = propertyDesc.getReadMethod().invoke(myObj);
				
			    if(type.equals(String.class))
			    {
			    	if(value!=null){
				    
                                        propertyDesc.setValue(propertyName, new SantizingUtility().santize(value.toString()));
                                        BeanWrapper beanWrapper=new BeanWrapperImpl(myObj);
                                        beanWrapper.setPropertyValue(propertyName, propertyDesc.getValue(propertyName));
                                        
                                       
			    	}
			    }
			    
			}

	
	  return myObj;	
	}
}
