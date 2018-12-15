<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<!-- navermap -->
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=jXfQcBQu5cG9ioO_wzwi"></script>

<!-- drag and drop -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jquery-ui.css">
<!-- drag and drop -->

<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
#add_plan {
    color: #ffffff;
    background-color: #18bc9c;
    border-color: #18bc9c;
}
#delete_button{
	background-color: #FF4000;
	border-color: #FF4000;
}

/*상단바*/
#header {
  padding-bottom: 10px;
  background:url(http://www.script-tutorials.com/demos/360/images/stars.png) repeat-x;
  z-index:0;
}

#header .col-md-7{
	margin-top: 1%; 
	padding-left: 15%;
	color: orange; 
	font-style:italic; 
	font-weight: bold; 
	text-shadow: 2px 2px 2px gold; 
	/* font-size: 1.5em; */
	
	
}

/*상단바 버튼*/
.btn-group {
  padding-top:15px;
}
@media (max-width: 484px){
	/*일정정보*/
	#column1 {
	  height:100%;
	  background-color:#48a3ff;
	  padding:0;
	  overflow:scroll; 
	  min-height: 82%;    
	}
	/*상세일정*/
	#column2 {
	  height:100%;background-color:#dddddd;padding:0;
	}
}

@media (min-width: 485px) and (max-width: 991px){
	/*일정정보*/
	#column1 {
	  height:100%;
	  background-color:#48a3ff;
	  padding:0;
	  overflow:scroll; 
	  min-height: 82%;    
	}
	/*상세일정*/
	#column2 {
	  height:100%;background-color:#dddddd;padding:0;
	}
}

@media (min-width: 992px){
	/*일정정보*/
	#column1 {
	  height:970px;
	  background-color:#48a3ff;
	  padding:0;
	  overflow:scroll; 
	  min-height: 82%;    
	}
	/*상세일정*/
	#column2 {
	  height:970px;background-color:#dddddd;padding:0;
	}
}

#column1>div h1,
#column1>div h2,
#column1>div h4 {
  color:white;
}
#plus {
  vertical-align: middle;color:white;
}

#header1 {
  height:15%;
}
#detail_plan {
  overflow:scroll; height:82%;background-color:#fff;
}
/* 여행지정보 */
#column3 {
  height:970px;background-color:#eaeaea;padding:0;
}
#header2 {
  height:15%;
}
#contents {
  overflow:scroll; height: 82%;background-color:#fff;
  float: left;
}

/*여행지정보 tab*/
.w3-container{
padding: 0;
}

#day_indicator > .selected{
	color:red;
}

a:hover,a:focus,a {
    color: #e74c3c;
    text-decoration: none;
}

/*g맵*/
#column4 {
  height:970px;
  padding: 0;
}
@media (max-width: 484px){
	#map {
	  width: 100%; height: 50%;
	}	
}
@media (min-width: 485px) and (max-width: 991px){
	#map {
	  width: 100%; height: 50%;
	}	
}
@media (min-width: 992px){
	#map {
	  width: 100%; height: 100%;
	}	
}


</style>

<!-- 여행지 정보 tab 디자인  -->
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>

<%@ include file="/WEB-INF/inc/nav/navbar_logoutModal.jsp"%>
<!-- 완료 클릭 시 계절, 테마 선택하는 모달창 -->
<%@ include file="completeModal.jsp"%>
<!-- 페이지 상단 -->
<div class="row" id="header" style="background-color: #1b2932;">
	<div class="col-md-7 col-md-offset-2 col-xs-6">
		<h2>${plan_name}</h2>		
	</div>
	<div class="col-md-3 col-xs-6">
		<div class="btn-group" style="display:flex;">
			<button type="button" data-toggle="dropdown" class="btn btn-info dropdown-toggle" >
				<span class="glyphicon glyphicon-user"></span><span class="caret"></span>
			</button>
			<%-- <a href="${pageContext.request.contextPath}/mypage/ing_plan.do"> --%>
				<button class="btn btn-primary" id="tempSave">임시저장</button>
			<!-- </a> -->
			<%-- <a href="${pageContext.request.contextPath}/mypage/end_plan.do"> --%>
				<button class="btn btn-success" id="" data-toggle="modal" data-target="#completeModal">완료</button>
			<!-- </a> -->
			<ul class="dropdown-menu" role="menu">
				<li><a href="${pageContext.request.contextPath}/service/service_center.do">고객센터</a></li>
				<li><a href="${pageContext.request.contextPath}/mypage/mypage.do">마이페이지</a></li>
				<li><a href="#" data-toggle="modal" data-target="#outModal">로그아웃</a></li>
			</ul>
		</div>
	</div>
</div>
<!--//페이지 상단 -->

<!-- 페이지 중단 -->
<div class="row">
	<!-- 일정정보 창 -->
	<div class="col-md-2" id="column1">
		<div>
			<h1 class="text-center"><b>일정정보 </b></h1>
		</div>
		<!-- day 추가 삭제 기능  -->
		<div class="text-center day_add">
			<div class="col-md-6 col-xs-6">
				<h2>
					<b>DAY 추가</b>
					<a href="#" id="day_plus"><span class="glyphicon glyphicon-plus-sign"></span></a>
				</h2>
			</div>
			<div class="col-md-6 col-xs-6">
				<h2>
					<b>DAY 삭제</b>
					<a href="#" id="day_delete"><span class="glyphicon glyphicon-minus-sign"></span></a>
				</h2>
			</div>
		</div>
		<!--// day 추가 삭제 기능 끝 -->

		<!-- 일정 생기는 부분 -->
		<div class="new" id="day_count"></div>
		<!--// 일정 생기는 부분 끝-->
	</div>
	<!--//일정정보 창 -->


	<!-- 상세일정 -->
	<div class="col-md-2" id="column2">
		<div id="header1">
			<h1 class="text-center">상세일정</h1>
 			<h2 class="text-center" style="margin-top: 10%;"><b>DAY1</b></h2>
		</div>
		<!-- detail_plan 만들어 지는 부분 -->
		<div id="detail_plan" class="text-center">
			<c:set var="detail_list" value="${detailPlanList}" />
			
  			<c:forEach var="day_count" begin="0" end="${plan_period}" varStatus="status1">
  				<div id="tab-panel">
  					<c:choose>
  						<c:when test="${status1.count eq 1}">
  							<div id="tab${status1.count}" class="show">
  						</c:when>
  						<c:otherwise>
  							<div id="tab${status1.count}" class="hide">
  						</c:otherwise>
  					</c:choose>
  						<c:forEach var="detail_list_count" begin="0" end="${fn:length(detailPlanList)}" varStatus="status2">
  							<c:if test="${detail_list[status2.index].day eq status1.count}">
	  							<div class="content" id="festival${status1.count}">
	  								<c:url var="travelImageUrl" value="/download.do">
										<%-- <c:param name="file" value="${detail_list[status2.index].fileDir}/${detail_list[status2.index].fileName}" /> --%>
										<c:param name="file" value="${detail_list[status2.index].imagePath}" />
										<c:param name="orgin" value="${detail_list[status2.index].originName}" />
									</c:url>
									<c:choose>
										<c:when test="${detail_list[status2.index].imagePath eq null}">
											<img alt="" src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-thumbnail" />
										</c:when>
										<c:otherwise>
											<img alt="" src="${travelImageUrl}" class="img-thumbnail" />
										</c:otherwise>
									</c:choose>
									
  									<c:forEach var="detail_category_list" begin="0" end="${fn:length(detailCategorylist)}" varStatus="status3">
										<c:choose>
											<c:when test="${detailCategorylist[status3.index].id eq detail_list[status2.index].id}">
												<h4>${detailCategorylist[status3.index].subject}</h4>
											</c:when>
										</c:choose>
									</c:forEach>
									
									<h5></h5><!-- 수정필요 --> 
									<div class="caption"> 
										<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
										<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
										<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
										<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
										<span class="glyphicon glyphicon-star" style="color: #FFAA00"></span>
										<span>${detail_list[status2.index].ratingAvg}점</span>
										<br/>
										<%-- <span>${festival.commentCount}개의 평가</span>&nbsp;&nbsp; --%>
										<span class="glyphicon glyphicon-eye-open">${detail_list[status2.index].hit}</span>
										<input type="hidden" class="travel_id" id="travel_id" value="${detail_list[status2.index].travelId}"/>
										<%-- <input type="hidden" class="x_point" id="x_point" value="${festival.travelMapX}"/> --%>
										<%-- <input type="hidden" class="y_point" id="y_point" value="${festival.travelMapY}"/> --%>
									</div>
										<input type="button" class="btn btn-default btn-sm item" id="delete_button" value="삭제">	
								</div>
  							</c:if>
  						</c:forEach>
  					</div>
  				</div>
			</c:forEach>
		</div>
		<!--// detail_plan 만들어 지는 부분 끝 -->
	</div>
	<!--//상세일정 -->

	<!-- 여행지정보 -->
	<div class="col-md-2" id="column3">
		<!-- 카테고리 -->
		<div id="header2">
			<h1 class="text-center">여행지정보</h1>
			<div class="tab" style="padding: 0px;">
				<div class="w3-row" >
					<a href="javascript:void(0)" onclick="openCategory(event, 'festival');">
						<div class="w3-third tablink w3-bottombar w3-hover-light-grey w3-padding w3-border-red text-center">
							<b>축제</b>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="openCategory(event, 'show');">
						<div class="w3-third tablink w3-bottombar w3-hover-light-grey w3-padding text-center">
							<b>공연</b>
						</div>
					</a>
					<a href="javascript:void(0)" onclick="openCategory(event, 'food');">
						<div class="w3-third tablink w3-bottombar w3-hover-light-grey w3-padding text-center">
							<b>관광지</b>
						</div>
					</a>
				</div>
			</div>
		</div>
		<!--//카테고리 -->

		<!-- 축제 탭 -->
		<div id="festival" class="tab category">
		<!-- 컨텐츠 -->
		<div class="col-xs-1"></div>
		<div id="contents" class="col-md-12 col-xs-10 text-center" style="height:53em;">
		<div class="col-xs-1"></div>
		<c:choose>
		
			<c:when test="${fn:length(festival) > 0}">
			
				<c:forEach var="festival" items="${festival}" varStatus="status">
					<c:url var="travelImageUrl" value="/download.do">
						<c:param name="file" value="${festival.imagePath}" />
						<c:param name="orgin" value="${festival.originName}" />
					</c:url>
					<div class="content" id="festival${status.count}">
						<c:choose>
							<c:when test="${festival.imagePath eq null}">
								<img alt="" src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-thumbnail">
							</c:when>
							<c:otherwise>
								<img alt="" src="${travelImageUrl}" class="img-thumbnail">
							</c:otherwise>
						</c:choose>
						
						<h4>${festival.travelSubject}</h4>
						<!-- <h5>축제</h5> -->
						<div class="caption">
							<span class="glyphicon glyphicon-star" style="color: #FA58F4;"></span>
							<span>${festival.travelRatingAvg}점</span>
							<br/>
							<span>${festival.commentCount}개의 평가</span>&nbsp;&nbsp;
							<span class="glyphicon glyphicon-eye-open">${festival.travelHit}</span>
							<input type="hidden" class="travel_id" id="travel_id" value="${festival.travelId}"/>
							<input type="hidden" class="x_point" id="x_point" value="${festival.travelMapX}"/>
							<input type="hidden" class="y_point" id="y_point" value="${festival.travelMapY}"/>
							<input type="hidden" class="travel_subject" id="travel_subject" value="${festival.travelSubject}"/>
							<input type="hidden" class="travel_summary" id="travel_summary" value="${festival.travelSummary}"/>
							<input type="hidden" class="travel_content" id="travel_content" value="${festival.travelContent}"/>
							<input type="hidden" class="travel_address" id="travel_address" value="${festival.travelAddress}"/>
						</div>
						<input type="button" class="btn btn-default btn-sm" id="add_plan" value="추가">
						<button class="btn btn-warning btn-sm" id="detail_place">상세보기</button>
					</div>
					<br/>
				</c:forEach>
				
			</c:when>
			
			<c:otherwise>
				<div class="nocontent" id="no festival">
					<p>조회된데이터가 없습니다.</p>
				</div>
			</c:otherwise>
			
		</c:choose>
		</div><!--//컨텐츠 끝 -->
		</div><!--//축제 탭 끝 -->

		<!-- 공연 탭 -->
		<div id="show" class="tab category" style="display:none">
		<!-- 컨텐츠 -->
		<div class="col-xs-1"></div>
		<div id="contents" class="col-md-12 col-xs-10 text-center" style="height:53em;">
		<div class="col-xs-1"></div>
			<c:choose>
			
			<c:when test="${fn:length(show) > 0}">
			
				<c:forEach var="show" items="${show}" varStatus="status">
					<c:url var="travelImageUrl" value="/download.do">
						<%-- <c:param name="file" value="${show.fileDir}/${show.fileName}" /> --%>
						<c:param name="file" value="${show.imagePath}" />
						<c:param name="orgin" value="${show.originName}" />
					</c:url>
					<div class="content" id="show${status.count}">
						<c:choose>
							<c:when test="${show.imagePath eq null}">
								<img alt="" src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-thumbnail">
							</c:when>
							<c:otherwise>
								<img alt="" src="${travelImageUrl}" class="img-thumbnail">
							</c:otherwise>
						</c:choose>
						<h4>${show.travelSubject}</h4>
						<!-- <h5>공연</h5> -->
						<div class="caption">
							<span class="glyphicon glyphicon-star" style="color: #FA58F4;"></span>
							<span>${show.travelRatingAvg}점</span>
							<br/>
							<span>${show.commentCount}개의 평가</span>&nbsp;&nbsp;
							<span class="glyphicon glyphicon-eye-open">${show.travelHit}</span>
							<input type="hidden" class="travel_id" id="travel_id" value="${show.travelId}"/>
							<input type="hidden" class="x_point" id="x_point" value="${show.travelMapX}"/>
							<input type="hidden" class="y_point" id="y_point" value="${show.travelMapY}"/>
							<input type="hidden" class="travel_subject" id="travel_subject" value="${show.travelSubject}"/>
							<input type="hidden" class="travel_summary" id="travel_summary" value="${show.travelSummary}"/>
							<input type="hidden" class="travel_content" id="travel_content" value="${show.travelContent}"/>
							<input type="hidden" class="travel_address" id="travel_address" value="${show.travelAddress}"/>
						</div>
						<input type="button" class="btn btn-default btn-sm" id="add_plan" value="추가">
						<button class="btn btn-warning btn-sm" id="detail_place">상세보기</button>
					</div>
					<br/>
				</c:forEach>
				
			</c:when>
			
			<c:otherwise>
				<div class="nocontent" id="no show">
					<p>조회된데이터가 없습니다.</p>
				</div>
			</c:otherwise>
			
			</c:choose>
		</div><!--//컨텐츠 끝 --> 
		</div><!--// 공연 탭 끝 -->

		<!-- 맛집 탭 -->
		<div id="food" class="tab category" style="display:none">
		<!-- 컨텐츠 -->
		<div class="col-xs-1"></div>
		<div id="contents" class="col-md-12 col-xs-10 text-center" style="height:53em;">
		<div class="col-xs-1"></div>
			<c:choose>
			
			<c:when test="${fn:length(food) > 0}">
			
				<c:forEach var="food" items="${food}" varStatus="status">
					<c:url var="travelImageUrl" value="/download.do">
						<%-- <c:param name="file" value="${food.fileDir}/${food.fileName}" /> --%>
						<c:param name="file" value="${food.imagePath}" />
						<c:param name="orgin" value="${food.originName}" />
					</c:url>
					<div class="content" id="food${status.count}">
						<c:choose>
							<c:when test="${food.imagePath eq null}"> 
								<img alt="" src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-thumbnail">
							</c:when>
							<c:otherwise>
								<img alt="" src="${travelImageUrl}" class="img-thumbnail">
							</c:otherwise>
						</c:choose>
						<h4>${food.travelSubject}</h4>
						<!-- <h5>맛집</h5> -->
						<div class="caption">
							<span class="glyphicon glyphicon-star" style="color: #FA58F4;"></span>
							<span>${food.travelRatingAvg}점</span>
							<br/>
							<span>${food.commentCount}개의 평가</span>&nbsp;&nbsp;
							<span class="glyphicon glyphicon-eye-open">${food.travelHit}</span>
							<input type="hidden" class="travel_id" id="travel_id" value="${food.travelId}"/>
							<input type="hidden" class="x_point" id="x_point" value="${food.travelMapX}"/>
							<input type="hidden" class="y_point" id="y_point" value="${food.travelMapY}"/>
							<input type="hidden" class="travel_subject" id="travel_subject" value="${food.travelSubject}"/>
							<input type="hidden" class="travel_summary" id="travel_summary" value="${food.travelSummary}"/>
							<input type="hidden" class="travel_content" id="travel_content" value="${food.travelContent}"/>
							<input type="hidden" class="travel_address" id="travel_address" value="${food.travelAddress}"/>
						</div>
						<input type="button" class="btn btn-default btn-sm" id="add_plan" value="추가">
						<button class="btn btn-warning btn-sm" id="detail_place">상세보기</button>
					</div>
					<br/>
				</c:forEach>
				
			</c:when>
			
			<c:otherwise>
				<div class="nocontent" id="no food">
					<p>조회된데이터가 없습니다.</p>
				</div>
			</c:otherwise>
			
			</c:choose>
		</div><!--// 컨텐츠 끝 -->
		</div><!--// 맛집 탭 끝 -->
	</div><!--//여행지정보 끝 -->

	<!-- Naver Map 화면 -->
	<div class="col-xs-2"></div>
	<div id="column4" class="col-md-6 col-xs-8 text-center">
		<div id="map"></div>
	</div>
	<div class="col-xs-2"></div>
	<!--//Naver Map 화면 -->

</div>
<!--//페이지 중단 -->


<!-- 페이지 하단 -->
<!--//페이지 하단 -->


<!-- drag and drop -->
<!-- <script src="${pageContext.request.contextPath}/assets/js/jquery-ui.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> -->
<!-- // drag and drop -->

<script type="text/javascript">
$(function(){
    /** 상세일정에 여행지 추가 */
    $(document).on('click', '#add_plan', function(e) {    
      var temp = $(this).parent().clone();
      
      temp.children("input").remove();
      temp.children("button").remove();
      var addButton = "<input type='button' class='btn btn-default btn-sm item' id='delete_button' value='삭제'>";
      temp.append(addButton);
      $("#detail_plan > #tab-panel > .show").append(temp);
      
      /** #add_plan을 클릭한 해당 여행지의 좌표값을 지도에 찍는다. */
      var latlng = new naver.maps.LatLng($(this).parent().children(".caption").children("#x_point").val(), $(this).parent().children(".caption").children("#y_point").val());
		infowindow.close(); /** 지도에 표시되어 있는 info 창을 닫는다 (polyline을 그려야 하기 때문에) */
      	marker.setPosition(latlng);
		
      var polyline = new naver.maps.Polyline({
      	map: map,
          path: [
            latlng  ]
            });
  
        }); 
    
    /** 상세일정의 여행지 삭제 */
    $(document).on('click', '.item', function(e) {
      $(this).parent().remove();  // 현재 항목 삭제
    });
    
	//JSTL 값을 저장
	var year = '<c:out value="${year}"/>';
	var month = '<c:out value="${month}"/>';
	var day = '<c:out value="${day}"/>';
	
    /** planning1의 일정만큼 day를 제시하는 함수 */
    $(document).ready(function() { 
    	var prd = ${plan_period};
    	
    	//빈 배열의 생성
    	var list1 = new Array();
    	
    	// 반복문을 통하여 배열의 칸을 확장하고 값을 저장
    	for (var i =0; i<=prd; i++) {
    		d = new Date(year, month-1, day);	
    		d = d.setDate(d.getDate() + i);	
    		list1[i] = d;	
    	};
    	
    	var datelist1 = new Array();
    	
    	for (var i=0; i < list1.length; i++) {
    		t = new Date(list1[i]);
    		
    		yy = t.getFullYear();
			mm = t.getMonth() + 1; mm = (mm < 10)?'0' + mm : mm;
			dd = t.getDate(); dd = (dd <10)?'0' + dd : dd;
			t = '' + yy + '-' +  mm  + '-' + dd;
			datelist1[i] = t;
    	}
		
    	count = 0;
    	for (var i=0; i < datelist1.length; i++) {
    		count = count + 1;	//' + datelist1[i] + ' 날짜 출력 미완성
    		if(count == 1){
    			var div = $('<div id="day_indicator">').addClass("text-center day").html(
    					'<a href="#tab' + count + '" class="day_tab selected"><b style="font-size: 20px;"></b><br /><b style="font-size: 30px;">DAY' + count + '</b><br /></a>');	
    		}else if(count > 1){
    			var div = $('<div id="day_indicator">').addClass("text-center day").html(
    					'<a href="#tab' + count + '" class="day_tab"><b style="font-size: 20px;"></b><br /><b style="font-size: 30px;">DAY' + count + '</b><br /></a>');
    		}
    		
            var detail = $('<div id=detail_tab-panel>').addClass("text-center day").html('<div id="tab' + count + '" class="hide"></div>');
            
            $("#detail_plan").append(detail);
            $(".new").append(div);
    	}
    	
    	/* 일정정보 추가 */
        //count = 0; // 미리 생성
        $(document).on('click', '#day_plus', function(e) {
	        e.preventDefault();
	        
	        //Day추가 시 count 1 증가
	        count = count + 1;
	        //html 요소 생성 >> 넘버링이 됨.
	        var div = $("<div id='day_indicator'>").addClass("text-center day").html('<a href="#tab' + count + '" class="day_tab"><b style="font-size: 20px;"></b><br /><b style="font-size: 30px;">DAY' + count + '</b><br /></a>');
	        var detail = $('<div id=tab-panel>').addClass("text-center day").html('<div id="tab' + count + '" class="hide"></div>');
	
	        $("#detail_plan").append(detail);
	        $(".new").append(div);
        });
        
        /** 일정정보 삭제 */       
        $("#day_delete").click(function(){
        	 if(count != 1){ //Day가 1개 남았을 때(Day1), 더이상 삭제할 수 없도록 지정
	        	//Day 삭제 시 내용 삭제
	          	$("#day_count").children().last().remove();
	          	$("#detail_plan #tab-panel").last().remove();
	          	$("#detail_plan #detail_tab-panel").last().remove();
	          	if(count >= 1){ //Day삭제 시 count 1 감소
	        		count = count - 1;	
	        	}else{ //Day의 숫자가 1이하로 떨어지지 않도록 함
	        		count = 0;
	        	}
        	}
        });
        
        
        /* 일정정보에서 day선택 시 tab기능*/
        $(document).on('click', '.day_tab', function(e){
           	e.preventDefault();// 페이지 이동 방지
    		
           	//일정정보 DAY클릭 시, 상세일정에 해당하는 DAY출력
           	var test = $(this).text();
			$("#header1 > h2").html('<b>'+ test +'</b>');
           	
			$(".day_tab").not(this).removeClass("selected");
			$(this).addClass("selected");
	   			
			var target = $(this).attr('href');
			$(target).removeClass('hide');
			$(target).addClass('show');
			$("#detail_plan > #tab-panel > div").not($(target)).addClass('hide');
			$("#detail_plan > #tab-panel > div").not($(target)).removeClass('show');
    	}); 
    
    });// planning1의 일정만큼 day를 제시하는 함수 끝
  });

</script>

<script>
/* 여행지정보 탭 기능 */
function openCategory(evt, categoryName) {
  var i, x, tablinks;
  x = document.getElementsByClassName("category");
  for (i = 0; i < x.length; i++) {
     x[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablink");
  for (i = 0; i < x.length; i++) {
     tablinks[i].className = tablinks[i].className.replace(" w3-border-red", "");
  }
  document.getElementById(categoryName).style.display = "block";
  evt.currentTarget.firstElementChild.className += " w3-border-red";
}
</script>

<!-- 네이버 지도 관련 스크립트 -->
 <script type="text/javascript">
  // 초기 지도설정
  var navermap = new naver.maps.LatLng(37.5666805, 126.9784147);

  var map = new naver.maps.Map('map', {
      center: new naver.maps.LatLng(37.5666805, 126.9784147),
      zoom: 10
  });

  var marker = new naver.maps.Marker({
      position: navermap,
      map: map
  });

// 지도 infowindow에 contentString을 담는 함수
  var infowindow = new naver.maps.InfoWindow({
  content: null
  });
  
// 지도아이콘 클릭시 정보창 닫히고,열리는 함수
naver.maps.Event.addListener(marker, "click", function(e) {
  if (infowindow.getMap()) {
      infowindow.close();
  } else {
      infowindow.open(map, marker);
  }
});

// 상세보기 클릭시 함수
$(document).on('click', '#detail_place', function(e) {
	//좌표확인용
	//alert($(this).parent().children(".caption").children("#x_point").val());
	//alert($(this).parent().children(".caption").children("#y_point").val());
	var latlng = naver.maps.LatLng($(this).parent().children(".caption").children("#x_point").val(), $(this).parent().children(".caption").children("#y_point").val());
	marker.setPosition(latlng);
	map.setCenter(marker.getPosition());
	infowindow.setContent([
		'<div class="iw_inner">',
		'	<h3>' + $(this).parent().children(".caption").children("#travel_subject").val() + '</h3>',
		'	<p>' + $(this).parent().children(".caption").children("#travel_address").val() + '<br />',
		'	</p>',
		'</div>'
		].join(''));
		infowindow.open(map,marker);
	});
<!--//네이버 지도 관련 스크립트 끝-->

/****************************************************************************/
    /** 여행일정 임시저장  스크립트 */
    $(document).on('click', '#tempSave', function(e) {
    	var start_date=${year}+'-'+${month}+'-'+ ${day};
    	//var term=${plan_period}+1;
    	var term=${plan_period}+1;
    	var subject="${plan_name}";
    	var season = 0;
    	var theme = 0;
    	
    	//detailPlan json 만들기
    	var filter = $("#detail_plan").find(".content");
    	// 각각의 Day마다의 여행지 content 갯수
    	var content_no = filter.length;
    	var filter2 = $("#day_count").find(".text-center");
    	//전체 Day 여행일 갯수
    	var daycount = filter2.length;
    	
    	var detailPlan = [];
    	for(i=0; i<daycount; i++){
    		for(k=0; k < $( "#tab" + (i+1) ).find(".content").length; k++){
   				detailPlan.push({"day" :i+1,
   								"content_no" :k+1,
   								"travel_id" : $("#tab"+ (i+1)+">.content:nth-child("+(k+1)+")").find("#travel_id").val(),
   								//"term" : daycount
							   });
   			}//day i의 컨텐츠 길이만큼 반복
    	}//day길이 : i만큼 반복
    	
    	var StringDetailPlan = JSON.stringify(detailPlan);
    	//var test = JSON.parse(StringDetailPlan);
    	console.log(filter);
    	console.log("각 Day에 해당하는 여행지 content 갯수 = " + content_no);
    	console.log("현재 여행코스의 여행일 갯수=" + daycount);    	
    	console.log("StringDetailPlan=" + StringDetailPlan);
    	$.ajax({
    		type: 'POST',
    		url: '${pageContext.request.contextPath}/content/plan_save.do',
    		dataType: 'json',
    		data: {
    			start_date: "${year}-${month}-${day}",
	        		term_num: daycount,
	        		subject: "${plan_name}",
	        		subject: "${plan_name}",
	        		travel_plan_id: "${travel_plan_id}",
        		season: 0,
        		theme: 0,
        			detailPlan: StringDetailPlan
    		},
    		success: function(data){
    			alert("여행일정을 임시저장하였습니다.");
    			//alert("planning2.jsp 저장 성공");
       			//if (data.result > 0){
       			//	alert("상세일정 저장 성공");
       			//}
       			//임시저장 클릭 시 -> 계획중인 일정으로 이동
       			window.location.href="${pageContext.request.contextPath}/mypage/ing_plan.do";
    		},
    		error: function(data, error){
    			alert("planning2 에러!!");
    		}
    	});
    });
    /** 여행일정  임시저장 스크립트 끝 */

/****************************************************************************/
</script> 
</body>
</html>