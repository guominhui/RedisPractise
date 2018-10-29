package com.pojo;

import java.util.Date;

public class OperateRecord {
	private int recId;//记录id
	private int opeType;//操作类型
	private int opeUserId;//操作执行人
	private int beOpeUserId;//被操作人Id
	private int beOpeParam;//被操作者Id
	private Date createTime;//操作产生时间

	public OperateRecord(int opeType, int opeUserId, int beOpeUserId, int beOpeParam, Date createTime) {
		super();
		this.opeType = opeType;
		this.opeUserId = opeUserId;
		this.beOpeUserId = beOpeUserId;
		this.beOpeParam = beOpeParam;
		this.createTime = createTime;
	}
	public int getRecId() {
		return recId;
	}
	public void setRecId(int recId) {
		this.recId = recId;
	}
	public int getOpeType() {
		return opeType;
	}
	public void setOpeType(int opeType) {
		this.opeType = opeType;
	}
	public int getOpeUserId() {
		return opeUserId;
	}
	public void setOpeUserId(int opeUserId) {
		this.opeUserId = opeUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getBeOpeUserId() {
		return beOpeUserId;
	}
	public void setBeOpeUserId(int beOpeUserId) {
		this.beOpeUserId = beOpeUserId;
	}
	public int getBeOpeParam() {
		return beOpeParam;
	}
	public void setBeOpeParam(int beOpeParam) {
		this.beOpeParam = beOpeParam;
	}

}
