package com.dao.customer;

import com.pojo.Customer;

public interface CustomerDao {
	 int updateCardCount(Customer user);
	 
	 Customer getCustomerByid(int id);
	 
	 int getPersonCount(int id);
	 
	 int updateProxyId(int customerId, int proxyId);
}
