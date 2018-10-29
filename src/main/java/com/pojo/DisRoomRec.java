package com.pojo;

public class DisRoomRec {
	int id ;
	int gameUid;          
	int roomId;       
	int roomType ;
	String roomTypeStr ;
	int dismissType ;   
	String dismissTypeStr ;   
	int ownerId;//解散房间类型OwnerId 
	String ownerNickName;
	long dismissTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGameUid() {
		return gameUid;
	}
	public void setGameUid(int gameUid) {
		this.gameUid = gameUid;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getRoomType() {
		return roomType;
	}
	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}
	public int getDismissType() {
		return dismissType;
	}
	public void setDismissType(int dismissType) {
		this.dismissType = dismissType;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerNickName() {
		return ownerNickName;
	}
	public void setOwnerNickName(String ownerNickName) {
		this.ownerNickName = ownerNickName;
	}
	public long getDismissTime() {
		return dismissTime;
	}
	public void setDismissTime(long dismissTime) {
		this.dismissTime = dismissTime;
	}
	
public String getRoomTypeStr() {
		return roomTypeStr;
	}
	public void setRoomTypeStr(String roomTypeStr) {
		this.roomTypeStr = roomTypeStr;
	}
	public String getDismissTypeStr() {
		return dismissTypeStr;
	}
	public void setDismissTypeStr(String dismissTypeStr) {
		this.dismissTypeStr = dismissTypeStr;
	}
public String getRoomTypeMsg(int gameType) {
		
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
public String getDismissTypeMsg(int dismissType) {
	switch (dismissType) {
	case 0:
		return "强制解散";
	case 1:
		return "全体通过解散";
	case 2:
		return "超时解散";
	case 3:
		return "正常解散";
	default:
		return "未识别";
	}
}
 
}
