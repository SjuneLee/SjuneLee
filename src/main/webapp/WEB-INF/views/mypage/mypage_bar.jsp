<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<!-- mypagebar -->
<h1>마이페이지</h1>
<!--메뉴바-->
<nav class="navbar navbar-default" role="navigation">
	<!--로고-->
	<div class="navbar-header">
		<!--반응형 메뉴 구현 기능 추가-->
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#gnb">
			<span class="sr-only">메뉴열기</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<!--반응형 메뉴 구현 기능 추가 끝-->
		<a class="navbar-brand" href="#"></a>
	</div>
	<!--메뉴 항목-->
	<div class="collapse navbar-collapse" id="gnb">
		<ul class="nav nav-tabs">
			<li class="active">
				<a href="#mypageTab1" data-toggle="tab">클립보드</a>
			</li>
			<li><a href="">완성된 일정</a>
			<li><a href="">계획중인 일정</a>
			<li><a href="">좋아한 일정</a>
	 		<li><a href="">개인정보 수정</a></li>
			<li><a href="">회원탈퇴</a></li>	
	 	</ul>	
	</div>
</nav>
<!-- //mypagebar -->