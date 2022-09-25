<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>doA</title>
</head>
<body>
<h1>
	결과페이지
</h1>
<P>${msg }</P>
<P>${member.id }${member.name }</P>
</body>
</html>
