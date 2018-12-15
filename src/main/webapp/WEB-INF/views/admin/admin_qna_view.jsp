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
</head>
<body>
	<div class="container">
		<h1 class="page-header text-center">질문 상세 보기</h1>
		
		<!-- 제목, 작성자, 작성일시, 조회수 -->
		<div class="alert alert-info">
			<h3 style="margin: 0">
			${readQna.subject}<br/>
			<small>
				<a href="mailto:seoul4u@gmail.com">
					<i class="glyphicon glyphicon-envelope"></i></a> 
					<%-- / 조회수 : ${readQna.hit} --%>  / 작성일시 : ${readQna.regDate}
			</small>
			</h3>
		</div>
		
		<!-- 내용 -->
		<section style="padding: 0 10px 20px 10px">
		${readQna.content}
		</section>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="info text-center" style="width: 100px">관리자 답변</th>
					<td>${readQna.qnaAnswer}</td>
				</tr>
			</tbody>
		</table>
		<!-- 다음글/이전글 -->
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="success" style="width: 100px">이전글</th>
					<td>
						<c:choose>
							<c:when test="${prevQna != null}">
								<c:url var="prevUrl" value="/admin/admin_qna_view.do">
									<c:param name="qna_id" value="${prevQna.id}" />
								</c:url>
								<a href="${prevUrl}">${prevQna.subject}</a>
							</c:when>
							<c:otherwise>
								이전글이 없습니다.
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th class="success" style="width: 100px">다음글</th>
					<td>
						<c:choose>
							<c:when test="${nextQna != null}">
								<c:url var="nextUrl" value="/admin/admin_qna_view.do">
									<c:param name="qna_id" value="${nextQna.id}" />
								</c:url>
								<a href="${nextUrl}">${nextQna.subject}</a>
							</c:when>
							<c:otherwise>
								다음글이 없습니다.
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- // 조회결과를 출력하기 위한 표 끝 -->
		
		<!-- 버튼시작 -->
		<div class="text-center">
			<a href="${pageContext.request.contextPath}/admin/admin_qna.do" class="btn btn-primary">목록</a>
			<a href="${pageContext.request.contextPath}/admin/admin_qna_edit.do?qna_id=${readQna.id}" class="btn btn-warning">답변작성</a>
			<a href="${pageContext.request.contextPath}/admin/admin_qna_delete.do?qna_id=${readQna.id}" class="btn btn-danger">삭제</a>
		</div>
		<!-- //버튼 끝 -->
	</div>
</body>
</html>