package com.service.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pojo.CardRecord;
import com.pojo.Proxy;
import com.service.base.BaseService;

public interface ProxyService extends BaseService<Proxy> {
	   /** 
  * 注意要和Employeer.xml的方法名对应 
  */
       Proxy findProxyByID(int id);

 /** 
  * 注意要和Employeer.xml的方法名对应 
  */
 int addProxy(Proxy proxy);

 /** 
  * 注意要和Employeer.xml的方法名对应 
  */
 void deleteProxy(String proxyId);

 /**
  * 注意要和Employeer.xml的方法名对应 
  */
 int updateProxy(Proxy proxy);
 
 /**
  * 代理买卡
  * @param proxyId
  * @param type
  * @param cardCount
  * @param cardLTime
  * @return
  */
 int updateCardCount(int triggerId, String sellerName, int proxyId, int type, int cardCount, Date cardLTime);

 
 List<Proxy> getProxysByRecommendID(int recommendId, int startIndex, int length);
 List<Proxy> getProxysByPioneerID(int pioneerId, int startIndex, int length);
 List<Proxy> getAllProxys(int startIndex, int length);
 List<Proxy> getAllProxys(Date startTime, Date endTime);
 /**
  * 代理售卡
  * @param userId
  * @param edUserId
  * @param type
  * @param toType
  * @param count
  * @param income
  * @param cardLTime
  * @return
  */
 int sellCardCount(int userId, int edUserId, int type, int toType, int count, int income);
 /**
  * 单纯更新房卡数
  * @param proxyId
  * @param type
  * @param cardCount
  * @return
  */
 int updateCardCount(int proxyId, int type, int cardCount);
 int updateCardCountWithProxy(Proxy proxy, int type, int cardCount);
 int clearCardCount(int proxyId, int type);
 int removeProxy(int proxyId);
 
 List<Integer> getProxyIdsByRecommendID(int recommendPerson);
 
 List<Proxy> getProxysByRecommendID(int recommendPerson);
 
 List<Integer> getProxyIdsByPioneerID(int pioneerPerson);
 
 List<Proxy> getProxysByTime(int id, Date crTime, Date enTime);
 
 List<Proxy> getProxysByIndex(int id, int startIndex, int length);
 
 
 int getProxysCountByRecommendID(int recommendId);
 
 int getProxysCountByPioneerID(int pioneerId);
 //-----------------------------------
 int getAllProxysCount();
 
 boolean handleThreeClassBonus(int id, String sellerName, int addCardCount);
 
 boolean exchangeCardwithBonus(int id, int exchangeClass);
 
 int editProxyForGameUid(int id, int curGameUid);
}
