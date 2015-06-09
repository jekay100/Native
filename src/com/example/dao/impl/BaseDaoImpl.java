package com.example.dao.impl;

import com.example.dao.BaseDao;
import com.example.utils.ReflectionUtils;

public class BaseDaoImpl<T> implements BaseDao {
	
	private Class<T> clazz = null;
	{
		clazz = ReflectionUtils.getClassGenricType(this.getClass());
	}
	
}
