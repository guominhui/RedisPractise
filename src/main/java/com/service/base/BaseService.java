package com.service.base;

import java.util.List;

public interface BaseService<T> {
	int editPassword(T person);
	List<T> getPersonLikeUsername(String username);
}
