package com.pojo;

import java.util.Date;

public class ExchangeRecord {
	int recordId;
	int exchangeType;
	int preBonus;
	Date exchangeTime;

	public ExchangeRecord(int exchangeType, int preBonus, Date exchangeTime) {
		super();
		this.exchangeType = exchangeType;
		this.preBonus = preBonus;
		this.exchangeTime = exchangeTime;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(int exchangeType) {
		this.exchangeType = exchangeType;
	}

	public int getPreBonus() {
		return preBonus;
	}

	public void setPreBonus(int preBonus) {
		this.preBonus = preBonus;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

}
