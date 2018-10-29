package com.dao.distribution.mapper;

import java.util.List;
import java.util.Map;

import com.pojo.DistributionRecord;

public interface DistributionMapper {
	
	List<DistributionRecord> findDistributionByID(Map<String, Object> paramMap);
	
	int addDistribution(DistributionRecord distributionRecord);
}
