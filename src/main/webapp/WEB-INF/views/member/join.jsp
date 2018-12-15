<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style>
/** body 영역이 메뉴에 가려지는 부분 확보하기 */
body {
	padding-top: 50px;
}
</style>
</head>
<body>

<!-- 페이지 상단 -->
<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
<!--//페이지 상단 -->

<!-- 페이지 중단 -->
<div class="container">
	<div class="page-header">
		<h1>회원가입</h1>
	</div>

	<form class="form-horizontal" method="post" enctype="multipart/form-data"
	action="${pageContext.request.contextPath}/member/join_ok.do">
		<div class="form-group">
			<label for="user_id" class="col-sm-3 control-label" >아이디</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="user_id" name="user_id" placeholder="영문+숫자, 4자 이상 입력.">
			</div><h4 class="text-danger" id ="id_disable"></h4><h4 class="text-info" id ="id_possible"></h4>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="user_pw">비밀번호</label>
			<div class="col-sm-6">
				<input class="form-control" id="user_pw" name="user_pw" type="password" placeholder="영문+숫자, 6자 이상 입력.">
				<!-- <p class="help-block">숫자, 특수문자 포함 8자 이상</p> -->
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="user_pw_re">비밀번호 확인</label>
			<div class="col-sm-6">
				<input class="form-control" id="user_pw_re" name="user_pw_re" type="password" placeholder="비밀번호 확인">
				<!-- <p class="help-block">비밀번호를 한번 더 입력해주세요.</p> -->
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="name">이름</label>
			<div class="col-sm-6">
				<input class="form-control" id="name" name="name" type="text" placeholder="이름을 입력하세요.">
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
			<label class="col-sm-3 control-label" for="user_birthdate">생년월일</label>
			<div class="col-sm-6">
				<input class="form-control" id="birthdate" name="birthdate" type="date" placeholder="생년월일을 입력하세요.">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="profileImg">프로필 사진</label>
			<div class="col-sm-6">
				<input class="form-control" id="profileImg" name="profileImg" type="file" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="email">이메일</label>
			<div class="col-sm-6">
				<input class="form-control" id="email" name="email" type="email" placeholder="이메일 입력하세요.">
			</div>
		</div>

		<div class="form-group">
			<label for='gender' class="col-sm-3 control-label">성별</label>
			<div class="col-sm-6">
				<label class="radio-inline">
					<input type="radio" name="gender" id="gender1" value="M"  /> 남자
				</label>
				<label class="radio-inline">
					<input type="radio" name="gender" id="gender2" value="F" /> 여자
				</label>
			</div>
		</div>

		<div class="form-group"> 
			<div class="col-sm-12 text-center">
				<button class="btn btn-primary" type="submit">회원가입</button>
				<a href="${pageContext.request.contextPath}/index.do"><button class="btn btn-danger" type="button">가입취소</button></a>
			</div>
		</div>
	</form>
</div>
<!--//페이지 중단 -->

<!-- 페이지 하단 -->
<%@ include file="/WEB-INF/inc/footer.jsp"%>
<!--//페이지 하단 -->

<script type="text/javascript">
	$(function() {
		// user_id input 변동시 이벤트
		$("#user_id").change(function() {
			// 입력값을 취득하고, 내용의 존재여부를 검사한다.
			var user_id_val = $("#user_id").val();
			
			if (!user_id_val) { // 입력되지 않았다면?
				alert("아이디를 입력하세요.");	// <-- 메시지 표시
				$("#id_possible").empty();
				$("#id_disable").empty();
				$("#user_id").focus();		// <-- 커서를 강제로 넣기
				return false;				// <-- 실행 중단
			}
			
			// 위의 if문을 무사히 통과했다면 내용이 존재한다는 의미이므로,
			// 입력된 내용을 Ajax를 사용해서 웹 프로그램에게 전달한다.
			$.ajax({
				"url": "${pageContext.request.contextPath}/member/id_unique_check.do",
				"type": "get",
				"dataType": "json",
				"data": { user_id: user_id_val},
				"success": function(req) {
					if (req.result == 'OK') {
						$("#id_possible").empty();
						$("#id_disable").empty();
						$("#id_possible").append('사용 가능한 아이디 입니다.');
					} else {
						$("#id_possible").empty();
						$("#id_disable").empty();
						$("#id_disable").append('사용할 수 없는 아이디 입니다.');
					}
				}
			})
		})
	})
</script>
</body>
</html>