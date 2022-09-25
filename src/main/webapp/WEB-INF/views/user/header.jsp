<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
	String root = request.getContextPath();
	/* out.print(root);
	System.out.println("stat값:" + request.getParameter("stat")); */
	String stat = request.getParameter("stat");
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/resources/css/member2.css">
<link href="/resources/css/w3.css" rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Blaka&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<link rel="stylesheet"href="https://fonts.googleapis.com/css?family=Inconsolata">
<link rel="preconnect" href="https://fonts.googleapis.com">
<script src="https://kit.fontawesome.com/8593beab87.js" crossorigin="anonymous"></script>
<script>
function init(){
		const aa = document.getElementsByClassName("modal");
		//const stat = document.getElementsByName("stat");
		const msg = document.getElementsByName("msg");
		const name = document.getElementsByName("name");
		var alert_msg;
		var modal_pop;
		//alert(name[0].value);
		if(msg[0].value == "loginOK"){
			alert_msg = name[0].value + "님 로그인이 되었습니다.";
			modal_pop = false;
			}
		if(msg[0].value == "loginFail"){ 
			alert_msg="로그인 정보가 없습니다.";
			modal_pop = true;
			//aa[0].style.display = "block";

		}
		if(msg[0].value == "memberOK"){ 
			alert_msg="들어올땐 맘대로지만";
			modal_pop=true;
			//aa[0].style.display = "block";
		}
		
		if(msg[0].value != "null") {alert(alert_msg);}
		if(modal_pop == true) {aa[0].style.display = "block";}
		
}
</script>
</head>
<body onload='init()'>
<input type="hidden" name="stat" value="<%= request.getParameter("stat")%>">
<input type="hidden" name="msg" value="<%= request.getAttribute("msg")%>">
<input type="hidden" name="name" value="${loginUser.name }">