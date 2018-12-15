<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<!doctype html>
<html lang='ko'>
<head>
	<meta charset='utf-8' />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<!-- Bootstrap + jQuery -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="http://code.jquery.com/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<!--// Bootstrap + jQuery -->
</head>
<body>
	<div class="container">
		<h1 class="page-header">회원 상세 보기</h1>
		
		<!-- 조회결과를 출력하기 위한 표 시작 -->
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="info text-center" width="130">회원번호</th>
					<td>${item.id}</td>
				</tr>
				<tr>
					<th class="info text-center">이름</th>
					<td>${item.name}</td>
				</tr>
				<tr>
					<th class="info text-center">아이디</th>
					<td>${item.userId}</td>
				</tr>
				<tr>
					<th class="info text-center">성별</th>
					<td>${item.gender}</td>
				</tr>
				<tr>
					<th class="info text-center">생년월일</th>
					<td>${item.birthdate}</td>
				</tr>
				<tr>
					<th class="info text-center">휴대폰번호</th>
					<td>${item.tel}</td>
				</tr>
				<tr>
					<th class="info text-center">이메일</th>
					<td>${item.email}</td>
				</tr>
				<tr>
					<th class="info text-center">가입일자</th>
					<td>${item.regDate}</td>
				</tr>
			</tbody>
		</table>
		<!-- // 조회결과를 출력하기 위한 표 끝 -->
		
		<!-- 버튼시작 -->
		<div class="text-center">
			<a href="${pageContext.request.contextPath}/admin/admin_member.do" 
			class="btn btn-primary">목록</a>
			<a href="${pageContext.request.contextPath}/admin/
			admin_member_delete.do?id=${item.id}" 
			class="btn btn-danger">삭제</a>
		</div>
		<!-- //버튼 끝 -->
	</div>
</body>
</html>