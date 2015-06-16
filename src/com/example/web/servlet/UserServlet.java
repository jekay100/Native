package com.example.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.utils.RequestMaping;
import com.example.utils.RequestMethod;


/**
 * user用户相关的请求处理器
 * @author anonymous
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	@RequestMaping(url="/list",method=RequestMethod.POST)
	public void list(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("list");
		
	}
}
