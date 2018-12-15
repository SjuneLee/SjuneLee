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
		<h1 class="page-header">공지사항 상세보기</h1>
		
		<!-- 제목, 작성자, 작성일시, 조회수 -->
		<div class="alert alert-info">
			<h3 style="margin: 0">
			${readNotice.subject}<br/>
			<small>
				<a href="mailto:seoul4u@gmail.com">
					<i class="glyphicon glyphicon-envelope"></i></a> 
					/ 조회수 : ${readNotice.hit}  / 작성일시 : ${readNotice.regDate}
			</small>
			</h3>
		</div>
		
		<!-- 첨부파일 목록 표시하기 -->
		<c:if test="${fileList != null}">
		<!-- 첨부파일 목록 -->
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="warning" style="width: 100px">첨부파일</th>
					<td>
						<c:forEach var="file" items="${fileList}">
							<!-- 다운로드를 위한 URL만들기 -->
							<c:url var="downloadUrl" value="/download.do">
								<c:param name="file" value="${file.fileDir}/${file.fileName}" />
								<c:param name="orgin" value="${file.originName}" />
							</c:url>
							<!-- 다운로드 링크 -->
							<a href="${downloadUrl}" class="btn btn-link btn-xs">${file.originName}</a>
						</c:forEach>
					</td>
				</tr>
			</tbody>
		</table>
		
		<!-- 이미지만 별도로 화면에 출력하기 -->
		<c:forEach var="file" items="${fileList}">
			<c:if test="${fn:substringBefore(file.contentType, '/') == 'image'}">
				<c:url var="downloadUrl" value="/download.do">
					<c:param name="file" value="${file.fileDir}/${file.fileName}" />
				</c:url>
				<p>
					<img src="${downloadUrl}" class="img-responsive" style="margin: auto"/>
				</p>
			</c:if>
		</c:forEach>
		</c:if>
		
		<!-- 내용 -->
		<section style="padding: 0 10px 20px 10px">
		${readNotice.content}
		</section>
		
		<!-- 다음글/이전글 -->
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="success" style="width: 100px">이전글</th>
					<td>
						<c:choose>
							<c:when test="${prevNotice != null}">
								<c:url var="prevUrl" value="/service/notice_read.do">
									<c:param name="customer_center_id" value="${prevNotice.id}" />
								</c:url>
								<a href="${prevUrl}">${prevNotice.subject}</a>
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
							<c:when test="${nextNotice != null}">
								<c:url var="nextUrl" value="/service/notice_read.do">
									<c:param name="customer_center_id" value="${nextNotice.id}" />
								</c:url>
								<a href="${nextUrl}">${nextNotice.subject}</a>
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
			<a href="${pageContext.request.contextPath}/service/notice.do" 
			class="btn btn-primary">목록</a>
		</div>
		<!-- //버튼 끝 -->
	</div>
</body>
</html>