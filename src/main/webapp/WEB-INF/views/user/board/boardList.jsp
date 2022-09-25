<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%-- <%@ page import="dto.Board" %>
<%@ page import="java.util.*" %>
<%

List<Board> board = (List<Board>)request.getAttribute("board");

%> --%>



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@ include file="../header.jsp"%>
<link href="/resources/css/memReg2Board.css" rel="stylesheet">
<script src="/resources/js/count.js"></script>
<script src="/resources/js/formCheck.js"></script>
<div class="header">
	<h1>CHICK BY CHICK</h1>
	<p>게시판입니다.</p>
</div>

<%@ include file="../menu.jsp"%>

<div class="freeboard">
	<button onclick="click_hidden()" class="button1">누르고싶지?</button>


	<form name="search" method="post" action="/board/list">
		<select name="category">
			<option value="title"
				<c:if test="${pageMaker.cri.category == 'title'}">selected</c:if>>제목</option>
			<option value="content"
				<c:if test="${pageMaker.cri.category == 'content'}">selected</c:if>>내용</option>
			<option value="name"
				<c:if test="${pageMaker.cri.category == 'name'}">selected</c:if>>작성자</option>
		</select>	
	<%-- <input type="hidden" name = "rowPerPage" value="${pageMaker.cri.rowPerPage}">
		<input type="hidden" name = "currentPage" value="${pageMaker.cri.currentPage}"> --%>
		<input type="text" name="key" value="${pageMaker.cri.key }">
		<input type="submit" value="검색">
	<!-- 페이지당 레코드수 -->
		<select name="rowPerPage" onchange="goAction()">
			<c:forEach var="i" begin="3" end="30" step="3">
			<option value="${i}"
			<c:if test="${i == pageMaker.cri.rowPerPage}"> selected </c:if> > ${i}개씩</option>
			</c:forEach>
		</select>
	</form>
	
	
	<script type="text/javascript">
	function goAction(){
		document.forms["search"].submit();
	}
	
	</script>
	
	
	<table>
		<colgroup>
			<col span="1" style="background-color: white">
			<col span="1" style="background-color: #D6EEEE">
			<col span="1" style="background-color: hotpink">
			<col span="1" style="background-color: hotpink">
			<col span="3" id="col_hidden">
			<!-- style="visibility:collapse" -->
		</colgroup>

		<tr>
			<th>순번</th>
			<th class="title"><a href="javascript:title()">제목</a></th>
			<th>작성일자</th>
			<th>조회수</th>
			<th>이름</th>
		</tr>
<%-- 		<%
		for (Board b : board) {
		%> --%>
		<c:forEach items="${ board }" var="b">
		<tr onclick="location.href='/board/detail?no=${b.seqNo}'">
			<td>${b.getNo()}</td>
			<%-- <td><a href="javascript:dufwk(1, '<%=title%>', '<%=content%>',<%=idx%>)"><%=title%></a></td> --%>
			<td>${b.getTitle()}</td>
			<td>${b.getWdate()}</td>
			<td>${b.getCount()}</td>
			<td>${b.getName()}</td>
			<td>${b.getId()}</td>
			<%-- <td><%=rs.getString("id")%></td> --%>
		</tr>
		</c:forEach>
<%-- <%
		}
		%> --%>

	</table>
		  <p>총레코드갯수:${pageMaker.total}</p>
<%-- 	<c:if test="${id != null}">
	</c:if> --%>
<%-- 	<a href="javascript:writeCheck('${loginUser.id }')"
		class="write">글작성</a> --%>
	<a href="/board/modify"
		class="write">글작성</a>
	<div class="page">
		<c:if test ="${pageMaker.prev}">
		  <a href="/board/list?currentPage=${pageMaker.startPage-1}&rowPerPage=${pageMaker.cri.rowPerPage}">&laquo;</a>
		</c:if>
		  <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
		  	<a href="/board/list?currentPage=${num }&rowPerPage=${pageMaker.cri.rowPerPage}"
		  	class="${pageMaker.cri.currentPage == num ? "active" : "" }">${num}</a>
		   </c:forEach>
		  
		  <!-- <a class="active" href="#">2</a> -->
		  <c:if test="${pageMaker.next}">
		  <a href="/board/list?currentPage=${pageMaker.endPage+1}&rowPerPage=${pageMaker.cri.rowPerPage}">&raquo;</a>
		  </c:if>
	</div>
</div>
<div id="contentPagemodal">
	<div class="contentMain">
		<a type="button" onclick="ekedk()" style="float: right">X</a>
		<p id="contentTitle"></p>
		<span>--------------------</span>
		<p id="contentSpace"></p>
		<a type="button" href="edit.jsp"></a>
	</div>
</div>


<script>
	function dufwk(sw, title, content, idx) {
		if (sw == 1) {
			document.getElementById("contentPagemodal").style.display = "block";
			document.getElementById("contentTitle").innerHTML = title;
			document.getElementById("contentSpace").innerHTML = content;
			countup(idx);
		}
	}
	function ekedk() {
		document.getElementById("contentPagemodal").style.display = "none";
		history.go(0);
	}
</script>




<%@ include file="../footer.jsp"%>
<%@ include file="../loginchick.jsp"%>
</body>
</html>
