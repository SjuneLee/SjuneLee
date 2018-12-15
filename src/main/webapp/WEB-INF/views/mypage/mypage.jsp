<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
body {
	padding-top: 50px;
	padding-bottom: 20px;
}

.item h3{
	overflow: hidden; 
  	text-overflow: ellipsis;
  	white-space: nowrap;
}
</style>
</head>

<body>
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
	
	<!-- 마이페이지 bar -->
   	<div class="container">
   		<div class="page-header"><h1 class="text-center">클립보드</h1></div>
	   		<!-- 메뉴 -->
   			<ul class="nav nav-tabs text-center" style="border-radius: 0.5em; background-color:black;"> 
   				<li class="active col-md-2 col-xs-4" style=""><a href="#" style=""><span class="glyphicon glyphicon-paperclip"></span> 클립보드</a></li>
				<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/end_plan.do"><span class="glyphicon glyphicon-calendar"></span> 완성된 일정</a>
				<li class="col-md-2 col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/ing_plan.do"><span class="glyphicon glyphicon-calendar"></span> 계획중인 일정</a>
				<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/like_plan.do"><span class="glyphicon glyphicon-heart"></span> 좋아한 일정</a>
	    		<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_edit.do"><span class="glyphicon glyphicon-user"></span> 개인정보 수정</a></li>
   				<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_out.do"><span class="glyphicon glyphicon-user"></span> 회원탈퇴</a></li>	
	    	</ul>
	    	<br/>
	    	<!-- // 메뉴 -->		
	   		<!-- 글 목록 시작 -->
			<div class="row multi-columns-row">
				<!-- 조회된 글이 있는 경우 시작 -->
				<c:choose>
					<c:when test="${fn:length(list) > 0}">
						<c:forEach var="likes" items="${list}">
							<!-- 게시물 항목 하나 -->
							<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<div class="thumbnail">
									<c:url var="readUrl" value="/content/travel_read.do">
		                                    <c:param name="category" value="${likes.category}" />
		                                    <c:param name="travel_id" value="${likes.travelId}" />
									</c:url>
									<!-- 링크 + 썸네일 -->
									<a href="${readUrl}">
										<c:choose>
											<c:when test="${likes.imagePath != null }">
												<c:url var="downloadUrl" value="/download.do">
													<c:param name="file" value="${likes.imagePath}" />
												</c:url>
												<img src="${downloadUrl}" class="img-responsive"/>
											</c:when>
											<c:otherwise>
												<img src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-responsive"/>
											</c:otherwise>
										</c:choose>
									</a>
									<!-- 제목 + 작성자 + 조회수 -->
		 							<div class="item">
										<h3 style=" text-align: center; border:4px; color:orange;">${likes.subject}</h3>						
									<div class="clearfix">
		                            	<a href="${pageContext.request.contextPath}/mypage/clip_delete.do?id=${likes.id}" class="btn btn-danger pull-right" onclick="return confirm('삭제할꼬야?')">삭제하기</a>
		                            </div>
		                            </div>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<div class="col-md-12">
							<h1 class="text-center">조회된 데이터가 없습니다.</h1>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<!--// 글 목록 끝 -->
			<!-- 페이지 번호 시작 --> 
			<nav class="text-center">
				<ul class="pagination">
					<!-- 이전 그룹으로 이동 -->
					<c:choose>
						<c:when test="${pageHelper.prevPage > 0}">
							<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
							<!-- 이전 그룹으로 이동하기 위한 URL 을 생성해서 "prevUrl"에 저장 -->
							<c:url var="prevUrl" value="/mypage/mypage.do">
								<c:param name="page" value="${pageHelper.prevPage}"></c:param>
							</c:url>
							
							<li><a href="${prevUrl}">&laquo;</a></li>
						</c:when>
						
						<c:otherwise>
							<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
							<li class='disabled'><a href="#">&laquo;</a></li>
						</c:otherwise>
					</c:choose>
					
					<!-- 페이지 번호 -->
					<!-- 현재 그룹의 시작페이지~끝 페이지 사이를 1씩 증가하면서 반복 -->
					<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}" step="1">
						<!-- 각 페이지 번호로 이동할 수 있는 URL 을 생성하여 page_url에 저장 -->
						<c:url var="pageUrl" value="/mypage/mypage.do">
							<c:param name="page" value="${i}"></c:param>
						</c:url>
						
						<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
						<c:choose>
							<c:when test="${pageHelper.page == i }">
								<li class='active'><a href="#">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageUrl}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					
					<!-- 다음 그룹으로 이동 -->
					<c:choose>
						<c:when test="${pageHelper.nextPage > 0}">
							<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
							<!-- 다음 그룹으로 이동하기 위한 URL 을 생성해서 "nextUrl"에 저장 -->
							<c:url var="nextUrl" value="/mypage/mypage.do">
								<c:param name="page" value="${pageHelper.nextPage}"></c:param>
							</c:url>
							
							<li><a href="${nextUrl}">&raquo;</a></li>
						</c:when>
						
						<c:otherwise>
							<!-- 다음 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
							<li class='disabled'><a href="#">&raquo;</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</nav> 
			<!-- //페이지 번호 끝 -->
			</div>
			<!-- // 마이페이지 bar -->
	
	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp"%>
	<!--//페이지 하단 -->

</body>
</html>