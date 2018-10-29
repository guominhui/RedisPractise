package com.dao.customer.mapper;

import com.pojo.Customer;

public interface CustomerMapper {
	   /** 
     * 注意要和Employeer.xml的方法名对应 
     */
       Customer findCustomerByID(int id);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    int addCustomer(Customer Customer);
  
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    void deleteEmployeer(String Customer);
  
    /**
     * 注意要和Employeer.xml的方法名对应 
     */
    int updateCustomer(Customer Customer);
    /**
     * 注意要和Employeer.xml的方法名对应 
     */
    int updateCardCount(Customer Customer);
    /** 
     * 注意要和Employeer.xml的方法名对应 
     */
    Customer findCustomerByRecommandID(String recommandId);
    
    int editPassword(String newPassword);
    
    int getPersonCount(int id);
    
    int updateCustomerGameUid(int customerId, int proxyId);
    
}
