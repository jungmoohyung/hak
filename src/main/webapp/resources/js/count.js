
function countup(idx){
	var x = new XMLHttpRequest();
	
	x.onreadystatechange = function(){
		
		//request finished and response is ready
		if (x.readyState === 4 && x.status	=== 200 ){
			console.log("정상작동");
		
		}else{
			//오류발생 : 403, 404
			console.log("서버 에라(403,404)");
		}
	};
	// 1,전송방식 2,요청방식 3,동기식방식
	x.open("get", "/member/count.jsp?idx=" + idx  ,true);
	x.send();
}



