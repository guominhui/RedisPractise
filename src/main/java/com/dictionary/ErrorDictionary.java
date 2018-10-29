package com.dictionary;

public enum ErrorDictionary {
	
	
	
	not_your_pionner(-10001),
	you_are_not_recommender(-10002),
	game_uid_notExist(-10003),//
	proxy_uid_hasExist(-10004),//
	date_tranform_err(-10005),//数据转换异常
	you_not_priority(-10006),//您无此权限
	just_support_to_customer(-10007);//仅支持对玩家的操作
	
	
	
	private int value;

	ErrorDictionary(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}


}
