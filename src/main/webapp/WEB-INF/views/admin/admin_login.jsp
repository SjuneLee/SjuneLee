<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
</head>
<body>
	<div class="pull-right" style="padding: 40px 50px;">
		<a href="${pageContext.request.contextPath}/index.do"><button class="btn btn-info">사용자페이지 이동</button></a>
	</div>
	<div class="container">
		<div class="col-md-offset-3">
		<form class="admin_login col-md-6" name="find_id" id="find_id" role="form" method="post" style="padding: 40px 50px;"
			action="${pageContext.request.contextPath}/admin/admin_login_ok.do">
			<h1>Admin Login</h1>
			<div class="form-group">
				<label for="admin_id">AdminID</label> <input type="text" class="form-control" id="admin_id" name="admin_id"
					placeholder="관리자 아이디를 입력하세요." maxlength="20">
			</div>
			<div class="form-group">
				<label for="admin_pw">AdminPassword</label> <input type="password" class="form-control" id="admin_pw" name="admin_pw"
					placeholder="비밀번호를 입력하세요." maxlength="20">
			</div>
			<div class="checkbox">
				<label><input type="checkbox" value="" checked>아이디
					저장하기</label>
			</div>
			<button type="submit" class="btn btn-success btn-block">LOGIN</button>
		</form>
		</div>
	</div>
</body>
</html>