<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<title>관리자 로그인</title>
<link rel="stylesheet" href="/css/admin.css">
<script>

	function formCheck() {
		var id = document.forms["login"]["id"].value;
		if (id == "") {
			alert("아이디를 입력하세요.");
			return false;
		}

		//비밀번호 체크 
		var pw = document.forms["login"]["pw"].value;
		if (pw == "") {
			alert("비밀번호를 입력하세요.");
			return false;
		} else {
		}
	}
	
	$(function(){
		$('html').click(function(e) {   
		document.querySelector("#msg").innerHTML = " ";
		}
	)});   
	
	 $(document).ready(function(){
		$("body").click(function(){
		var id = document.querySelector("#id");
		var pw = document.querySelector("#pw");
		var msg = document.querySelector("#msg");
		id.onfocus = function (e){
			msg.innerHTML = " ";
		};
		pw.onfocus = function (e){
			msg.innerHTML = " ";
		};
	}
	}); 
</script>
</head>
<body id = "body">
	<form class="form" name="login" method="post" action="login" onsubmit="return formCheck()">
		<div style="text-align: center;">
			<label>관리 시스템</label><br>
			<br>
			<input type="text" id="id" name="id" placeholder="아이디" ><br> 
			<input type="password" id="pw" name="pw" placeholder="비밀번호"><br>
			<p id="msg">${msg}</p>
			<br> <br> <input class="loginbtn" type="submit" value="로그인">
		</div>
	</form>
</body>
</html>