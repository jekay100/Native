package com.example.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.example.dao.BaseDao;
import com.example.exception.DBException;
import com.example.utils.DateUtil;
import com.example.utils.JDBCUtil;
import com.example.utils.ReflectionUtil;

public class BaseDaoImpl<T> implements BaseDao {
	private static Logger logger = Logger.getLogger(BaseDaoImpl.class);
	QueryRunner runner = new QueryRunner();
	
	private Class<T> clazz = null;
	{
		clazz = ReflectionUtil.getClassGenricType(this.getClass());
	}
	
	
	public void saveOrUpdate(T t) {
		Object[] args = getSaveObjectSql(t);
		update((String) args[0], (Object[])args[1]);
	}
	
	
	/**
	 * 通用的增删改方法
	 * @param sql
	 * @param args
	 */
	public void update(String sql, Object ... args) {
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
			runner.update(conn, sql, args);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DBException(e);
		}
	}
	
	
	/**
	 * 通用的查询方法，返回单个对象
	 * @param sql
	 * @param args
	 * @return
	 */
	public T getInstance(String sql, Object ... args) {
		ResultSetHandler<T> hanlder = new BeanHandler<T>(clazz);
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
			return runner.query(conn, sql, hanlder, args);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DBException(e);
		} 
	}
	
	
	/**
	 * 通用的查询方法，返回对象的List集合
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getInstances(String sql, Object ... args) {
		ResultSetHandler<List<T>> handler = new BeanListHandler<T>(clazz);
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
			return runner.query(conn, sql, handler, args);
		} catch (SQLException e) {
			throw new DBException(e);
		} 
	}
	
	
	/**
	 * 特征值的查询，返回指定条件的查询结果
	 * @param sql
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <K> K getCount(String sql ,Object ... args) {
		Connection conn = null;
		K k = null;
		try {
			conn = JDBCUtil.getConnection();
			k = (K) runner.query(conn, sql, new ScalarHandler(), args);
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return k;
	}
	
	
	/**
	 * 向表中指量量添加数据
	 * @param sql
	 * @param params
	 */
	public void batchUpdate(String sql,Object[][] params){
		Connection conn = null;
		try{
			conn = JDBCUtil.getConnection();
			runner.batch(conn, sql, params);
		} catch(SQLException e){
			logger.debug(e.getMessage());
			throw new DBException(e);
		}
	}
	
	
	/**
	 * 获取保存对象的sql语句
	 * @param object
	 * @return
	 */
	public Object[] getSaveObjectSql(T t) {
		StringBuffer mBuf = new StringBuffer("");
		StringBuffer vBuf = new StringBuffer("");
		String tableName = t.getClass().getSimpleName().toLowerCase();
		mBuf.append("insert into ")
			.append(tableName)
			.append(" (");
		vBuf.append(" values(");
		List<Object> vList = new ArrayList<>();
		Field[] fields = t.getClass().getDeclaredFields();
		for(Field field : fields) {
			String fieldName = field.getName();
			Object fieldValue = ReflectionUtil.invokeGetter(t, fieldName);
			if(fieldValue == null) {
			} else if(fieldValue instanceof Date) {
				Date date = (Date) fieldValue;
				fieldValue = DateUtil.getDateTimeHtmStr(date);
			}
			mBuf.append(fieldName+",");
			vBuf.append("?,");
			vList.add(fieldValue);
		}
		mBuf.deleteCharAt(mBuf.length()-1).append(")");
		vBuf.deleteCharAt(vBuf.length()-1).append(")");
		String sql = mBuf.toString()+vBuf.toString();
		Object[] results = new Object[]{sql,vList.toArray()};
		return results;
	}

}
