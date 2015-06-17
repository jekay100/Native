<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/common.jsp" %><%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
		<title>用户管理列表</title>
	</head>
	<body>
		<table align="center">
			<tr>
				<th>ID</th>
				<th>用户名</th>
				<th>邮箱</th>
				<th>手机</th>
			</tr>
			<c:forEach items="${page.content}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>${user.phone}</td>
				</tr>
			</c:forEach>
		</table>
		<tags:page page="${page}" queryString="${queryString}"></tags:page>
	</body>
</html>