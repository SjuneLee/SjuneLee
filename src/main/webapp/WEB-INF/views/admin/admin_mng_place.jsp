<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>

<!-- naver map -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=jXfQcBQu5cG9ioO_wzwi" ></script>
<!-- // naver map -->


<style type="text/css">
.nav-tabs li a:hover{
	background-color: #58ACFA;
	color: black;
}

.pagination li a {
	width: 12%;
}
</style>
</head>
<body>
	<!-- 게시물등록 모달창 구현부분 -->
	<%@ include file="mngPlace_registerModal.jsp"%>
	
	<!--//게시물등록 모달창 구현부분 -->
	
	<!-- 페이지 상단 -->
	<div class="col-md-12 col-xs-12" style="padding: 3% 10%;">
		<a href="${pageContext.request.contextPath}/index.do"
			class="pull-right"><button class="btn btn-info">사용자페이지
				이동</button></a>
	</div>

	<div class="container" style="padding: 40px 50px;">
		<h2 class="text-center">관리자 페이지</h2>
		<ul class="nav nav-tabs nav-justified">
			<li><a href="${pageContext.request.contextPath}/admin/admin_notice.do">공지사항</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/admin_member.do">회원관리</a></li>
			<li class="active"><a href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=festival" style="background-color:orange;">여행지관리</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/admin_qna.do">질문답변 관리</a></li>
		</ul>
	</div>
	<!--//페이지 상단 -->
 	<div class="container" >
		<ul class="nav nav-tabs nav-justified">
			<li><a href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=festival">축제행사</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=show">공연전시</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=food">관광지</a></li>
		</ul>
	</div>

	
	
	<!-- 페이지 중단 -->
	<div class=container style="padding: 0px 50px;">
		<h1 class="page-header"  style="color:blue;">${bbsName}</h1>
		<div class="tab-content">
			<!-- 회원 검색창 -->
			<div class="row" style="vertical-align: middle">
				<form method="get" action="${pageContext.request.contextPath}/admin/admin_mng_place.do">
				<input type="hidden" name="category" value="${category}" />
					<div class="form-group col-md-2 col-xs-4" style="padding: 0; margin: 0;">
						<label for="select2" class="control-label"></label> 
						<select class="form-control" name="search">
							<option value="subject" >제목</option>
							<option value="content" >내용</option>
						</select>
					</div>
					<div class="form-group col-md-6 col-xs-8" style="padding: 0; margin: 0;">
						<label for="keyword"></label> 
						<input type="text" name="keyword" class="form-control" placeholder="내용을 입력해주세요" value="${keyword}" />
					</div>			
					<div class="form-group col-md-2 col-xs-6" style="padding: 0; margin: 0;">
						<label for="submit"></label>
						<button type="submit" class="btn btn-primary form-control">검색</button>						
					</div>
					<div class="form-group col-md-2 col-xs-6" style="padding: 0; margin: 0;">
						<label for="reset"></label>
						<button type="reset" class="btn btn-default form-control">초기화</button>
					</div>
					</form>
				</div>
			<br />
				<!-- 여행지 게시물 table -->
			<div class="container col-md-12" style="padding: 0; margin: 0;">
				<div class="table-responsive">
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th class="text-center"><input type="checkbox" id="mngPlace_check_all" /></th>
								<th class="text-center" >번호</th>
								<th class="text-center" >제목</th>
								<!-- <th class="text-center" >내용</th> -->
								<th class="text-center" >작성자</th>
								<th class="text-center" >작성일</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(list) > 0}">
									<c:forEach var="travel" items="${list}">
										<tr>										
											<td class="text-center">
											<input type="checkbox" class="mngPlace_check" 
												value="${travel.id}"/></td>
											<td class="text-center">${maxPageNo}</td>
											<td class="text-center">
								            	<c:url var="readUrl" value="/admin/mngPlace_read.do">
								            		<c:param name="category" value="${travel.category}" />
								            		<c:param name="travel_id" value="${travel.id}" />
								            	</c:url>
												<a href="${readUrl}">${travel.subject}</a>
											</td>
											<%-- <td class="text-center">${travel.content}</td> --%>
											<td class="text-center">관리자</td>
											<td class="text-center">${travel.regDate}</td>
										</tr>
										<c:set var="maxPageNo" value="${maxPageNo-1}" />
									</c:forEach>		
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="8" class="text-center" style="line-height: 100px;">
										조회된 데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
			<br />
			<!--//회원관리 table -->

	<!-- 페이지 중단 -->

	<!-- 글 쓰기 버튼 -->
	<form method="post" action="${pageContext.request.contextPath}/admin/admin_MngPlace_delete_check.do" id="MngPlace_list">
		<div class="col-md-12 col-xs-12">
			<button type="button" data-toggle="modal" data-target="#placeModal" class="btn btn-danger">게시물등록</button>
			<button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')" class="btn btn-info">삭제하기</button>
		</div>
	</form>

<!-- 페이지 번호 시작 --> 
<nav class="text-center">
	<ul class="pagination">
		<!-- 이전 그룹으로 이동 -->
		<c:choose>
			<c:when test="${pageHelper.prevPage > 0}">
				<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
				<!-- 이전 그룹으로 이동하기 위한 URL 을 생성해서 "prevUrl"에 저장 -->
				<c:url var="prevUrl" value="/admin/admin_mng_place.do">
					<c:param name="category" value="${category}"></c:param>
					<c:param name="keyword" value="${travelkeyowrd}"></c:param>
					<c:param name="page" value="${pageHelper.prevPage}"></c:param>
				</c:url>
				
				<li><a href="${prevUrl}">&laquo;</a></li>
			</c:when>
			
			<c:otherwise>
				<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
				<li class='disabled'><a href="#">&laquo;</a></li>
			</c:otherwise>
		</c:choose>
		
		<!-- 페이지 번호 -->
		<!-- 현재 그룹의 시작페이지~끝 페이지 사이를 1씩 증가하면서 반복 -->
		<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}" step="1">
			<!-- 각 페이지 번호로 이동할 수 있는 URL 을 생성하여 page_url에 저장 -->
			<c:url var="pageUrl" value="/admin/admin_mng_place.do">
				<c:param name="category" value="${category}"></c:param>
				<c:param name="keyword" value="${travelkeyowrd}"></c:param>
				<c:param name="page" value="${i}"></c:param>
			</c:url>
			
			<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
			<c:choose>
				<c:when test="${pageHelper.page == i }">
					<li class='active'><a href="#">${i}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${pageUrl}">${i}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		
		<!-- 다음 그룹으로 이동 -->
		<c:choose>
			<c:when test="${pageHelper.nextPage > 0}">
				<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
				<!-- 다음 그룹으로 이동하기 위한 URL 을 생성해서 "nextUrl"에 저장 -->
				<c:url var="nextUrl" value="/admin/admin_mng_place.do">
					<c:param name="category" value="${category}"></c:param>
					<c:param name="keyword" value="${travelkeyowrd}"></c:param>
					<c:param name="page" value="${pageHelper.nextPage}"></c:param>
				</c:url>
				
				<li><a href="${nextUrl}">&raquo;</a></li>
			</c:when>
			
			<c:otherwise>
				<!-- 다음 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
				<li class='disabled'><a href="#">&raquo;</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</nav> 
<!-- //페이지 번호 끝 -->
	<!--//페이지 하단 -->
</div>
<script>
/* 체크된 여행지삭제 */
$(function() {
	 $("#MngPlace_list").submit(function(e) {
           e.preventDefault();
	// 선택된 항목을 가져온다 --> 선택된 체크박스의 배열
   var check_list = $(".mngPlace_check:checked");
	 // 배열의 길이가 0이라면 선택된 항목이 없다는 의미이므로 중단
   if (check_list.length == 0) {
       alert("선택된 항목이 없습니다.");
       return false;
   }
	 var chk_mngPlace = [];
// 배열의 길이만큼 반복한다.
    for (var i=0; i<check_list.length; i++) {
       // jQuery객체에 push로 배열에 체크박스 값을 추가한다.
      chk_mngPlace.push($(check_list[i]).val());   
  		}  
  		/* $.each($("input[name='checked_member']:checked"), function(){
  			chk_mngPlace.push($(this).val());
  		}); */
  		
   console.log("checked="+chk_mngPlace);
   $.ajax({
		method: 'post',
		url: '${pageContext.request.contextPath}/admin/admin_MngPlace_delete_check.do', 
		traditional: true,
		data: {chkArr: chk_mngPlace}, 
		
		 success: function(req) {
	          console.log('return string : ');
	          location.href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=festival" 
	          alert("여행지삭제를 완료했습니다.")
       	}	 
		});	
	});	
		
});	 
</script>
		<script>
			$(document).ready(function() {
				$(".nav-tabs a").click(function() {
					$(this).tab('show');
				});
			});
			
			/* 체크박스 전체선택 */
			$("#mngPlace_check_all").change(function() {
				$(".mngPlace_check").prop('checked', $(this).prop('checked'));
			});
		</script>
		
		<!-- naver map -->
		<script>
		var nmap = new naver.maps.Map('naverMap', { // naverMap 값은 div 의 id 값
			center : new naver.maps.LatLng(37.5782709,126.9770043), // 지도 중앙 위치 : 위도, 경도 설정
			zoom : 8, // 줌 설정 : 1~14, 수치가 클수록 지도 확대(줌인), 이 옵션 생략시 기본값 9
			zoomControl : true, // 줌 컨트롤 표시 (기본값 표시안함)
			zoomControlOptions : { // 줌 컨트롤 오른쪽 위로 위치 설정
				position : naver.maps.Position.TOP_RIGHT // 오른쪽 위로 설정값
			},
			mapTypeControl : true, // 일반ㆍ위성 지도보기 컨트롤 표시 (기본값 표시안함)
		});


		naver.maps.Event.addListener(nmap, 'click', function(e){
			$('[name=namp_lat]').val(e.coord.lat()); // 위도
			$('[name=namp_lng]').val(e.coord.lng()); // 경도
			
			$('input[name=mapX]').val(e.coord.lat());
			$('input[name=mapY]').val(e.coord.lng());
		});
		</script>
		<!-- // naver map -->
		
	</div>
</body>
</html>