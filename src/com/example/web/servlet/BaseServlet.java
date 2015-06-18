package com.example.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.utils.RequestMaping;
import com.example.utils.RequestMethod;
import com.example.utils.WebUtil;

/**
 * servlet的基类封装类
 * 创建此基类Servlet子类及子类的方法说明:
 * 创建子类servlet时的路径映射需使用/xxx/*
 * 
 * 路径中的*号即访问的子路径跟方法名
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BaseServlet.class);
	
    public BaseServlet() {
        super();
    }
    
    /**
     * GET请求, 
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Method method = filter(request, response);
		if(method==null) { 
			String path = request.getRequestURL().toString();
			WebUtil.forwardUI(request, response, "message", path, "/error/404.jsp");;
			return ;
		}
		RequestMaping annotation = method.getAnnotation(RequestMaping.class);
		RequestMethod[] methods = annotation.method();
		if(methods!=null ){
			for(RequestMethod reqMethod : methods) {
				if("GET".equals(reqMethod.toString())) {
					try {
						method.invoke(this, request, response);
						return ;
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (IllegalArgumentException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
				} else {
					response.getWriter().println("<div style='background: #525D76; color: white;font-size: 30px;'>HTTP Status 405 - Request method 'GET' not supported!</div>");
				}
			}
		}
	}

	/**
	 * POST请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Method method = filter(request, response);
		if(method==null) { 
			String path = request.getRequestURL().toString();
			WebUtil.forwardUI(request, response, "message", path, "/error/404.jsp");
			return ;
		}
		RequestMaping annotation = method.getAnnotation(RequestMaping.class);
		RequestMethod[] methods = annotation.method();
		if(methods!=null ){
			for(RequestMethod reqMethod : methods) {
				if("POST".equals(reqMethod.toString())) {
					try {
						method.invoke(this, request, response);
						return ;
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (IllegalArgumentException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
					}
				} else {
					response.getWriter().println("<div style='background: #525D76; color: white;font-size: 30px;'>HTTP Status 405 - Request method 'GET' not supported!</div>");
				}
			}
		}
	}

	
	protected Method filter(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int lastIndexOf = uri.lastIndexOf("/");
		String methodName = uri.substring(lastIndexOf+1);
		Method method = null;
		try {
			method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage());
		} catch (SecurityException e) {
			logger.error(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
		return method;
	}
	
}
