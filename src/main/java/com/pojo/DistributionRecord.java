package com.pojo;

import java.util.Date;

public class DistributionRecord {

	int firstPersonId;
	String firstPerson;
	int secondPersonId;
	String secondPerson;
	int thirdPersonId;
	String thirdPerson;
    int bonus1;
    int bonus2;
    int bonus3;
    int sellCardNum;
    String sellerName;
    Date sellTime;
    long sellTimeInMb;
    
	
	public DistributionRecord() {
		super();
	}
	public DistributionRecord(int firstPersonId, String firstPerson, int bonus1, int sellCardNum, String sellerName,
			Date sellTime) {
		super();
		this.firstPersonId = firstPersonId;
		this.firstPerson = firstPerson;
		this.bonus1 = bonus1;
		this.sellCardNum = sellCardNum;
		this.sellerName = sellerName;
		this.sellTime = sellTime;
	}
	public String getFirstPerson() {
		return firstPerson;
	}
	public void setFirstPerson(String firstPerson) {
		this.firstPerson = firstPerson;
	}
	public String getSecondPerson() {
		return secondPerson;
	}
	public void setSecondPerson(String secondPerson) {
		this.secondPerson = secondPerson;
	}
	public String getThirdPerson() {
		return thirdPerson;
	}
	public void setThirdPerson(String thirdPerson) {
		this.thirdPerson = thirdPerson;
	}
	public int getBonus1() {
		return bonus1;
	}
	public void setBonus1(int bonus1) {
		this.bonus1 = bonus1;
	}
	public int getBonus2() {
		return bonus2;
	}
	public void setBonus2(int bonus2) {
		this.bonus2 = bonus2;
	}
	public int getBonus3() {
		return bonus3;
	}
	public void setBonus3(int bonus3) {
		this.bonus3 = bonus3;
	}
	public int getSellCardNum() {
		return sellCardNum;
	}
	public void setSellCardNum(int sellCardNum) {
		this.sellCardNum = sellCardNum;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public Date getSellTime() {
		return sellTime;
	}
	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}
	public long getSellTimeInMb() {
		return sellTimeInMb;
	}
	public void setSellTimeInMb(long sellTimeInMb) {
		this.sellTimeInMb = sellTimeInMb;
	}
	public int getFirstPersonId() {
		return firstPersonId;
	}
	public void setFirstPersonId(int firstPersonId) {
		this.firstPersonId = firstPersonId;
	}
	public int getSecondPersonId() {
		return secondPersonId;
	}
	public void setSecondPersonId(int secondPersonId) {
		this.secondPersonId = secondPersonId;
	}
	public int getThirdPersonId() {
		return thirdPersonId;
	}
	public void setThirdPersonId(int thirdPersonId) {
		this.thirdPersonId = thirdPersonId;
	}
    


}
