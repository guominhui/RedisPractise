package com.dao.disRoomRec.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dao.disRoomRec.DisRoomRecDao;
import com.dao.disRoomRec.mapper.DisRoomRecMapper;
import com.pojo.DisRoomRec;
import com.utils.SqlSessionUtil;

@Repository
public class DisRoomRecDaoImpl implements DisRoomRecDao{

	@Override
	public List<DisRoomRec> findDistRoomRecByRoomNo(int roomNo) {
		// TODO Auto-generated method stub
		List<DisRoomRec> list=null;
		try {
			SqlSession sqlSession = SqlSessionUtil.getSqlSession();
			DisRoomRecMapper disRoomRecMapper = sqlSession.getMapper(DisRoomRecMapper.class);
			//使用map承载数据，防止随后的扩展
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("roomId", roomNo);
			list= disRoomRecMapper.findDistRoomRecByRoomNo(paramMap);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
