<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8" />
<title>Seoul4U</title>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style>
body {
	background:url(https://4.bp.blogspot.com/_AQ0vcRxFu0A/S9shDGGyMTI/AAAAAAAAAYk/kn3WTkY2LoQ/s1600/IMG_0714.JPG);
	background-size: cover;
}
#background_image {
	width: 100%;
	height: 300px;
}
#headerbar {
	background-color: #2c3e50;
	width: 100%;
	height: 65px;
	text-align: center;
	vertical-align: middle;
	padding-top: 10px;
}
#h2 {
	margin-top: 10px;
	margin-bottom: 10px;
}
#filterbar {
	margin-bottom: 25px;
}
.item h3{
	overflow: hidden; 
  	text-overflow: ellipsis;
  	white-space: nowrap;
}


</style>
</head>
<body>
	<!-- 페이지 상단 -->
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
	<!-- //페이지 상단 끝 -->
	
	<!-- 페이지 중단 -->
	<div class="container">
		
		<img src="${pageContext.request.contextPath}/assets/img/travelplan.jpg" id="background_image">
		<div id="headerbar">
			<a class="btn btn-default" href="${pageContext.request.contextPath}/content/planning1.do" target="_blank" role="button">여행일정만들기</a>
		</div>
		<div id="h2">
			<h2 align="center">여행자들의 일정보기</h2>
		</div>
	
		<!-- 필터링바 시작 -->
		<div id="filterbar">
			<div class="container-fluid bg-light ">
				<div class="row align-items-center justify-content-center">
					<form method="get" action="${pageContext.request.contextPath}/content/tripMenu_plan.do">
					<div class="col-md-3 col-sm-6 col-xs-12 pt-3">
						<div class="form-group ">
							<select name="travelDays" class="form-control">
								<option value="all">여행일</option>
								<option  value="ten" >10일 미만</option>
								<option  value="tenOver" >10일 이상</option>
							</select>
						</div>
					</div>
					<div class="col-md-3 col-sm-6 col-xs-12 pt-3">
						<div class="form-group">
							<select name="travelMoment" class="form-control">
								<option value="10">여행시기</option>
								<option  value="1" >봄</option>
								<option  value="2" >여름</option>
								<option  value="3" >가을</option>
								<option  value="4" >겨울</option>
							</select>
						</div>
					</div>
					<div class="col-md-3 col-sm-6 col-xs-12 pt-3">
						<div class="form-group">
							<select name="travelTheme" class="form-control">
								<option value="10">여행테마</option>
								<option value="1" >나홀로여행</option>
								<option value="2" >커플여행</option>
								<option value="3" >친구와 함께 여행</option>
								<option value="4" >가족과 함께 여행</option>
								<option value="5" >단체여행</option>
								<option value="6" >비즈니스여행</option>
							</select>
						</div>
					</div>
					<div class="col-md-3 col-sm-6 col-xs-12">
						<button type="submit" class="btn btn-primary btn-block">Search</button>
					</div>
					</form>
				</div>
			</div>
		</div>
		<!-- //필터링바 끝 -->

	   		<!-- 글 목록 시작 -->
			<div class="row multi-columns-row">
				<!-- 조회된 글이 있는 경우 시작 -->
				<c:choose>
					<c:when test="${fn:length(list) > 0}">
						<c:forEach var="travelPlan" items="${list}">
							<!-- 게시물 항목 하나 -->
							<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<div class="thumbnail">
									<c:url var="readUrl" value="/plan/plan_info/schedule.do">
										<c:param name="travel_plan_id" value="${travelPlan.id}" />
									</c:url>
									<!-- 링크 + 썸네일 -->
									<a href="${readUrl}">
										<c:choose>
											<c:when test="${travelPlan.imagePath != null }">
												<c:url var="downloadUrl" value="/download.do">
													<c:param name="file" value="${travelPlan.imagePath}" />
												</c:url>
												<img src="${downloadUrl}" class="img-responsive"/>
											</c:when>
											<c:otherwise>
												<img src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-responsive"/>
											</c:otherwise>
										</c:choose>
									</a>
									<!-- 제목 + 작성자 + 조회수 -->
                                    <div class="item" >
                                    		<div class="clearfix" style=" text-align: center; border:4px; color:#3498db;">
                                    		<h3>${travelPlan.subject}</h3>
											<span class="glyphicon glyphicon-thumbs-up" style="color: #f39c12; text-align: center;">&nbsp;${travelPlan.likeSum}개</sapn>
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

<%-- 			<div class="col-md-4 col-sm-6 col-xs-12 text-center">
				<div class="thumbnail">
					<img alt="캐리비안의 해적" src="${pageContext.request.contextPath}/assets/img/thumb6.jpg">
					<div class="caption">
						<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
						<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
						<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
						<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
						<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
						<span>4.9점 (16개의 평가)</span> <span
							class="glyphicon glyphicon-eye-open"> 612</span>
					</div>
				</div>
			</div>
		</div> --%>
		<!-- 페이지 번호 시작 --> 
		<%-- 	<nav class="text-center">
				<ul class="pagination">
					<!-- 이전 그룹으로 이동 -->
					<c:choose>
						<c:when test="${pageHelper.prevPage > 0}">
							<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
							<!-- 이전 그룹으로 이동하기 위한 URL 을 생성해서 "prevUrl"에 저장 -->
							<c:url var="prevUrl" value="/content/tripMenu_plan.do">
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
						<c:url var="pageUrl" value="/content/tripMenu_plan.do">
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
							<c:url var="nextUrl" value="/content/tripMenu_plan.do">
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
			</nav>  --%>
			<!-- //페이지 번호 끝 -->
	</div>
	<!--// 페이지 중단 끝 -->
	
	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp"%>
	<!--//페이지 하단 끝-->

</body>
</html>