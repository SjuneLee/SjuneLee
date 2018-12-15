<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false" %>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/inc/common.jsp"%>
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
	<!-- 회원삭제 모달창 구현부분 -->
	<%-- <%@ include file="member_deleteModal.jsp"%> --%>
	<!--//회원삭제 모달창 구현부분 -->

	<!-- 페이지 상단 -->
	<div class="col-md-12 col-xs-12" style="padding: 3% 10%;">
		<a href="${pageContext.request.contextPath}/index.do"
			class="pull-right"><button class="btn btn-info">사용자페이지
				이동</button></a>
	</div>

	<div class="container" style="padding: 40px 50px;">
		<h2 class="text-center">관리자 페이지</h2>
		<ul class="nav nav-tabs nav-justified">
			<li><a
				href="${pageContext.request.contextPath}/admin/admin_notice.do">공지사항</a></li>
			<li class="active"><a
				href="${pageContext.request.contextPath}/admin/admin_member.do" style="background-color:orange;">회원관리</a></li>
			<li><a
				href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=festival">여행지관리</a></li>
			<li><a
				href="${pageContext.request.contextPath}/admin/admin_qna.do">질문답변관리</a></li>
		</ul>
	</div>
	<!--//페이지 상단 -->
	<!-- 페이지 중단 -->
	<div class=container style="padding: 0px 50px;">
		<div class="tab-content">
			<!-- 회원 검색창 -->
			<form method="get" action="${pageContext.request.contextPath}/admin/admin_member.do" >
				<div class="row box">
					<div class="form-group col-md-2 col-xs-6" style="padding: 0; margin: 0;">
						<label for="select2" class="control-label"></label> 
						<select	class="form-control" name="date">
							<option value="lastest">최신순</option>
							<option value="last">오래된 순</option>
						</select>
					</div>
					<div class="form-group col-md-2 col-xs-6"
						style="padding: 0; margin: 0;">
						<label for="select1" class="control-label"></label> 
						<select class="form-control" name="search">
							<option value="userid">아이디검색</option>
							<option value="tel">휴대폰번호</option>
							<option value="gender">성별</option>
							<option value="email">이메일검색</option>
						</select>
					</div>
					<div class="form-group col-md-6 col-xs-12"
						style="padding: 0; margin: 0;">
						<label for="search"></label> 
						<input type="search" class="form-control" id="search" name="keyword"
							value="${keyword}" placeholder="내용을 입력해주세요" maxlength="20" />
					</div>
					<div class="form-group col-md-1 col-xs-6" style="padding: 0; margin: 0;">
						<label for="submit"></label>						
						<button type="submit" class="btn btn-primary form-control">검색</button>						
					</div>
					<div class="form-group col-md-1 col-xs-6" style="padding: 0; margin: 0;">
						<label for="reset"></label>
						<button type="reset" class="btn btn-default form-control">초기화</button>
					</div>
				</div>
				
			</form>
			<br />
			<!--//회원 검색창 -->

			<!-- 회원관리 table -->
			<div class="container col-md-12" style="padding: 0; margin: 0;">
				<div class="table-responsive">
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th class="text-center"><input type="checkbox"
									id="member_check_all" /></th>
								<th class="text-center">회원번호</th>
								<th class="text-center">이름</th>
								<th class="text-center">아이디</th>
								<th class="text-center">성별</th>
								<th class="text-center">생년월일</th>
								<th class="text-center">휴대폰번호</th>
								<th class="text-center">이메일</th>
								<th class="text-center">가입일자</th>
							</tr>
						</thead>
						
						<tbody>
							<c:choose>
								<c:when test="${fn:length(list) > 0}">
									<c:forEach var="item" items="${list}">
										<tr>
											<td class="text-center">
												<input type="checkbox" class="member_check" 
													 value="${item.id}"/>
											</td>
											<td class="text-center">${item.id}</td>
											<td>
												<c:url var="readUrl" value="/admin/admin_member_view.do">
													<c:param name="id" value="${item.id}" />
												</c:url>
												<a href="${readUrl}">${item.name}</a>
											</td>
											<td class="text-center">${item.userId}</td>
											<td class="text-center">${item.gender}</td>
											<td class="text-center">${item.birthdate}</td>
											<td class="text-center">${item.tel}</td>
											<td class="text-center">${item.email}</td>
											<td class="text-center">${item.regDate}</td>
										</tr>
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
			<form method="post" action="${pageContext.request.contextPath}/admin/admin_member_delete_check.do" id="member_list">
				<div class="text-left">
					<button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')" class="btn btn-danger">회원삭제</button>
				</div>
			</form>
		</div>
	<!-- 페이지 중단 -->
<!-- 페이지 번호 -->
		<nav class="text-center">
			<ul class="pagination">
				<!-- 이전 그룹 -->
				<c:choose>
					<c:when test="${page.prevPage > 0}">
						<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
						<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 prevUrl에 저장 -->
						<c:url var="prevUrl" value="/admin/admin_member.do">
							<c:param name="keyword" value="${keyword}"></c:param>
							<c:param name="page" value="${page.prevPage}"></c:param>
						</c:url>
						
						<li><a href="${prevUrl}">&laquo;</a></li>
					</c:when>
					<c:otherwise>
						<!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면 -->
						<li class="disabled"><a href="#">&laquo;</a></li>
					</c:otherwise>
				</c:choose>
								
				<!-- 페이지 번호 -->
				<!-- 현재 그룹의 시작페이지~끝페이지 사이를 1씩 증가하면서 반복 -->
				<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}" step="1">
					<!-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 page_url에 저장 -->
					<c:url var="pageUrl" value="/admin/admin_member.do">
						<c:param name="keyword" value="${keyword}"></c:param>
						<c:param name="page" value="${i}"></c:param>
					</c:url>
					
					<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
					<c:choose>
						<c:when test="${page.page == i}">
							<li class="active"><a href="#">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageUrl}">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			
				<!-- 다음 그룹 -->
				<c:choose>
					<c:when test="${page.nextPage > 0}">
						<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
						<!-- 다음 그룹으로 이동하기 위한 URL을 생성해서 nextUrl에 저장 -->
						<c:url var="nextUrl" value="/admin/admin_member.do">
							<c:param name="keyword" value="${keyword}"></c:param>
							<c:param name="page" value="${page.nextPage}"></c:param>
						</c:url>
						
						<li><a href="${nextUrl}">&raquo;</a></li>
					</c:when>
					<c:otherwise>
						<!-- 다음 그룹에 대한 페이지 번호가 존재하지 않는다면 -->
						<li class="disabled"><a href="#">&raquo;</a></li>
					</c:otherwise>
				</c:choose>
				
			</ul>
		</nav>
	<!-- 페이지 하단(내용 없음) -->
	<!--//페이지 하단 -->

		<script>		

			/* 체크된 회원삭제 */
			 $(function() {
				 $("#member_list").submit(function(e) {
		                e.preventDefault();
				// 선택된 항목을 가져온다 --> 선택된 체크박스의 배열
                var check_list = $(".member_check:checked");
            	 // 배열의 길이가 0이라면 선택된 항목이 없다는 의미이므로 중단
                if (check_list.length == 0) {
                    alert("선택된 항목이 없습니다.");
                    return false;
                }
            	 var chk_member = [];
             // 배열의 길이만큼 반복한다.
                 for (var i=0; i<check_list.length; i++) {
                    // jQuery객체에 push로 배열에 체크박스 값을 추가한다.
                   chk_member.push($(check_list[i]).val());   
               		}  
               		               		
                console.log("checked="+chk_member);
                $.ajax({
        			method: 'post',
        			url: '${pageContext.request.contextPath}/admin/admin_member_delete_check.do', 
        			traditional: true,
        			data: {chkArr: chk_member}, 
        			
        			 success: function(req) {
        		          console.log('return string : ');
        		          location.href="${pageContext.request.contextPath}/admin/admin_member.do" 
        		          alert("회원탈퇴를 완료했습니다.")
        	        	}	 
        			});	
				});	
					
			});	 
			
			  	
			/* 체크박스 전체선택  */
			$("#member_check_all").change(function() {
				$(".member_check").prop('checked', $(this).prop('checked'));
			});
		</script>
	</div>
</body>
</html>