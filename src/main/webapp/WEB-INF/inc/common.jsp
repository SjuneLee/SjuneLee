<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>	
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Seoul4U</title>

		<!-- 모바일 웹 페이지 설정 -->
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/ico/favicon.png" />
		<link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-144-precomposed.png" />

		<!-- 나눔고딕 웹 폰트 적용 -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/nanumfont.css" />
		
		<!-- Bootstrap & Jqery -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
		<script src="${pageContext.request.contextPath}/assets/js/jquery-1.10.2.min.js"></script>
		<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
		
		<!-- CKEditor -->
		<script src="http://cdn.ckeditor.com/4.4.7/standard/ckeditor.js"></script>
		
		<!-- handlebars -->
		<script src="${pageContext.request.contextPath}/assets/plugins/handlebars/handlebars-v4.0.5.js"></script>
		<!-- AjaxHelper -->
		<script src="${pageContext.request.contextPath}/assets/plugins/ajax/ajax_helper.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/plugins/ajax/ajax_helper.css" />
		
		<!-- AjaxForm -->
		<script src="${pageContext.request.contextPath}/assets/plugins/ajax-form/jquery.form.min.js"></script>
		
		<!-- Multi-column -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/assets/plugins/multi-column/ie-row-fix.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/multi-column/multi-columns-row.css"/>
		
		<!-- 반응형 웹을 지원하지 않을 경우 -->
		<!-- <link rel="stylesheet" href="assets/css/non-responsive.css" /> -->

		<!-- IE8 이하 버전 지원 -->
		<!--[if lt IE 9]>
		<script type="text/javascript" src="assets/js/html5shiv.js"></script>
		<script type="text/javascript" src="assets/js/respond.min.js"></script>
		<![endif]-->

		<!-- IE10 반응형 웹 버그 보완 -->
		<!--[if gt IE 9]>
		<link rel="stylesheet" type="text/css" href="assets/css/ie10.css" />
		<script type="text/javascript" src="assets/js/ie10.js"></script>
		<![endif]-->
