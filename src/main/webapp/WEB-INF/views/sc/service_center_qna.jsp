<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp" %>
<style type="text/css">
	body {
		padding-top: 50px;
		padding-bottom: 20px;
	}
</style>
</head>
 <body>
 	<!-- 페이지 상단 -->
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp" %>
	<!--//페이지 상단 -->
	<div class="container" style="padding:40px 50px;">
		<h2 class="text-center">고객센터</h2>
		<ul class="nav nav-tabs nav-justified">
			<li><a href="${pageContext.request.contextPath}/service/service_center.do">공지사항</a></li>
			<li class="active"><a href="${pageContext.request.contextPath}/service/service_center_qna.do">질문답변</a></li>
		</ul>
	</div>
	
<!-- container -->
<div class=container style="padding:0px 50px;">
	<!-- 공지사항 table -->
	<div class="container col-md-11">
		<div class="table-responsive">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="text-center col-md-1">번호</th>
						<th class="text-center col-md-1">답변상태</th>
						<th class="text-center col-md-5">제목</th>
						<th class="text-center col-md-2">작성자</th>
						<th class="text-center col-md-2">작성일</th>
						<th class="text-center col-md-2">조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach var="qna" items="${list}">
								<tr>
									<td class="text-center">${maxPageNo}</td>
									<td class="text-center">${qna.qnaAnswerCondition}</td>
									<td class="text-center">
										<%-- <a href="${pageContext.request.contextPath}/admin/admin_qna_view.do">${qna.subject}</a> --%>
										<c:url var="readUrl" value="/service/service_qna_view.do">
											<%-- <c:param name="category" value="${category}" /> --%>
											<c:param name="customer_center_id" value="${qna.id}" />
										</c:url>
										<c:choose>
											<c:when test="${fn:contains(qna.qnaSecretCondition, 'Y') && loginInfo.id ne qna.memberId}">
												비밀글입니다.
											</c:when>
											<c:otherwise>
												<a href="${readUrl}">${qna.subject}</a>
											</c:otherwise>
										</c:choose>				
									</td>
									<c:if test="${qna.name != null}">										
										<td class="text-center">${qna.name}</td>
									</c:if>
									<c:if test="${qna.name == null}">										
										<td class="text-center">탈퇴회원</td>
									</c:if>
									<td class="text-center">${qna.regDate}</td>
									<td class="text-center">${qna.hit}</td>
								</tr>
								<c:set var="maxPageNo" value="${maxPageNo-1}" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="8" class="text-center" style="line-height: 100px;">조회된 데이터가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
	<br />
	<!--//공지사항 table -->

	<!-- 공지사항 중단 -->
	<!-- 페이지네이션 -->
			<nav class="text-center">
				<ul class="pagination">
					<!-- 이전 그룹 -->
					<c:choose>
						<c:when test="${pageHelper.prevPage > 0}">
							<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
							<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 prevUrl에 저장 -->
							<c:url var="prevUrl" value="/service/service_center_qna.do">
								<c:param name="keyword" value="${keyword}"></c:param>
								<c:param name="page" value="${pageHelper.prevPage}"></c:param>
							</c:url>

							<li><a href="${prevUrl}">&laquo;</a></li>
						</c:when>
						<c:otherwise>
							<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면 -->
							<li class="disabled"><a href="#">&laquo;</a></li>
						</c:otherwise>
					</c:choose>

					<!-- 페이지 번호 -->
					<!-- 현재 그룹의 시작페이지~끝페이지 사이를 1씩 증가하면서 반복 -->
					<c:forEach var="i" begin="${pageHelper.startPage}"
						end="${pageHelper.endPage}" step="1">
						<!-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 page_url에 저장 -->
						<c:url var="pageUrl" value="/service/service_center_qna.do">
							<c:param name="keyword" value="${keyword}"></c:param>
							<c:param name="page" value="${i}"></c:param>
						</c:url>

						<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
						<c:choose>
							<c:when test="${pageHelper.page == i}">
								<li class="active"><a href="#">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageUrl}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<!-- 다음 그룹 -->
					<c:choose>
						<c:when test="${pageHelper.nextPage > 0}">
							<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
							<!-- 다음 그룹으로 이동하기 위한 URL을 생성해서 nextUrl에 저장 -->
							<c:url var="nextUrl" value="/service/service_center_qna.do">
								<c:param name="keyword" value="${keyword}"></c:param>
								<c:param name="page" value="${pageHelper.nextPage}"></c:param>
							</c:url>

							<li><a href="${nextUrl}">&raquo;</a></li>
						</c:when>
						<c:otherwise>
							<!-- 다음 그룹에 대한 페이지 번호가 존재하지 않는다면 -->
							<li class="disabled"><a href="#">&raquo;</a></li>
						</c:otherwise>
					</c:choose>

				</ul>
			</nav>
			<!--// 페이지네이션 끝 -->
	<a href="${pageContext.request.contextPath}/service/service_center_qnaAdd.do">
		<button class="btn btn-primary">글쓰기</button>
	</a>
	<div class="pull-right">
		<form action="${pageContext.request.contextPath}/service/service_center_qna.do"
		method="get">
			<div class="form-group col-md-4 col-xs-4"
			style="padding: 0; margin: 0;">
				<label for="select1" class="control-label"></label> 						
				<select class="form-control" name="search">
					<option value="subject" >제목</option>
					<option value="content" >내용</option>
				</select>
			</div>
			<div class="form-group col-md-6 col-xs-8"
				style="padding: 0; margin: 0;">
				<label for="keyword"></label> <input type="text"
					class="form-control" id="keyword" name="keyword"
					value="${keyword}" placeholder="내용을 입력해주세요" maxlength="20" />
			</div>
			<div class="form-group col-md-2 col-xs-12"
				style="padding: 0; margin: 0;">
				<label for="submit"></label>
				<button type="submit" class="btn btn-primary form-control">검색</button>
			</div>
		</form>
	</div>
	<!--//공지사항 중단 -->
</div>
<!-- //container -->
	
	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp" %>
	<!--//페이지 하단 -->
	
<script>
	$(document).ready(function(){
		$(".nav-tabs a").click(function(){
		$(this).tab('show');
		});
	});
</script>

</body>
</html>