package com.service.gameRecord;

import java.util.Date;
import java.util.List;

import com.pojo.GameRecord;

public interface GameRecordService {

	List<GameRecord> handGameRecordMsgWithRoomId(int roomId, int roomIndex);
	List<GameRecord> findGameRecordByPlayerId(int playerId, Date startTime, Date endTime, int startIndex, int length);
    String getGameTypeMsg(int gameType);
}
