<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
body {
	padding-top: 50px;
	padding-bottom: 20px;
}

.thumbnail {
	height: 350px;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
	
	<div class="container">
		<div class="page-header"><h1 class="text-center">개인정보 수정</h1></div>
   		<!-- 메뉴 -->
		<ul class="nav nav-tabs text-center" style="border-radius: 0.5em; background-color:black;">
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/mypage.do"><span class="glyphicon glyphicon-paperclip"></span> 클립보드</a></li>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/end_plan.do"><span class="glyphicon glyphicon-calendar"></span> 완성된 일정</a>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/ing_plan.do"><span class="glyphicon glyphicon-calendar"></span> 계획중인 일정</a>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/like_plan.do"><span class="glyphicon glyphicon-heart"></span> 좋아한 일정</a>
	   		<li class="active col-md-2 col-xs-4"><a href="#"><span class="glyphicon glyphicon-user"></span> 개인정보 수정</a></li>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_out.do"><span class="glyphicon glyphicon-user"></span> 회원탈퇴</a></li>	
    	</ul>
    	<br/>
    	<!-- // 메뉴 -->	
	    <br/>
	    <form class="form-horizontal" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/member/edit_ok.do">
			<div class="form-group">
		    	<label class="col-sm-3 control-label" for="user_pw">비밀번호</label>
		    	<div class="col-sm-6">
		      		<input class="form-control" id="user_pw" name="user_pw" type="password" placeholder="영문+숫자로 6자 이상 입력." />
		      		<!-- <p class="help-block">숫자, 특수문자 포함 8자 이상</p> -->
		      	</div>
		    </div>
		    <div class="form-group">
			    <label class="col-sm-3 control-label" for="new_user_pw">새 비밀번호</label>
			    <div class="col-sm-6">
				    <input class="form-control" id="user_pw" name="new_user_pw" type="password" placeholder="영문+숫자로 6자 이상 입력." />
				    <!-- <p class="help-block">숫자, 특수문자 포함 8자 이상</p> -->
			    </div>
		    </div>
		    <div class="form-group">
		    	<label class="col-sm-3 control-label" for="new_user_pw_re">새 비밀번호 확인</label>
		        <div class="col-sm-6">
		        	<input class="form-control" id="user_pw_re" name="new_user_pw_re" type="password" placeholder="비밀번호 확인." />
		        	<!-- <p class="help-block">비밀번호를 한번 더 입력해주세요.</p> -->
		        </div>
		    </div>
		    <div class="form-group">
		        <label class="col-sm-3 control-label" for="tel">휴대폰번호</label>
	        	<div class="col-sm-6">
		            <!-- <div class="input-group"> -->
		            <input type="tel" class="form-control" id="tel" name="tel" placeholder="- 없이 입력해 주세요." />
	            	<!-- </div> -->
	          	</div>
		    </div>
		    <div class="form-group">
		    	<label for='profile_img' class="col-sm-3 control-label">프로필 사진</label>
		    	<div class="col-sm-6">
		    		<input type="file" name="profile_img" id="profile_img" class="form-control" />
		    	</div>
		    </div>
		    
		    <c:if test="${cookie.profileThumbnail != null}">
			    <div class="form-group">
			    	<!-- 등록된 프로필 이미지 표시하기 -->
			    	<div class="col-md-10 col-md-offset-3">
			    		<p>
			    			<img src="${pageContext.request.contextPath}/download.do?file=${cookie.profileThumbnail.value}" class="img-circle"/>
			    			<label class="checkbox-inline">
			    				<input type="checkbox" name="img_del" id="img_del" value="Y" /> 이미지 삭제
			    			</label>
			    		</p>
			    		<script type="text/javascript">
			    			$(function() {
			    				// 이미지가 등록된 상태이므로, 파일의 신규 등록을 방지
			    				$("#profile_img").prop("disabled", true);
			    				$("#img_del").change(function() {
			    					$("#profile_img").prop("disabled", !$(this).is(":checked"));
			    				});
			    			});
			    		</script>
			    	</div>
			    </div>
		    </c:if>
		    <div class="form-group">
		    	<div class="col-sm-12 text-center">
			        <button class="btn btn-primary btn1" type="submit">회원정보수정</button>
			        <a href="${pageContext.request.contextPath}/mypage/mypage.do"><button class="btn btn-danger" type="button">수정취소</button></a>
		    	</div>
		    </div>
	    </form>
	</div>
</body>
</html>