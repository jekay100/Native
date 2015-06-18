package com.example.service.impl;

import java.util.LinkedHashMap;

import com.example.dao.UserDao;
import com.example.dao.impl.UserDaoImpl;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.Direction;
import com.example.utils.Page;

/**
 * user用户相关接口的具体实现类
 * 所有需要使用的通用方法需要重写使用userDao调用才可
 * 特殊方法需另定义
 * @author anonymous
 *
 */
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements
		UserService {
	private UserDao userDao = new UserDaoImpl();
	
	
	@Override
	public Page<User> getPage(Integer page, Integer pageSize,
			LinkedHashMap<String, Direction> orders, String propertyName,
			Object propertyValue) {
		return userDao.getPage(page, pageSize, orders, propertyName, propertyValue);
	}

	
	@Override
	public Page<User> getPage(Integer page, Integer pageSize) {
		return userDao.getPage(page, pageSize);
	}

}
