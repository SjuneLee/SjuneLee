<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	</style>
</head>
<body>
<!-- 페이지 상단 -->
<%@ include file="/WEB-INF/inc/nav/navbar.jsp" %>
<!--// 페이지 상단 -->

<!--// 페이지 중단 -->
<div class="container row-box">
	<form class="find_id col-md-6" name="find_id" id="find_id" role="form" method="post"
	style="padding:40px 50px;" action="${pageContext.request.contextPath}/member/find_id_ok.do">
		<h1>Find ID</h1>
		<div class="form-group">
			<label for="user_name">UserName</label>
			<input type="text" class="form-control" id="user_name1" name="user_name" placeholder="이름 입력하세요." maxlength="20">
		</div>
		<div class="form-group">
			<label for="user_tel">UserTel</label>
			<input type="text" class="form-control" id="user_tel" name="user_tel" placeholder="폰번호를 입력하세요.(-생략)" maxlength="20">
		</div>
 		<button type="submit" class="btn btn-success btn-block">FIND</button>
	</form>

	<form class="find_pw col-md-6" name="find_pw" id="find_pw" role="form" method="post"
	style="padding:40px 50px;" action="${pageContext.request.contextPath}/member/find_pw_ok.do"><!-- action >> main/member/findId_exception.jsp>>java페이지로 만들어야 함. -->
		<h1>Find Password</h1>
		<div class="form-group">
			<label for="user_name">UserID</label>
			<input type="text" class="form-control" id="user_id" name="user_id" placeholder="아이디를 입력하세요." maxlength="20">
		</div>
		<div class="form-group">
			<label for="user_email">email</label>
			<input type="email" class="form-control" id="user_email" name="user_email" placeholder="이메일 주소를 입력하세요." maxlength="30">
		</div>
		<button type="submit" class="btn btn-success btn-block">FIND</button>
	</form>
</div>
<%@ include file="/WEB-INF/inc/footer.jsp" %>
</body>
</html>