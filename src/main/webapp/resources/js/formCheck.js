
function del_confirm(seqno){
	var rs = confirm('정말로 삭제하시겠습니까?');
	if (rs){
		location.href="boardDelete.bo?no="+seqno;
		
	}

}














function writeCheck(id){
	var x = new XMLHttpRequest();
	
	x.onreadystatechange = function(){

		if (x.readyState === 4 && x.status	=== 200 ){

			var rsp = x.responseText.trim();
			
			if(rsp == "0"){
				alert("로그인후 이용하세요")
				location.href="/board/List"
				return false;
			}else{
				location.href="/board/register"
			}
			
		}else{
			//오류발생 : 403, 404
			console.log("서버 에라(403,404)");
		}
	};
	// 1,전송방식 2,요청방식 3,동기식방식
	x.open("get", "/member/idSearch.jsp?id=" + id ,true);
	x.send();
}
/*function writeCheckwriteUpdate(id){
	var x = new XMLHttpRequest();
	
	x.onreadystatechange = function(){

		if (x.readyState === 4 && x.status	=== 200 ){

			var rsp = x.responseText.trim();
			
			if(rsp == "0"){
				alert("로그인후 이용하세요")
				location.href="memReg2Board.jsp?stat=1"
				return false;
			}else{
				location.href="BoardWrite.jsp"
			}
			
		}else{
			//오류발생 : 403, 404
			console.log("서버 에라(403,404)");
		}
	};
	// 1,전송방식 2,요청방식 3,동기식방식
	x.open("get", "/member/idSearch.jsp?id=" + id ,true);
	x.send();
}
*/
function writeCheck2(id){
	var x = new XMLHttpRequest();
	
	x.onreadystatechange = function(){

		if (x.readyState === 4 && x.status	=== 200 ){

			var rsp = x.responseText.trim();
			
			if(rsp == "0"){
				alert("본인이 아님미다.")
				location.href="document.referrer"
				return false;
			}else{
				location.href="memReg2Board.jsp"
			}
			
		}else{
			//오류발생 : 403, 404
			console.log("서버 에라(403,404)");
		}
	};
	// 1,전송방식 2,요청방식 3,동기식방식
	x.open("get", "/member/idSearch.jsp?id=" + id ,true);
	x.send();
}

	
	/*if (id == "joy"){
		console.log("이거다");
	}else{
		alert("로그인 후 이용해주세요");
		return false;
	}*/
	





/* 22,06.15
 id체크*/
function idCheck(){
	var id = document.forms["memRegForm"]["id"].value;
	
	var x = new XMLHttpRequest();
	
	
	x.onreadystatechange = function(){
		var msg = document.getElementById("idCheckMsg");
		console.log(id)
		//request finished and response is ready
		if (x.readyState === 4 && x.status	=== 200 ){
			console.log("ok");
			//문자 앞뒤 공백 삭제
			/*document.getElementById("isIdCheck").value = x.responseText.trim();*/
			var rsp = x.responseText.trim();
			document.getElementById("isIdCheck").value =rsp;
			
			if(rsp == "0"){
				msg.innerText = "사용가능";
				
			}else{
				msg.innerText = "사용불가";
			}
			
		}else{
			//오류발생 : 403, 404
			console.log("서버 에라(403,404)");
		}
	};
	// 1,전송방식 2,요청방식 3,동기식방식
	x.open("get", "/member/idDoubleCheck?id=" + id ,true);
	x.send();
}











/**
 * 2022년 6월 2일
 *작성자 : 정무형
 *내용 :회원가입 폼체크
 */
 
 
 
 
function formCheck(){
	
	//비밀번호 체크
	var pw = document.forms["memRegForm"]["pw"].value;
	
	/* alert(pw); */
		if(pw.length < 6 || pw.length > 10){
			alert('비밀번호를 확인하세요');
			return false;
		}
/* 	
	if(document.forms["memRegForm"]["name"].value.length) < 1) {
		alert("이름좀써라");
		return false;
	}
	 */
	/* return false; */
	var name = document.forms["memRegForm"]["name"].value;
	
	/* alert(name); */
		if(name.length < 1){
			document.getElementById("msg").innerHTML = "이름을 입력하세요"
			return false;
		}
	//성별체크
	var gender = document.forms["memRegForm"]["gender"].value;
		if(gender == "" ){
			alert("성별을 체크하세요");
			return false;
		}
	//취미 체크
	var hobby_length = document.forms["memRegForm"]["hobby"].length;
	/* alert(hobby_length); */
	/* 0번째의 체크값으로 확인 */
	for(var i=0;i<hobby_length;i++){
		
		if(document.forms["memRegForm"]["hobby"][i].checked){
			/* alert((i+1) + "개가 선택되었습니다."); */
			console.log( i + "번째 취미가 선택됨");
			break;
		}
	}
	if(i == hobby_length){
		return false;
	}
}

function inputDomain(){
	console.log("도메인선택함");
	var doma = document.forms["memRegForm"]["selDomain"].value;
	console.log("선택 옵션값 : " + doma);
	document.forms["memRegForm"]["domain"].value = doma;
	if (doma != ""){
		document.forms["memRegForm"]["domain"].readOnly = true;	
		document.forms["memRegForm"]["domain"].style.backgroundColor = 'gray';
	}else{
		document.forms["memRegForm"]["domain"].style.backgroundColor = 'white';
		document.forms["memRegForm"]["domain"].readOnly = false;
		document.forms["memRegForm"]["domain"].focus();
	}
}

















