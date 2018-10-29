package com.service.distribution.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.distribution.DistributionDao;
import com.pojo.DistributionRecord;
import com.service.distribution.DistributionService;

@Service
public class DistributionServiceImpl implements DistributionService{

	@Autowired
	public DistributionDao distributionDao;

	@Override
	public List<DistributionRecord> findDistributionByID(int id,Date startTime,Date endTime,int startIndex,int length) {

		return distributionDao.findDistributionByID(id,startTime,endTime,startIndex,length);
	}

	@Override
	public void addDistribution(DistributionRecord distributionRecord) {

		distributionDao.addDistribution(distributionRecord);

	}

}
