package com.dao.gameRecord.mapper;

import java.util.List;
import java.util.Map;

import com.pojo.GameRecord;

public interface GameRecordMapper {
	List<GameRecord> findGameRecordByID(int roomId);
	List<GameRecord> getGameRecordsByPlayerId(Map<String, Object> paramMap);
}
