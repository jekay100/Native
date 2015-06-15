package com.example.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.example.exception.DBException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JAVA操作MySQL的工具类
 * 提供获取连接与关闭资源的方法
 * 
 * 08_:完成 事务过滤处理 
 * @author Administrator
 */

public class JDBCUtil {
	private static Logger logger = Logger.getLogger(JDBCUtil.class);
	//二级缓存
	private static DataSource ds = null ;
	//类似一级缓存
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
	static {
		logger.debug("加载c3p0配置文件 !");
		ds = new ComboPooledDataSource("c3p0");
	}
	
	//获取连接
	public static Connection getConnection()  {
		Connection conn = null;
		try {
			conn = threadLocal.get();
			if(conn==null) {
				conn = ds.getConnection();
				threadLocal.set(conn);
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DBException(e);
		}
		return conn;
	}
	
	//关闭连接
	public static void closeConn(Connection conn) {
		try {
			if(conn != null) {
				threadLocal.remove();
				conn.close();
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			throw new DBException(e);
		}
	}
}
