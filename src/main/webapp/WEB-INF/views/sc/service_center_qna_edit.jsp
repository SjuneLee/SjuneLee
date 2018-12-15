<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="true" %>

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
	<script src="//cdn.ckeditor.com/4.10.1/standard/ckeditor.js"></script>
</head>
<body>
	<div class="container">
		<h1 class="page-header text-center">질문 수정하기</h1>
		
		<form method="post" action="${pageContext.request.contextPath}/service/service_center_qna_edit_ok.do?customer_center_id=${item.id}">
			<!-- 조회결과를 출력하기 위한 표 시작 -->
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th class="info text-center">제목</th>
						<td>
							<input type="text" name="subject" value="${item.subject}" class="form-control" />
						</td>
					</tr>
					<tr>
						<th class="info text-center">이름</th>
						<td>
							${item.name}
						</td>
					</tr>
					<tr>
						<th class="info text-center">아이디</th>
						<td>${item.userId}</td>
					</tr>
					<tr>
						<th class="info text-center">등록일자</th>
						<td>${item.regDate}</td>
					</tr>
					<tr>
						<th class="info text-center">고객 질문</th>
						<td>
							<textarea name="content" class="ckeditor" style="height: 100%;">
								${item.content}
							</textarea>
						</td>
					</tr>
					<tr>
						<th class="info text-center">관리자 답변</th>
						<td disabled>${item.qnaAnswer}</td>
					</tr>
				</tbody>
			</table>
			<!-- // 조회결과를 출력하기 위한 표 끝 -->
			
			<button type="submit" class="btn btn-warning">수정완료</button>
			<a href="${pageContext.request.contextPath}/service/service_center_qna.do" class="btn btn-primary">목록</a>
		</form>

	</div>
</body>
</html>