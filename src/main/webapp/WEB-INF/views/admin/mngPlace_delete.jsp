<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<!-- Modal -->
<div class="modal fade" id="deletplaceModal" role="dialog">
	<div class="modal-dialog">

	<!-- Modal content-->
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4>게시물 삭제하기</h4>
		</div>
		<div class="modal-body" style="padding:40px 50px;">
			<form name="myform" method="post" action="${pageContext.request.contextPath}/admin/travel_delete_ok.do">
			<input type="hidden" name="category" value="${category}"/>
			<input type="hidden" name="travel_id" value="${travelId}"/>
			
			<div style='width:300px; margin:50px auto;'>
				<!-- 자신의 글인 경우와 아닌 경우에 대한 분기 -->
				<c:choose>
					<c:when test="${myTravel == true}" >
						<!-- 자신의 글에 대한 삭제 -->
						<p> 정말 이 게시물을 삭제하시겠습니까?</p>
					</c:when>
				</c:choose>
			<div class="form-group">
				<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
				<button type="submit" class="btn btn-danger">삭제</button>
			</div>
			</div>
			</form>
		</div>
	</div>
	</div>
</div>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp" %>
</head>
<body>
	<div class="container">
		<h1>${bbsName} - <small>글 삭제하기</small></h1>
	
		<form name="myform" method="post" action="${pageContext.request.contextPath}/admin/travel_delete_ok.do">
			<!-- 카테고리와 게시글 번호 상태유지 -->
			<input type="hidden" name="category" value="${category}"/>
			<input type="hidden" name="travel_id" value="${travelId}"/>
			
			<div style='width:300px; margin:50px auto;'>
				<!-- 자신의 글인 경우와 아닌 경우에 대한 분기 -->
				<c:choose>
					<c:when test="${myTravel == true}" >
						<!-- 자신의 글에 대한 삭제 -->
						<p> 정말 이 게시물을 삭제하시겠습니까?</p>
					</c:when>
				</c:choose>
			<div class="form-group">
				<button type='submit' class='btn btn-danger btn-block'>삭제하기</button>
				<button type='button' class='btn btn-primary btn-block' onclick="history.back()">삭제취소</button>
			</div>
			</div>
		</form>
	</div>
</body>
</html>
