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
		<button type="button" id="disconnectBtn">뒤로</button>
	</div>
	
	<div id="msgArea"></div>
	
	<div>
		<input type="text" id="msg" name="msg" placeholder="메세지를 입력하세요">
		<input type="button" id="sendBtn" value="전송">
	</div>

	<script>
	/* 아이피주소:톰캣포트/서버프로그램구현되잇는 경로  */
		var userid = '${loginUser.id}';
		var chatNo = "<c:out value='${chatNo}'/>"
		
		console.log("채팅방 번호 ="+chatNo);
		var ws;
		
		connect();
		
		function connect(){
			ws = new WebSocket("ws://192.168.10.135:8088/chatServer");
			
			ws.onopen = function(){
				console.log('연결생성');
				
				sendMsg("enter","");
				$('#msg').focus();
				
				};
//				ws.send(JSON.stringify(msg));
			
			
			ws.onmessage = function(e){
				console.log('서버로부터 받은 메세지:'+e.data);
				addMsg(e.data);
			};
			
			ws.onclose = function(){
				console.log('연결종료');
			};
			
		};
		
			
		$('#sendBtn').on("click",function(){
			sendMsg("chat", $('#msg').val());
			
			$('#msg').val('');
			$('#msg').focus();
		});
		
		$('#msg').keydown(function(){
			if(event.keyCode == 13){
				sendMsg("chat", $('#msg').val());
				$('#msg').val('');
				$('#msg').focus();
			}
		});
		
		//메세지전송
		function sendMsg(step,msg){
			var msg = {
				step : step,
				chatNo : chatNo,
				userid : userid,
				msg : msg
			};
			ws.send(JSON.stringify(msg));
		};
		
		//받은메세지 보여주기
		function addMsg(data){
			var tag = '<div style="magin-bottom:3px;">';
				tag += data;
				tag += '<span style="font-size:11px;color:#gray;">'
				tag += new Date().toLocaleTimeString();
				tag += '</span></div>';
				$('#msgArea').append(tag);
		}
		$('#disconnectBtn').click(function(){
			location.replace("/chatList");
			sendMsg("out","");
			//서버 @OnClose	호출
			ws.close();
		});
	</script>

</body>
</html>