package com.example.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;
import com.example.utils.Page;
import com.example.utils.RequestMaping;
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
	private UserService userService = new UserServiceImpl();
	
	@RequestMaping(method=RequestMethod.GET)
	public void list(HttpServletRequest request, HttpServletResponse response) {
		String pageNum = request.getParameter("pageNum");
		int pageNo = Integer.parseInt(pageNum);
		Page<User> page = userService.getPage(pageNo, 10);
		request.setAttribute("page", page);
		try {
			WebUtil.forwardUI(request, response, "msg", "success", "/WEB-INF/views/user/list.jsp");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//文件下载测试
	@RequestMaping(method=RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SmartUploadException {
		SmartFileUtil.downloadFile(this.getServletConfig(), request, response, "G://1.jpg", "images/jpeg", "图片.jpg");
	
	}
	
	//文件上传测试
	@RequestMaping(method=RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SmartUploadException {
		SmartFileUtil.upload(this.getServletConfig(), request, response, "/files");
	}
}
