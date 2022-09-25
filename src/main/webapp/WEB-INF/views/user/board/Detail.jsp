<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../loginchick.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file = "../header.jsp" %>
<link href="/resources/css/boardDetail.css" rel="stylesheet">
<script src="/resources/js/count.js"></script>
<script src="/resources/js/formCheck.js"></script>
<div class="header">
  <h1>CHICK BY CHICK</h1>
  <p>게시판입니다.</p>
</div>

<%@ include file = "../menu.jsp" %>
<!-- 	<label>제목</label>
	<label>내용</label>
	<label>작성일자</label>
	<label>작성자</label>
	<label>조회수</label>
	<hr>
	<label>댓글내용</label>
	<label>댓글작성자</label>
	<label>댓글작성일시</label>
	 -->

<div class="freeboard">
	<c:set value="${board}" var="board"/>
		


	<c:if test="${ loginUser.id eq board.id }"> 
	<a class="button" href="boardDetail.bo?no=${ board.seqNo }&page=modify">수정</a>
	<a class="button" href="javascript:del_confirm('${board.seqNo}')" style="float:right">삭제</a>
	</c:if>
	<table>
		<colgroup>
			<col span="1" style="background-color:white">
			<col span="1" style="background-color:#D6EEEE">
			<col span="1" style="background-color:hotpink">
			<col span="1" style="background-color:hotpink">
		</colgroup>
		<tr>
			<td>제목</td>
			<td colspan="4">${board.title}</td>
		</tr>
		<tr><td>작성일자</td>
			<td>${board.wdate}</td>
			<td>작성자 : ${board.name}</td>
			<td>조회수</td>
			<td>${board.count}</td>
		</tr>
		<tr class="bcontent">
			<td>내용</td>
			<td colspan="4">${board.content}</td>
		</tr>
		<c:set value="${board.attachfile}" var="attachfile"/>
		<c:if test="${attachfile != null}">
		<c:forEach items="${attachfile}" var="file">
		<tr>
		<c:set value="${file.type }" var="filetype"/>
		<c:set value="${fn:substring(filetype, 0, fn:indexOf(filetype,'/')) }" var="type"/>
		<c:set value="${file.thumbnail.fileName }" var ="thumb_file"/>
		
		<c:choose> 
			<c:when test="${type eq 'image' }">
				<td>
				<img src="/upload/thumbnail/${thumb_file }">
				</td>
				<td colspan="4">
			</c:when>
			<c:when test="${type != 'image' }">
				<td colspan="5">
			</c:when>
		</c:choose> 
		<form name="filedown" method="post" action="/file/fileDown">
		<input type="hidden" name="filename" value="${file.fileName }">
		<input type="hidden" name="savefilename" value="${file.saveFileName }">
		<input type="hidden" name="filepath" value="${file.filePath }">
			<div>
				${file.fileName}(사이즈:${file.fileSize} bytes)
			</div>
				<input type="submit" value="다운로드">
		</form>
		</td>
		</tr>
					</c:forEach>
				</c:if>
		</table>
		<!-- 첨부파일 -->
		
<script>
function replyidcheck(id){
	var errmsg = document.getElementById("reply_err_msg");
	if (id == "null"){
		errmsg.innerHTML = "로그인하세요";
		return false;
	}
	
}
</script>

		<form class="rform" action="/board/replyWrite.jsp" method="post" onsubmit="return replyidcheck('${logid}')">
			<input type="text" name="rcontent" class="rcontent" placeholder="댓글을 작성하세요" required>
			<input type="hidden" name="bseqNo" value="${board.seqNo}">
			<input type="submit" class="rbutton" name="rbutton" value="댓글작성">
		</form>
		<center><p id="reply_err_msg" style="color:red;"></p></center>

		<div class="reply">
		<c:set value="${ board.reply }" var="reply" />
		<c:if test="${ reply != null }">
			<c:forEach items="${reply}" var ="r">
			<div style="float:left">
			${r.id}
			</div>
			<div style="float:right">
			${r.wdate}
			</div>
			<div>
			${r.comment}
			</div>
			<hr>

			</c:forEach>
		</c:if>
		</div>
</div>
<%@ include file = "../footer.jsp" %>
</body>
</html>