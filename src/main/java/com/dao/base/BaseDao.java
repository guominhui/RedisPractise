package com.dao.base;

import java.util.List;

import com.pojo.CardRecord;
import com.pojo.MainProxy;

public interface BaseDao<T> {
	int editPassword(T person);
	 List<T> getPersonLikeUsername(String username);
}
