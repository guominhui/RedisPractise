package com.dao.exchangeRecord.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.dao.distribution.mapper.DistributionMapper;
import com.dao.exchangeRecord.ExchangeRecordDao;
import com.dao.exchangeRecord.mapper.ExchangeRecordMapper;
import com.pojo.DistributionRecord;
import com.pojo.ExchangeRecord;
import com.utils.SqlSessionUtil;

@Component
public class ExchangeRecordDaoImpl implements ExchangeRecordDao {

	@Override
	public int addExchangeBonus(ExchangeRecord exchangeRecord) {

		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			ExchangeRecordMapper exchangeRecordMapper = sqlSession.getMapper(ExchangeRecordMapper.class);
			exchangeRecordMapper.addExchangeBonus(exchangeRecord);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
}
