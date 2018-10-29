package com.dao.disRoomRec.mapper;

import java.util.List;
import java.util.Map;

import com.pojo.DisRoomRec;
import com.pojo.DistributionRecord;

public interface DisRoomRecMapper {
	
	List<DisRoomRec> findDistRoomRecByRoomNo(Map<String, Object> paramMap);
	
}
