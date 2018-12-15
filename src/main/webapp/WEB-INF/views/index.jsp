<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
/* ripple effect */
body {
	padding-top: 50px;
	padding-bottom: 20px;
	background-image:
		url(${pageContext.request.contextPath}/assets/img/sea4.jpg);
	background-size: 100% 100%;
}
/* // ripple effect */
.carousel {
	height: 500px;
	margin-bottom: 60px;
}

.carousel-inner>.item>img {
	min-width: 100%;
	height: 500px;
}

/** 캐러셀의 반응형 기능 */
@media ( min-width : 768px) {
	.carousel-caption p {
		margin-bottom: 20px;
		font-size: 21px;
		line-height: 1.4;
	}
}
.thumbnail{margin: 1em;}
.page-header {margin-bottom: 0px;}
 /* 제목 영역 > 제목 텍스트 */
.page-header h2 {
	font-weight: bold;
	color: #fff;
	margin-bottom: 0;
}

 .page-header .btn {
	 margin-bottom: -50px; 
} 

/* 썸네일 이미지 크기*/
.img-responsive {
	width: 100%;
}

.navbar-brand{
	font-color: black;
}
</style>

</head>
<body>
	<!-- 페이지 상단 -->
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
	<!--//페이지 상단 -->

	<!-- 페이지 중단 -->
	<%@ include file="/WEB-INF/inc/carousel.jsp"%>
	<%-- <%@ include file="/WEB-INF/inc/grid.jsp"%> --%>
	<div class="container">

		<!-- 글 목록 시작 -->
		<!-- 축제/행사 목록 -->
		<div class="col-md-4">
			<div class="page-header clearfix">
				<h2 class="pull-left">
					축제/행사
				</h2>
				<div class="pull-right">
					<a href="${pageContext.request.contextPath}/content/travel_list.do?category=festival" 
					class="btn btn-warning btn-xs">more</a>
				</div>				
			</div>
			<div class="row multi-columns-row">
				<c:choose>
					<c:when test="${fn:length(festivalList) > 0 }">
						<c:forEach var="travel" items="${festivalList}">
							<!-- 게시물 항목 하나 -->
							<div class="thumbnail">
								<c:url var="readUrl" value="/content/travel_read.do">
									<c:param name="category" value="${travel.category}" />
									<c:param name="travel_id" value="${travel.id}" />
								</c:url>
								<!-- 링크 +썸네일 -->
								<a href="${readUrl}" class="thumbnail imghvr-zoom-out-right">
									<c:choose>
										<c:when test="${travel.imagePath != null }">
											<c:url var="downloadUrl" value="/download.do">
												<c:param name="file" value="${travel.imagePath}" />
											</c:url>
											<img src="${downloadUrl}" class="img-responsive" />
											<figcaption>
												<h3>${travel.subject}</h3>
												<div class="clearfix">
													<div class="pull-left">${travel.regDate}</div>
												</div>
											</figcaption>
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.request.contextPath}/assets/img/no_image.jpg"
												class="img-responsive" />
											<figcaption>
												<h3>${travel.subject}</h3>
												<div class="clearfix">
													<div class="pull-left">${travel.regDate}</div>
												</div>
											</figcaption>
										</c:otherwise>
									</c:choose>
								</a>
							</div>							
						</c:forEach>
					</c:when>
				</c:choose>
			</div>
		</div>
		<!-- 공연/전시 목록 -->
		<div class="col-md-4">
			<div class="page-header clearfix">
				<h2 class="pull-left">
					공연/전시
				</h2>
				<div class="pull-right">
					<a href="${pageContext.request.contextPath}/content/travel_list.do?category=show" class="btn btn-warning btn-xs">more</a>
				</div>				
			</div>
			<div class="row multi-columns-row">
				<c:choose>
					<c:when test="${fn:length(showList) > 0 }">
						<c:forEach var="travel" items="${showList}">
							<!-- 게시물 항목 하나 -->
							<div class="thumbnail">
								<c:url var="readUrl" value="/content/travel_read.do">
									<c:param name="category" value="${travel.category}" />
									<c:param name="travel_id" value="${travel.id}" />
								</c:url>
								<!-- 링크 +썸네일 -->
								<a href="${readUrl}" class="thumbnail imghvr-zoom-out-right">
									<c:choose>
										<c:when test="${travel.imagePath != null }">
											<c:url var="downloadUrl" value="/download.do">
												<c:param name="file" value="${travel.imagePath}" />
											</c:url>
											<img src="${downloadUrl}" class="img-responsive" />
											<figcaption>
												<h3>${travel.subject}</h3>
												<div class="clearfix">
													<div class="pull-left">${travel.regDate}</div>
												</div>
											</figcaption>
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.request.contextPath}/assets/img/no_image.jpg"
												class="img-responsive" />
											<figcaption>
												<h3>${travel.subject}</h3>
												<div class="clearfix">
													<div class="pull-left">${travel.regDate}</div>
												</div>
											</figcaption>
										</c:otherwise>
									</c:choose>
								</a>
							</div>							
						</c:forEach>
					</c:when>
				</c:choose>
			</div>
		</div>
		<!-- 맛집 목록 -->
		<div class="col-md-4">
			<div class="page-header clearfix">
				<h2 class="pull-left">
					관광지
				</h2>
				<div class="pull-right">
					<a href="${pageContext.request.contextPath}/content/travel_list.do?category=food" class="btn btn-warning btn-xs">more</a>
				</div>
			</div>
			<div class="row multi-columns-row">
				<c:choose>
					<c:when test="${fn:length(foodList) > 0 }">
						<c:forEach var="travel" items="${foodList}">
							<!-- 게시물 항목 하나 -->
							<div class="thumbnail">
								<c:url var="readUrl" value="/content/travel_read.do">
									<c:param name="category" value="${travel.category}" />
									<c:param name="travel_id" value="${travel.id}" />
								</c:url>
								<!-- 링크 +썸네일 -->
								<a href="${readUrl}" class="thumbnail imghvr-zoom-out-right">
									<c:choose>
										<c:when test="${travel.imagePath != null }">
											<c:url var="downloadUrl" value="/download.do">
												<c:param name="file" value="${travel.imagePath}" />
											</c:url>
											<img src="${downloadUrl}" class="img-responsive" />
											<figcaption>
												<h3>${travel.subject}</h3>
												<div class="clearfix">
													<div class="pull-left">${travel.regDate}</div>
												</div>
											</figcaption>
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.request.contextPath}/assets/img/no_image.jpg"
												class="img-responsive" />
											<figcaption>
												<h3>${travel.subject}</h3>
												<div class="clearfix">
													<div class="pull-left">${travel.regDate}</div>
												</div>
											</figcaption>
										</c:otherwise>
									</c:choose>
								</a>
							</div>							
						</c:forEach>
					</c:when>
				</c:choose>
			</div>
		</div>
		<!-- //글 목록 끝 -->
	</div>
	<!--//페이지 중단 -->

	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp"%>
	<!--//페이지 하단 -->
</body>

<!-- ripple effect -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/assets/plugins/ripple/jquery.ripples-min.js"></script>
<!-- // ripple effect -->

<script type="text/javascript">
	// ripple effect
	$(document).ready(function() {
		$('body').ripples({
			resolution : 512,
			dropRadius : 10, //px
			perturbance : 0.005,
		});
	});
	// ripple effect - 끝
</script>
</html>