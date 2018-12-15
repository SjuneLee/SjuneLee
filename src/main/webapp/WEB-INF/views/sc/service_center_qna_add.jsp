<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp" %>
	<style type="text/css">
		body {
			padding-top: 50px;
			padding-bottom: 20px;
		}
		
		.thumbnail { height: 350px; }
	</style>
	<!-- CKEditor 참조 -->
	<script src="//cdn.ckeditor.com/4.10.1/standard/ckeditor.js"></script>
</head>
 <body>
 	<!-- 페이지 상단 -->
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp" %>
	<!--//페이지 상단 -->
	
	<!-- 페이지 중단 -->
	<div class="container" style="padding:40px 50px;">
  		<h2 class="text-center">질문하기</h2>
  		<%-- <form class="form-horizontal" method="post" enctype="multipart/form-data"
  		action="${pageContext.request.contextPath}/service/service_center_qnaAdd_ok.do"> --%>
  		<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/service/service_center_qnaAdd_ok.do">  		
  			<input type="hidden" name="member_id" value="${loginInfo.id}">
  			
			<div class="form-group">
				<label for="subject" class="control-label" >글제목</label>
				<input type="text" class="form-control" id="subject" name="subject" placeholder="제목을 입력해주세요." />
			</div>
			<div class="form-group">
				<label class="control-label" for="user_name">작성자</label>
				<input class="form-control" id="user_name" name="user_name" type="text" placeholder="${loginInfo.name}" disabled />
			</div>
			<!-- <div class="form-group">
				<label class="control-label" for="file_add">첨부파일</label>
				<input class="form-control" id="file_add" name="file_add" type="file" multiple />
				<button class="btn btn-info" type="reset">초기화</button>
			</div> -->
			<div class="form-group">
				<label class="control-label" for="content">내용</label>
				<textarea name="content" id="content" class="ckeditor"></textarea>
			</div>
			<div class="form-group">
				<label class="control-label" for="secret">
					<input type="checkbox" id="secret" name="secret" value="Y" />비밀글로 등록
				</label>
			</div>
			<div class="form-group"> 
				<div class="text-center">
					<button class="btn btn-primary" type="submit">작성완료</button>
					<button class="btn btn-danger" type="button" onclick="history.back();">
					취소</button>					
				</div>
			</div>
		</form>
	</div>
	
	<!-- 페이지 중단 -->
	
	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp" %>
	<!--//페이지 하단 -->
</body>
</html>