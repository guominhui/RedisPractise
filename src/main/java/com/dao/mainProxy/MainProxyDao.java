package com.dao.mainProxy;

import java.util.Date;
import java.util.List;

import com.dao.base.BaseDao;
import com.pojo.Customer;
import com.pojo.MainProxy;


public interface MainProxyDao extends BaseDao<MainProxy> {
      /*public User getUserByid(int id);
        public Map<String,Object> getPowerByName(String name);
        public void addUser(User user);
        public void save(User user);*/
      MainProxy getUserByid(int id);
	 int addUser(MainProxy user);
	 int updateCardLTime(MainProxy mainProxy);
	 int updateCardLCount(MainProxy mainProxy);
	 int updateCardFCount(MainProxy mainProxy);
	 List<MainProxy> getMainProxys(int startIndex, int length);
	 int getMainProxysCount();
	 int updateMainProxy(MainProxy mainProxy);
}
