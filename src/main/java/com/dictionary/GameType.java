package com.dictionary;

public enum GameType {
	MJ_XIAN(1),
	MJ_ZHUANZHUAN(2),
	MJ_DADIANZI(3),
	MJ_LaiYuan(4),
	MJ_HaErBin(5),
	MJ_ERREN(11),
	MJ_SANREN(12),
	POKER_DN(100),
	POKER_P3Z(110),
	POKER_Ddz(120),
	POKER_SD(121);
    private int value;

	GameType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
    

}
