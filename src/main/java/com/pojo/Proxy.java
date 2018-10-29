package com.pojo;

import java.util.Date;

public class Proxy extends Person{
	
	
	String address;
	String contactName;
	String contactPhone;
	String emergencyContact;
	int recommendPerson;
	int pioneerPerson;
	long createTimeInMb;
	private int bonus;
	int distributionClass;
	int gameUid;//仅在内存中使用，为游戏用户的Id
	
	public Proxy() {
		super();
	}
	public Proxy(String username,String address, String contactName, String contactPhone,
			 int recommendPerson,int pioneerPerson,Date createTime) {
		super();
		this.username=username;
		this.address = address;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.recommendPerson = recommendPerson;
		this.pioneerPerson = pioneerPerson;
		this.createTime=createTime;
	}
	public Proxy(String username,String address, String contactName, String contactPhone,
			 int gameUid,int recommendPerson,int pioneerPerson,Date createTime) {
		super();
		this.username=username;
		this.address = address;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.gameUid = gameUid;
		this.recommendPerson = recommendPerson;
		this.pioneerPerson = pioneerPerson;
		this.createTime=createTime;
	}
	
	public Proxy(int userId,String username,String address, String contactName, String contactPhone) {
		super();
		this.userId=userId;
		this.username=username;
		this.address = address;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
	}
	public Proxy(int userId,String username,String address, String contactName, String contactPhone,int gameUid) {
		super();
		this.userId=userId;
		this.username=username;
		this.address = address;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.gameUid = gameUid;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public int getRecommendPerson() {
		return recommendPerson;
	}
	public void setRecommendPerson(int recommendPerson) {
		this.recommendPerson = recommendPerson;
	}
	public long getCreateTimeInMb() {
		return createTimeInMb;
	}
	public void setCreateTimeInMb(long createTimeInMb) {
		this.createTimeInMb = createTimeInMb;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public void addBonus(double bonus) {
		this.bonus+=bonus;
	}
	public int getPioneerPerson() {
		return pioneerPerson;
	}
	public void setPioneerPerson(int pioneerPerson) {
		this.pioneerPerson = pioneerPerson;
	}
	public int getDistributionClass() {
		return distributionClass;
	}
	public void setDistributionClass(int distributionClass) {
		this.distributionClass = distributionClass;
	}
	public int getGameUid() {
		return gameUid;
	}
	public void setGameUid(int gameUid) {
		this.gameUid = gameUid;
	}
	
	

}
