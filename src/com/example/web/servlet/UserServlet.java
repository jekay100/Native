package com.example.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;
import com.example.utils.MimeType;
import com.example.utils.Page;
import com.example.utils.RequestMapping;
import com.example.utils.RequestMethod;
import com.example.utils.SmartFileUtil;
import com.example.utils.WebUtil;
import com.jspsmart.upload.SmartUploadException;


/**
 * user用户相关的请求处理器
 * @author anonymous
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserServlet.class);
	private UserService userService = new UserServiceImpl();
	
	/**
	 * 用户列表的
	 * @param request
	 * @param response
	 */
	@RequestMapping(method=RequestMethod.GET)
	public void list(HttpServletRequest request, HttpServletResponse response) {
		String pageNum = request.getParameter("pageNum");
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNum);
		} catch(Exception e){}
		Page<User> page = userService.getPage(pageNo, 10);
		request.setAttribute("page", page);
		try {
			WebUtil.forwardUI(request, response, "msg", "success", "/WEB-INF/views/user/list.jsp");
		} catch (ServletException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	//文件下载测试
	@RequestMapping(method=RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SmartUploadException {
		SmartFileUtil.downloadFile(this.getServletConfig(), request, response, "G://学易爱听写_v1.3.4.apk", MimeType.APK, "学易.apk");
	}
	
	//文件上传测试
	@RequestMapping(method=RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SmartUploadException {
		SmartFileUtil.upload(this.getServletConfig(), request, response, "/files");
	}
}
