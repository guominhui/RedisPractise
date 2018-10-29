package com.service.mainProxy;

import java.util.Date;
import java.util.List;

import com.pojo.MainProxy;
import com.service.base.BaseService;


public interface MainProxyService extends BaseService<MainProxy>{
	MainProxy getyMainProxyById(int id);
	String addUser(MainProxy user);
	/**
	 * 处理总代赠送和销售
	 * @param user
	 * @param edUserId
	 * @param type   0销售。1赠送
	 * @param toType    0目标代理，1目标玩家
	 * @param count
	 * @return
	 */
    int sellCardCount(int userId, int edUserId, int type, int toType, int count, int income);
	/**
	 * 管理员给总代送卡
	 * @param mainProxyId
	 * @param sellType
	 * @param count
	 * @param cardLTime
	 * @return
	 */
    int updateCardCount(int mainProxyId, int sellType, int count, Date cardLTime);
	 List<MainProxy> getMainProxys(int startIndex, int length);
	 int getMainProxysCount();
	 int updateMainPorxy(MainProxy mainProxy);
	/*public Map<String,Object> getPowerByName(String name);*/
	/*public void save(User user);*/
}
