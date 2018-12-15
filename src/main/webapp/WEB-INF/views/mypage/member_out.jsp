<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
body {
	padding-top: 50px;
	padding-bottom: 20px;
}

.thumbnail {
	height: 350px;
}
</style>
</head>

<body>
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
	
	<!-- 마이페이지 bar -->
   	<div class="container">
   		<div class="page-header"><h1 class="text-center">회원 탈퇴</h1></div>
   		<!-- 메뉴 -->
		<ul class="nav nav-tabs text-center" style="border-radius: 0.5em; background-color:black;">
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/mypage.do"><span class="glyphicon glyphicon-paperclip"></span> 클립보드</a></li>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/end_plan.do"><span class="glyphicon glyphicon-calendar"></span> 완성된 일정</a>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/ing_plan.do"><span class="glyphicon glyphicon-calendar"></span> 계획중인 일정</a>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/like_plan.do"><span class="glyphicon glyphicon-heart"></span> 좋아한 일정</a>
	   		<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_edit.do"><span class="glyphicon glyphicon-user"></span> 개인정보 수정</a></li>
			<li class="active col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_out.do"><span class="glyphicon glyphicon-user"></span> 회원탈퇴</a></li>	
    	</ul>
    	<br/>
    	<!-- // 메뉴 -->	
	    <br/>
	    <!-- 가입폼 시작 -->
		<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/mypage/member_out_ok.do">
			
			<div class="form-group">
				<label class="col-sm-3 control-label" for="user_id">아이디</label>
				<div class="col-sm-6">
				<input type="text" name="user_id" id="user_id" class="form-control" placeholder="아이디를 입력하세요."/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-3 control-label" for="user_pw">비밀번호</label>
				<div class="col-sm-6">
				<input type="password" name="user_pw" id="user_pw" class="form-control" placeholder="비밀번호를 입력하세요."/>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-6 col-sm-offset-3">
				<button type="submit" class="btn btn-warning btn-block">탈퇴하기</button>
				</div>
			</div>
		</form>
		<!-- 가입폼 끝 -->
		
	</div>
	<!-- // 마이페이지 bar -->
	
	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp"%>
	<!--//페이지 하단 -->
</body>
</html>