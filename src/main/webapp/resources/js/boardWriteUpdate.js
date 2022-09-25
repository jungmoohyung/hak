function boardWriteUpdate(seqno){
	
	var x = new XMLHttpRequest();
	
	
	x.onreadystatechange = function(){
		
		if (x.readyState === 4 && x.status	=== 200 ){
			console.log("ok");
			var title = x.responseText.trim(title);
			var content = x.responseText.trim(content);
			console.log(title);
			console.log(content);
			if(content != null){
			document.getElementById("updateWrite").value = title;
			document.getElementById("updateWriteContent").value = content;
			location.href="BoardWriteUpdate.jsp"
			msg.innerText = "사용가능";
				
			}else{
				msg.innerText = "사용불가";
				location.href="memReg2Board.jsp"
			}
			
		}else{
			//오류발생 : 403, 404
			console.log("서버 에라(403,404)");
		}
	};
	// 1,전송방식 2,요청방식 3,동기식방식
	x.open("get", "/jsp/boardWriteUpdatecheck.jsp?seqno=" + seqno ,true);
	x.send();
}