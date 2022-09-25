<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<body>
<!-- 우측하단의 미니 메뉴바 -->
<div class="reset1">	
<div class="hide">
	<button onclick="">
	<i class="fa-solid fa-burger"></i>
	</button>
</div>
<%-- <%
	
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
	boolean isLogin = false;
	if ( name != null){
		isLogin = true;
	}
%> --%>
<%-- <% if(isLogin) { %> --%>
<c:set value="${loginUser}" var="loginuser" />
<c:if test="${loginuser != null }">
 <!-- 로그아웃용 아이콘 -->
<div class="menu1">
	<a href="/logout.do" type="button">
	<i class="fa-solid fa-plug-circle-xmark"></i>
	</a>
</div>
</c:if>
<c:if test="${loginuser eq null }">
<!-- 로그인용 아이콘 -->
<div class="menu1">
	<a href="javascript:ekewk(1)" type="button">
	<i class="fa-solid fa-key"></i>
	</a>
</div>
</c:if>
<!-- 마이페이지용 아이콘 -->
<div class="circle">
	<a href="/board/list" type="button">
		<i class="fa-brands fa-github-alt">
		</i>
	</a>	
</div>
</div>

<!-- 로그인 창 -->
<div class="modal">
	<div class="login" style="margin: 50px; background-image:url('/img/burger.png');" onsubmit="">
		<div style="vertical-align: middle">
			<p>
				<button style="position: absolute; top: 0; right: 0;"
					onclick="ekewk(0)">x</button>
			</p>
			<form action="/login" class="login2" method="post">
				<p><input type="text" class="log" name="id" placeholder="id" maxlength="10" required></p>
				<p><input type="password" class="log" name="password" placeholder="password" required maxlength="10"></p>
				<p><button type="submit">로그인</button></p>
			</form>

			<form class="">
				<a href="member/memReg2" class="w3-button w3-aqua sub">회원가입</a> 
				<a href="#" type="submit" class="w3-button w3-aqua sub">비밀번호 찾기</a>
			</form>
		</div>
	</div>
</div>


</body>

<!-- 움직이는거 실험용 -->
<script>
/* 토글 버튼의 스크립트 */
const toggleBtn = document.querySelector('.hide');
const menu = document.querySelector('.menu1');
const circle = document.querySelector('.circle');
/*클릭이 될때마다 지정된 함수를 호출해줘*/
toggleBtn.addEventListener('click', () => {
	menu.classList.toggle('active');
	circle.classList.toggle('active');
});
 /* 보드판 긴거 숨기기 */
function click_hidden(){
	var element = document.getElementById("col_hidden");
	   element.classList.toggle("col_hidden");
}
 /* 로그인창 */
function ekewk(sw) {
			const aa = document.getElementsByClassName("modal");
			if (1 == sw) {
				aa[0].style.display = "block";
			} else {
				aa[0].style.display = "none";
			}
		}
</script>