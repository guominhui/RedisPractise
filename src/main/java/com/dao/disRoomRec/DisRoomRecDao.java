package com.dao.disRoomRec;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pojo.DisRoomRec;

public interface DisRoomRecDao {

	List<DisRoomRec> findDistRoomRecByRoomNo(int roomNo);
}