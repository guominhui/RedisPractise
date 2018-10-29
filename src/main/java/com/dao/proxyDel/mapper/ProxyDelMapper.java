package com.dao.proxyDel.mapper;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pojo.Proxy;

public interface ProxyDelMapper {
	   /** 
     * 注意要和Employeer.xml的方法名对应 
     */
       Proxy findProxyByID(int id);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    int addProxy(Proxy user);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    int deleteProxy(int userId);
  
    /**
     * 注意要和Employeer.xml的方法名对应 
     */
    int updateProxy(Proxy user);
    
    int updateLCardCount(Proxy user);
    
    int updateFCardCount(Proxy user);
    
    int clearFCardCount(int userId);
    
    int updateBonus(Proxy user);
    
    int exchangeBonus(Proxy user);
    
    List<Proxy> getAllProxys(Map<String, Object> paramMap);
    
    int getAllProxysCount();
    //需要添加起始指针-----------
    List<Proxy> getProxysByRecommendID(Map<String, Object> message) ;
    
    List<Proxy> getProxysByPioneerID(Map<String, Object> message) ;
    
    int getProxysCountByRecommendID(int recommendId) ;
    
    int getProxysCountByPioneerID(int recommendId) ;
    
    int editPassword(Proxy user);
    
    List<Integer> getProxyIdsByRecommendID(int recommendPerson);
    
    List<Integer> getProxyIdsByPioneerID(int pioneerPerson);
    //模糊查找，需要起始指针-------------
    List<Proxy> getProxyLikeUsername(String username);
    //按时间方式查看所有代理
    
  //按时间方式查看下级代理
  List<Proxy> getProxys(Map<String, Object> msg);
    
    List<Proxy> getProxysByRecommendID(int recommendId) ;
}
