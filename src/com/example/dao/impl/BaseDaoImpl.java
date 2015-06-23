package com.example.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.example.dao.BaseDao;
import com.example.exception.DBException;
import com.example.utils.DateUtil;
import com.example.utils.Direction;
import com.example.utils.JDBCUtil;
import com.example.utils.Page;
import com.example.utils.ReflectionUtil;


/**
 * 持久层基类，封装通用方法
 * @author anonymous
 * @param <T>
 * @param <PK>
 */
public abstract class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	private static Logger logger = Logger.getLogger(BaseDaoImpl.class);
	QueryRunner runner = new QueryRunner();
	
	protected Class<T> clazz = null;
	{
		clazz = ReflectionUtil.getClassGenricType(this.getClass());
	}
	
	
	@Override
	public void save(T t) {
		Object[] args = getSaveObjectSql(t);
		this.update((String) args[0], (Object[])args[1]);
	}

	
	@Override
	public void delete(PK id) {
		String tableName = clazz.getSimpleName().toLowerCase();
		String sql = "delete from "+tableName+" where id=?";
		this.update(sql, id);
	}

	
	@Override
	public void update(T t) {
		Object[] args = this.getUpdateObjectSql(t);
		this.update((String) args[0], (Object[])args[1]);
	}
	
	
	@Override
	public T getById(PK id) {
		String sql = this.getSelectSql();
		sql += " where id=?";
		return this.getInstance(sql, id);
	}

	
	@Override
	public List<T> getAll() {
		String sql = this.getSelectSql();
		return this.getInstances(sql);
	}

	@Override
	public T getByProperty(String propertyName, Object propertyValue) {
		String sql = this.getSelectSql();
		sql += " where "+propertyName+"=?";
		return this.getInstance(sql, propertyValue);
	}

	@Override
	public List<T> getsByProperty(String propertyName, Object propertyValue) {
		String sql = this.getSelectSql();
		sql += " where "+propertyName+"=?";
		return this.getInstances(sql, propertyValue);
	}

	@Override
	public List<T> getsByProperty(String propertyName, Object propertyValue,
			LinkedHashMap<String, Direction> orders) {
		String sql = this.getSelectSql();
		sql += " where "+propertyName+"=?";
		StringBuffer buf = new StringBuffer("");
		if(orders!=null && orders.size()>0) {
			buf.append(" order by ");
			for(String key : orders.keySet()) {
				buf.append(key).append(" ")
				.append(orders.get(key).toString())
				.append(",");
			}
			buf.deleteCharAt(buf.length()-1);
		}
		sql += buf.toString();
		return this.getInstances(sql, propertyValue);
	}

	@Override
	public Page<T> getPage(Integer page, Integer pageSize,
			LinkedHashMap<String, Direction> orders, String propertyName,
			Object propertyValue) {
		String tableName = clazz.getSimpleName().toLowerCase();
		StringBuffer buf = new StringBuffer("select count(id) from "+tableName);
		System.out.println(buf.toString());
		String tempSql = buf.toString();
		Long total = null;
		if(propertyName!=null) {
			buf.append(" where ")
				.append(propertyName)
				.append("=?");
			//获取条件查询中的总记录数
			total = this.getCount(buf.toString(), propertyValue);
		} else {
			total = this.getCount(buf.toString());
		}
		
		//添加排序条件
		if(orders!=null && orders.size()>0) {
			buf.append(" order by ");
			for(String key : orders.keySet()) {
				buf.append(key).append(" ")
				.append(orders.get(key).toString())
				.append(",");
			}
			buf.deleteCharAt(buf.length()-1);
		}
		
		int index = (page-1)*pageSize;
		//添加分页参数
		buf.append(" limit ").append(index).append(",").append(pageSize);
		String sql = this.getSelectSql();
		sql = buf.toString().replace(tempSql, sql);
		
		List<T> content = null;
		if(propertyName!=null) {
			content = this.getInstances(sql, propertyValue);
		} else {
			content = this.getInstances(sql);
		}
		return new Page<>(content, page, total, pageSize);
	}
	
	
	@Override
	public Page<T> getPage(Integer page, Integer pageSize) {
		return getPage(page, pageSize, null, null, null);
	}
	

	@Override
	public int updatePropertyById(Long id, String propertyName,
			Object propertyValue) {
		String tableName = clazz.getSimpleName().toLowerCase();
		StringBuffer buf = new StringBuffer("");
		buf.append("update ").append(tableName)
			.append(" set ")
			.append(propertyName)
			.append("=? ")
			.append("where id=?");
		return this.update(buf.toString(), propertyValue, id);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getPropertyValueById(Long id, String propertyName) {
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = null;
		ResultSet rs = null;
		String tableName = clazz.getSimpleName().toLowerCase();
		try {
			sql = "select "+propertyName+" from "+tableName+" where id=?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			rs.next();
			return (E) rs.getObject(propertyName);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DBException(e);
		} finally {
			JDBCUtil.closeConn(conn, ps, rs);
		}
	}

	
	@Override
	public List<T> like(String key, String property) {
		String sql = this.getSelectSql();
		sql += " where "+property+" like ?";
		return this.getInstances(sql, "%"+key+"%");
	}
	
	
	@Override
	public Set<T> getRandomData(Integer count) {
		return null;
	}
	

	@Override
	public T getRandomEntity() {
		return null;
	}
	
	
	/**
	 * 通用的增删改方法
	 * @param sql
	 * @param args
	 */
	protected int update(String sql, Object ... args) {
		Connection conn = null;
		try {
			conn = JDBCUtil.getConnection();
			return runner.update(conn, sql, args);
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
	protected T getInstance(String sql, Object ... args) {
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
	protected List<T> getInstances(String sql, Object ... args) {
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
	protected <K> K getCount(String sql ,Object ... args) {
		Connection conn = null;
		K k = null;
		try {
			conn = JDBCUtil.getConnection();
			k = (K) runner.query(conn, sql, new ScalarHandler(), args);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			JDBCUtil.closeConn(conn);
		}
		return k;
	}
	
	
	/**
	 * 向表中批量添加数据
	 * @param sql
	 * @param params
	 */
	protected void batchUpdate(String sql,Object[][] params){
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
	protected Object[] getSaveObjectSql(T t) {
		StringBuffer mBuf = new StringBuffer("");
		StringBuffer vBuf = new StringBuffer("");
		String tableName = t.getClass().getSimpleName().toLowerCase();
		mBuf.append("INSERT INTO ")
			.append(tableName)
			.append(" (");
		vBuf.append(" VALUES(");
		List<Object> vList = new ArrayList<>();
		Method[] methods = t.getClass().getMethods();
		for(Method method : methods) {
			String methodName = method.getName();
			if(methodName.startsWith("get") && !methodName.equals("getClass")) {
				String fieldName = StringUtils.uncapitalize(methodName.substring(3));
				if(fieldName.equals("id")) {
					continue;
				}
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
		}
		mBuf.deleteCharAt(mBuf.length()-1).append(")");
		vBuf.deleteCharAt(vBuf.length()-1).append(")");
		String sql = mBuf.toString()+vBuf.toString();
		logger.info("save object sql="+sql);
		Object[] results = new Object[]{sql,vList.toArray()};
		return results;
	}
	
	
	/**
	 * 获取更新对象的sql语句
	 * @param object
	 * @return
	 */
	protected Object[] getUpdateObjectSql(T t) {
		StringBuffer mBuf = new StringBuffer("");
		String tableName = t.getClass().getSimpleName().toLowerCase();
		mBuf.append("update ")
		.append(tableName)
		.append(" set ");
		List<Object> vList = new ArrayList<>();
		Object id = null;
		Method[] methods = t.getClass().getMethods();
		for(Method method : methods) {
			String methodName = method.getName();
			if(methodName.startsWith("get") && !methodName.equals("getClass")) {
				String fieldName = StringUtils.uncapitalize(methodName.substring(3));
				if(fieldName.equals("id")) {
					id = ReflectionUtil.invokeGetter(t, fieldName);
					continue;
				}
				Object fieldValue = ReflectionUtil.invokeGetter(t, fieldName);
				if(fieldValue == null) {
				} else if(fieldValue instanceof Date) {
					Date date = (Date) fieldValue;
					fieldValue = DateUtil.getDateTimeHtmStr(date);
				}
				mBuf.append(fieldName+"=?,");
				vList.add(fieldValue);
			}
		}
		mBuf.deleteCharAt(mBuf.length()-1);
		mBuf.append(" where id=?");
		vList.add(id);
		String sql = mBuf.toString();
		logger.info("update object sql="+sql);
		Object[] results = new Object[]{sql,vList.toArray()};
		return results;
	}
	
	
	/**
	 * 获取查询的sql语句
	 * @return
	 */
	public String getSelectSql() {
		StringBuffer mBuf = new StringBuffer("");
		mBuf.append("select ");
		Method[] methods = clazz.getMethods();
		for(Method method : methods) {
			String methodName = method.getName();
			if(methodName.startsWith("get") && !methodName.equals("getClass")) {
				String fieldName = StringUtils.uncapitalize(methodName.substring(3));
				mBuf.append(fieldName+",");
			}
		}
		mBuf.deleteCharAt(mBuf.length()-1);
		String tableName = clazz.getSimpleName().toLowerCase();
		mBuf.append(" from ")
			.append(tableName);
		String sql = mBuf.toString();
		logger.info("select object sql="+sql);
		return sql;
	}
}
