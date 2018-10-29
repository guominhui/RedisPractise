package com.service.customer;

import java.util.Date;
import java.util.List;

import com.pojo.Customer;
import com.pojo.MainProxy;

import net.sf.json.JSONObject;

public interface CustomerService {
	
	int updateCardCount(int customerId, int count);
	
	Customer QueryCustomerById(int id);
	
	int updateCardCountWithoutReport(int customerId, int count);
	
	int updateProxyId(int customerId, int proxyId);
	
	JSONObject queryOneCustomerInMobile(JSONObject jsonObject, String userIdString);
	
	int getRecommendPersonCount(int id);
}
