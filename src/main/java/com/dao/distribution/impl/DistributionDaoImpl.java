package com.dao.distribution.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dao.distribution.DistributionDao;
import com.dao.distribution.mapper.DistributionMapper;
import com.pojo.DistributionRecord;
import com.utils.SqlSessionUtil;
import com.utils.ValidateUtils;

@Repository
public class DistributionDaoImpl implements DistributionDao {

	@Override
	public List<DistributionRecord> findDistributionByID(int id,Date startTime,Date endTime,int startIndex,int length) {
		List<DistributionRecord> list=null;
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			DistributionMapper distributionMapper = sqlSession.getMapper(DistributionMapper.class);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("userId", id);
			if (ValidateUtils.isValidatedDate(startTime,endTime)) {
				paramMap.put("startTime", startTime);
				paramMap.put("endTime", endTime);	
			}
			if (ValidateUtils.isValidatedIndex(startIndex,length)) {
				paramMap.put("startIndex", startIndex);
				paramMap.put("length", length);	
			}
			list= distributionMapper.findDistributionByID(paramMap);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void addDistribution(DistributionRecord distributionRecord) {
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			DistributionMapper distributionMapper = sqlSession.getMapper(DistributionMapper.class);
			distributionMapper.addDistribution(distributionRecord);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
