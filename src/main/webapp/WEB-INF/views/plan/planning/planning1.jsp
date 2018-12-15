<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	//여행제목 변수 선언 할당
	String plan_name = "여행제목을 지어보세요.";
%>
<!doctype html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/datepicker/datepicker.min.css" />
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
html, body {
	height: 100%;
	width: 100%;
	margin:0;
	padding:0;
	background-image: url(${pageContext.request.contextPath}/assets/img/planning1.jpg);	
	background-size: cover;
}
.planning {
	margin-left: 2%;
}
/*상단바*/
#header {
	padding-bottom: 10px;
	background:url(http://www.script-tutorials.com/demos/360/images/stars.png) repeat-x;
	color: orange; 
	font-style:italic; 
	font-weight: bold; 
	text-shadow: 2px 2px 2px gold; 
}
/*일정선택*/
#column1 {
	padding-top:2%;
	color: black;
}
/*지도*/
#column2 {
	padding-top:2%;
}
#column2 img {
	width:90%;
}
</style>

</head>
<body>
<%@ include file="/WEB-INF/inc/nav/navbar_logoutModal.jsp"%>
<%@ include file="submitModal.jsp"%>
	<!-- 페이지 상단 -->
	<div class="row" id="header" style="background-color: #1b2932;">
		<div class="col-md-7 col-md-offset-2 col-sm-7 col-sm-offset-2 col-xs-9">
			<h3><%=plan_name%></h3>
		</div>
		<div class="col-md-3 col-sm-3 col-xs-3">
			<div class="btn-group" style="padding-top: 15px;">
				<button type="button" data-toggle="dropdown"
					class="btn btn-info dropdown-toggle">
					<span class="glyphicon glyphicon-user"></span><span class="caret"></span>
				</button>
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
	<div class="planning">
		<div class="row">
		<!-- 일정선택 창 -->
		<div class="col-md-3 col-sm-3" id="column1">
			<h1 class="text-center">여행기간 선택</h1>
			<div class="text-center">
				<h2>
					출발일<a href="#" id="show-cal"><span class="glyphicon glyphicon-calendar" style="color: #97FF00"></span></a>
				</h2>
				<input type="text" id="datepicker" readonly/>
			</div>
			<div class="text-center" style="padding-bottom: 25px;white-space:nowrap;">
				<h2>기간
					<a href='#' onclick="form_btn(-1)"><span class="glyphicon glyphicon-minus-sign" style="color: #97FF00"></span></a>
					<input type="button" id="text" value="1" style="color: #000000" />
					<a href='#'onclick="form_btn(1)"><span class="glyphicon glyphicon-plus-sign" style="color: #97FF00"></span></a>
				</h2>
			</div>

			<div class="text-center">
				<a href="#" data-toggle="modal" data-target="#submitModal" id="submit_button"
					class="btn btn-warning btn-lg">상세일정 만들기</a>
			</div>
		</div>
		<!--//일정선택 창 -->

		<!-- 여행일정 짜는 법 -->
		<div class="col-md-9 col-sm-9 text-center" id="column2">
			<img src="${pageContext.request.contextPath}/assets/img/plan.jpg" />
		</div>
		<!--//google Map 화면 -->
	</div>
	<!--//페이지 중단 -->
	</div>
	

	<!-- 페이지 하단 -->
	<!--//페이지 하단 -->
</body>

<!-- datepicker 참조 -->
<script src="${pageContext.request.contextPath}/assets/plugins/datepicker/datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>

<script type="text/javascript">

/* DatePicker */
	$(function() {
		var dateToday = new Date();	//현재 날짜 정보 저장
		$("#datepicker").datepicker({
			autoHide : true, // 날짜 선택 후 자동 숨김 (true/false)
			format : 'yyyy-mm-dd', // 날짜 형식
			language : 'ko-KR', // 언어
			weekStart : 0, // 시작요일(0=일요일~6=토요일)
			trigger : '#show-cal', // 클릭시 달력을 표시할 요소의 id
			startDate: dateToday //지정한 날짜 이후부터 선택 가능
		});
	});
/*//DatePicker */

/* 기간 증가감소 버튼 */
	// 폼값 증가&감소
	function form_btn(n) {
		var text = document.getElementById("text"); // 폼 선택
		text_val = parseInt(text.value); // 폼 값을 숫자열로 변환
		text_val += n; // 계산
		text.value = text_val; // 계산된 값을 바꾼다
		if (text_val <= 0) {
			text.value = 1; // 만약 값이 0 이하면 1로 되돌려준다, 1보다 작은 수는 나타나지 않게하기 위해   
		}
	}
/*// 기간 증가감소 버튼  */

/* 상세일정 만들기 클릭시 날짜 출력  */
$(function() {
	$("#submit_button").click(function(){
		var startdate =$("#datepicker").val(); //시작일: datepicker에서 가져옴.
		var period = $("#text").val(); //여행기간: text에서 가져옴.
		var prd = parseInt(period) - 1;//여행기간 >> 숫자형변환 - 1
		
		$("#plan_period").val(prd);// sumbit모달 plan_period에 값 전달
		
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
		$("#start_date").val(startdate);
		$("#last_date").val(lastdate);
		$("#plan_date").val(startdate + "~" + lastdate);//"시작일 ~ 마지막일"을  submit모달의  plan_date에 출력"
	});
});
/*//상세일정 만들기 클릭시 날짜 출력  */
</script>
<script
	src="${pageContext.request.contextPath}/assets/plugins/ripple/jquery.ripples-min.js"></script>
<!-- // ripple effect -->

<script type="text/javascript">
	// ripple effect
/* 	$(document).ready(function() {
		$('body').ripples({
			resolution : 512,
			dropRadius : 10, //px
			perturbance : 0.005,
		});
	}); */
	// ripple effect - 끝
</script>
</html>