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
					/ 조회수 : ${readQna.hit}  / 작성일시 : ${readQna.regDate}
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
								<c:choose>
									<c:when test="${fn:contains(prevQna.qnaSecretCondition, 'Y') && prevQna.memberId ne loginInfo.id}">
										<c:url var="prevUrl" value="/service/service_qna_view.do">
											<c:param name="customer_center_id" value="${prevQna.id}" />
										</c:url>
										비밀글입니다.
									</c:when>
									<c:otherwise>
										<c:url var="prevUrl" value="/service/service_qna_view.do">
											<c:param name="customer_center_id" value="${prevQna.id}" />
										</c:url>
										<a href="${prevUrl}">${prevQna.subject}</a>									
									</c:otherwise>
								</c:choose>	
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
								<c:choose>
									<c:when test="${fn:contains(nextQna.qnaSecretCondition, 'Y') && nextQna.memberId ne loginInfo.id}">
										<c:url var="nextUrl" value="/service/service_qna_view.do">
											<c:param name="customer_center_id" value="${nextQna.id}" />
										</c:url>
										비밀글입니다.
									</c:when>
									<c:otherwise>
										<c:url var="nextUrl" value="/service/service_qna_view.do">
											<c:param name="customer_center_id" value="${nextQna.id}" />
										</c:url>
										<a href="${nextUrl}">${nextQna.subject}</a>									
									</c:otherwise>
								</c:choose>	
							</c:when>
							<c:otherwise>
								다음글이 없습니다.
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
		</table>
		
		<!-- 버튼시작 -->
		<div class="text-center" style="padding: 80px 0px">
			<!-- // 조회결과를 출력하기 위한 표 끝 -->
			<a href="${pageContext.request.contextPath}/service/service_center_qna.do" 
			class="btn btn-primary">목록</a>
			<a href="${pageContext.request.contextPath}/service/service_center_qna_edit.do?customer_center_id=${readQna.id}"
			class="btn btn-warning">질문수정</a>
			<a href="${pageContext.request.contextPath}/service/service_center_qna_delete.do?customer_center_id=${readQna.id}" 
			class="btn btn-danger">삭제</a>
		</div>

	</div>
</body>
</html>