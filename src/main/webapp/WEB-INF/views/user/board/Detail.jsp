<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../loginchick.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
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
			<!-- 댓글 등록 폼 -->
			<div id="replyInput">
				<input id="comment"type="text" name="rcontent" class="rcontent" placeholder="댓글을 작성하세요" required>
				<button id="addReplyBtn" class="rbutton" name="rbutton" value="댓글작성">
				<center><p id="reply_err_msg" style="color:red;"></p></center>
			</div>
		
		
		
		<!-- 댓글 리스트 출력 영역 -->
		
		<div id="reply-list">
			<ul class="reply_ul">
				<li data-rno='74'>
					<div> 작성자 | 작성일자 | 댓글내용 </div>
				</li>
			</ul>
		</div>
		
		<!-- 댓글 페이지 리스트 출력 -->
		<div class="reply-page-list"></div>
		
		
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


<div id="reply_modal">
	<div class="login" style="margin: 50px; background-image:url('/img/burger.png');" onsubmit="">
		<div style="vertical-align: middle">
			<p>
				<button id="modalCloseBtn" style="position:absolute; top:0; right: 0;"
					>x</button>
			</p>
			
				<textarea name="content"></textarea>
				
				<button id="replyModifyBtn" class="w3-button w3-aqua sub">댓글수정</button> 
				<button id="replyDeleteBtn" class="w3-button w3-aqua sub">댓글삭제</button>
		</div>
	</div>
</div>






<script type="text/javascript" src="/resources/js/reply.js"></script>
<script>
/* 즉시 실행 함수 (IIFE) 
  (function(){
	 문장
 })
 */

 var seqno = '<c:out value="${board.seqNo}"/>';
 var id = '<c:out value="${loginUser.id}"/>'
$(document).ready(function(){
	console.log(replyService);
	
	console.log("=====================");
	console.log("Reply get LIST");
	
	
	var modal = $("#reply_modal");
	var modal_content = modal.find("textarea[name='content']")
	modal.hide();
	
	$("#modalCloseBtn").on("click", function(e){
		modal.hide();
	});
	
	$(".reply_ul").on("click", "li", function(e){
		var rno = $(this).data("rno");
		
		replyService.get(rno, function(data){
			console.log("REPLY GET DATA");
			console.log("댓글:"+rno+"번내용"+data.content);
			modal_content.val(data.content);
			modal.data("rno",data.seqno);
		}) 
		modal.show();
	});
 	
	
	/* 댓글 등록 버튼 */
	$("#addReplyBtn").on("click",function(e){
		var comment = document.getElementById("comment").value;
		
		var reply ={
				boardNo : seqno,
				id:id,
				comment : comment
		};
		replyService.add(reply,function(result){
			alert(result);
			document.getElementById("comment").value = "";
//			document.getElementById("newLine").innerHTML = "<li>"+reply.comment +"</li>";
			showList(1);
		});
	});
	
	
	
	
	
	
	
	/* 댓글 수정버튼 클릭 시 */
	$("#replyModifyBtn").on("click",function(e){
		console.log("댓글 수정 번호 :" + modal.data("rno"));
		console.log("댓글 수정 내용 :" + modal_content.val());
		
		var reply = {seqno : modal.data("rno"),
					content : modal_content.val()};
		
 		replyService.update(reply, function(result){
			alert(result);
			modal.hide();
			showList(1);
		}); 
	});
	
	/* 댓글 삭제 버튼 클릭시 */
	$("#replyDeleteBtn").on("click", function(e){
		var rno = modal.data("rno");
		console.log("댓글 삭제번호:" + rno);
		replyService.remove(rno, function(result){
			alert(result);
			modal.hide();
			showList(1);
		})
	});
	
	showList(1);
	
	function showList(page){
		replyService.getList({bno:seqno,page:1}, function(list){

			/* 댓글이 없는 경우 */
			if(list == null || list.length == 0){
				$(".reply_ul").html("");
				return;
				
			}
			/* 댓글이 있는 경우 */
			var str="";
			for(var i = 0, len=list.length || 0; i < len; i++){
				console.log(list[i]);
				str += "<li data-rno='"+list[i].seqno +"'><div class='replyRow'>" + list[i].rn + " | " +list[i].id ;
				str += " | " + list[i].wdate + " | " +list[i].content + "</div></li>";
			}
			
			$(".reply_ul").html(str);
		});
	}
	
	showReplyPage(18);
	
	/* 댓글 페이지 리스트 출력 */
	function showReplyPage(replyCnt){
		var currentPage = 1;
		
		var endPage = Math.ceil(currentPage/5.0)*5;
		var startPage = endPage - 4;
		
		console.log("endPage:"+endPage);
		
		var prev = startPage != 1;
		var next = false;
		
		if (endPage * 5 >= replyCnt){
			endPage = Math.ceil(replyCnt/5.0);	
		}
		if(endPage * 5 < replyCnt){
			next = true;
		}
		
		var str = "<ul class='pageUL' style='display: flex; list-style:none; justify-content: space-around;'>";
		
		if(prev){
			str += "<li class='page-link'>";
			str += "<a href='"+(startPage-1)+"'> 이전페이지</a></li>";
		}
		
		for (var i=startPage; i <= endPage; i++){
			var active = currentPage == i ? "active" : "";
			str += "<li class='page-link " + active + "'>";
			str += "<a href='" +i+ "'>" +i+ "</a></li>";
		}
		if (next){
			str += "<li class='page-link'>";
			str += "<a href='" +(endPage+1)+ "'>다음페이지</a></li>";
		}
		
		str += "</ul>";
		console.log(str);
		$(".reply-page-list").html(str);
		
	}
		

	
	
	
	
});
</script>

</body>
</html>
