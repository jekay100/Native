package com.example.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.example.dao.BaseDao;
import com.example.exception.DBException;
import com.example.utils.JDBCUtils;
import com.example.utils.ReflectionUtils;

public class BaseDaoImpl<T> implements BaseDao {
	QueryRunner runner = new QueryRunner();
	
	private Class<T> clazz = null;
	{
		clazz = ReflectionUtils.getClassGenricType(this.getClass());
	}
	

	/**
	 * 通用的增删改方法
	 * @param sql
	 * @param args
	 */
	public void update(String sql, Object ... args) {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			runner.update(conn, sql, args);
		} catch (SQLException e) {
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
			conn = JDBCUtils.getConnection();
			return runner.query(conn, sql, hanlder, args);
		} catch (SQLException e) {
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
			conn = JDBCUtils.getConnection();
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
			conn = JDBCUtils.getConnection();
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
			conn = JDBCUtils.getConnection();
			runner.batch(conn, sql, params);
		} catch(SQLException e){
			throw new DBException(e);
		}
	}
	
	
	/**
	 * 获取保存对象的sql语句
	 * @param object
	 * @return
	 */
	protected String getSaveObjectSql(T t) {
		StringBuffer sql = new StringBuffer("");
		String tableName = t.getClass().getSimpleName().toLowerCase();
		sql.append("insert into ")
			.append(tableName)
			.append(" (");
		
		
		
		
		
		
		
		
		return sql.toString();
	}

}
