<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
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
	
 	<div class="container">		
   		<div class="page-header"><h1 class="text-center">계획중인  일정</h1></div>
 		<%-- <c:forEach var="detailList" items="${detailPlanList}">
			<p>상세여행일정 id= ${detailList.id}</p>
		</c:forEach> --%>   		
   		<!-- 메뉴 -->
		<ul class="nav nav-tabs text-center" style="border-radius: 0.5em; background-color:black;">
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/mypage.do"><span class="glyphicon glyphicon-paperclip"></span> 클립보드</a></li>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/end_plan.do"><span class="glyphicon glyphicon-calendar"></span> 완성된 일정</a>
			<li class="active col-md-2 col-xs-4"><a href="#"><span class="glyphicon glyphicon-calendar"></span> 계획중인 일정</a>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/like_plan.do"><span class="glyphicon glyphicon-heart"></span> 좋아한 일정</a>
	   		<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_edit.do"><span class="glyphicon glyphicon-user"></span> 개인정보 수정</a></li>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_out.do"><span class="glyphicon glyphicon-user"></span> 회원탈퇴</a></li>	
    	</ul>
    	<br/>
    	<!-- // 메뉴 -->
    	
    	<div class="row">
			<c:choose>
				<c:when test="${fn:length(travelPlanList) > 0}">
					<c:set var="detail_plan_list" value="${detailPlanList2}" />			
					<c:forEach var="travelPlan" items="${travelPlanList}" varStatus="status">
						<!-- 게시물 항목 하나 -->
						<!-- season = 0이면 계획된 일정만 출력 -->
						<c:if test="${travelPlan.season eq 0}"> 
							<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<div class="thumbnail">
									<c:url var="readUrl" value="/content/planning2.do">
		                                    <c:param name="travel_plan_id" value="${travelPlan.id}" />
									</c:url>
									<!-- 링크 + 썸네일 -->
									<c:if test="${detail_plan_list[status.index].imagePath != null}">
									<c:url var="travelImageUrl" value="/download.do">
										<c:if test="${travelPlan.id eq detail_plan_list[status.index].travelPlanId}">
											<c:param name="file" value="${detail_plan_list[status.index].imagePath}" />
											<c:param name="orgin" value="${detail_plan_list[status.index].originName}" />
										</c:if>
									</c:url>
									<a href="#"><img src="${travelImageUrl}" class="img-responsive"/></a>
									</c:if>
									<c:if test="${detail_plan_list[status.index].imagePath == null}">
									<a href="#"><img src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-responsive"/></a>
									</c:if>
									<!-- 제목 + 작성자 + 조회수 -->
		 							<div class="item">
										<h3 style=" text-align: center; border:4px; color:orange;">${travelPlan.subject}</h3>					
										<div class="clearfix text-center">
											<a href="${readUrl}" class="btn btn-warning">수정하기</a>
			                            	<a href="${pageContext.request.contextPath}/mypage/ing_plan_delete.do?id=${travelPlan.id}" class="btn btn-danger" onclick="return confirm('계획중인 일정을 삭제하시겠습니까?')">삭제하기</a>
			                            </div>
		                            </div>
		                            
								</div>
							</div>
						</c:if>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<h1 class="text-center">조회된 데이터가 없습니다.</h1>
				</c:otherwise>
			</c:choose>
		</div> 
    	
	</div>

	
	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp"%>
	<!--//페이지 하단 -->
	
</body>
</html>