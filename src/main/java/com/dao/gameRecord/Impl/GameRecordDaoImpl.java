package com.dao.gameRecord.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dao.gameRecord.GameRecordDao;
import com.dao.gameRecord.mapper.GameRecordMapper;
import com.pojo.GameRecord;
import com.utils.SqlSessionUtil;
import com.utils.ValidateUtils;

@Repository
public class GameRecordDaoImpl implements GameRecordDao{


	@Override
	public List<GameRecord> findGameRecordByID(int roomId) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			GameRecordMapper opeRecMapper = sqlSession.getMapper(GameRecordMapper.class);
			List<GameRecord> result=opeRecMapper.findGameRecordByID(roomId);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<GameRecord>();
	}

	@Override
	public List<GameRecord> findGameRecordByPlayerId(int playerId,Date startTime,Date endTime,int startIndex,int length) {
		try {
			SqlSession	sqlSession = SqlSessionUtil.getSqlSession();
			GameRecordMapper opeRecMapper = sqlSession.getMapper(GameRecordMapper.class);
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("playerId", playerId);
			if (ValidateUtils.isValidatedDate(startTime,endTime)) {
				paramMap.put("startTime", startTime.getTime()/1000);
				paramMap.put("endTime", endTime.getTime()/1000);	
			}
			if (ValidateUtils.isValidatedIndex(startIndex,length)) {
				paramMap.put("startIndex", startIndex);
				paramMap.put("length", length);	
			}
			List<GameRecord> result=opeRecMapper.getGameRecordsByPlayerId(paramMap);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<GameRecord>();
	}


}
