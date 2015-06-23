package com.example.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import com.example.dao.UserDao;
import com.example.dao.impl.UserDaoImpl;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.utils.Direction;
import com.example.utils.Encodes;
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

	
	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}
	
	
	@Override
	public void save(User user) {
		String passwordEncode = this.passwordEncode(user.getPassword(), user.getUsername());
		user.setPassword(passwordEncode);
		userDao.save(user);
	}
	
	
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	
	/**
	 * 用户密码的盐值加密密
	 * @param password
	 * @param halt
	 * @return
	 */
	private String passwordEncode(String password, String halt) {
		password = Encodes.encodeByMD5(password);
		halt = Encodes.encodeByMD5(halt);
		return Encodes.encodeByMD5(password+halt);
	}
}
