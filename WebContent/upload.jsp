<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/common.jsp" %>
		<title>Insert title here</title>
	</head>
<body>
	 <form action="${ctx}/user/upload" method="post" enctype="multipart/form-data">
	 	上传图片:<input type="file" name="logo"/><br/>
	 	上传图片:<input type="file" name="logo"/><br/>
	 	上传图片:<input type="file" name="logo"/><br/>
	 	<input type="submit" value="提交"/>
	 </form>
</body>
</html>