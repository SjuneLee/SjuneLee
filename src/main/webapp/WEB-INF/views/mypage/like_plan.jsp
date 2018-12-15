<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
<style type="text/css">
body {
	padding-top: 50px;
	padding-bottom: 20px;
}

.item h3{
	overflow: hidden; 
  	text-overflow: ellipsis;
  	white-space: nowrap;
}
</style>
</head>

<body>
	<%@ include file="/WEB-INF/inc/nav/navbar.jsp"%>
	
	<div class="container">
   		<div class="page-header"><h1 class="text-center">좋아한 일정</h1></div>
   		<!-- 메뉴 -->
		<ul class="nav nav-tabs text-center" style="border-radius: 0.5em; background-color:black;">
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/mypage.do"><span class="glyphicon glyphicon-paperclip"></span> 클립보드</a></li>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/end_plan.do"><span class="glyphicon glyphicon-calendar"></span> 완성된 일정</a>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/ing_plan.do"><span class="glyphicon glyphicon-calendar"></span> 계획중인 일정</a>
			<li class="active col-md-2 col-xs-4"><a href="#"><span class="glyphicon glyphicon-heart"></span> 좋아한 일정</a>
	   		<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_edit.do"><span class="glyphicon glyphicon-user"></span> 개인정보 수정</a></li>
			<li class="col-md-2 col-xs-4"><a href="${pageContext.request.contextPath}/mypage/member_out.do"><span class="glyphicon glyphicon-user"></span> 회원탈퇴</a></li>	
    	</ul>
    	<br/>
    	<!-- // 메뉴 -->
    	
    	<div class="row">
			<c:choose>
				<c:when test="${fn:length(likePlanList) > 0}">
					<c:forEach var="likePlan" items="${likePlanList}">
					<!-- 게시물  -->
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					    	<div class="thumbnail">
								<c:url var="readUrl" value="/plan/plan_info/schedule.do">
									<c:param name="travel_plan_id" value="${likePlan.travelPlanId}" />
								</c:url>
								<!-- 링크 + 썸네일 -->
								<a href="${readUrl}">
									<c:choose>
										<c:when test="${likePlan.imagePath != null}">
											<c:url var="downloadUrl" value="/download.do">
												<c:param name="file" value="${likePlan.imagePath}" />
											</c:url>
											<img src="${downloadUrl}" class="img-responsive" />
										</c:when>
										<c:otherwise>
										<img src="${pageContext.request.contextPath}/assets/img/no_image.jpg" class="img-responsive"/>
										</c:otherwise>
									</c:choose>									
								</a>
								<!-- 제목 + 작성자 + 조회수 -->
	 							<div class="item">
									<h3 style=" text-align: center; border:4px; color:orange;">${likePlan.subject}</h3>						
									<div class="clearfix">
		                            	<a href="${pageContext.request.contextPath}/mypage/like_plan_delete.do?id=${likePlan.id}" class="btn btn-danger pull-right" onclick="return confirm('좋아한 일정을 삭제하시겠습니까?')">삭제하기</a>
		                            </div>
	                            </div>
					         </div>
				        </div>
				    </c:forEach>    
			        <!-- // 게시물  -->
				</c:when>
				<c:otherwise>
					<h1 class="text-center">조회된 데이터가 없습니다.</h1>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<!-- 페이지 하단 -->
	<%@ include file="/WEB-INF/inc/footer.jsp"%>
	<!--//페이지 하단 -->
	
	<!-- Javascript -->
	<script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
	<script>
		$(function() {
			$(".btn2").click(function() {
				// 확인,취소버튼에 따른 후속 처리 구현
				swal({
					title : '확인', // 제목
					text : "정말 삭제하시겠습니까?", // 내용
					type : 'warning', // 확인버튼 표시 문구
					confirmButtonText : '완료', // 아이콘 종류
					showCancelButton : true, //취소버튼 표시 여부
					cancelButtonText : '취소' // 취소버튼 표시문구
				}).then(function(result) { // 버튼이 눌러졌을 경우의 콜백 연결
					if (result.value) {
						swal('삭제', '성공적으로 삭제되었습니다.', 'success'); // 확인 버튼이 눌러진 경우
					} else if (result.dismiss === 'cancel') { // 취소버튼이 눌러진 경우
						swal('취소', '삭제가 취소되었습니다.', 'error');
					}
				});
			});
		});
	</script>
</body>
</html>