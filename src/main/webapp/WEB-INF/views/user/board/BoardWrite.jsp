<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="header">
  <h1>CHICK BY CHICK</h1>
  <p>회원가입 하는곳입니다.</p>
</div>
<script src="/js/count.js"></script>
<%@ include file = "../menu.jsp" %>


<form enctype="multipart/form-data" action="/board/Reg" method="post" onsubmit="return check(this)">
<div>
<span>글작성</span>
</div>


<div>
<input type="text" name="title" required>제목
</div>


<div>
<input type="checkbox" name="open" value='Y'>공개 여부
</div>


<div>
<textarea style="width:100%;" placeholder="내용" name="content" rows="30" cols="300" required ></textarea>
</div>

<div>
<input type="file" name="filename">
</div>

<input type="hidden" value="0" name="count">
<input type="submit" value="글작성"> <input type="button" href="" value="취소">
</form>
<script>
function check(f){
	if (f.open.value == ""){
		alert("공개여부를 체크하세요");
		return false;
	}
	return true;
}
</script>


<%@ include file = "../footer.jsp" %>
<%@ include file = "../loginchick.jsp" %>