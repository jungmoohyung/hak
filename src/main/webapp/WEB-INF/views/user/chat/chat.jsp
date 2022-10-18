<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<c:set value ="${loginUser }" var="loginuser"/>
	<div>
		<textarea rows="5" cols="30" id="msgArea"></textarea>
		<br>
		<input type="text" id="targetUser" placeholder="대화상대id">
		<input type="text" id="chatMsg" placeholder="메세지를 입력하세요">
		<input type="button" id="sendBtn" value="전송">
	</div>

	<script>
	/* 아이피주소:톰캣포트/서버프로그램구현되잇는 경로  */
		let url = "ws://localhost:8011/mychat";
		var userid = '${loginUser.id}';
		
		var ws;
		
		connect();
		
		function connect(){
			ws = new WebSocket(url);
			
			ws.onopen = function(){
				console.log('연결생성');
				var msg = {
					type:"register",
					userid:userid
				};
				ws.send(JSON.stringify(msg));
				
			};
			
			ws.onmessage = function(e){
				console.log('서버로부터 받은 메세지:'+e.data);
				addMsg(e.data);
			};
			
			ws.onclose = function(){
				console.log('연결종료');
			};
			
		}
		
		$(function(){
			
			$('#sendBtn').on("click",function(){
				
				var chat = $("#msgArea").val();
				chat = chat + "\n["+userid+"]"+$("#chatMsg").val();
				$("#msgArea").val(chat);
				sendMsg();
				$("#chatMsg").val("");
				
			});

		});
		
		//메세지전송
		function sendMsg(){
			var msg = {
				type : "chat",
				target : $("#targetUser").val(),
				message : $("#chatMsg").val(),
				userid : userid
			};
			ws.send(JSON.stringify(msg));
		}
		function addMsg(msg){
			var chat = $('#msgArea').val();
			chat = chat + "\n" + msg;
			$('#msgArea').val(chat);
		}
	</script>

</body>
</html>