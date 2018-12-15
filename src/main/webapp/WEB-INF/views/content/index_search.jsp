<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">

body {

    line-height: 1.42857143;
    color: #3498db;
    background-color: #ffffff;
} 		
#container1 {
    border-radius: 25px;
    border: 2px solid #18bc9c;
	margin: 5em 1em 1em 2em ; 
    padding: 2em;
    width: 80%;
    min-height: 30%;
	
}
#container2 {
    border-radius: 25px;
    border: 2px solid #18bc9c;
	margin: 1em 1em 1em 2em; 
    padding: 2em;
    width: 80%;
    min-height: 30%;
	
}
</style>
</head>
<body>
	<!-- 페이지 상단 -->
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
	<!--//페이지 상단 -->

	<!-- 페이지 중단 (여행지) -->	

			<div class="container col-md-12" id="container1" >
			<h1 class="page-header"  style="color:#2c3e50;">여행지 검색내용</h1>
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover" style="table-layout: fixed;">
						<thead>
							<tr>
								<th class="text-center">카테고리</th>
								<th class="text-center">제목</th>
								<th class="text-center">내용</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(list) > 0}">
									<c:forEach var="travel" items="${list}">
										<tr>
											<td class="text-center">
												 <c:choose>
												  	<c:when test="${travel.category == 'festival'}"> 축제</c:when>
												  	<c:when test="${travel.category == 'show'}">공연</c:when>
												 	<c:otherwise> 맛집</c:otherwise>
												 </c:choose>
											 </td>
											<td class="text-center">
												<c:url var="readUrl" value="/content/travel_read.do">
                                    				<c:param name="category" value="${travel.category}" />
                                    				<c:param name="travel_id" value="${travel.id}" />	
												</c:url> <a href="${readUrl}">${travel.subject}</a>
											</td> 
											<td class="text-center" style=" text-overflow: ellipsis; overflow: hidden; white-space:nowrap; width:10px;"><text>${travel.content}</text></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="8" class="text-center"
											style="line-height: 100px;">조회된 데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
	
	<!-- 페이지 중단 (여행일정) -->

			<div class="container col-md-12" id="container2" >
				<h1 class="page-header"  style="color:#2c3e50;">여행일정 검색 내용</h1>
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="text-center">분류</th>
									<th class="text-center">제목</th>
									<th class="text-center">기간</th>
									<th class="text-center">테마</th>
									<th class="text-center">계절</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${fn:length(planlist) > 0}">
										<c:forEach var="travelplan" items="${planlist}">
											<tr>
												<td class="text-center">${travelplan.id}</td>
												<td class="text-center"><c:url var="readUrl" value="/plan/plan_info/schedule.do">
														<c:param name="travel_plan_id" value="${travelplan.id}" />
													</c:url> <a href="${readUrl}">${travelplan.subject}</a></td>
												<td class="text-center">${travelplan.term} 일</td>
												<td class="text-center">
													 <c:choose>
													  	<c:when test="${travelplan.theme == 1}">나홀로</c:when>
													  	<c:when test="${travelplan.theme == 2}">커플</c:when>
													  	<c:when test="${travelplan.theme == 3}">친구</c:when>
													  	<c:when test="${travelplan.theme == 4}">가족</c:when>
													  	<c:when test="${travelplan.theme == 5}">단체</c:when>
													 	<c:otherwise> 비즈니스 </c:otherwise>
													 </c:choose>
												 </td>
												<td class="text-center">
													 <c:choose>
													  	<c:when test="${travelplan.season == 1}"> 봄</c:when>
													  	<c:when test="${travelplan.season == 2}">여름</c:when>
													  	<c:when test="${travelplan.season == 3}">가을</c:when>
													 	<c:otherwise> 겨울 </c:otherwise>
													 </c:choose>
												 </td>											 																		
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="8" class="text-center"
												style="line-height: 100px;">조회된 데이터가 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>

	<!-- //페이지 중단 (여행일정) -->
		<%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>