package com.dao.operateRecord.Impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dao.operateRecord.OperateRecordDao;
import com.dao.operateRecord.mapper.OperateRecordMapper;
import com.dao.proxy.mapper.ProxyMapper;
import com.pojo.OperateRecord;
import com.pojo.Proxy;
import com.utils.SqlSessionUtil;

@Repository
public class OperateRecordDaoImpl implements OperateRecordDao{

	@Override
	public int addOperateRecord(OperateRecord opeRec) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			OperateRecordMapper opeRecMapper = sqlSession.getMapper(OperateRecordMapper.class);
			int result=opeRecMapper.addOperateRecord(opeRec);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
