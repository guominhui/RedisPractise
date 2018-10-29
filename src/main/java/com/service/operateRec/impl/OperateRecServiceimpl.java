package com.service.operateRec.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.operateRecord.OperateRecordDao;
import com.dao.proxy.ProxyDao;
import com.dao.proxyDel.ProxyDelDao;
import com.dictionary.OperateConstant;
import com.pojo.OperateRecord;
import com.pojo.Proxy;
import com.service.operateRec.OperateReService;

@Service
public class OperateRecServiceimpl implements OperateReService{

	@Autowired
	OperateRecordDao operateRecordDao;

	@Autowired
	ProxyDelDao proxyDelDao;

	@Autowired
	ProxyDao proxyDao;

	@Override
	public int addOperate(OperateRecord operateRecord,int proxyId) {

		operateRecordDao.addOperateRecord(operateRecord);
		if(operateRecord.getOpeType()==OperateConstant.delete.getValue()){
			if(proxyId!=0){
				Proxy proxy=proxyDao.findProxyByID(proxyId);
				if(proxy!=null){
					return proxyDelDao.addProxy(proxy);	
				}
				
			}
		}
		return 0;
	}

}
