<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ include file="navbar_loginModal.jsp"%>
<%@ include file="navbar_logoutModal.jsp"%>
<!-- 축제행사 이미지 hover 장소 옮겨야 함. -->
<link href="${pageContext.request.contextPath}/assets/plugins/imageHover/css/imagehover.css" rel="stylesheet" media="all">
<!-- // 축제행사 이미지 hover 장소 옮겨야 함. -->
<head>
	<style type="text/css">
		#main_menu, .navbar-brand {
		  color: #fff;  
		  text-shadow: 0 0 20px #FF0000;
		}
		.btn-default {
		    color: #ffffff;
		    background-color: #18bc9c;
		    border-color: #18bc9c;
		}			
	</style>
	
</head>
<!-- 메뉴바 -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	<!-- 배경을 제외한 메뉴 항목의 영역 제한 -->
	<div class="container">
		<!-- 로고 영역 -->
		<div class="navbar-header">
			<!-- 반응형 메뉴 버튼 -->
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
			<!--// 반응형 메뉴 버튼 -->
			<!-- 로고 -->
			<a class="navbar-brand" href="${pageContext.request.contextPath}/index.do">Seoul4U</a>
			<!--// 로고 -->
		</div>
		<!--// 로고 영역 -->

		<!-- 메뉴 영역 -->
		<div class="navbar-collapse collapse">
			<!-- 메인메뉴 -->
			<ul class="nav navbar-nav" id="main_menu">
				<li><a href="${pageContext.request.contextPath}/index.do" class="active">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/content/travel_list.do?category=festival">축제/행사</a></li>
				<li><a href="${pageContext.request.contextPath}/content/travel_list.do?category=show">공연/전시</a></li>
				<li><a href="${pageContext.request.contextPath}/content/travel_list.do?category=food">관광지</a></li>
				<li><a href="${pageContext.request.contextPath}/content/tripMenu_plan.do">여행코스</a></li>
			</ul>
			<!--// 메인메뉴 -->
			<!-- 서치 + 로그인 + 회원가입 -->
			<c:choose>
				<c:when test="${loginInfo == null}">
				<form class="navbar-form navbar-right" method="get" action="${pageContext.request.contextPath}/content/index_search.do">
					<input type="text" value="${keyword}" id="keyword" name="keyword" placeholder="내용을 입력해주세요" class="form-control col-md-3 col-xs-3 col-sm-3">
					<button type="submit" class="btn btn-default" style="margin-left:5px; color=#18bc9c;">검색</button>
					<button type="button" data-toggle="modal" data-target="#myModal"
						class="btn btn-warning">로그인</button>
					<a href="${pageContext.request.contextPath}/member/join.do">
						<button type="button" class="btn btn-danger">회원가입</button>
					</a>
				</form> 
				</c:when>
				
				<c:otherwise>
				<form class="navbar-form navbar-right" method="get" action="${pageContext.request.contextPath}/content/index_search.do">
					<input type="text" value="${keyword}" id="keyword" name="keyword" placeholder="내용을 입력해주세요" class="form-control col-md-3 col-xs-3 col-sm-3">
					<button type="submit" class="btn btn-default" style="margin-left:5px;">검색</button>
					<div class="btn-group">
						<div data-toggle="dropdown" class="dropdown-toggle"> 
							<c:if test="${cookie.profileThumbnail != null}">
							<img src="${pageContext.request.contextPath}/download.do?file=${cookie.profileThumbnail.value}" class="img-circle" style="cursor:pointer; height: 45px;" />
							</c:if>
							<c:if test="${cookie.profileThumbnail == null}">
							<span>&nbsp;&nbsp;</span>
							<span class="glyphicon glyphicon-user" style="color: #C2E9F1; font-size: 40px; cursor:pointer;"></span><span class="caret"></span>
							</c:if>
						</div>
						<ul class="dropdown-menu" role="menu">
							<li><a
								href="${pageContext.request.contextPath}/service/service_center.do">고객센터</a></li>
							<li><a
								href="${pageContext.request.contextPath}/mypage/mypage.do">마이페이지</a></li>
							<li><a href="#" data-toggle="modal" data-target="#outModal">로그아웃</a></li>
						</ul>
					</div>
				</form>
				</c:otherwise>
			</c:choose>
			<!--// 서치 + 로그인 + 회원가입 -->
		</div>
		<!--// 메뉴 영역 -->
	</div>
	<!--// 배경을 제외한 메뉴 항목의 영역 제한 -->
</div>
<!--// 메뉴바 -->

<!-- addclass-active-refresh -->
<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> -->
<!-- // addclass-active-refresh -->
<!-- 메뉴바 Active 스크립트 함수 -->
<script type="text/javascript">
	$(function() {
		$("#main_menu a").click(function(e) {
			var link = $(this);
			var item = link.parent("li");
			if (item.hasClass("active")) {
				item.removeClass("active").children("a").removeClass("active");
			} else {
				item.addClass("active").children("a").addClass("active");
			}
			if (item.children("ul").length > 0) {
				var href = link.attr("href");
				link.attr("href", "#");
				setTimeout(function() {
					link.attr("href", href);
				}, 300);
				e.preventDefault();
			}
		}).each(function() {
			var link = $(this);
			if (link.get(0).href === location.href) {
				link.addClass("active").parents("li").addClass("active");
				return false;
			}
		});
	});
</script>
<!--//메뉴바 Active 스크립트 함수 -->