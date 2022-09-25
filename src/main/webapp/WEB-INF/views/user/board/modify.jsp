<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../header.jsp" %>
<%-- <%
	String ids = (String)session.getAttribute("id");
	String seqno = request.getParameter("no");
	PreparedStatement pstmt = conn.prepareStatement("select content, title, open from board where seqno = ?");
	
	System.out.println(ids);
	System.out.println(seqno);
	String content = "";
	String title = "";
	String open = "";
try{
	pstmt.setString(1, seqno);
	ResultSet rs = pstmt.executeQuery();

	
	if(rs.next()){
	content = rs.getString("content");
	title = rs.getString("title");
	open = rs.getString("open");
%>
 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set value="${board}" var="board"/>

<div class="header">
  <h1>CHICK BY CHICK</h1>
  <p>회원가입 하는곳입니다.</p>
</div>
<script src="/js/count.js"></script>
<%@ include file = "../menu.jsp" %>


<form action="/board/register" enctype="multipart/form-data" method="post" name="upload">
<div>
<span>글작성${board.title }</span>
</div>


<div>
<%-- <input type="text" name="title" required value="<%= title %>"> --%>
<input type="text" name="title" required value="${board.title }">
</div>


<div>
<input type="checkbox" name="open" value='Y' <c:if test="${board.open eq 'Y' }"> checked </c:if>>공개 여부 <!-- 원래는 라디오스로 함 -->
<%-- <input type="checkbox" name="open" value='Y' <%if(rs.getString("open").equals("Y")) out.print("checked"); %>>공개 여부 <!-- 원래는 라디오스로 함 --> --%>

</div>


<div>
<%-- <textarea style="width:100%;" name="content" rows="30" cols="300" required><%= content %></textarea> --%>
<textarea style="width:100%;" name="content" rows="30" cols="300" required>${board.content }</textarea>
</div>

<c:set value="${board.attachfile}" var="attachfile"/>
<div>
	<c:choose>
		<c:when test ="${empty attachfile}">
			<input type="file" name="filename">
		</c:when>
		<c:when test="${!empty attachfile }">
			<c:forEach items="${attachfile}" var="file">
				<c:set value="${file.type}" var="type"/>
				<c:set value="${fn:substring(type, 0, fn:indexOf(type,'/')) }" var="filetype"/>
				
				<div id = "fileSector">
				
					<c:if test="${filetype eq 'image' }">
						<c:set value="${file.thumbnail.fileName}" var="thumb_file"/>
						<img src="/upload/thumbnail/${thumb_file }">
					</c:if>
					${file.fileName}(사이즈:${file.fileSize })
					<input type="button" value="삭제" onclick="fileDel('${file.attSeqNo}','${file.saveFileName}','${file.filePath}',
					'${thumb_file }')">
				
				</div>
				
			</c:forEach>
		</c:when>
	</c:choose>
</div>
<script>
function fileDel(attSeqNo,saveFileName,filePath,thumb_file){
	
	var ans = confirm("정말로 삭제하시겠습니까?");
	
	if (ans){
		var x = new XMLHttpRequest();
		x.onreadystatechange = function(){
			if(x.readyState === 4 && x.status === 200){
				
				var tag = document.getElementById("fileSector");
				
				
				if (x.responseText.trim() === "0"){
					alert("파일 삭제에 실패 하였습니다.");
				} else {
					alert("파일 삭제에 완료 하였습니다.");
					tag.innerHTML = "<input type='file' name='filename'>";
				}
				
			}else{
			console.log('에러코드는:' + x.status);
				
			}
		
		};
	}
	
	//방식,매핑정보,동기방식
	x.open("POST", "/file/fileDel", true);
	x.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	x.send("no="+attSeqNo+"&savefilename="+saveFileName+"&filepath="+filePath+"&thumb_filename="+thumb_file);

}

</script>



<input type="hidden" value="0" name="count">
<%-- <input type="hidden" value="<%= seqno %>" name="seqno">--%>
<input type="hidden" value="${board.seqNo }" name="seqno">

<input type="submit" value="수정"> <input type="button" value="취소">
</form>
<%-- <%
	}else{
	out.print("0");
	}
	
} catch (SQLException e) {
	e.printStackTrace();
} finally {

	if(pstmt != null)
		try {
			pstmt.close();
		} catch (Exception e) {}
	if(conn != null)
		try {
			conn.close();
		} catch (Exception e) {}
}
%> --%>

<%@ include file = "../footer.jsp" %>
<%@ include file = "../loginchick.jsp" %>