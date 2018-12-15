<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!-- 캐러셀 영역 구성 -->
		<div id="carousel-generic" class="carousel slide" data-ride="carousel">
			<!-- 현재 위치 표시 -->
			<ol class="carousel-indicators">
				<li data-target="#carousel-generic" data-slide-to="0" class="active"></li>
				<li data-target="#carousel-generic" data-slide-to="1"></li>
				<li data-target="#carousel-generic" data-slide-to="2"></li>
			</ol>

			<!-- 내용 영역 -->
			<div class="carousel-inner">
				<!-- 항목 (1) -->
				<div class="item active">
					<img src="${pageContext.request.contextPath}/assets/img/MainPoster1.jpg" alt="슬라이더(1)">
				</div>

				<!-- 항목 (2) -->
				<div class="item">
					<img src="${pageContext.request.contextPath}/assets/img/MainPoster2.jpg" alt="슬라이더(2)">
				</div>

				<!-- 항목 (3) -->
				<div class="item">
					<img src="${pageContext.request.contextPath}/assets/img/MainPoster3.jpg" alt="슬라이더(3)">
				</div>
			</div>
			<!-- // 내용영역 -->

			<!-- 이동 버튼 -->
			<a class="left carousel-control" href="#carousel-generic" data-slide="prev"> <span class="icon-prev"></span> </a>
			<a class="right carousel-control" href="#carousel-generic" data-slide="next"> <span class="icon-next"></span> </a>
		</div>
		<!-- 캐러셀 영역 끝 -->