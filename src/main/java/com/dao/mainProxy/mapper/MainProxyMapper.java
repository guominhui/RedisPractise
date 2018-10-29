package com.dao.mainProxy.mapper;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pojo.MainProxy;

public interface MainProxyMapper {
	   /** 
     * 注意要和Employeer.xml的方法名对应 
     */
       MainProxy getMainProxyByID(int userId);
    List<MainProxy> getMainProxyLikeUsername(String username);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    int addMainProxy(MainProxy user);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    void deleteMainProxy(String user);
  
    /**
     * 注意要和Employeer.xml的方法名对应 
     */
    int updateMainProxy(MainProxy user);
 
    int updateCardLTime(MainProxy user);
    
    int updateCardLCount(MainProxy count);
 
    
    int updateCardFCount(MainProxy count);
    
    List<MainProxy> getMainProxys(Map<String, Object> paramMap);
    int getMainProxysCount();
    int editPassword(MainProxy user);
}
