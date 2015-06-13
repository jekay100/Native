package com.example.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * servlet的基类封装类
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BaseServlet.class);
	
    public BaseServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 请求参数中带method, 可以使一个servlet处理多个不同的请求
		 */
		String methodName = request.getParameter("method");
		Method method = null;
		try {
			method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (NoSuchMethodException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

}
