package com.dao.proxyDel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.dao.base.BaseDao;
import com.pojo.CardRecord;
import com.pojo.Proxy;

public interface ProxyDelDao extends BaseDao<Proxy>{

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
 
 int updateBonus(Proxy user);
 
 int exchangeBonus(Proxy user);
 
 List<Proxy> getProxysByPioneerID(int pioneerId, int startIndex, int length);
 
 int getProxysCountByRecommendID(int recommendId);
 
 int getProxysCountByPioneerID(int pioneerId);
 //-----------------------------------
 List<Proxy> getAllProxys(Date startTime, Date endTime, int startIndex, int length);
 
 int getAllProxysCount();

 List<Integer> getProxyIdsByRecommendID(int recommendPerson);
 
 List<Integer> getProxyIdsByPioneerID(int pioneerPerson);
 
 List<Proxy> getProxys(int id, Date crTime, Date enTime, int startIndex, int length);

 List<Proxy> getProxysByRecommendID(int recommendPerson);
 
 int clearFCardCount(int userId);
 
 int removeProxy(int proxyId);
}
