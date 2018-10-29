package com.service.proxy.impl;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.exchangeRecord.ExchangeRecordDao;
import com.dao.proxy.ProxyDao;
import com.dictionary.ErrorDictionary;
import com.pojo.CardRecord;
import com.pojo.Customer;
import com.pojo.DistributionRecord;
import com.pojo.ExchangeRecord;
import com.pojo.GameRecord;
import com.pojo.MainProxy;
import com.pojo.Proxy;
import com.service.customer.CustomerService;
import com.service.distribution.DistributionService;
import com.service.proxy.ProxyService;
import com.utils.ConfigUtil;
/**
 * 代理的数据实现层
 * @author GuoMinhui
 *
 */
@Service
public class ProxyServiceImpl implements ProxyService{

	@Autowired
	ProxyDao proxyDao;

	@Autowired
	CustomerService customerService;

	@Autowired
	DistributionService distributionService;
	
	@Autowired
	ExchangeRecordDao exchangeRecordDao;
	
	@Override
	public Proxy findProxyByID(int id) {
		return proxyDao.findProxyByID(id);
	}
	@Override
	public int editProxyForGameUid(int id,int curGameUid) {
		//大前提，代理的游戏gameUid游戏id被修改
		//验证，代理的游戏账号是否为空，如果原本不为空，要连带修改userinfo表（删除原来）,当前非0，添加现在
		//                       原本为空，修改userinfo表
		Proxy proxy=proxyDao.findProxyByID(id);
		if(proxy==null||proxy.getUserId()==0){
			return ErrorDictionary.date_tranform_err.getValue();
		}
		
		if(proxy.getGameUid()==curGameUid&&curGameUid!=0){
			return 1;
		}
		
		if(proxy.getGameUid()!=0){
			//验证输入的游戏Id
			Customer customer=customerService.QueryCustomerById(curGameUid);
			if(customer==null){
				return ErrorDictionary.game_uid_notExist.getValue();
			}
			if(customer.getProxyUid()!=0){
				return ErrorDictionary.proxy_uid_hasExist.getValue();
			}
			//验证输入的游戏Id
			customerService.updateProxyId(proxy.getGameUid(), 0);
		   if(curGameUid!=0){
			   customerService.updateProxyId(curGameUid, proxy.getUserId());
		   }
		}else{
			 customerService.updateProxyId(curGameUid, proxy.getUserId());
		}
		return 1;
	}
	


	@Override
	public int addProxy(Proxy proxy) {
		//检验玩家输入的游戏账号id是否存在
		Customer customer=customerService.QueryCustomerById(proxy.getGameUid());
        //暂时玩家游戏Id为不强制项
		//		if(customer==null||customer.getUserId()==0){
//			return ErrorDictionary.game_uid_notExist.getValue();
//		}
		if(customer!=null&&customer.getProxyUid()!=0){
			return ErrorDictionary.proxy_uid_hasExist.getValue();
		}
		//检验玩家输入的游戏账号id是否存在
		int result=-1;
		StringBuffer stringBuffer=new StringBuffer();
		for (int i = 0; i < 7; i++) {
			stringBuffer.append((int)(Math.random()*10));	
		}
		proxy.setInitPassword(stringBuffer.toString());
		proxy.setPassword(stringBuffer.toString());
		result=proxyDao.addProxy(proxy);
		//暂时关闭强制输入游戏id
		if(customer!=null){
			customer.setProxyUid(proxy.getUserId());
			customerService.updateProxyId(proxy.getGameUid(), proxy.getUserId());
		}
		
		return result;
	}

	@Override
	public void deleteProxy(String proxyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateProxy(Proxy proxy) {
		return proxyDao.updateProxy(proxy);
	}



	@Override
	public List<Proxy> getProxysByRecommendID(int recommendId,int startIndex,int length) {
		return proxyDao.getProxysByRecommendID(recommendId);
	}

	/**
	 * 更新代理的卡数，代理为被操作人，即他人为代理充卡
	 */
	@Override
	public int updateCardCount(int triggerId,String sellerName,int proxyId, int type,int cardCount,Date cardLTime) {
		Proxy proxy= proxyDao.findProxyByID(proxyId);
		int result=-1;
		//充卡人不是管理员的情况下，判断是否是他的开户人
//		if(triggerId>=100){
//			int ItspioneerId=0;
//			 ItspioneerId=proxy.getPioneerPerson();
//			if (ItspioneerId!=triggerId&&ItspioneerId!=0) {
//				return ErrorDictionary.not_your_pionner.getValue();
//			}
//		}
		if (type==0) {
			proxy.setCardFCount(proxy.getCardFCount()+cardCount);
			result=proxyDao.updateFCardCount(proxy);
			if(result>0){
				handleThreeClassBonus(proxyId,sellerName,cardCount);
			}

		}else if(type==1){
			proxy.setCardLTime(cardLTime);
			proxy.setCardLCount(proxy.getCardLCount()+cardCount);
			result=proxyDao.updateLCardCount(proxy);
		}
		return result;
	}
	/**
	 * 根据销售类型，变更代理的房卡数
	 */
	@Override
	public int updateCardCount(int proxyId, int type,int cardCount) {
		Proxy proxy= proxyDao.findProxyByID(proxyId);
		int result=-1;
		if (type==0) {
			proxy.setCardFCount(proxy.getCardFCount()+cardCount);
			result=proxyDao.updateFCardCount(proxy);
		}else if(type==1){
			proxy.setCardLCount(proxy.getCardLCount()+cardCount);
			result=proxyDao.updateLCardCount(proxy);
		}
		return result;
	}
	/**
	 * 根据销售类型，变更代理的房卡数，主动更卡，代理给玩家充卡引起的卡数变动
	 */
	@Override
	public int updateCardCountWithProxy(Proxy proxy, int type,int cardCount) {
		int result=-1;
		if (type==0) {
			proxy.setCardFCount(proxy.getCardFCount()+cardCount);
			result=proxyDao.updateFCardCount(proxy);
		}else if(type==1){
			proxy.setCardLCount(proxy.getCardLCount()+cardCount);
			result=proxyDao.updateLCardCount(proxy);
		}
		return result;
	}

	@Override
	public List<Proxy> getAllProxys(int startIndex,int length) {
		// TODO Auto-generated method stub
		return proxyDao.getAllProxys(null,null,startIndex, length);
	}

/**
 * 处理代理售卡的统一处理，全局唯一
 */
	@Override
	public int sellCardCount(int userId, int edUserId, int type, int toType, int count,int income) {
		Proxy proxy=findProxyByID(userId);
		Date cardLTime=proxy.getCardLTime();
		int result=-1;
		if (toType==0) {//处理被操作代理
			result=updateCardCount(userId,proxy.getUsername(),edUserId,type,count,cardLTime);
		    //代理不能给代理充卡
			//return -1;
		}else if (toType==1) {//玩家
//			Customer edCustommer=customerService.QueryCustomerById(edUserId);
//			if(edCustommer.getRecommendUid()!=0&&edCustommer.getRecommendUid()!=userId){
//				return ErrorDictionary.you_are_not_recommender.getValue();	
//			}
			result=customerService.updateCardCount(edUserId, count);
		}
		if(result>0){
			//赠送成功的情况下，修改自身数据
			proxy.setIncome(proxy.getIncome()+income);
			result=updateCardCount(proxy.getUserId(), type,-count);
			//给自己更新数据失败，扣除之前操作
			if (result<=0) {
				if (toType==0) {//处理被操作代理
					result=updateCardCount(edUserId,type,-count);
				}else if (toType==1) {//玩家
					result=customerService.updateCardCount(edUserId,-count);
				}	
			}
		}
		return result;
	}

	@Override
	public int editPassword(Proxy person) {
		// TODO Auto-generated method stub
		return proxyDao.editPassword(person);
	}

	@Override
	public List<Integer> getProxyIdsByRecommendID(int recommendPerson) {
		return proxyDao.getProxyIdsByRecommendID(recommendPerson);
	}

	@Override
	public List<Proxy> getPersonLikeUsername(String username) {
		return proxyDao.getPersonLikeUsername(username);
	}

	@Override
	public List<Proxy> getProxysByTime(int id, Date crTime, Date enTime) {
		return proxyDao.getProxys(id, crTime, enTime,-1,-1);
	}

	@Override
	public List<Proxy> getAllProxys(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return proxyDao.getAllProxys(startTime, endTime, -1, -1);
	}

	@Override
	public int getProxysCountByRecommendID(int recommendId) {
		return proxyDao.getProxysCountByRecommendID(recommendId);
	}

	@Override
	public int getAllProxysCount() {
		return proxyDao.getAllProxysCount();
	}

	@Override
	public List<Proxy> getProxysByIndex(int id, int startIndex, int length) {
		return proxyDao.getProxys(id, null, null,startIndex,length);
	}

	@Override
	public boolean handleThreeClassBonus(int id,String sellerName,int addCardCount) {
		Proxy proxy=findProxyByID(id);
		Map<Integer, Integer> bonusTrueMap=ConfigUtil.getBonus_class_map();
		if (bonusTrueMap.keySet().size()<=0){
			return false;
		}
		for (int i = ConfigUtil.bonusClassKey.length-1; i >=0; i--) {
			if(addCardCount>=ConfigUtil.bonusClassKey[i]){
				//要分享的利润总和
				int whole=bonusTrueMap.get(ConfigUtil.bonusClassKey[i]);
				         //创建分销记录
				DistributionRecord distributionRecord=new DistributionRecord(id,proxy.getUsername(), (int)(whole*ConfigUtil.getBonus_class_ratio()[0]), addCardCount, sellerName, new Date(System.currentTimeMillis()));
				//处理本体的三级分销利益==================
				proxy.addBonus(whole*ConfigUtil.getBonus_class_ratio()[0]);
				proxyDao.updateBonus(proxy);
				//处理上一级的三级分销利益=================
				Proxy recommendFirst=findProxyByID(proxy.getRecommendPerson());
				if (recommendFirst==null) {
					distributionService.addDistribution(distributionRecord);
					return true;
				}
				recommendFirst.addBonus(whole*ConfigUtil.getBonus_class_ratio()[1]);
				proxyDao.updateBonus(recommendFirst);
				         //处理分销记录
				distributionRecord.setSecondPersonId(recommendFirst.getUserId());
				distributionRecord.setSecondPerson(recommendFirst.getUsername());
				distributionRecord.setBonus2((int)(whole*ConfigUtil.getBonus_class_ratio()[1]));
				//处理上上级的三级分销利益=================
				Proxy recommendTwice=findProxyByID(recommendFirst.getRecommendPerson());
				if (recommendTwice==null) {
					distributionService.addDistribution(distributionRecord);
					return true;
				}
				
				recommendTwice.addBonus(whole*ConfigUtil.getBonus_class_ratio()[2]);
				proxyDao.updateBonus(recommendTwice);
				          //处理分销记录
				distributionRecord.setThirdPersonId(recommendTwice.getUserId());
				distributionRecord.setThirdPerson(recommendTwice.getUsername());
				distributionRecord.setBonus3((int)(whole*ConfigUtil.getBonus_class_ratio()[2]));
				//最终保存分销记录==========================
				distributionService.addDistribution(distributionRecord);
				return true;
			}
			
		}
		
/*		for (Entry<Integer, Integer> entry:bonusTrueMap.entrySet()) {
			if (entry.getKey()==addCardCount){
				//处理本体的三级分销利益==================
				proxy.addBonus(entry.getValue()*ConfigUtil.getBonus_class_ratio()[0]);
				proxyDao.updateBonus(proxy);
				//处理上一级的三级分销利益=================
				Proxy recommendFirst=findProxyByID(proxy.getRecommendPerson());
				if (recommendFirst==null) {
					return true;
				}
				recommendFirst.addBonus(entry.getValue()*ConfigUtil.getBonus_class_ratio()[1]);
				proxyDao.updateBonus(recommendFirst);
				//处理上上级的三级分销利益=================
				Proxy recommendTwice=findProxyByID(recommendFirst.getRecommendPerson());
				if (recommendTwice==null) {
					return true;
				}
				recommendTwice.addBonus(entry.getValue()*ConfigUtil.getBonus_class_ratio()[2]);
				proxyDao.updateBonus(recommendTwice);
				return true;
			}
		}*/
		return false;



	}

	@Override
	public boolean exchangeCardwithBonus(int id, int exchangeClass) {

		try {
			if(exchangeClass<0||exchangeClass>ConfigUtil.getBonusClassKey().length){
				return false;
			}
			Proxy proxy=findProxyByID(id);
			int exchangeCardNum=ConfigUtil.getBonusClassKey()[exchangeClass];
			int exchangeCardCost=ConfigUtil.getBonusPrice()[exchangeClass];
			if(proxy.getBonus()<exchangeCardCost){
				return false;
			}
			proxy.addCardFCount(exchangeCardNum);
			int preBonus=proxy.getBonus();
			proxy.addBonus(-exchangeCardCost);
			int result=proxyDao.exchangeBonus(proxy);
			if(result>0){
				exchangeRecordDao.addExchangeBonus(new ExchangeRecord( exchangeClass+1, preBonus, new Time(System.currentTimeMillis())));
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Proxy> getProxysByPioneerID(int pioneerId, int startIndex, int length) {
		return proxyDao.getProxysByPioneerID(pioneerId,startIndex,length);
	}

	@Override
	public int getProxysCountByPioneerID(int pioneerId) {
		return proxyDao.getProxysCountByPioneerID(pioneerId);
	}

	@Override
	public List<Integer> getProxyIdsByPioneerID(int pioneerPerson) {
		return proxyDao.getProxyIdsByPioneerID(pioneerPerson);
	}

	@Override
	public List<Proxy> getProxysByRecommendID(int recommendPerson) {
		// TODO Auto-generated method stub
		return proxyDao.getProxysByRecommendID(recommendPerson);
	}
	@Override
	public int clearCardCount(int proxyId, int type) {
		// TODO Auto-generated method stub
		if(type==0){
			return proxyDao.clearFCardCount(proxyId);	
		}
		return -1;
		
	}
	@Override
	public int removeProxy(int proxyId) {
			return proxyDao.removeProxy(proxyId);	
	}



}
