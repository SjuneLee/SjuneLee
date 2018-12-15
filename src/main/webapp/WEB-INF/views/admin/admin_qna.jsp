<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
			<li><a href="${pageContext.request.contextPath}/admin/admin_mng_place.do?category=festival">여행지관리</a></li>
			<li class="active">
				<a href="${pageContext.request.contextPath}/admin/admin_qna.do" style="background-color: orange;">질문답변 관리</a>
			</li>
		</ul>
	</div>
	<!--//페이지 상단 -->
	<!-- 페이지 중단 -->
	<div class=container style="padding: 0px 50px;">
		<div class="tab-content">
			<!-- 질문답변 검색창 -->
			<div class="row-box">
				<form method="get" action="${pageContext.request.contextPath}/admin/admin_qna.do">
				<input type="hidden" name="category" value="${qna}" />			
					<div class="form-group col-md-2 col-xs-6"
						style="padding: 0; margin: 0;">
						<label for="select1" class="control-label"></label> 
						<select class="form-control" name="condition">
							<option value="1">전체</option>
							<option value="2">답변완료</option>
							<option value="3">미답변</option>
						</select>
					</div>
					<div class="form-group col-md-2 col-xs-6"
						style="padding: 0; margin: 0;">
						<label for="select2" class="control-label"></label> 
						<select class="form-control" name="search">
							<option value="subject">제목</option>
							<option value="content">내용</option>
							<option value="qnaAnswer">답변</option>
						</select>
					</div>
					<div class="form-group col-md-4 col-xs-12"
						style="padding: 0; margin: 0;">
						<label for="keyword"></label> 
						<input type="text" name="keyword" id="keyword" class="form-control" placeholder="내용을 입력해주세요" value="${keyword}" />
					</div>
					<div class="form-group col-md-2 col-xs-6"
						style="padding: 0; margin: 0;">
						<label for="submit" class="control-label"></label>
						<button type="submit" class="btn btn-primary form-control">검색</button>
					</div>
					<div class="form-group col-md-2 col-xs-6"
						style="padding: 0; margin: 0;">
						<label for="reset" class="control-label"></label>
						<button type="reset" class="btn btn-default form-control">초기화</button>
					</div>
				</form>
			</div>
			<br />
			<!--//질문답변 검색창 -->

			<!-- 질문답변 table -->
			<div class="container col-md-12 col-xs-12" style="padding: 0; margin: 0;">
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="text-center"><input type="checkbox"
									id="qna_check_all" /></th>
								<th class="text-center">번호</th>
								<th class="text-center">상태</th>
								<th class="text-center">제목</th>
								<th class="text-center">이름</th>
								<th class="text-center">질문일자</th>								
								<th class="text-center">답변일</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${fn:length(list) > 0}">
									<c:forEach var="item" items="${list}">
										<tr>
											<td class="text-center">
											<input type="checkbox" class="qna_check" 
											value="${item.id}"/></td>
											<td class="text-center">${maxPageNo}</td>
											<td class="text-center">${item.qnaAnswerCondition}</td>
											<td class="text-center">												
												<c:url var="prevUrl" value="/admin/admin_qna_view.do">													
													<c:param name="qna_id" value="${item.id}" />
												</c:url>
												<a href="${prevUrl}">${item.subject}</a>
											</td>
											<c:if test="${item.name != null}">										
											<td class="text-center">${item.name}</td>
											</c:if>
											<c:if test="${item.name == null}">										
											<td class="text-center">탈퇴회원</td>
											</c:if>
											<td class="text-center">${item.regDate}</td>											
											<td class="text-center">${item.qnaAnswerDate}</td>
										</tr>
										<c:set var="maxPageNo" value="${maxPageNo-1}" />
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="9" class="text-center" style="line-height: 100px;">
										조회된 데이터가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
			<br />
			<!--//질문답변 table -->
			
			
			
			<div class="row box col-md-12 col-xs-12"
				style="padding: 0; margin: 0;">
					<form method="post" action="${pageContext.request.contextPath}/admin/admin_qna_delete_check.do" id="qna_list">
						<div class="col-md-12 col-xs-12">
							<button type="submit" onclick="return confirm('정말 삭제하시겠습니까?')" class="btn btn-danger">삭제하기</button>
						</div>
					</form>
				<div class="col-md-12 col-xs-12" style="padding: 0; margin: 0;">
					<!-- 페이지네이션 -->
					<nav class="text-center">
						<ul class="pagination">
							<!-- 이전 그룹 -->
							<c:choose>
								<c:when test="${pageHelper.prevPage > 0}">
									<!-- 이전 그룹에 대한 페이지 번호가 존재한다면? -->
									<!-- 이전 그룹으로 이동하기 위한 URL을 생성해서 prevUrl에 저장 -->
									<c:url var="prevUrl" value="/admin/admin_qna.do">
										<c:param name="keyword" value="${keyword}"></c:param>
										<c:param name="category" value="qna"></c:param>
										<c:param name="page" value="${pageHelper.prevPage}"></c:param>
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
							<c:forEach var="i" begin="${pageHelper.startPage}"
								end="${pageHelper.endPage}" step="1">
								<!-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 page_url에 저장 -->
								<c:url var="pageUrl" value="/admin/admin_qna.do">
									<c:param name="keyword" value="${keyword}"></c:param>
									<c:param name="category" value="qna"></c:param>
									<c:param name="page" value="${i}"></c:param>
								</c:url>

								<!-- 반복중의 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 -->
								<c:choose>
									<c:when test="${pageHelper.page == i}">
										<li class="active"><a href="#">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageUrl}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<!-- 다음 그룹 -->
							<c:choose>
								<c:when test="${pageHelper.nextPage > 0}">
									<!-- 다음 그룹에 대한 페이지 번호가 존재한다면? -->
									<!-- 다음 그룹으로 이동하기 위한 URL을 생성해서 nextUrl에 저장 -->
									<c:url var="nextUrl" value="/admin/admin_qna.do">
										<c:param name="keyword" value="${keyword}"></c:param>
										<c:param name="category" value="qna"></c:param>
										<c:param name="page" value="${pageHelper.nextPage}"></c:param>
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
					<!--// 페이지네이션 끝 -->
				</div>
			</div>

		</div>

	<!-- 페이지 중단 -->

	<!-- 페이지 하단(내용 없음) -->
	<!--//페이지 하단 -->
	<script>
	/* 체크된 글삭제 */
	 $(function() {
		 $("#qna_list").submit(function(e) {
               e.preventDefault();
		// 선택된 항목을 가져온다 --> 선택된 체크박스의 배열
       var check_list = $(".qna_check:checked");
   	 // 배열의 길이가 0이라면 선택된 항목이 없다는 의미이므로 중단
       if (check_list.length == 0) {
           alert("선택된 항목이 없습니다.");
           return false;
       }
   	 var chk_qna = [];
    // 배열의 길이만큼 반복한다.
        for (var i=0; i<check_list.length; i++) {
           // jQuery객체에 push로 배열에 체크박스 값을 추가한다.
          chk_qna.push($(check_list[i]).val());   
      		}  
      		
       console.log("checked="+chk_qna);
       $.ajax({
			method: 'post',
			url: '${pageContext.request.contextPath}/admin/admin_qna_delete_check.do', 
			traditional: true,
			data: {chkArr: chk_qna}, 
			
			 success: function(req) {
		          console.log('return string : ');
		          location.href="${pageContext.request.contextPath}/admin/admin_qna.do" 
		          alert("질문글 삭제를 완료했습니다.")
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
			
			/* 체크박스 전체선택  */
			$("#qna_check_all").change(function() {
				$(".qna_check").prop('checked', $(this).prop('checked'));
			});
		</script>
	</div>
</body>
</html>