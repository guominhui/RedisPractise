package com.dao.distribution;

import java.util.Date;
import java.util.List;

import com.pojo.DistributionRecord;

public interface DistributionDao {

	List<DistributionRecord> findDistributionByID(int id, Date startTime, Date endTime, int startIndex, int length);

	void addDistribution(DistributionRecord distributionRecord);
}
