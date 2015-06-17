package com.example.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * web相关的工具类
 * @author anonymous
 *
 */
public class WebUtil {
	
	private static Logger logger = Logger.getLogger(WebUtil.class); 
	
	
	/**
	 * 跳转到结果页的通用方法
	 * @param request
	 * @param response
	 * @param message
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void forwardResult(HttpServletRequest request,
			HttpServletResponse response,String message) throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher("/result.jsp").forward(request, response);
	}
	
	
	/**
	 * 转发到其他页面
	 * @param request
	 * @param response
	 * @param name	转发时所存储信息的key
	 * @param message  转发时所存储信息的value
	 * @param path  转发路径
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void forwardUI(HttpServletRequest request,HttpServletResponse response,String name,Object message
			,String path) throws ServletException, IOException {
		request.setAttribute(name, message);
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	
	/**
	 * 使用反射对请求参数数据封装成bean的对象
	 * 将请求参数封装到对应的实体类对象中
	 */
	@SuppressWarnings("unchecked")
	public static <T> T request2Bean(HttpServletRequest request,Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
			Enumeration<String> params = request.getParameterNames();
			while(params.hasMoreElements()) {
				String name = params.nextElement();
				
				//request中参数含有非对象属性的情况的处理机制
				String methodName = "set"+StringUtils.capitalize(name);
				try {
					Method method = clazz.getMethod(methodName);
					if(method==null)
						continue;
				} catch (NoSuchMethodException e) {
					logger.debug("name is not "+clazz.getSimpleName()+"`s field");
					continue;
				}
				
				String value = request.getParameter(name);
				BeanUtils.copyProperty(t, name, value);
			}
		} catch (IllegalAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		return t;
	}
	
}
