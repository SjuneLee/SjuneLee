<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp" %>
<!-- 별점 기능 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/star-rating.min.css" media="all">
<script src="${pageContext.request.contextPath}/assets/js/star-rating.min.js" type="text/javascript"></script>
<!--//별점 기능 -->
<style type="text/css">
#tabmenu {background-color: #2c3e50;}

.head-page {
	margin-top: 7%;
	margin-bottom: 1%;
	height: 20%;
	border-bottom: 0.5px solid #E6E6E6;
}
#contentInfo {
	padding-top: 13.5%;
}
#header_subject, #rating, #address, #likes, #period {
	color: white;
	text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
i.glyphicon.glyphicon-plane {
    color: #e91b93;
}
i.glyphicon.glyphicon-home {
    color: #e9d81b;
}
</style>
</head>

<body>
<%@ include file="/WEB-INF/inc/nav/navbar.jsp" %>
	<div class="container">
		<!-- head page -->
		<div class="row head-page">
			<c:choose>
			<c:when test="${planInfo[0].fileDir != null}">
				<c:url var="backGroundUrl" value="/download.do">
					<c:param name="file" value="${planInfo[0].fileDir}/${planInfo[0].fileName}" />
					<c:param name="orgin" value="${planInfo[0].fileOriginName}" />
				</c:url>
				<div class="col-md-12 col-sm-12 col-xs-12" id="contentInfo"
					style= "background: url('${backGroundUrl}') no-repeat 100% 100%/100% 100%;">
					<h2 id = "period"></h2>
					<h1 id = "header_subject">
						${planInfo[0].planSubject}
					</h1>
					<h2 id = "likes_condition" class ="text-warning">&nbsp;</h2>
					<p id ="likes">
						&nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-up"></span><span id ="like_sum">${planInfo[0].planLikeSum}</span>
					</p>
					<p id = "rating">
						평점:${ratingAvg} / ${commentCount}개의 평가
					</p>
					<form class="form-horizontal" method="post">
						<input type="hidden" name="travelPlanId" value="${travelPlan.id}">
						<button type="submit"   name="likes_change" id="likes_change" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-heart"></span> 좋아요 </button><br><br>
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-12 col-sm-12 col-xs-12" id="contentInfo"
					style= "background: url('${pageContext.request.contextPath}/assets/img/no_image.jpg') no-repeat 100% 100%/100% 100%;">
					<h2 id = "period"></h2>
					<h1 id = "header_subject">
						${planInfo[0].planSubject}
					</h1>
					<h2 id = "likes_condition" class ="text-warning">&nbsp;</h2>
					<p id ="likes">
						&nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-up"></span><span id ="like_sum">${planInfo[0].planLikeSum}</span>
					</p>
					<p id = "rating">
						평점:${ratingAvg} / ${commentCount}개의 평가
					</p>
					<form class="form-horizontal" method="post">
						<input type="hidden" name="travelPlanId" value="${travelPlan.id}">
						<button type="submit"   name="likes_change" id="likes_change" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-heart"></span> 좋아요 </button><br><br>
					</form>
				</div>
			</c:otherwise>
			</c:choose>
		</div>
		<!--// head page -->
		
		<!-- tab menu -->
		<ul class="nav nav-tabs nav-tabs-responsive list-inline" id="tabmenu">
			<li class="active"><a href="${pageContext.request.contextPath}/plan/plan_info/schedule.do?travel_plan_id=${planInfo[0].planId}">여행지 정보</a></li>
			<li><a href="${pageContext.request.contextPath}/plan/plan_info/map.do?travel_plan_id=${planInfo[0].planId}" target="_blank">지도</a></li>
			<li><a href="${pageContext.request.contextPath}/plan/plan_info/comments.do?travel_plan_id=${planInfo[0].planId}">댓글</a></li>
			<!-- <li class="pull-right"><a href="#">좋아요<span class="glyphicon glyphicon-thumbs-up"></span></a></li> -->
		</ul>
		<!-- // tab menu -->
		
		<!-- 여행지 정보 -->
		<div class="row trip">
		<div class="col-lg-11 col-md-12  col-sm-10 col-xs-10">
		
		<c:choose>
		<c:when test="${planInfo != null}">
		<c:forEach var="content" items="${planInfo}" varStatus="status">
		<!-- content start -->
		<section id="day" class="day"><h1><span class="day__number label label-info">Day ${content.day}</span></h1>
			<div class="row">
			<div class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
				<h2 id="1-1" class="panel__date">No.${content.contentNo}</h2>
			</div>
			<div class="col-lg-10 col-md-12 col-xs-12 panel">
				<div class="agenda">
					<div class="agenda__content clearfix">
						<div class="col-md-6 col-sm-6 col-xs-12">
							<c:choose>
								<c:when test="${content.fileDir != null}">
									<c:url var="ContentImageUrl" value="/download.do">
										<c:param name="file" value="${content.imagePath}" />	
									</c:url>
									<img alt="" src="${ContentImageUrl}" class="img-responsive">
								</c:when>
								<c:otherwise>
									<img alt="" src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-thumbnail">
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-md-6 col-sm-6 col-xs-12">
							<h2><i class="glyphicon glyphicon-plane"></i>${content.travelSubject}</h2>
							<br/>
							<div class="caption">
								<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
								<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
								<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
								<span> 평점 : ${content.travelRatingAvg}점 /</span>
 								<span class="glyphicon glyphicon-heart" style="color: red"></span><span> 좋아요: ${content.travelLikeSum}개 / </span>
								<span class="glyphicon glyphicon-eye-open"> ${content.travelHit}</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			</div>
		</section>
		<!-- end content -->
		</c:forEach>
		</c:when>
		<c:otherwise>
		<h1>조회된 결과가 없습니다.</h1>
		</c:otherwise>
		</c:choose>
		
		</div>
		</div><!-- end row trip -->
		<!-- 여행지 정보 -->
	</div><!-- end container -->
<footer>
<!-- 페이지 하단 -->
<%@ include file="/WEB-INF/inc/footer.jsp" %>
<!-- // 페이지 하단-->
</footer>


<script type="text/javascript">
<!-- 좋아요기능 관련 스크립트 -->
	$(function() {
		// 좋아요 버튼 클릭시 이벤트
		$("#likes_change").click(function(e) {
			e.preventDefault();
			var travel_plan_id_val = '<c:out value="${planInfo[0].planId}"/>';
			console.log("travel_plan_id_val="+travel_plan_id_val);
			var loginInfo = '<c:out value="${loginInfo}"/>';
			$.ajax({
				"url" : "${pageContext.request.contextPath}/plan/plan_info/likes.do",
				"type": "post",
				"dataType": "json",
				"data": { travel_plan_id: travel_plan_id_val, login_info: loginInfo },
				"success" : function(req) {
					if(loginInfo == 0){//test
						alert("로그인 하셔야 합니다.");
						return false;	
					}
					
					if (req.result == 0) {
						
						$("#likes_condition").empty();
						$("#likes_condition").append('좋아한 일정에 없는 곳입니다.');
					} else {
						$("#likes_condition").empty();
						$("#likes_condition").append('좋아한 일정에 저장되었습니다.');
					}
				}
			})
		})
	})
<!--//좋아요기능 관련 스크립트 끝-->	

/* 상세일정 만들기 클릭시 날짜 출력  */
$(function() {
		var startdate ="${planInfo[0].startDate}"; //시작일
		var period = ${planInfo[0].term}; //여행기간
		var prd = parseInt(period) - 1;//여행기간 >> 숫자형변환 - 1
		
		function date_add(startdate, prd) { //함수 : startdate에서 (period - 1)일 후 날짜를 리턴함.
			var yy = parseInt(startdate.substr(0,4),10);
			var mm = parseInt(startdate.substr(5,2),10);
			var dd = parseInt(startdate.substr(8),10);
			d= new Date(yy, mm-1, dd + prd);
			
			yy = d.getFullYear();
			mm = d.getMonth() + 1; mm = (mm < 10)?'0' + mm : mm;
			dd = d.getDate(); dd = (dd <10)?'0' + dd : dd;
			return '' + yy + '-' +  mm  + '-' + dd;
		}
		
		var lastdate = date_add(startdate, prd);//마지막일
		$("#period").html(startdate + "~" + lastdate);//"시작일 ~ 마지막일"을  head page에 출력"
});
/*//상세일정 만들기 클릭시 날짜 출력  */
</script>
</body>
</html>