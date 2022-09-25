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
${msg }	헬로우 월드
</h1>
<br>${m.id }<br>${m.name }<br>${board.title }
<img src="/img/지도.png">
<P>   </P>
</body>
</html>
