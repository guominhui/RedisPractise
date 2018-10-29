package com.service.gameRecord.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.gameRecord.GameRecordDao;
import com.dictionary.GameType;
import com.pojo.GameRecord;
import com.service.gameRecord.GameRecordService;
import com.utils.CopyUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class GameRecordServiceImpl implements GameRecordService{

	@Autowired
	private GameRecordDao gameRecordDao;

	@Override
	public List<GameRecord> handGameRecordMsgWithRoomId(int roomId,int roomIndex) {
		List<GameRecord> gameRecordPost=new ArrayList<GameRecord>();
		List<GameRecord> gameRecords=gameRecordDao.findGameRecordByID(roomId);
		if(gameRecords.size()==0){
			return gameRecords;
		}else{
			for(int i=0;i<gameRecords.size();i++){
				if(i!=roomIndex){
					continue;
				}
				JSONArray jsonArray=JSONArray.fromObject(gameRecords.get(i).getResultAttr());
				for(int j=0;j<jsonArray.size();j++){
					GameRecord gameRecord=CopyUtil.depthClone(gameRecords.get(i));
					JSONObject jsonObject=JSONObject.fromObject(jsonArray.get(j));
					gameRecord.setGameUidSingle(gameRecord.getGameUid()+"00"+(j+1));
					gameRecord.setCurGameCount(j+1);
					long end_time=jsonObject.getLong("end_time");
					gameRecord.setSingleCreateTime(end_time);
					int score_1=jsonObject.getInt("score_1");
					gameRecord.setScore1(score_1);
					int score_2=jsonObject.getInt("score_2");
					gameRecord.setScore2(score_2);
					int score_3=jsonObject.optInt("score_3",0);
					gameRecord.setScore3(score_3);
					int score_4=jsonObject.optInt("score_4",0);
					gameRecord.setScore4(score_4);
					int score_5=jsonObject.optInt("score_5",0);
					gameRecord.setScore5(score_5);
					int score_6=jsonObject.optInt("score_6",0);
					gameRecord.setScore6(score_6);
					int score_7=jsonObject.optInt("score_7",0);
					gameRecord.setScore7(score_7);
                    gameRecord.setGameTypeMsg(getGameTypeMsg(gameRecord.getGameType()));
					gameRecord.setResultAttr("");
					gameRecordPost.add(gameRecord);
				}
			}	
		}
		
		return gameRecordPost;
	}

	@Override
	public String getGameTypeMsg(int gameType) {
		
		switch (gameType) {
		case 1:
			return "推到胡麻将";
		case 2:
			return "转转麻将";
		case 3:
			return "打点子麻将";
		case 4:
			return "涞源麻将";
		case 5:
			return "哈尔滨麻将";
		case 11:
			return "二人麻将";
		case 12:
			return "三人麻将";
		case 100:
			return "拼十";
		case 110:
			return "拼三张";
		case 120:
			return "斗地主";
		case 121:
			return "三代";
		default:
			return "未识别";
		}
	}

	@Override
	public List<GameRecord> findGameRecordByPlayerId(int playerId,Date startTime,Date endTime,int startIndex,int length){
		List<GameRecord> gameRecordPost=new ArrayList<GameRecord>();
		List<GameRecord> gameRecords=gameRecordDao.findGameRecordByPlayerId(playerId, startTime, endTime, startIndex, length);
		if(gameRecords.size()==0){
			return gameRecords;
		}else{
			for(int i=0;i<gameRecords.size();i++){
				if("".equals(gameRecords.get(i).getResultAttr())){
					continue;
				}
				
				JSONArray jsonArray=JSONArray.fromObject(gameRecords.get(i).getResultAttr());
				for(int j=0;j<jsonArray.size();j++){
					GameRecord gameRecord=CopyUtil.depthClone(gameRecords.get(i));
					JSONObject jsonObject=JSONObject.fromObject(jsonArray.get(j));
					gameRecord.setGameUidSingle(gameRecord.getGameUid()+"00"+(j+1));
					gameRecord.setCurGameCount(j+1);
					long end_time=jsonObject.getLong("end_time");
					gameRecord.setSingleCreateTime(end_time);
					int score_1=jsonObject.getInt("score_1");
					gameRecord.setScore1(score_1);
					int score_2=jsonObject.getInt("score_2");
					gameRecord.setScore2(score_2);
					int score_3=jsonObject.optInt("score_3",0);
					gameRecord.setScore3(score_3);
					int score_4=jsonObject.optInt("score_4",0);
					gameRecord.setScore4(score_4);
					int score_5=jsonObject.optInt("score_5",0);
					gameRecord.setScore5(score_5);
					int score_6=jsonObject.optInt("score_6",0);
					gameRecord.setScore6(score_6);
					int score_7=jsonObject.optInt("score_7",0);
					gameRecord.setScore7(score_7);
                    gameRecord.setGameTypeMsg(getGameTypeMsg(gameRecord.getGameType()));
					gameRecord.setResultAttr("");
					gameRecordPost.add(gameRecord);
				}
			}	
		}
		
		return gameRecordPost;
	
	}

}
