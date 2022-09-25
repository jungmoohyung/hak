<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file = "../header.jsp" %>
<body>

<script src="/resources/js/formCheck.js"></script>
<div class="header">
  <h1>CHICK BY CHICK</h1>
  <p>${msg}</p>
</div>

<%@ include file = "../menu.jsp" %>
<!-- 회원가입 -->

<div class="row">
  <div class="leftcolumn">
    <div class="card" id="form">
		<h2 style="text-align:center">회원가입 양식</h2>
		<hr>
			<form name="memRegForm" method="post" action="/member/register" onsubmit="return formCheck()">
				<div class="input1">
				
					<p> 아이디 </p>
					<input type="text" placeholder="아이디" name="id" maxlength="10" required onchange="idCheck()">
					<input type="text" id="isIdCheck">
					<p id="idCheckMsg"></p>
					
					<p>비밀번호</p>
					<input type="password" placeholder="비밀번호" name="pw"maxlength="10" required>
					
					<p>이름</p>
					<input type="text" name="name" placeholder="이름">
					<span id="msg" ></span>
				</div>
				
				<div style="margin:10px">
					<p> 성별 </p>
					<input type="radio" name="gender" value="F"> 여자
					<input type="radio" name="gender" value="m"> 남자<br>
				</div>
				
				<div>
					<fieldset style="width:35%; text-align: center; ">
					<legend>취미</legend>
					<input type="checkbox" name="hobby" value="tennis"> 테니스 
					<input type="checkbox" name="hobby" value="tennis2"> 테니스
					<input type="checkbox" name="hobby" value="tennis3"> 테니스
				</fieldset>
				</div>
				<div class="email">
					<p>이메일주소</p>
					<input type="text" placeholder="이메일" name="eid">@
					<input type="text" placeholder="도메인주소" name="domain">
					<select name="selDomain" onchange="inputDomain()">
						<option value="">직접입력</option>
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="daum.net">daum.net</option>
					</select>
				</div>
				<p> 자기소개 </p>
				<textarea rows="5" cols="50" placeholder="하고싶은말(적당히)" name="intro"></textarea><br>
				<input type="submit" value="회원가입">
				<input type="reset" value="리셋">
			</form>
		</div>
	</div>
</div>



<%@ include file= "../footer.jsp" %>

<%@ include file= "../loginchick.jsp" %>


</body>
</html>
