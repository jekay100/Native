package com.example.dao;

import java.io.Serializable;

/**
 * 持久层的基接口, 封装通用的接口方法 
 * @author anonymous
 * @param <T>
 * @param <PK>
 */
public interface BaseDao<T, PK extends Serializable> {
	
//	//保存对象
//	public void save(T t);
//	//删除对象
//	public void remove(T t);
//	//根据Id删除对应的对象
//	public void delete(PK id);
//	//更新某个对象
//	public void update(T t);
//	//根据id获取某个对象
//	public T getById(PK id);
//	//获取所有对象
//	public List<T> getAll();
//	public Page<T> getPage();
	
}
