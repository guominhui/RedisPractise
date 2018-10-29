package com.dao.manager.mapper;


import java.util.Date;

import com.pojo.Manager;

public interface ManagerMapper {
	   /** 
     * 注意要和Employeer.xml的方法名对应 
     */
       Manager findManagerByID(int id);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    int addManager(Manager user);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    void deleteManager(Manager user);
  
    /**
     * 注意要和Employeer.xml的方法名对应 
     */
    int updateManager(Manager user);
 
    int updateCardLTime(Date date);
    int editPassword(Manager user);
    
    
}
